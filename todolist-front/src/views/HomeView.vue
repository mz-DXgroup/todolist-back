<template>
  <div>
    <h2>Document List</h2>
    <button @click="showPopup" class="btn btn-primary">추가</button>

    <FormModal v-if="showModal" @close="showModal = false">
      <template #header>
        <h3>Document 추가</h3>
      </template>
      <template #body>
        <form action="" @submit.prevent="submitForm">
          <label for="d-title">제목</label>
          <input id="d-title" type="text" v-model="dTitle" class="form-control"> <br>
          <label for="d-description">내용</label>
          <input id="d-description" type="text" v-model="dDescription" class="form-control">
          <hr>
          <label for="startDate">시작일: </label>
          <input id="startDate" type="datetime-local" v-model="period.startDate"> <br>
          <label for="endDate">종료일: </label>
          <input id="endDate" type="datetime-local" v-model="period.endDate"> <hr> <br>
          <p><button type="submit" class="btn btn-primary">추가</button></p>
          <button @click="showPopup" class="btn btn-danger">취소</button>
        </form>
      </template>
    </FormModal>
    <div class="row" v-if="document">
      <div class="col-md-4" v-for="(doc, index) in document.content" :key="index">
        <div class="card mb-3 mt-3 ml-3">
          <div class="card-body">
            <router-link :to="{ name: 'detail', params: { id:  doc.documentId }}">
              <h5 class="card-title"><strong>제목 : </strong>{{ doc.title }}</h5>
            </router-link>
            <p class="card-text"><strong>내용 : </strong>{{ doc.description }}</p>
            <p class="card-text"><strong>시작일 : </strong>{{ doc.period.startDate }}</p>
            <p class="card-text"><strong>종료일 : </strong>{{ doc.period.endDate }}</p>
          </div>
        </div>
      </div>
    </div>
    <button class="btn btn-danger">모두 삭제</button>
  </div>
</template>

<script>
import FormModal from '../components/common/FormModal.vue'
import axios from 'axios';

export default {
  data() {
    return {
      period: {
        startDate: '',
        endDate: '',
      },
      dTitle: '',
      dDescription: '',
      document: null, // 객체로 초기화
      showModal: false
    };
  },
  mounted() {
    this.getDocument();
  },
  methods: {
    getDocument() {
      axios.get('http://localhost:8090/api/documents').then(res => {
        this.document = res.data; // 객체로 저장
        console.log(this.document.content);
      })
    },
    showPopup() {
      this.showModal = !this.showModal;
    },
    submitForm() {
      const data = {
        period: {
          startDate: this.period.startDate,
          endDate: this.period.endDate
        },
        title: this.dTitle,
        description: this.dDescription,
        memberId: '1',
      }
      // 시작일, 종료일 날짜 형식 확인
      axios.post('http://localhost:8090/api/documents', data, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then(response => {
        console.log(response, this.period.startDate, this.period.endDate);
      });

      // window.location.reload();
      this.showModal = !this.showModal;
      this.$router.go(this.$router.currentRoute);
    }
  },
  components: {
    'FormModal': FormModal
  },
};
</script>

<style lang="scss" scoped>

</style>
