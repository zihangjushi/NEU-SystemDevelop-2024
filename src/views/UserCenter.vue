<template>
  <div class="about">
    <el-container style="height: 100vh; border: 1px solid #eee">
      <el-aside class="menu-with-shadow" width="200px" style="color: rgb(255,255,255)">
        <el-menu :default-openeds="['3']">
          <el-menu-item index="1">
            <template #title>
              <img src="@/assets/logo1.png" style="width: 40px; height: 40px; margin-right: 5px;">
              <span style="font-size: 20px;"><strong>测盟汇</strong></span>
            </template>
          </el-menu-item>

          <el-menu-item index="2">
            <template #title>
              <el-icon><HomeFilled /></el-icon>首页
            </template>
          </el-menu-item>

          <el-sub-menu index="3">
            <template #title><el-icon><Menu /></el-icon>管理</template>
            <el-menu-item-group>
              <el-menu-item index="3-1"><el-icon><UserFilled /></el-icon>用户管理</el-menu-item>
              <el-menu-item index="3-2"><el-icon><Management /></el-icon>部门管理</el-menu-item>
              <el-menu-item index="3-3"><el-icon><Orange /></el-icon>行业动态管理</el-menu-item>
              <el-menu-item index="3-4"><el-icon><List /></el-icon>课程管理</el-menu-item>
              <el-menu-item index="3-5"><el-icon><TrendCharts /></el-icon>会议管理</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header class="header-with-shadow" style="font-size: 12px">
          <div style="display: flex; justify-content: space-between; width: 100%;">
            <div style="text-align: left; position: relative; top: 20px;">
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>个人中心</el-breadcrumb-item>
              </el-breadcrumb>
            </div>
            <el-dropdown :hide-on-click="false">
              <div style="display: flex; align-items: center;">
                <el-avatar icon="UserFilled" style="font-size: 20px; margin-right: 10px; position: relative; top: 8px;"></el-avatar>
                <el-button type="text" style="font-size: 15px; color: rgb(0,0,0); position: relative; top: 8px;">徐洋</el-button>
              </div>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="personalCenter">个人中心</el-dropdown-item>
                  <el-dropdown-item @click="back">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>

        <el-main>
          <el-row :gutter="20">
            <el-col :span="7">
              <el-card style="height: 607px; margin-top: 10px;">
                <template #header>
                  <div class="card-header">
                    <span>个人信息</span>
                  </div>
                </template>

                <el-row :gutter="20">
                  <el-col :span="24" style="text-align: center;">
                    <el-avatar size="large" icon="UserFilled" style="margin-bottom: 20px;"></el-avatar>
                  </el-col>
                </el-row>
                <el-divider></el-divider>
                <el-row :gutter="20">
                  <el-col :span="8">
                    <el-icon><UserFilled /></el-icon>
                    <strong>用户名:</strong>
                  </el-col>
                  <el-col :span="16">
                    <span>{{ userInfo.username }}</span>
                  </el-col>
                </el-row>
                <el-divider></el-divider>
                <el-row :gutter="20">
                  <el-col :span="8">
                    <el-icon><Iphone /></el-icon>
                    <strong>手机号码:</strong>
                  </el-col>
                  <el-col :span="16">
                    <span>{{ userInfo.phone }}</span>
                  </el-col>
                </el-row>
                <el-divider></el-divider>
                <el-row :gutter="20">
                  <el-col :span="8">
                    <el-icon><Message /></el-icon>
                    <strong>用户邮箱:</strong>
                  </el-col>
                  <el-col :span="16">
                    <span>{{ userInfo.email }}</span>
                  </el-col>
                </el-row>
                <el-divider></el-divider>
                <el-row :gutter="20">
                  <el-col :span="8">
                    <el-icon><Management /></el-icon>
                    <strong>所属部门:</strong>
                  </el-col>
                  <el-col :span="16">
                    <span>{{ userInfo.department }}</span>
                  </el-col>
                </el-row>
                <el-divider></el-divider>
                <el-row :gutter="20">
                  <el-col :span="8">
                    <el-icon><Avatar /></el-icon>
                    <strong>所属角色:</strong>
                  </el-col>
                  <el-col :span="16">
                    <span>{{ userInfo.role }}</span>
                  </el-col>
                </el-row>
                <el-divider></el-divider>
                <el-row :gutter="20">
                  <el-col :span="8">
                    <el-icon><Calendar /></el-icon>
                    <strong>创建日期:</strong>
                  </el-col>
                  <el-col :span="16">
                    <span>{{ userInfo.createDate }}</span>
                  </el-col>
                </el-row>
              </el-card>
            </el-col>

            <el-col :span="17">
              <el-card style="height: 607px; margin-top: 10px;">
                <template #header>
                  <div class="card-header">
                    <span>基本资料</span>
                  </div>
                </template>
                <el-tabs v-model="activeTab">
                  <el-tab-pane label="基本资料" name="baseInfo">
                    <!-- 基本资料内容 -->
                    <el-form :model="basicInfoForm" label-width="80px">
                      <el-form-item label="真实姓名" prop="realName" required>
                        <el-input v-model="basicInfoForm.realName"></el-input>
                      </el-form-item>
                      <el-form-item label="手机号码" prop="phone" required>
                        <el-input v-model="basicInfoForm.phone"></el-input>
                      </el-form-item>
                      <el-form-item label="邮箱" prop="email" required>
                        <el-input v-model="basicInfoForm.email"></el-input>
                      </el-form-item>
                      <el-form-item label="性别" prop="gender" >
                        <el-radio-group v-model="basicInfoForm.gender">
                          <el-radio label="男">男</el-radio>
                          <el-radio label="女">女</el-radio>
                        </el-radio-group>
                      </el-form-item>
                      <el-form-item>
                        <el-button type="primary" @click="submitBasicInfo">保存</el-button>
                        <el-button type="danger" @click="back">关闭</el-button>
                      </el-form-item>
                    </el-form>
                  </el-tab-pane>
                  <el-tab-pane label="密码修改" name="passwordChange">
                    <!-- 密码修改内容 -->
                    <el-form :model="passwordForm" label-width="80px">
                      <el-form-item label="旧密码" prop="oldPassword" required>
                        <el-input type="password" v-model="passwordForm.oldPassword"></el-input>
                      </el-form-item>
                      <el-form-item label="新密码" prop="newPassword" required>
                        <el-input type="password" v-model="passwordForm.newPassword"></el-input>
                      </el-form-item>
                      <el-form-item label="确认密码" prop="confirmPassword" required>
                        <el-input type="password" v-model="passwordForm.confirmPassword"></el-input>
                      </el-form-item>
                      <el-form-item>
                        <el-button type="primary" @click="submitPasswordChange">提交</el-button>
                        <el-button type="danger" @click="back">关闭</el-button>
                      </el-form-item>
                    </el-form>
                  </el-tab-pane>
                </el-tabs>
              </el-card>
            </el-col>
          </el-row>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import {Iphone, Management, UserFilled} from "@element-plus/icons-vue";

export default {
  components: {Iphone, Management, UserFilled },
  setup() {
    const userInfo = ref({
      username: '徐洋',
      phone: '12345678901',
      email: 'xuyang@example.com',
      department: '技术部',
      role: '管理员',
      createDate: '2022-01-01'
    });

    const basicInfoForm = ref({
      realName: '徐洋',
      phone: '12345678901',
      email: 'xuyang@example.com',
      gender: '男'
    });

    const passwordForm = ref({
      oldPassword: '',
      newPassword: '',
      confirmPassword: ''
    });

    const activeTab = ref('baseInfo');

    const router = useRouter();

    const personalCenter = () => {
      router.push('/userCenter');
    };

    const back = () => {
      router.push('/meeting');
    };

    const submitBasicInfo = () => {
      // 提交基本资料修改逻辑
      console.log('基本资料表单提交', basicInfoForm.value);
    };

    const submitPasswordChange = () => {
      // 提交密码修改逻辑
      console.log('密码修改表单提交', passwordForm.value);
    };

    return {
      userInfo,
      basicInfoForm,
      passwordForm,
      activeTab,
      personalCenter,
      back,
      submitBasicInfo,
      submitPasswordChange
    };
  }
};
</script>

<style>
.el-container {
  display: flex;
  height: 100vh;
}

.el-header {
  background-color: #ffffff;
  color: #333;
  line-height: 60px;
  z-index: 1;
  position: relative;
}

.el-aside {
  color: #333;
  z-index: 2;
  position: relative;
}

.el-main {
  flex: 1;
  overflow: auto;
}

.menu-with-shadow {
  box-shadow: 5px 0px 6px rgba(0.5, 0.5, 0,0.5);
}

.header-with-shadow {
  box-shadow: 0px 0px 3px rgba(0, 0, 0,0.5);
}

.card-header {
  font-size: 20px;
  font-weight: bold;
}
</style>
