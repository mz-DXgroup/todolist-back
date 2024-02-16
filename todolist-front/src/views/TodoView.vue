<template>
  <div class="row" v-if="todo">
    <div class="col-md-4" v-for="(item, index) in todo.content" :key="index">
      <div class="card mb-3 mt-3 ms-3 me-3">
        <div class="card-body" v-if="!item.isTodoEditing">
          <strong>제목 : </strong>{{ item.todo }} <br>
          <strong>시작일 : </strong>{{ item.period.startDate }} <br>
          <strong>종료일 : </strong>{{ item.period.endDate }} <br><br>
          <button @click="toggleTodoEditMode(index)" class="btn btn-primary">
            수정
          </button>
          <button @click="removeTodo(index)" class="btn btn-danger ms-1">
            삭제
          </button>
        </div>
        <div v-else>
          <input type="text" v-model="item.todo" /> <br>
          <input type="datetime-local" v-model="item.period.startDate"> <br>
          <input type="datetime-local" v-model="item.period.endDate"> <br>
          <button @click="saveTodoEdit(index)" class="btn btn-success">
            저장
          </button>
          <button @click="cancelTodoEdit(index)" class="btn btn-danger">
            취소
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { ref } from "vue";
import { useRoute } from "vue-router";

export default {
  setup() {
    const route = useRoute();
    const { id } = route.params;
    const todo = ref({});
    return { todo, id };
  },
  mounted() {
    this.getTodo();
  },
  methods: {
    getTodo() {
      axios.get(`http://localhost:8090/api/todos/${this.id}`).then((res) => {
        this.todo = res.data; // 객체로 저장
      });
    },
    toggleTodoEditMode(index) {
      this.todo.content[index].isTodoEditing = true;
    },
    saveTodoEdit(index) {
      const editedTodo = this.todo.content[index];
      axios.put(`http://localhost:8090/api/todo/${editedTodo.todoId}`, editedTodo)
        .then(() => {
          this.todo.content[index].isTodoEditing = false;
        })
        .catch(error => {
          console.error('저장 중 오류 발생:', error);
        });
    },
    cancelTodoEdit(index) {
      this.todo.content[index].isTodoEditing = false;
    },
    removeTodo(index) {
  const todoToRemove = this.todo.content[index];
  axios.delete(`http://localhost:8090/api/todo`, {
    params: { todoId: todoToRemove.todoId }
  })
    .then(() => {
      this.todo.content.splice(index, 1);
    })
    .catch(error => {
      console.error('삭제 중 오류 발생:', error);
    });
},
  },
};
</script>

<style lang="scss" scoped></style>