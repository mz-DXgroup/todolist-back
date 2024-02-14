<template>
  <div v-if="!isEditing">
    <p>AboutView 페이지 입니다.</p>
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

  <button @click="toggleEditMode" v-if="!isEditing">수정</button>
  <button @click="saveChanges" v-if="isEditing">저장</button>
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
    return { id, detailInfo, isEditing };
  },
  methods: {
    getDocumentDetail() {
      axios
        .get(`http://localhost:8090/api/documents/${this.id}`)
        .then((res) => {
          this.detailInfo = res.data;
          console.log(this.detailInfo);
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
  },
  mounted() {
    this.getDocumentDetail();
  },
};
</script>

<style lang="scss" scoped></style>
