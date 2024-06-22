<template>
  <div class="register-background">
    <div class="register-container" style="margin-top: 40px">
      <h3 style="font-size: 30px;">用户注册</h3>
      <!-- 注册表单 -->

      <el-form :model="registerForm" :rules="rules" ref="registerFormRef" class="register-form">
        <el-form-item label="企业名称" prop="userName">
          <el-input v-model="registerForm.userName" placeholder="请输入企业名称"></el-input>
        </el-form-item>

        <el-form-item label="联系方式" prop="phoneNumber">
          <el-input v-model="registerForm.phoneNumber" placeholder="请输入企业联系方式"></el-input>
        </el-form-item>

        <el-form-item label="设置密码" prop="password">
          <el-input type="password" v-model="registerForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input type="password" v-model="registerForm.confirmPassword" placeholder="请确认密码"></el-input>
        </el-form-item>

        <el-form-item label="验证码" prop="code">
          <div class="code-input-container">
            <el-input v-model="registerForm.code" placeholder="请输入验证码" class="captcha-input"></el-input>
            <img :src="captchaUrl" class="captcha-img" @click="refreshCaptcha" />
          </div>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm" style="margin-top: 20px; margin-left: 110px;">注册</el-button>
          <el-button @click="back" style="margin-top: 20px;">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue';
import { ElForm, ElFormItem, ElInput, ElButton, ElMessage } from 'element-plus';
import axios from 'axios'; // Import axios

export default defineComponent({
  components: {
    ElForm,
    ElFormItem,
    ElInput,
    ElButton
  },
  data() {
    return {
      registerForm: {
        userName: '',
        phoneNumber: '',
        password: '',
        confirmPassword: '',
        code: ''
      },
      rules: {
        userName: [
          { required: true, message: '请输入企业名称', trigger: 'blur' },
          { min: 5, max: 14, message: '长度在 5 到 14 个字符', trigger: 'blur' }
        ],
        phoneNumber: [
          { required: true, message: '请输入企业联系方式', trigger: 'blur' },
          { min: 5, max: 14, message: '长度在 5 到 14 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码长度要大于6', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          {
            validator: (rule, value) => {
              if (value !== this.registerForm.password) {
                return Promise.reject(new Error('两次输入的密码不一致'));
              } else {
                return Promise.resolve();
              }
            },
            trigger: 'blur'
          }
        ],
        code: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
      },
      captchaUrl: require('@/assets/captcha.png') // Initialize captcha image URL
    };
  },
  methods: {
    refreshCaptcha() {
      // Logic to refresh captcha image
      this.captchaUrl = require('@/assets/captcha.png'); // Replace with logic to fetch new captcha URL
    },
    submitForm() {
      if (this.$refs.registerFormRef) {
        this.$refs.registerFormRef.validate((valid) => {
          if (valid && this.registerForm.code === 'yhuv') {
            const postData = {
              userName: this.registerForm.userName,
              phoneNumber: this.registerForm.phoneNumber,
              password: this.registerForm.password
            };

            axios.post('http://localhost:8090/users/register', postData)
                .then(response => {
                  this.handleSuccess(response); // Pass response to handleSuccess function
                })
                .catch(error => {
                  this.handleError(error); // Pass error to handleError function
                });
          } else {
            this.handleError(); // Handle validation errors or incorrect captcha code
          }
        });
      } else {
        console.error('Form reference is undefined.');
      }
    },
    handleSuccess(response) {
      console.log('Registration successful:', response.data);
      this.$router.push('/login'); // Redirect to login page after successful registration
      ElMessage({
        showClose: true,
        message: '注册成功!',
        type: 'success'
      });
    },
    handleError(error) {
      console.error('Registration failed:', error);
      ElMessage.error('注册失败！请检查信息是否正确！');
    },
    back() {
      // Navigate back to login page
      this.$router.push('/login');
    }
  }
});
</script>


<style scoped>
.register-container {
  width: 350px;
  margin: auto;
  margin-top: 180px;
  padding: 15px 35px;
  background-color: aliceblue;
  border: 1px solid blue;
  border-radius: 15px;
  box-shadow: 0 0 25px #000000;
}

.code-input-container {
  display: flex;
  align-items: center;
}

.register-background {
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
  margin-left: 10px;
  margin-top: 0px;
}

.captcha-input {
  flex-grow: 1;
  margin-right: 10px;
}
</style>
