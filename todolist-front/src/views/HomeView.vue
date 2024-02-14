<template>
  <div>
    <h2>Document List</h2>
    <button @click="showPopup" class="btn btn-primary">추가</button>


    <!-- <div class="card" style="width: 18rem;">
  <div class="card-body">
    <h5 class="card-title">Card title</h5>
    <h6 class="card-subtitle mb-2 text-body-secondary">Card subtitle</h6>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
  </div>
</div> -->

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
          <button type="submit" class="btn btn-primary">추가</button>
          <button @click="showPopup" class="btn btn-danger">취소</button>
        </form>
      </template>
    </FormModal>
    <div v-if="document">
      <ul>
        <li v-for="(doc, index) in document.content" :key="index">
          <router-link :to="{ name: 'detail', params: { id:  doc.documentId }}">
            <strong>제목 : </strong>{{ doc.title }} <br>
          </router-link>
          <strong>내용 : </strong>{{ doc.description }} <br>
          <strong>시작일 : </strong>{{ doc.period.startDate }} <br>
          <strong>종료일 : </strong>{{ doc.period.endDate }} <br><br>
        </li>
      </ul>
    </div>
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

<!-- <script>
import FormModal from '../components/common/FormModal.vue'
import axios from 'axios';

export default {
  data() {
    return {
      period: {
        startDate: null, // Date 객체로 초기화
        endDate: null, // Date 객체로 초기화
      },
      dTitle: '',
      dDescription: '',
      document: null,
      showModal: false
    };
  },
  mounted() {
    this.getDocument();
  },
  methods: {
    getDocument() {
      axios.get('http://localhost:8090/api/documents').then(res => {
        this.document = res.data;
      })
    },
    showPopup() {
      this.showModal = !this.showModal;
    },
    submitForm() {
      // Date 객체를 ISO 8601 형식의 문자열로 변환
      const data = {
        period: {
          startDate: this.period.startDate.toISOString(),
          endDate: this.period.endDate.toISOString()
        },
        title: this.dTitle,
        description: this.dDescription,
        memberId: '1',
      }
      axios.post('http://localhost:8090/api/documents', data, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then(response => {
        console.log(response);
        // 성공적으로 서버로 전송되었을 때의 처리
      });
    }
  },
  components: {
    'FormModal': FormModal
  },
};
</script> -->

<style lang="scss" scoped>

</style>
