<template>
  <div class="row" v-if="todo">
    <div class="col-md-4" v-for="(item, index) in todo.content" :key="index">
      <div class="card mb-3 mt-3 ms-3 me-3" :class="{ 'is-checked': !item.isChecked }">
        <div class="card-body" v-if="!item.isTodoEditing">
          <strong>제목 : </strong>{{ item.todo }} <br />
          <strong>설명 : </strong>{{ item.description }} <br />
          <strong>시작일 : </strong>{{ item.period.startDate }} <br />
          <strong>종료일 : </strong>{{ item.period.endDate }} <br />
          <div v-if="item.fileResponses && item.fileResponses.length">
            <strong>첨부파일 :</strong>
            <ul>
              <li v-for="(fileResponse, fileIndex) in item.fileResponses" :key="fileIndex">
                {{ fileResponse.fileName }}
                <button @click="getFileDownload(index, fileIndex)" class="btn btn-primary">
                  ↓
                </button>
                <button @click="removeFile(index, fileIndex)" class="btn btn-danger ms-1">
                  삭제
                </button>
              </li>
            </ul>
          </div>

          <input type="file" @change="handleFileUpload(index, $event)" class="mt-2" />
          <br />
          <button @click="finishTodo(index)" class="btn btn-primary mt-3 me-1">
            완료
          </button>
          <button @click="toggleTodoEditMode(index)" class="btn btn-primary mt-3">
            수정
          </button>
          <button @click="removeTodo(index)" class="btn btn-danger mt-3 ms-1">
            삭제
          </button>
        </div>
        <div v-else>
          <input type="text" v-model="item.todo" /> <br />
          <input type="text" v-model="item.description" /> <br />
          <input type="date" v-model="item.period.startDate" /> <br />
          <input type="date" v-model="item.period.endDate" /> <br />
          <button @click="saveTodoEdit(index)" class="btn btn-success">
            저장
          </button>
          <button @click="cancelTodoEdit(index)" class="btn btn-danger">
            취소
          </button>
        </div>
      </div>
    </div>
    <div>
      <button @click="showTodoPopup" class="btn btn-danger mt-2 ms-3">
        전체 삭제
      </button>
      <br />
      <router-link :to="{ name: 'home' }" class="router-link ms-2">home</router-link>
    </div>
    <FormModal v-if="showTodoModal" @close="showTodoModal = false">
      <template #header>
        <p>※주의</p>
      </template>
      <template #body>
        <p>Document와 하위의 항목 모두 삭제하시겠습니까?</p>
        <button @click="removeTodoAll" class="btn btn-primary">확인</button>
        <button @click="showTodoPopup" class="btn btn-danger ms-1">취소</button>
      </template>
    </FormModal>
  </div>
</template>

<script>
import FormModal from "../components/common/FormModal.vue";
import axios from "axios";
import { ref } from "vue";
import { useRoute } from "vue-router";

export default {
  setup() {
    const route = useRoute();
    const { id } = route.params;
    const todo = ref({});
    const filetest = ref({});
    const showTodoModal = ref(false);
    return { todo, id, filetest, showTodoModal };
  },
  mounted() {
    this.getTodo();
    // this.getFile();
  },
  methods: {
    getTodo() {
      const token = window.localStorage.getItem("token");
      axios.get(`http://localhost:8090/api/todos/${this.id}`, {
        headers: {
          "Content-Type": "application/json",
          "Authorization": token,
        }
      }).then((res) => {
        this.todo = res.data; // 객체로 저장
        console.log(this.todo.content);
      });
    },
    showTodoPopup() {
      this.showTodoModal = !this.showTodoModal;
      console.log(this.showTodoModal);
    },
    handleFileUpload(index, event) {
      const test = this.todo.content[index];
      const file = event.target.files[0];
      const formData = new FormData();
      const token = window.localStorage.getItem("token");
      formData.append("multipartFile", file);

      axios
        .post(`http://localhost:8090/api/file/${test.todoId}`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            "Authorization": token,
          },
        })
        .then((response) => {
          console.log("파일 업로드 성공:", response.data);
          console.log("아이디", test.todoId);
        })
        .catch((error) => {
          console.error("파일 업로드 오류:", error);
          console.log("아이디", test.todoId);
        });
    },

    toggleTodoEditMode(index) {
      this.todo.content[index].isTodoEditing = true;
    },
    saveTodoEdit(index) {
      const editedTodo = this.todo.content[index];
      const token = window.localStorage.getItem("token");

      axios
        .put(`http://localhost:8090/api/todo/${editedTodo.todoId}`, editedTodo, {
          headers: {
            "Content-Type": "application/json",
            "Authorization": token,
          },
        })
        .then(() => {
          this.todo.content[index].isTodoEditing = false;
        })
        .catch((error) => {
          console.error("저장 중 오류 발생:", error);
        });
    },
    cancelTodoEdit(index) {
      this.todo.content[index].isTodoEditing = false;
      this.$router.go(this.$router.currentRoute);
    },
    removeTodo(index) {
      const todoToRemove = this.todo.content[index];
      const token = window.localStorage.getItem("token");
      axios
        .delete(`http://localhost:8090/api/todo`, {
          headers: {
            "Content-Type": "application/json",
            "Authorization": token,
          },
          params: { todoId: todoToRemove.todoId },
        })
        .then(() => {
          this.todo.content.splice(index, 1);
        })
        .catch((error) => {
          console.error("삭제 중 오류 발생:", error);
        });
    },
    removeTodoAll() {
      const token = window.localStorage.getItem("token");
      axios.delete(`http://localhost:8090/api/todo/all/${this.id}`, {
        headers: {
          "Content-Type": "application/json",
          "Authorization": token,
        },
      }).then(() => {
        console.log(this.id);
      });
    },
    finishTodo(index) {
      const todoToFinish = this.todo.content[index];
      const token = window.localStorage.getItem("token");
      todoToFinish.isChecked = !todoToFinish.isChecked;
      axios
        .put(
          `http://localhost:8090/api/todo/${todoToFinish.todoId}`,
          todoToFinish, {
            headers: {
              "Content-Type": "application/json",
              "Authorization": token,
            }
          }
        )
        .then(() => {
          console.log("완료");
        })
        .catch((error) => {
          console.error("완료 처리 중 오류 발생:", error);
        });
    },
    getFileDownload(todoIndex, fileIndex) {
      const todoToDownload = this.todo.content[todoIndex];
      const fileToDownload = todoToDownload.fileResponses[fileIndex];
      const fileId = fileToDownload.fileId;
      const fileName = fileToDownload.fileName;
      const token = window.localStorage.getItem("token");

      axios({
        url: `http://localhost:8090/api/file/download/${fileId}`,
        headers: {
          "Content-Type": "application/json",
          "Authorization": token,
        },
        method: "GET",
        responseType: "blob",
      })
        .then((response) => {
          const url = window.URL.createObjectURL(new Blob([response.data]));
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", fileName);
          document.body.appendChild(link);
          link.click();

          window.URL.revokeObjectURL(url);
        })
        .catch((error) => {
          console.log(fileId);
          console.error("파일 다운로드 중 오류 발생:", error);
        });
    },
    removeFile(todoIndex, fileIndex) {
      const todoToRemoveFile = this.todo.content[todoIndex];
      const fileToRemove = todoToRemoveFile.fileResponses[fileIndex];
      const fileId = fileToRemove.fileId;
      const token = window.localStorage.getItem("token");

      axios
        .delete(`http://localhost:8090/api/file/download/${fileId}`, {
          headers: {
            "Content-Type": "application/json",
            "Authorization": token,
          },
        })
        .then(() => {
          this.todo.content[todoIndex].fileResponses.splice(fileIndex, 1);
          console.log("파일 삭제 성공");
        })
        .catch((error) => {
          console.error("파일 삭제 중 오류 발생:", error);
        });
    },
  },
  components: {
    FormModal: FormModal,
  },
};
</script>

<style>
.is-checked {
  text-decoration: line-through;
}
</style>
