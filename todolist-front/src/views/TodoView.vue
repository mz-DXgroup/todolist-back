<template>
  <div v-if="todo">
      <ul>
        <li v-for="(todo, index) in todo.content" :key="index">
          <strong>제목 : </strong>{{ todo.todo }} <br>
          <!-- <strong>내용 : </strong>{{ todo.description }} <br> -->
          <strong>시작일 : </strong>{{ todo.period.startDate }} <br>
          <strong>종료일 : </strong>{{ todo.period.endDate }} <br><br>
        </li>
      </ul>
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
        console.log(this.todo.content);
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>
