<template>
  <h1 class="display-6 ms-3 mt-2">{{ detailInfo.title }} 상세화면</h1>
  <div class="card mt-3 ms-3 me-3">
    <div class="card-body">
      <div v-if="!isEditing">
        제목: {{ detailInfo.title }} <br />
        설명: {{ detailInfo.description }} <br />
        시작일: {{ detailInfo.period?.startDate }} <br />
        종료일: {{ detailInfo.period?.endDate }} <br />
      </div>

      <div v-else>
        <input type="text" v-model="editedTitle" /> <br />
        <input type="text" v-model="editedDescription" /> <br />
        <input type="date" v-model="period.editedStartDate" /> <br />
        <input type="date" v-model="period.editedEndDate" /> <br />
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
    <button @click="removeDocument(id)" v-if="!isEditing" class="btn btn-danger mt-3 ms-3 me-3 mb-2">
      삭제
    </button>
  </div>
  <button @click="toggleAdd" type="button" class="btn btn-outline-primary mt-3 ms-3">
    Todo 추가
  </button>
  <button type="button" class="btn btn-outline-secondary mt-3 ms-2">
    <router-link :to="{ name: 'todo', params: { id: detailInfo.documentId } }" class="router-link">Todo 조회</router-link>
  </button>

  <button type="button" class="btn btn-outline-success mt-3 ms-2">
    <router-link :to="{ name: 'home' }" class="router-link">home </router-link>
  </button>

  <hr />
  <div v-if="isAdd" class="ms-3 mb-2">
    <label>제목</label>
    <input type="text" v-model="todo" class="form-control" style="max-width: 300px" />
    <br />
    <label>설명</label>
    <input type="text" v-model="description" class="form-control" style="max-width: 300px" />
    <br />
    <label>시작일</label>
    <input type="date" v-model="period.startDate" class="form-control" style="max-width: 300px" />
    <br />
    <label>종료일</label>
    <input type="date" v-model="period.endDate" class="form-control" style="max-width: 300px" />
    <br /><br />
    <button @click="addTodo" class="btn btn-primary">추가</button> <br />
  </div>
  <div v-else></div>
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
      editedTitle: "",
      editedDescription: "",
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
          console.log(this);
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
      const editedTitle = this.editedTitle.trim();
      const editedDescription = this.editedDescription.trim();
      const editedStartDate = this.period.editedStartDate;
      const editedEndDate = this.period.editedEndDate;
      // 제목과 설명 null값 체크
      if (!editedTitle || !editedDescription) {
        console.log(editedDescription);
        alert("제목과 설명을 입력해주세요.");
        return;
      }
      // 제목과 설명 글자수 제한
      if (editedTitle.length > 50 || editedDescription.length > 200) {
        alert("제목은 50글자 이내, 설명은 200글자 이내로 입력해주세요.");
        return;
      }
      // 시작일과 종료일 null값 체크
      if (!editedStartDate || !editedEndDate) {
        alert("시작일과 종료일을 입력해주세요.");
        return;
      }
      if (editedStartDate > editedEndDate) {
        alert("시작일이 종료일보다 클 수 없습니다.");
        return;
      }

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
      if (this.detailInfo.isTodoEmpty === false) {
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
      } else {
        alert("Todo 항목을 먼저 삭제해주세요.");
      }
    },
    addTodo() {
      if (!this.todo.trim() || !this.description.trim()) {
        alert("제목과 설명을 입력해주세요.");
        return;
      }
      if (this.todo.length > 50 || this.description.length > 200) {
        alert("제목은 50글자 이내, 설명은 200글자 이내로 입력해주세요.");
      }
      if (!this.period.startDate || !this.period.endDate) {
        alert("시작일과 종료일을 모두 입력해주세요.");
        return;
      }
      if (this.period.startDate > this.period.endDate) {
        alert("시작일이 종료일보다 클 수 없습니다.");
        return;
      }
      if (this.period.startDate < this.detailInfo.period.startDate) {
        alert("해당 Document의 시작일과 종료일을 확인하세요.");
        return;
      }
      if (this.period.endDate > this.detailInfo.period.endDate) {
        alert("해당 Document의 시작일과 종료일을 확인하세요.");
        return;
      }
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
