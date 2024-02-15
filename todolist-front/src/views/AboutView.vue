<template>
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

  <button @click="toggleEditMode" v-if="!isEditing" class="btn btn-primary">
    수정
  </button>
  &nbsp;
  <button @click="saveChanges" v-if="isEditing" class="btn btn-primary">
    저장
  </button>
  &nbsp;
  <button @click="removeDocument(id)" class="btn btn-danger">삭제</button>
  <br /><br /><br />
  <router-link :to="{ name: 'todo', params: { id: detailInfo.documentId } }"
    >Todo 조회</router-link
  >
  <br />
  <router-link :to="{ name: 'home' }">home </router-link>

  <hr>
  <label>제목</label>
  <input type="text" v-model="todo" /> <br>
  <label>설명</label>
  <input type="text" v-model="description" /> <br>
  <label>시작일</label>
  <input type="datetime-local" v-model="period.startDate" /> <br>
  <label>종료일</label>
  <input type="datetime-local" v-model="period.endDate" /> <br><br>
  <button @click="addTodo">추가</button> <br>
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
    const documentId = id;

    return {
      id,
      detailInfo,
      isEditing,
      history,
      period: {
        startDate: '',
        endDate: '',
      },
      todo: '',
      description: '',
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
          endDate: this.period.endDate
        },
        todo: this.todo,
        description: this.description,
        documentId: this.documentId,
        isChecked: true
      }
      axios.post('http://localhost:8090/api/todo', data, {
        headers: {
          'Content-Type' : 'application/json'
        }
      })
      .then(response => {
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

<style lang="scss" scoped></style>
