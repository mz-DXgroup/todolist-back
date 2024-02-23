<template>
  <div>
    <div>
      <h2>회원가입 페이지</h2>
      <div id="loginForm">
        <form>
          <p>
            <input class="w3-input" placeholder="ID를 입력해주세요." v-model="join_id"><br>
          </p>
          <p>
            <input class="w3-input" placeholder="비밀번호를 입력해주세요." v-model="join_pw" type="password">
          </p>
          <p>
            <input class="w3-input" placeholder="이름을 입력해주세요." v-model="join_name"><br>
          </p>
          <p>
            <input class="w3-input" placeholder="이메일을 입력해주세요." v-model="join_email"><br>
          </p>
          <p>
            <input class="w3-input" placeholder="역할을 입력해주세요." v-model="join_role"><br>
          </p>
          <p>
            <button @click="join" class="w3-button w3-green w3-round">회원가입</button>
          </p>
        </form>
      </div>
    </div>
    <div>
      <h2>로그인 페이지</h2>
      {{ token }}
      <div id="loginForm">
        <form @submit.prevent="fnLogin">
          <p>
            <input class="w3-input" name="uid" placeholder="ID를 입력해주세요." v-model="user_id"><br>
          </p>
          <p>
            <input name="password" class="w3-input" placeholder="비밀번호를 입력해주세요." v-model="user_pw" type="password">
          </p>
          <p>
            <button type="submit" class="w3-button w3-green w3-round">Login</button>
          </p>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      user_id: '',
      user_pw: '',
      join_id: '',
      join_pw: '',
      join_name: '',
      join_email: '',
      join_role: '',
      token: '',
    }
  },
  methods: {
    fnLogin() {
      if (this.user_id === '') {
        alert('ID를 입력하세요.');
        return;
      }

      if (this.user_pw === '') {
        alert('비밀번호를 입력하세요.');
        return;
      }
      const data = {
        userId: this.user_id,
        pw: this.user_pw,
      };

      axios.post('http://localhost:8090/api/login', data, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": this.token,
          },
      })
      .then(response => {
        this.token = response.data.jwt;
        alert('로그인 되었습니다.');
        window.localStorage.setItem("token", this.token);
        this.$router.push('/home')
      })
      .catch(error => {
        alert('로그인에 실패했습니다.');
        console.error('로그인 실패:', error);
      });
    },
    join() {
      if (this.join_name === '') {
        alert("이름을 입력하세요.");
        return;
      }
      if (this.join_email === '') {
        alert("이메일을 입력하세요.");
        return;
      }
      if (this.join_id === '') {
        alert("id를 입력하세요.");
        return;
      }
      if (this.join_pw === '') {
        alert("pw를 입력하세요.");
        return;
      }
      if (this.join_role === '') {
        alert("역할을 입력하세요.");
        return;
      }

      axios.post('http://localhost:8090/api/join', {
        name: this.join_name,
        email: this.join_email,
        userId: this.join_id,
        pw: this.join_pw,
        role: this.join_role,
      })
      .then(response => {
        console.log(response);
        alert('회원가입 되었습니다.');
      })
      .catch(error => {
        alert('회원가입에 실패했습니다.');
        console.error('회원가입 실패:', error);
      });
    },
  }
}
</script>

<style>
#loginForm {
  width: 500px;
  margin: auto;
}
</style>
