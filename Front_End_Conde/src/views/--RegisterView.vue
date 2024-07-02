<template>
  <div class="register-background">
    <div class="register-container" style="margin-top: 40px">
      <h3 style="font-size: 30px;">用户注册</h3>
      <!-- 注册表单 -->

      <el-form :model="registerForm" :rules="rules" ref="registerFormRef" class="register-form">
        <el-form-item label="企业名称" prop="username">
          <el-input v-model="registerForm.username" placeholder="请输入企业名称"></el-input>
        </el-form-item>

        <el-form-item label="联系方式" prop="tel">
          <el-input v-model="registerForm.tel" placeholder="请输入企业联系方式"></el-input>
        </el-form-item>

        <el-form-item label="设置密码" prop="password">
          <el-input type="password" v-model="registerForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input type="password" v-model="registerForm.confirmPassword" placeholder="请确认密码"></el-input>
        </el-form-item>

        <el-form-item label="验证码" prop="code">

        </el-form-item>
        <div class="code-input-container" style="margin-top: 30px;">
          <el-input v-model="registerForm.code" placeholder="请输入验证码" class="captcha-input" style="margin-top: -30px;"></el-input>
          <img :src="captchaUrl" class="captcha-img"  @click="refreshCaptcha" />
        </div>

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
        username: '',
        tel: '',
        password: '',
        confirmPassword: '',
        code: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入企业名称', trigger: 'blur' },
          { min: 5, max: 14, message: '长度在 5 到 14 个字符', trigger: 'blur' }
        ],
        tel: [
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
            validator: (rule, value, callback) => {
              if (value !== this.registerForm.password) {
                callback(new Error('两次输入的密码不一致'));
              } else {
                callback();
              }
            },
            trigger: 'blur'
          }
        ],
        code: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
      },
      captchaUrl: require('@/assets/captcha.png') // 初始化验证码图片
    };
  },
  methods: {
    refreshCaptcha() {
      // 刷新验证码图片的逻辑
      // 示例：假设重新生成一个随机数作为验证码
      this.captchaUrl = require('@/assets/captcha.png'); // 替换成新的验证码地址或重新生成验证码的方法
    },
    submitForm() {
      // Verify if $refs.registerFormRef is defined before accessing it
      if (this.$refs.registerFormRef) {
        this.$refs.registerFormRef.validate((valid) => {
          if (valid && this.registerForm.code === 'yhuv') {
            this.handleSuccess();
          } else {
            this.handleError();
          }
        });
      } else {
        console.error('Form reference is undefined.');
      }
    },
    handleSuccess() {
      console.log('注册表单提交成功:', this.registerForm);
      // 注册成功后跳转到登录页面
      this.$router.push('/login');
      ElMessage({
        showClose: true,
        message: '注册成功！',
        type: 'success'
      });
    },
    handleError() {
      console.error('注册信息有误，请检查并重新填写！');
      ElMessage.error('注册信息有误，请检查并重新填写！');
    },
    back() {
      // 返回到登录页面
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
  margin-top: -30px;
}

.captcha-input {
  flex-grow: 1; /* 让输入框充满剩余空间 */
  margin-right: 10px; /* 调整输入框与验证码图片的间距 */
}
</style>
