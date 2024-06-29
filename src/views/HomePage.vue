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

          <el-menu-item index="2" @click="navigateTo('/homepage')">
            <template #title>
              <el-icon>
                <HomeFilled/>
              </el-icon>
              首页
            </template>
          </el-menu-item>

          <el-sub-menu index="3">
            <template #title>
              <el-icon>
                <Menu/>
              </el-icon>
              管理
            </template>
            <el-menu-item-group>
              <el-menu-item index="3-1" @click="navigateTo('/userManage')">
                <el-icon>
                  <UserFilled/>
                </el-icon>
                用户管理
              </el-menu-item>
              <el-menu-item index="3-2">
                <el-icon>
                  <Management/>
                </el-icon>
                部门管理
              </el-menu-item>
              <el-menu-item index="3-3">
                <el-icon>
                  <Orange/>
                </el-icon>
                行业动态管理
              </el-menu-item>
              <el-menu-item index="3-4">
                <el-icon>
                  <List/>
                </el-icon>
                课程管理
              </el-menu-item>
              <el-menu-item index="3-5">
                <el-icon>
                  <TrendCharts/>
                </el-icon>
                会议管理
              </el-menu-item>
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
                <el-breadcrumb-item></el-breadcrumb-item>
              </el-breadcrumb>
            </div>
            <el-dropdown :hide-on-click="false">
              <div style="display: flex; align-items: center;">
                <el-avatar icon="UserFilled"
                           style="font-size: 20px; margin-right: 10px; position: relative; top: 8px;"></el-avatar>
                <el-button type="text" style="font-size: 15px; color: rgb(0,0,0); position: relative; top: 8px;"><span>{{
                    loginUser.userName
                  }}</span></el-button>
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

        <el-main class="el-main" style="margin-left:80px;margin-top:10px">
          <el-row :gutter="90">
            <el-col :span="11">
              <!-- 用户管理卡片 -->
              <div class="flip-card" @click="navigateTo('/userManage')">
                <div class="flip-card-inner">
                  <div class="flip-card-front" style="background-color: #409EFF; color: white;">
                    <el-icon class="card-icon"><UserFilled /></el-icon>
                    <span class="card-title">用户管理</span>
                  </div>
                  <div class="flip-card-back" style="background-color: #409EFF; color: white;">
                    <el-icon class="card-icon"><UserFilled /></el-icon>
                    <span >用户管理子系统是软件的核心模块之一，支持全面的用户信息管理，涵盖企业租户与个人用户的注册、登录、信息修改及删除。系统提供便捷的界面操作，可维护用户账号安全，管理企业租户资料，实现用户与企业组织架构的有效整合与高效管控。</span>
                  </div>
                </div>
              </div>

              <!-- 部门管理卡片 -->
              <div class="flip-card">
                <div class="flip-card-inner">
                  <div class="flip-card-front" style="background-color: #67C23A; color: white;">
                    <el-icon class="card-icon"><Management /></el-icon>
                    <span class="card-title">部门管理</span>
                  </div>
                  <div class="flip-card-back" style="background-color: #67C23A; color: white;">
                    <el-icon class="card-icon"><Management /></el-icon>
                    <span >部门管理子系统是组织架构维护的核心工具，支持查看与管理多级部门结构，包括新增、修改、删除部门及调整部门层级。用户可便捷查询部门信息，实现部门负责人、邮箱等关键信息的维护，以及部门状态的切换，提升企业内部管理效率与协同能力。</span>
                  </div>
                </div>
              </div>
            </el-col>

            <el-col :span="11">
              <!-- 课程管理卡片 -->

              <div class="flip-card" >
                <div class="flip-card-inner">
                  <div class="flip-card-front" style="background-color: #E6A23C; color: white;">
                    <el-icon class="card-icon"><List /></el-icon>
                    <span class="card-title">课程管理</span>
                  </div>
                  <div class="flip-card-back" style="background-color: #E6A23C; color: white;">
                    <el-icon class="card-icon"><List /></el-icon>
                    <span>课程管理子系统是一个多功能教育平台，用于课程的创建、查询、编辑与删除。用户可通过直观界面新增课程信息，如名称、简介及排序，并上传课程封面和视频资源。系统支持课程状态监控，提供搜索与分类浏览，以及Excel导出功能，极大地丰富了教育资源的管理和学习体验。</span>
                  </div>
                </div>
              </div>

              <!-- 会议管理卡片 -->
              <div class="flip-card" @click="navigateTo('/meeting')">
                <div class="flip-card-inner">
                  <div class="flip-card-front" style="background-color: #F56C6C; color: white;">
                    <el-icon class="card-icon"><TrendCharts /></el-icon>
                    <span class="card-title">会议管理</span>
                  </div>
                  <div class="flip-card-back" style="background-color: #F56C6C; color: white;">
                    <el-icon class="card-icon"><TrendCharts /></el-icon>
                    <span>会议管理子系统是针对高效会议组织设计的B/S架构平台，涵盖会议的创建、查询、修改和删除。用户可通过Web界面轻松添加会议信息，支持富文本描述和图片上传。系统展示会议列表，区分进行中与已结束状态，并能按名称、创建者等搜索。还具备会议资料在线管理与Excel导出功能，显著提升了会议管理的效率与便利性。</span>
                  </div>
                </div>
              </div>
            </el-col>
          </el-row>

          <!-- 行业动态管理卡片 -->
          <div class="flip-card">
            <div class="flip-card-inner" style="width:1075px">
              <div class="flip-card-front" style="background-color: #909399; color: white;">
                <el-icon class="card-icon"><Orange /></el-icon>
                <span class="card-title">行业动态管理</span>
              </div>
              <div class="flip-card-back" style="background-color: #909399; color: white;">
                <el-icon class="card-icon"><Orange /></el-icon>
                <span >行业动态管理子系统是一个集信息展示与交互于一体的平台，专注于行业新闻与活动的发布、浏览与搜索。用户可享受个性化动态推送，利用智能筛选浏览最新资讯，支持关键词搜索与分类查看。系统提供动态详情页，融合图文富媒体内容，及下拉刷新、上拉加载等动态数据浏览特性，优化用户体验，促进信息时效性与传播效率。</span>
              </div>
            </div>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import {ref, computed, onMounted} from 'vue';
import {useStore} from 'vuex';

import {format, parseISO} from 'date-fns';
import {useRouter} from 'vue-router';
import {Management, UserFilled} from "@element-plus/icons-vue";

export default {
  components: {Management, UserFilled},
  setup() {

    const userInfo = ref({
      userName: '',
      phoneNumber: '',
      email: '',
      departmentId: '',
      role: '',
      createTime: '',
      password: '',
    });

    const departmentInfo = ref({
      departmentName: '',
    });

    const basicInfoForm = ref({
      userId: '',
      realName: '',
      phoneNumber: '',
      email: '',
      gender: '',
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
      router.push('/login');
    };

    const onPhoneNumberInput = (event) => {
      basicInfoForm.value.phoneNumber = event.target.value;
    };


    const store = useStore();
    const loginUser = ref('')
    onMounted(async () => {
      await Promise.all([
        loginUser.value = store.state.user,

      ])
    });



    const createTime = computed(() => {
      if (!userInfo.value.createTime) return ''; // 处理 createTime 为空的情况
      try {
        const parsedDate = parseISO(userInfo.value.createTime);
        return format(parsedDate, 'yyyy-MM-dd HH:mm:ss');
      } catch (error) {
        console.error('Error parsing or formatting date:', error);
        return ''; // 如果日期解析或格式化出错，返回空字符串
      }
    });

    const navigateTo = (routeName) => {
      router.push( routeName );
    };
    return {
      userInfo,
      departmentInfo,
      basicInfoForm,
      passwordForm,
      activeTab,
      personalCenter,
      back,
      createTime,
      onPhoneNumberInput,
      navigateTo,
      loginUser,
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
  box-shadow: 5px 0px 6px rgba(0.5, 0.5, 0, 0.5);
}

.header-with-shadow {
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.5);
}

.card-header {
  font-size: 20px;
  font-weight: bold;
}

.custom-card {
  width: 100%;
  height: 120px;

  margin-bottom: 25px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 20px;
}

.custom-card .card-icon {
  font-size: 40px;
  margin-bottom: 10px;
}

.custom-card .card-title {
  font-size: 18px;
  font-weight: bold;
}

.el-main {
  flex: 1;
  overflow: hidden; /* 隐藏滚动条 */
}

.flip-card {
  background-color: transparent;
  width: 100%;
  height: 190px;
  perspective: 1000px;
  margin-bottom: 20px;
}

.flip-card-inner {
  position: relative;
  width: 100%;
  height: 100%;
  text-align: center;
  transition: transform 0.6s;
  transform-style: preserve-3d;
}

.flip-card:hover .flip-card-inner {
  transform: rotateY(180deg);
}

.flip-card-front, .flip-card-back {
  position: absolute;
  width: 100%;
  height: 100%;
  backface-visibility: hidden;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.flip-card-back {
  transform: rotateY(180deg);
}

.card-icon {
  font-size: 60px;
  margin-bottom: 10px;
}

.card-title {
  font-size: 24px;
  font-weight: bold;
}
</style>
