<template>
  <h1 class="display-6 ms-3 mt-2">{{ detailInfo.title }} 상세화면</h1>
<div class="card mt-3 ms-3 me-3">
  <div class="card-body">
<div v-if="!isEditing">
    {{ detailInfo.title }} <br />
    {{ detailInfo.description }} <br />
    {{ detailInfo.period?.startDate }} <br />
    {{ detailInfo.period?.endDate }}
  </div>

  <div v-else>
    <input type="text" v-model="editedTitle" /> <br />
    <input type="text" v-model="editedDescription" /> <br />
    <input type="datetime-local" v-model="period.editedStartDate" /> <br />
    <input type="datetime-local" v-model="period.editedEndDate" /> <br />
  </div>
  </div>

  

  <button @click="toggleEditMode" v-if="!isEditing" class="btn btn-primary mt-3 ms-3 me-3">
    수정
  </button>
  <button @click="saveChanges" v-if="isEditing" class="btn btn-primary mt-3 ms-3 me-3">
    저장
  </button>
  <button @click="cancelChanges" v-if="isEditing" class="btn btn-danger mt-3 ms-3 me-3">
    취소
  </button>
  <button @click="removeDocument(id)" v-if="!isEditing" class="btn btn-danger mt-3 ms-3 me-3 mb-2">삭제</button>
  </div>
  <button @click="toggleAdd" type="button" class="btn btn-outline-primary mt-3 ms-3">Todo 추가</button>
  <button type="button" class="btn btn-outline-secondary mt-3 ms-2"><router-link :to="{ name: 'todo', params: { id: detailInfo.documentId } }" class="router-link">Todo 조회</router-link></button>
  
  <button type="button" class="btn btn-outline-success mt-3 ms-2"><router-link :to="{ name: 'home' }" class="router-link">home </router-link></button>

  <hr />
  <div v-if="isAdd">
  <label>제목</label>
  <input type="text" v-model="todo" /> <br />
  <label>설명</label>
  <input type="text" v-model="description" /> <br />
  <label>시작일</label>
  <input type="datetime-local" v-model="period.startDate" /> <br />
  <label>종료일</label>
  <input type="datetime-local" v-model="period.endDate" /> <br /><br />
  <button @click="addTodo">추가</button> <br />
  </div>
  <div v-else>
  </div>
</template>

<script>
import { useRoute } from "vue-router";
import axios from "axios";
import { ref } from "vue";

export default {
  data() {
    const route = useRoute();
    const { id } = route.params;
    const detailInfo = ref({});
    const isEditing = ref(false);
    const isAdd = ref(false);
    const documentId = id;

    return {
      id,
      detailInfo,
      isEditing,
      isAdd,
      history,
      period: {
        startDate: "",
        endDate: "",
      },
      todo: "",
      description: "",
      documentId,
    };
  },
  methods: {
    getDocumentDetail() {
      axios
        .get(`http://localhost:8090/api/documents/${this.id}`)
        .then((res) => {
          this.detailInfo = res.data;
          console.log(this.id);
          // console.log(this.detailInfo);
        });
    },
    toggleEditMode() {
      this.isEditing = !this.isEditing;
      console.log(this.detailInfo.period.startDate);
      if (this.isEditing) {
        this.editedTitle = this.detailInfo.title;
        this.editedDescription = this.detailInfo.description;
        this.period = {}; //객체를 먼저 선언해주고 그 다음에 값을 입력해야 함.

        this.period.editedStartDate = this.detailInfo?.period?.startDate;
        this.period.editedEndDate = this.detailInfo?.period?.endDate;
      }
      console.log(this.period);
    },
    toggleAdd() {
      this.isAdd = !this.isAdd;
    },
    saveChanges() {
      const updatedData = {
        period: {
          startDate: this.period.editedStartDate,
          endDate: this.period.editedEndDate,
        },
        title: this.editedTitle,
        description: this.editedDescription,
        memberId: "1",
      };

      axios
        .put(`http://localhost:8090/api/documents/${this.id}`, updatedData)
        .then(() => {
          this.isEditing = false;
          this.getDocumentDetail();
        })
        .catch((error) => {
          console.error("Error updating document:", error);
        });
    },
    cancelChanges() {
      this.isEditing = !this.isEditing;
    },
    removeDocument(documentId) {
      axios
        .delete("http://localhost:8090/api/documents", {
          params: { documentId },
        })
        .then(() => {
          console.log("Document successfully deleted.");
        })
        .catch((error) => {
          console.error("Error deleting document:", error);
        });
    },
    addTodo() {
      console.log("확인");
      const data = {
        period: {
          startDate: this.period.startDate,
          endDate: this.period.endDate,
        },
        todo: this.todo,
        description: this.description,
        documentId: this.documentId,
        isChecked: true,
      };
      axios
        .post("http://localhost:8090/api/todo", data, {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          console.log(response, this.period.startDate, this.period.endDate);
        });
    },
  },
  mounted() {
    this.getDocumentDetail();
    // this.getTodo();
  },
};
</script>

<style scoped>
.router-link {
  text-decoration: none;
  color: black;
}
</style>
