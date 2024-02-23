<template>
  <div>
    <h1 class="display-6 ms-3 mt-2">Document List</h1>
    <button @click="showPopup" class="btn btn-primary mt-3 ms-3">추가</button>
    <button @click="todayTodo" class="btn btn-primary mt-3 ms-3">
      오늘의 할 일
    </button>
    <div v-for="(item, index) in todayData" :key="index">
      <div class="card mt-3 ms-3 me-3">
        <div card-body ms-3>{{ item.todo }}</div>
      </div>
    </div>
    <FormModal v-if="showModal" @close="showModal = false">
      <template #header>
        <h3>Document 추가</h3>
      </template>
      <template #body>
        <form action="" @submit.prevent="submitForm">
          <label for="d-title">제목</label>
          <input id="d-title" type="text" v-model="dTitle" class="form-control" />
          <br />
          <label for="d-description">내용</label>
          <input id="d-description" type="text" v-model="dDescription" class="form-control" />
          <hr />
          <label for="startDate">시작일: </label>
          <input type="date" class="form-control" placeholder="date input" v-model="period.startDate" />
          <br />
          <label for="endDate">종료일: </label>
          <input type="date" class="form-control" placeholder="date input" v-model="period.endDate" />
          <hr />
          <br />
          <button type="submit" class="btn btn-primary me-1">추가</button>
          <button @click="showPopup" class="btn btn-danger">취소</button>
        </form>
      </template>
    </FormModal>
    <div class="row" v-if="document">
      <div class="col-md-4" v-for="(doc, index) in document.content" :key="index">
        <div class="card mb-3 mt-3 ml-3 ms-3 me-3">
          <div class="card-body">
            <router-link :to="{ name: 'detail', params: { id: doc.documentId } }">
              <h5 class="card-title"><strong>■ </strong>{{ doc.title }}</h5>
            </router-link>
          </div>
        </div>
      </div>
    </div>
    <button @click="showCheckPopup" class="btn btn-danger ms-3">
      전체 삭제
    </button>
    <!-- <button @click="removeTodoAll" class="btn btn-danger me-3 ms-3">Todo 전체 삭제</button> -->
    <FormModal v-if="showCheckModal" @close="showCheckModal = false">
      <template #header>
        <p>※주의</p>
      </template>
      <template #body>
        <p>Document와 하위의 항목 모두 삭제하시겠습니까?</p>
        <button @click="removeAll" class="btn btn-primary me-1">확인</button>
        <button @click="showCheckPopup" class="btn btn-danger">취소</button>
      </template>
    </FormModal>
  </div>
</template>
<script>
import FormModal from "../components/common/FormModal.vue";
import axios from "axios";

export default {
  data() {
    return {
      period: {
        startDate: "",
        endDate: "",
      },
      dTitle: "",
      dDescription: "",
      memberId: "",
      document: null, // 객체로 초기화
      showModal: false,
      showCheckModal: false,
      todayData: null,
      userId: "",
    };
  },
  mounted() {
    this.getDocument();
  },
  methods: {
    // getDocument() {
    //   const token = window.localStorage.getItem("token");
    //   const userId = window.localStorage.getItem("userId");
    //   console.log(token);
    //   console.log(userId);
    //   axios.get(`http://localhost:8090/api/documents/documents/${userId}`, {
    //     headers: {
    //       "Content-Type": "application/json",
    //       "Authorization": token,
    //     },
    //   }).then((res) => {
    //     this.document = res.data; // 객체로 저장
    //     console.log(this.document.content);
    //   });
    // },
    getDocument() {
  const token = window.localStorage.getItem("token");
  const userId = window.localStorage.getItem("userId");
  console.log(token);
  console.log(userId);

  // 쿼리 매개변수 구성
  const queryParams = new URLSearchParams({
    page: 0,
    size: 5,
    sort: 'string'
  });

  axios.get(`http://localhost:8090/api/documents/documents/${userId}?${queryParams.toString()}`, {
    headers: {
      "Content-Type": "application/json",
      "Authorization": token,
    },
  }).then((res) => {
    this.document = res.data; // 객체로 저장
    console.log(this.document.content);
  });
},

    showPopup() {
      this.showModal = !this.showModal;
    },
    showCheckPopup() {
      this.showCheckModal = !this.showCheckModal;
    },
    submitForm() {
      if (!this.dTitle.trim() || !this.dDescription.trim()) {
        alert("제목과 설명을 입력해주세요.");
        return;
      }
      if (this.dTitle.length > 50 || this.dDescription.length > 200) {
        alert("제목은 50글자 이내, 설명은 200글자 이내로 입력해주세요.");
        return;
      }
      if (!this.period.startDate || !this.period.endDate) {
        alert("시작일과 종료일을 모두 입력해주세요.");
        return;
      }
      if (this.period.startDate > this.period.endDate) {
        alert("시작일이 종료일보다 클 수 없습니다.");
        return;
      }
      // 제목 중복값 체크
      const isDuplicateTitle = this.document.content.some(
        (doc) => doc.title === this.dTitle
      );
      if (isDuplicateTitle) {
        alert("동일한 제목이 이미 존재합니다. 다른 제목을 입력해주세요.");
        return;
      }
      const dUserId = window.localStorage.getItem("userId");
      const data = {
        period: {
          startDate: this.period.startDate,
          endDate: this.period.endDate,
        },
        title: this.dTitle,
        description: this.dDescription,
        userId: dUserId,
      };
      const token = window.localStorage.getItem("token");
      axios
        .post("http://localhost:8090/api/documents", data, { 
          headers: {
            "Content-Type": "application/json",
            "Authorization": token,
          },
        })
        .then((response) => { 
          console.log(response);
          console.log("Post 성공");
        });

      this.showModal = !this.showModal;
    },
    removeAll() {
      const token = window.localStorage.getItem("token");
      axios.delete("http://localhost:8090/api/documents/all", {
        headers: {
          "Content-Type": "application/json",
          "Authorization": token,
        }
      }).then(() => {
        this.showCheckModal = !this.showCheckModal;
        this.$router.go(this.$router.currentRoute);
      });
    },
    todayTodo() {
      const token = window.localStorage.getItem("token");
      axios.get("http://localhost:8090/api/todo/today/4", {
        headers: {
          "Content-Type": "application/json",
          "Authorization": token,
        }
      }).then((res) => {
        this.todayData = res.data;
        console.log("확인", this.todayData);
      });
    },
  },

  components: {
    FormModal: FormModal,
  },
};
</script>

<style scoped></style>
