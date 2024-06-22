<template>
  <div class="about">
    <el-container style="height: 100vh; border: 1px solid #eee">

      <!-- /side容器，用来存放侧边菜单 -->
      <el-aside class="menu-with-shadow" width="200px" style="color: rgb(255,255,255)">
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
              <el-icon><HomeFilled /></el-icon>首页
            </template>
          </el-menu-item>
          <!-- //序号为3的菜单栏，用来显示各个菜单项和图标 -->
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

      <!-- //head容器，用来存放路径信息 -->
      <el-container>
        <el-header class="header-with-shadow" style="font-size: 12px">
          <div style="display: flex; justify-content: space-between; width: 100%;">
            <div style="text-align: left;position: relative; top: 20px;">
              <!-- 用来存放路径信息的头部栏，可以进行路径的跳转 -->
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item><a href="/">会议管理</a></el-breadcrumb-item>
                <el-breadcrumb-item>会议列表</el-breadcrumb-item>
                <el-breadcrumb-item>会议详情</el-breadcrumb-item>
              </el-breadcrumb>
            </div>
            <!-- 下拉菜单，用于展开个人中心和退出按钮 -->
            <el-dropdown :hide-on-click="false">
              <div style="display: flex; align-items: center;">
                <el-avatar icon="UserFilled" style="font-size: 20px; margin-right: 10px;position: relative; top: 8px;"></el-avatar>
                <el-button type="text" style="font-size: 15px; color: rgb(0,0,0);position: relative; top: 8px;">徐洋</el-button>
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
            <span style="color: rgb(150,150,150)"><strong>会议名称</strong></span>
            <el-input v-model="input1" placeholder="请输入内容" style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
            <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>创建人</strong></span>
            <el-input v-model="input2" placeholder="请输入内容" style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
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
            <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
              <!-- 表单列 -->
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="name" label="会议名称" width="300" header-align="center" align="center"></el-table-column>
              <el-table-column prop="creator" label="创建人" width="100" header-align="center" align="center"></el-table-column>
              <el-table-column prop="state" label="会议状态" width="100" header-align="center" align="center"></el-table-column>
              <el-table-column prop="content" label="会议内容" width="300" header-align="center" align="center"></el-table-column>
              <el-table-column prop="date" label="开始时间" width="100" header-align="center" align="center"></el-table-column>
              <el-table-column prop="operate" label="操作" header-align="center" align="center">
                <!-- template插槽，用于向菜单的最后一列中插入两个操作按钮 -->
                <template #default="scope">
                  <el-button size="mini" type="success" icon="Edit" @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                  <el-button size="mini" type="danger" icon="Delete" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-main>

        <!-- 同样的容器，但是是背景图片 -->
        <div class="container" style="grid-template-rows: auto 1fr auto;margin-left: 500px">
          <el-pagination background layout="prev, pager, next" :total="50" class="pagination" style="grid-row: 3;margin-bottom: 10px"></el-pagination>
        </div>

      </el-container>
    </el-container>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import {Management, UserFilled} from "@element-plus/icons-vue";
export default {
  components: {Management, UserFilled},
  setup() {
    // 用来存放数据的表格
    const tableData = ref([
      {
        date: '2023-06-10',
        creator: '李四',
        name: '团队建设会议',
        state: '进行中',
        content: '讨论团队建设与发展'
      },
      {
        date: '2023-05-22',
        creator: '王五',
        name: '产品发布会',
        state: '已结束',
        content: '介绍新产品功能与特性'
      },
      {
        date: '2023-04-18',
        creator: '赵六',
        name: '技术分享会',
        state: '未开始',
        content: '分享最新的技术趋势'
      },
      {
        date: '2023-03-15',
        creator: '张三',
        name: '市场推广会议',
        state: '已结束',
        content: '讨论市场推广策略'
      },
      {
        date: '2023-02-10',
        creator: '钱七',
        name: '季度总结会',
        state: '已结束',
        content: '总结季度工作成果与问题'
      }
    ]);
      
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
    const multipleTable = ref(null);

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

    const handleEdit = (index, row) => {
      console.log(index, row);
    };

    const handleDelete = (index, row) => {
      console.log(index, row);
    };

    const router = useRouter();

    const personalCenter=()=>{
      router.push('/userCenter');
    };

    const back=()=>{
      router.push('/login');
    };
    return {
      tableData,
      pickerOptions,
      value1,
      input1,
      input2,
      multipleTable,
      toggleSelection,
      handleSelectionChange,
      handleEdit,
      handleDelete,
      personalCenter,
      back,
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
  z-index: 1; /* Adjusted z-index for header */
  position: relative; /* Added to ensure z-index is applied */
}

.el-aside {
  color: #333;
  z-index: 2; /* Adjusted z-index for aside */
  position: relative; /* Added to ensure z-index is applied */
}

.el-main {
  flex: 1;
  overflow: auto;
}

.menu-with-shadow {
  box-shadow: 5px 0px 6px rgba(0.5, 0.5, 0,0.5); /* 自定义阴影效果，可根据需要调整颜色和大小 */
}

.header-with-shadow {
  box-shadow: 0px 0px 3px rgba(0, 0, 0,0.5); /* 自定义阴影效果，可根据需要调整颜色和大小 */
}

.custom-table .el-table__header th {
  background-color: #e0e0e0; /* 更改背景颜色 */
  color: #6c6c6c; /* 更改文字颜色 */
  text-align: center; /* 居中对齐 */
}

html, body {
  margin: 0;
  padding: 0;
  height: 100%;
}
</style>
