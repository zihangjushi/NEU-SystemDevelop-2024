<template>
  <div class="body">
    <el-form :model="loginForm" :rules="rules" ref="loginFormRef" class="loginContainer">
      <h2 class="loginTitle">
        测盟汇管理系统
      </h2>

      <el-form-item prop="username">
        <el-input type="text" v-model="loginForm.username" placeholder="请输入用户名" />
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" />
      </el-form-item>
      <el-form-item prop="code">
        <el-input type="text" auto-complete="off" v-model="loginForm.code" placeholder="请输入验证码" style="width: 250px; margin-right: 5px" />
        <img :src="captchaUrl" class="captcha-img" @click="refreshCaptcha" />
      </el-form-item>
      <el-checkbox v-model="checked" class="loginRemember">记住我</el-checkbox>

      <el-button type="primary" style="width:100%" @click="submitLogin">登录</el-button>
      <div class="button-container">
        <el-button style="width:100%; margin-top: 10px;" @click="register">注册</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
// import { useStore } from 'vuex';
import { defineComponent } from 'vue'; 
// import { useStore } from 'vuex'
import axios from 'axios';

import { ElForm, ElFormItem, ElInput, ElButton, ElMessage } from 'element-plus';

export default defineComponent({
  components: {
    ElForm,
    ElFormItem,
    ElInput,
    ElButton
  },
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
        code: ''
      },
      checked: true,
      captchaUrl: require('@/assets/captcha.png'), // Initial captcha URL
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 14, message: '长度在 2 到 14 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码长度要大于6', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitLogin() {
      this.$refs.loginFormRef.validate(valid => {
        if (valid && this.loginForm.code === 'yhuv') {
          let fd = new FormData()
          fd.append('userName', this.loginForm.username);
          fd.append('password', this.loginForm.password);
          axios.post('http://localhost:8070/user/login', fd)
            .then(response => {
              if (response.data.isOk) {
                this.$store.dispatch('setUser', response.data.user);
                ElMessage({
                  showClose: true,
                  message: '登录成功！',
                  type: 'success'
                });
                console.log("登录成功的数据+", response.data.user.userName, response.data.user.role)
                this.$router.push({ path: '/homepage'});
              } else {
                ElMessage.error('登录出错请重新输入');
              }
            })
        }
      });
    },
    register() {
      this.$router.push('/meeting');
    },
    refreshCaptcha() {
      // Replace with actual logic to refresh captcha
      this.captchaUrl = require('@/assets/captcha.png');
    }
  }
});
</script>

<style lang="less" scoped>
.loginContainer {
  border-radius: 15px;
  background-clip: padding-box;
  text-align: left;
  margin: auto;
  margin-top: 180px;
  width: 350px;
  padding: 15px 35px 15px 35px;
  background: aliceblue;
  border: 1px solid blue;
  box-shadow: 0 0 25px #000000;
}

.loginTitle {
  margin: 0 auto 20px auto;
  text-align: center;
  font-size: 40px;
}

.loginRemember {
  text-align: left;
  margin: 0 0 15px 0;
}

.body {
  width: 100vw;
  height: 100vh;
  background-image: url("../assets/background.png");
  background-size: 100%;
  overflow: hidden;
}

.captcha-img {
  width: 80px;
  height: 30px;
  cursor: pointer;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  margin: 0 0 0px 10px;
}

.button-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
