<template>
  <div class="register-container">
    <div class="register">
      <h1>欢迎注册测盟汇系统</h1>
      <div class="form-row">
        <div class="form-column">
          <label for="newCompanyName">公司名</label>
          <input id="newCompanyName" v-model="newCompanyName" type="text" />
          <span v-if="errors.newCompanyName">{{ errors.newCompanyName }}</span>
        </div>
        <div class="form-column">
          <label for="contactName">联系人姓名</label>
          <input id="contactName" v-model="contactName" type="text" />
          <span v-if="errors.contactName">{{ errors.contactName }}</span>
        </div>
      </div>
      <div class="form-row">
        <div class="form-column">
          <label for="contactPhone">联系电话</label>
          <input id="contactPhone" v-model="contactPhone" type="text" pattern="\d*" />
          <span v-if="errors.contactPhone">{{ errors.contactPhone }}</span>
        </div>
        <div class="form-column">
          <label for="description">公司简介</label>
          <input id="description" v-model="description" type="text" />
        </div>
      </div>
      <div class="form-row">
        <div class="form-column">
          <label for="realname">用户姓名</label>
          <input id="realname" v-model="realname" type="text" />
          <span v-if="errors.realname">{{ errors.realname }}</span>
        </div>
        <div class="form-column">
          <label for="usernum">用户账号</label>
          <input id="usernum" v-model="usernum" type="text" pattern="\d*" />
          <span v-if="errors.usernum">{{ errors.usernum }}</span>
        </div>
      </div>
      <div class="form-row">
        <div class="form-column">
          <label for="password">用户密码</label>
          <input id="password" v-model="password" type="password" />
          <span v-if="errors.password">{{ errors.password }}</span>
        </div>
        <div class="form-column">
          <label for="email">用户邮箱</label>
          <input id="email" v-model="email" type="text" />
          <span v-if="errors.email">{{ errors.email }}</span>
        </div>
      </div>
      <div class="form-row">
        <div class="form-column">
          <label for="gender">性别</label>
          <select id="gender" v-model="gender">
            <option value="0">男</option>
            <option value="1">女</option>
          </select>
        </div>
      </div>
      <div class="form-row">
        <button @click="register">注册</button>
        <button @click="redirectToLogin">返回登录页面</button>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue';
import axios from 'axios';

export default defineComponent({
  name: 'UserRegister',
  data() {
    return {
      description: '',
      companies: [],
      companyName: '',
      newCompanyName: '',
      contactName: '',
      contactPhone: '',
      realname: '',
      usernum: '',
      password: '',
      email: '',
	gender:'0',
      errors: {}
    };
  },
  methods: {

    validateForm() {
      this.errors = {};
      if (this.companyOption === 'new') {
        if (!this.newCompanyName) this.errors.newCompanyName = '公司名不能为空';
        if (!this.contactName) this.errors.contactName = '联系人姓名不能为空';
        if (!this.contactPhone) {
          this.errors.contactPhone = '联系电话不能为空';
        } else if (!/^\d+$/.test(this.contactPhone)) {
          this.errors.contactPhone = '联系电话只能包含数字';
        }
      }
      if (!this.realname) this.errors.realname = '用户名不能为空';
      if (!this.usernum) {
        this.errors.usernum = '用户账号不能为空';
      } else if (!/^\d+$/.test(this.usernum)) {
        this.errors.usernum = '用户账号只能包含数字';
      }
      if (!this.password) this.errors.password = '用户密码不能为空';

      return Object.keys(this.errors).length === 0;
    },
    register() {
      if (this.validateForm()) {
        const userData = {
          realname: this.realname,
          usernum: this.usernum,
          password: this.password,
          description: this.description,
          gender: this.gender,
          newCompanyName: this.newCompanyName,
          contactName: this.contactName,
          contactPhone: this.contactPhone,
          email: this.email,
        };

        axios.post('http://localhost:8070/user/register', userData, { withCredentials: true })
          .then(response => {
            if (response.data.isOk) {
              alert('注册成功');
              this.$router.push('/');
            } else {
              alert('注册失败');
            }
          })
          .catch(error => {
            console.error('注册失败', error);
            alert('注册失败');
          });
      }
    },
    redirectToLogin() {
      this.$router.push('/');
    }
  },
  mounted() {

  }
});
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url('@/image/background2.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.register {
  background-color: rgba(255, 255, 255, 0.8);
  padding: 50px;
  border-radius: 10px;
  text-align: center;
  width: 600px; /* 扩大宽度以适应两列布局 */
}

.form-row {
  display: flex;
  justify-content: space-between;
}

.form-column {
  flex: 0 0 48%; /* 确保每列占据48%的宽度，留出空间间隔 */
}

label {
  display: block;
  margin: 10px 0 5px;
}

input, select, button {
  display: block;
  width: 100%;
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

span {
  color: red;
  font-size: 12px;
}
</style>
