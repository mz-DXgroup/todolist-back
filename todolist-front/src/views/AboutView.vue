<template>
  <div>
    <p>AboutView 페이지 입니다.</p>
    {{ detailInfo.title }} <br>
    {{ detailInfo.description }} <br>
  </div>
</template>

<script>
import { useRoute } from 'vue-router'
import axios from 'axios';
import { ref } from 'vue';

export default {
  data () {
      const route = useRoute()
      const { id } = route.params
      const detailInfo = ref({})
    return { id ,detailInfo }

  },
  methods:{
    getDocumentDetail() {
      axios.get(`http://localhost:8090/api/documents/${this.id}`).then(res => {
        this.detailInfo = res.data
        
        
      })
    },
  },
  mounted() {
    this.getDocumentDetail();
  },
  

}

</script>

<style lang="scss" scoped>

</style>
