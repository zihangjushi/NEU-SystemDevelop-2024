<template>
  <div class="about">
    <el-container style="height: 100vh; border: 1px solid #eee">

      <!-- /side容器，用来存放侧边菜单 -->
      <el-aside class="menu-with-shadow" width="240px" style="color: rgb(255,255,255)">
        <el-menu :default-openeds="['3']">
          <!-- //序号为1的侧边栏，用来显示测盟汇和图片组件 -->
          <el-menu-item index="1">
            <template #title>
              <img src="@/assets/logo1.png" style="width: 40px; height: 40px; margin-right: 5px;">
              <span style="font-size: 20px;"><strong>测盟汇</strong></span>
            </template>
          </el-menu-item>
          <!-- //序号为2的菜单栏，用来显示标题（首页） -->
          <el-menu-item index="2">
            <template #title>
              <el-icon>
                <HomeFilled />
              </el-icon>首页
            </template>
          </el-menu-item>
          <!-- //序号为3的菜单栏，用来显示各个菜单项和图标 -->
          <el-sub-menu index="3">
            <template #title><el-icon>
                <Menu />
              </el-icon>管理</template>

            <el-sub-menu>
              <template #title>
                <el-icon>
                  <UserFilled />
                </el-icon>
                <span>用户管理</span>
              </template>
              <el-input v-model="searchQuery" style="width: 160px" placeholder="输入以搜索" clearable @input="searchMenu" />
              <el-sub-menu v-for="(company, index) in companyList" :key="company.companyId" class="left-align"
                :index="'3-1-' + (index + 1)">
                <template #title>
                  {{ company.companyName }}
                </template>
                <el-menu-item v-for="department in getDepartmentsByCompany(company.companyId)"
                  :key="department.departmentId" :index="'3-1-' + (index + 1) + '-' + department.departmentId">
                  {{ department.departmentName }}
                </el-menu-item>
              </el-sub-menu>
            </el-sub-menu>
            <el-menu-item index="3-2"><el-icon>
                <Management />
              </el-icon>部门管理</el-menu-item>
            <el-menu-item index="3-3"><el-icon>
                <Orange />
              </el-icon>行业动态管理</el-menu-item>
            <el-menu-item index="3-4"><el-icon>
                <List />
              </el-icon>课程管理</el-menu-item>
            <el-menu-item index="3-5"><el-icon>
                <TrendCharts />
              </el-icon>用户管理</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>

      <!-- //head容器，用来存放路径信息 -->
      <el-container>
        <el-header class="header-with-shadow" style="font-size: 12px">
          <div style="display: flex; justify-content: space-between; width: 100%;">
            <div style="text-align: left;position: relative; top: 20px;">
              <!-- 用来存放路径信息的头部栏，可以进行路径的跳转 -->
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item><a href="/">用户管理</a></el-breadcrumb-item>
                <el-breadcrumb-item>用户列表</el-breadcrumb-item>
                <el-breadcrumb-item>用户详情</el-breadcrumb-item>
              </el-breadcrumb>
            </div>
            <!-- 下拉菜单，用于展开个人中心和退出按钮 -->
            <el-dropdown :hide-on-click="false">
              <div style="display: flex; align-items: center;">
                <el-avatar icon="UserFilled"
                  style="font-size: 20px; margin-right: 10px;position: relative; top: 8px;"></el-avatar>
                <el-button type="text"
                  style="font-size: 15px; color: rgb(0,0,0);position: relative; top: 8px;">{{ userName }}</el-button>
              </div>
              <!-- template是下拉插槽，用来存放dropdown中的内容 -->
              <template #dropdown>
                <!-- 下拉菜单中的选项，前者可以打开个人中心，后者退出登录 -->
                <el-dropdown-menu>
                  <el-dropdown-item @click="personalCenter">个人中心</el-dropdown-item>
                  <el-dropdown-item @click="back">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>

        <!-- main容器，用来存放页面的主要内容 -->
        <el-main>

          <div style="text-align: left;">
            <!-- 两个文本框及其输入提示信息，分别于input1和input2双向绑定 -->
            <span style="color: rgb(150,150,150)"><strong>用户名称</strong></span>
            <el-input v-model="input1" placeholder="请输入内容"
              style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
            <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>创建人</strong></span>
            <el-input v-model="input2" placeholder="请输入内容"
              style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
            <!-- 开始时间和结束时间的选择，双向绑定到了value1属性（目前只有开始时间 -->
            <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>开始时间</strong></span>
            <el-date-picker v-model="value1" type="date" placeholder="选择日期" style="margin-left: 5px;">
            </el-date-picker>
            <!-- 查询和重置按钮 -->
            <el-button type="primary" style="margin-left: 15px;" icon="Search">搜索</el-button>
            <el-button plain style="margin-left: 15px;" icon="Refresh">重置</el-button>
          </div>

          <!-- 一行中四个按钮，用来新增、删除修改、导出（暂时没有绑定方法） -->
          <el-row style="text-align: left;">
            <el-button type="primary" plain icon="Plus" style="margin-top: 5px">新增</el-button>
            <el-button type="success" plain icon="Edit" style="margin-top: 5px">修改</el-button>
            <el-button type="danger" plain icon="Delete" style="margin-top: 5px">删除</el-button>
            <el-button type="warning" plain icon="Download" style="margin-top: 5px">导出</el-button>
          </el-row>

          <!-- 用户菜单 -->
          <div class="custom-table" style="margin-top: 10px;">
            <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%"
              @selection-change="handleSelectionChange">
              <!-- 表单列 -->
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="userId" label="用户编号" width="100" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="realName" label="用户姓名" width="100" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="userName" label="用户昵称" width="100" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="departmentId" label="所属部门" width="100" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="phoneNumber" label="手机号码" width="200" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="state" label="状态" width="100" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="createTime" label="创建时间" width="200" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="operate" label="操作" header-align="center" align="center">
                <!-- template插槽，用于向菜单的最后一列中插入两个操作按钮 -->
                <template #default="scope">
                  <el-button size="mini" type="success" icon="Edit"
                    @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                  <el-button size="mini" type="danger" icon="Delete"
                    @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-main>

        <!-- 同样的容器，但是是背景图片 -->
        <div class="container" style="grid-template-rows: auto 1fr auto;margin-left: 500px">
          <el-pagination background layout="prev, pager, next" :total="50" class="pagination"
            style="grid-row: 3;margin-bottom: 10px"></el-pagination>
        </div>

      </el-container>
    </el-container>
  </div>
</template>

<script>
import {onMounted, ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { Management, UserFilled } from "@element-plus/icons-vue";
import { useStore } from 'vuex';
export default {
  components: { Management, UserFilled },
  setup() {
    // 用来存放数据的表格
    


    // 用来选择日期的组件
    const pickerOptions = ref({
      disabledDate(time) {
        return time.getTime() > Date.now();
      },
      shortcuts: [{
        text: '今天',
        onClick(picker) {
          picker.emit('pick', new Date());
        }
      }, {
        text: '昨天',
        onClick(picker) {
          const date = new Date();
          date.setTime(date.getTime() - 3600 * 1000 * 24);
          picker.emit('pick', date);
        }
      }, {
        text: '一周前',
        onClick(picker) {
          const date = new Date();
          date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
          picker.emit('pick', date);
        }
      }],
      multipleSelection: []
    });

    const value1 = ref('');
    const input1 = ref('');
    const input2 = ref('');
    const userName = ref('');
    const searchQuery = ref('')

    const tableData = ref([]);
    const companyList = ref([]);
    const departmentList = ref([]);
    const searchList = ref([]);
    const multipleTable = ref(null);

    const getDepartmentsByCompany = (companyId) => {
      return departmentList.value.filter(department => department.companyId === companyId);
    }

    const toggleSelection = (rows) => {
      if (rows) {
        rows.forEach(row => {
          multipleTable.value.toggleRowSelection(row);
        });
      } else {
        multipleTable.value.clearSelection();
      }
    };

    const handleSelectionChange = (val) => {
      pickerOptions.value.multipleSelection = val;
    };

    // 修改按钮
    const handleEdit = (index, row) => {
      console.log(index, row);
    };

    // 删除按钮
    const handleDelete = (userName) => {
      console.log(userName);
    };

    // 导入路由
    const router = useRouter();
    const store = useStore();

    // 路由到用户中心
    const personalCenter = () => {
      router.push('/userCenter');
    };

    // 返回登录界面
    const back = () => {
      router.push('/login');
    };

    // 用来将函数初始加载到companyList数组中
    const initCompanyList = () => {
      axios.get("http://localhost:8070/company/list")
        .then(response => {
          if (response.data.isOk) {
            console.log("公司数据读取成功")
            companyList.value = response.data.companies;
            console.log(companyList)
          }
        })
    }

    const initDepartmentList = () => {
      axios.get("http://localhost:8070/department/list")
        .then(response => {
          if (response.data.isOk) {
            console.log("部门数据读取成功")
            departmentList.value = response.data.departments;
            console.log(departmentList)
          }
        })
    }

    const initUser = () => {
      userName.value = store.state.userName
    }

    onMounted(async () => {
      initCompanyList();
      initDepartmentList();
      initUser();
    })

    // setup的返回值
    return {
      tableData,
      pickerOptions,
      value1,
      input1,
      input2,
      searchQuery,
      companyList,
      departmentList,
      searchList,
      multipleTable,
      userName,
      toggleSelection,
      handleSelectionChange,
      handleEdit,
      handleDelete,
      personalCenter,
      back,
      initCompanyList,
      getDepartmentsByCompany,
    }
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
  /* Adjusted z-index for header */
  position: relative;
  /* Added to ensure z-index is applied */
}

.el-aside {
  color: #333;
  z-index: 2;
  /* Adjusted z-index for aside */
  position: relative;
  /* Added to ensure z-index is applied */
}

.el-main {
  flex: 1;
  overflow: auto;
}

.menu-with-shadow {
  box-shadow: 5px 0px 6px rgba(0.5, 0.5, 0, 0.5);
  /* 自定义阴影效果，可根据需要调整颜色和大小 */
}

.header-with-shadow {
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.5);
  /* 自定义阴影效果，可根据需要调整颜色和大小 */
}

.custom-table .el-table__header th {
  background-color: #e0e0e0;
  /* 更改背景颜色 */
  color: #6c6c6c;
  /* 更改文字颜色 */
  text-align: center;
  /* 居中对齐 */
}

html,
body {
  margin: 0;
  padding: 0;
  height: 100%;
}
</style>