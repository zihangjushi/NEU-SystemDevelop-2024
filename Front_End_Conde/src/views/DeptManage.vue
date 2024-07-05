<template>
  <div class="about">
    <el-container style="height: 100vh; border: 1px solid #eee">
      <!-- 侧边栏 -->
      <el-aside class="menu-with-shadow" width="200px" style="color: rgb(255,255,255)">
        <el-menu :default-openeds="['3']">
          <el-menu-item index="1">
            <!-- logo和测盟汇 -->
            <template #title>
              <img src="@/assets/logo1.png" style="width: 40px; height: 40px; margin-right: 5px;">
              <span style="font-size: 20px;"><strong>测盟汇</strong></span>
            </template>
            <!-- logo和测盟汇 -->
          </el-menu-item>

          <el-menu-item index="2" >
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
				<el-menu-item index="3-1" @click="routeToCompanyManage"><el-icon>
				<OfficeBuilding />
				</el-icon>租户管理</el-menu-item>
              <el-menu-item index="3-2" @click="navigateTo('/userManage')">
                <el-icon>
                  <UserFilled/>
                </el-icon>
                用户管理
              </el-menu-item>
              <el-menu-item index="3-3" @click="navigateTo('/')">
                <el-icon>
                  <Management/>
                </el-icon>
                部门管理
              </el-menu-item>
              <el-menu-item index="3-4" @click="routeToNewsManage">
                <el-icon>
                  <Orange/>
                </el-icon>
                行业动态管理
              </el-menu-item>
              <el-menu-item index="3-5" @click="navigateTo('/')">
                <el-icon>
                  <List/>
                </el-icon>
                课程管理
              </el-menu-item>
              <el-menu-item index="3-6" @click="navigateTo('/meeting')">
                <el-icon>
                  <TrendCharts/>
                </el-icon>
                会议管理
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <!-- 管理目录 -->

        </el-menu>
      </el-aside>
      <!-- 侧边栏 -->

      <el-container direction="vertical">
        <!-- 头部 -->
        <el-header class="header-with-shadow" style="font-size: 12px">
          <div style="display: flex; justify-content: space-between; width: 100%;">
            <div style="text-align: left;position: relative; top: 20px;">
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item><a href="http://localhost:8080/dept">部门管理</a></el-breadcrumb-item>
                <el-breadcrumb-item>部门列表</el-breadcrumb-item>
                <!-- <el-breadcrumb-item>部门详情</el-breadcrumb-item> -->
              </el-breadcrumb>
            </div>
            <el-dropdown :hide-on-click="false">
              <div style="display: flex; align-items: center;">
                <el-avatar icon="UserFilled" style="font-size: 20px; margin-right: 10px;position: relative; top: 8px;"></el-avatar>
                <el-button type="text" style="font-size: 15px; color: rgb(0,0,0);position: relative; top: 8px;">{{
                    loginUser.userName
                  }}
                </el-button>
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
        <!-- 头部 -->

        <el-main>
          <div style="text-align: left;">
            <span style="color: rgb(150,150,150)"><strong>部门名称</strong></span>
            <el-input v-model="inputDeptname" placeholder="请输入内容" style="margin-left: 5px;width: 200px;height: 30px;"></el-input>

            <span style="color: rgb(150,150,150)"><strong>状态</strong></span>
            <el-select v-model="inputDeptstate" placeholder="请选择状态" style="margin-left: 5px; width: 200px; height: 30px;">
              <el-option label="正常" :value="1"></el-option>
              <el-option label="停用" :value="0"></el-option>
            </el-select>

            <el-button type="primary" @click="searchDept" style="margin-left: 15px;" icon="Search">搜索</el-button>
            <el-button plain style="margin-left: 15px;" icon="Refresh" @click="resetForm">重置</el-button>
          </div>

          <div>
            <el-row style="text-align: left;">
              <el-button type="primary" @click="openCompanyAddDialog" plain icon="Plus" style="margin-top: 5px">
                新增租户
              </el-button>
            </el-row>
            <!-- 表格 -->
            <el-table
                :data="tableData"
                style="width: 100%; margin-bottom: 20px"
                row-key="id"
                border
            >
              <el-table-column prop="name" sortable label="名称" width="auto"></el-table-column>
              <el-table-column prop="contactName" sortable v-if="!hiddenColumns.includes('contactName')"></el-table-column>
              <el-table-column prop="contactName" sortable v-if="!hiddenColumns.includes('departmentName')"></el-table-column>
              <el-table-column prop="phoneNumber" sortable v-if="!hiddenColumns.includes('phoneNumber')"></el-table-column>
              <el-table-column prop="adminName" sortable v-if="!hiddenColumns.includes('adminName')"></el-table-column>
              <el-table-column prop="sort" sortable label="排序" width="auto"></el-table-column>
              <el-table-column label="状态" width="200">

                <template #default="scope">
                  <el-popover effect="light" trigger="hover" placement="top" width="auto">
                    <template #default>
                      <div>状态: {{ scope.row.status }}</div>
                    </template>
                    <template #reference>
                      <el-tag v-if="scope.row.status === 1">正常</el-tag>
                      <el-tag v-else-if="scope.row.status === 0" style="color: red;">停用</el-tag>
                    </template>
                  </el-popover>
                </template>
              </el-table-column>
              <el-table-column prop="createTime" sortable label="创建时间" width="auto"></el-table-column>
              <el-table-column label="操作" width="auto">
                <template #default="scope">
                  <el-button type="primary" size = "small" icon="Plus" @click="openDepartmentAddDialog(scope.$index, scope.row)">新增
                  </el-button>
                  <el-button type="success" size = "small" icon="Edit" @click="openEditDialog(scope.$index, scope.row)">修改
                  </el-button>
                  <el-button type="danger" size = "small" icon="Delete" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-main>
        <!-- 分页 -->
        <div class="container" style="grid-template-rows: auto 1fr auto;margin-left: 500px">
          <el-pagination align='center'
                         style="grid-row: 3;margin-bottom: 10px"
                         background
                         class="pagination"
                         @current-change="handleCurrentChange"
                         :current-page="pageNum"
                         :page-size="pageSize"
                         layout="prev, pager, next"
                         :total="total">
            >
          </el-pagination>
        </div>
        <!-- 分页 -->
        <!--公司 新增 | 修改-->
        <el-dialog v-model="dialogCompanyFormVisible" title="新增租户" width="500">
          <el-form :model="companyForm">
            <el-form-item label="租户名称" :label-width="formLabelWidth">
              <el-input v-model="companyForm.companyName" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="联系电话" :label-width="formLabelWidth">
              <el-input v-model="companyForm.phoneNumber" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="联系人" :label-width="formLabelWidth">
              <el-input v-model="companyForm.contactName" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="管理员" :label-width="formLabelWidth">
              <el-input v-model="companyForm.adminName" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="状态" :label-width="formLabelWidth">
              <el-radio-group v-model="companyForm.state" placeholder="状态" class="ml-4">
                <el-radio :value="1" size="large">正常</el-radio>
                <el-radio :value="0" size="large">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form>
          <template #footer>
            <div class="dialog-footer">
              <el-button @click="dialogCompanyFormVisible = false">取消</el-button>
              <el-button type="primary" @click="handleRequest">
                确定
              </el-button>
            </div>
          </template>
        </el-dialog>
        <!--部门 新增 | 修改-->
        <el-dialog v-model="dialogDepartmentFormVisible" title="新增部门" width="500">
          <el-form :model="departmentForm">
            <el-form-item label="部门名称" :label-width="formLabelWidth">
              <el-input v-model="departmentForm.departmentName" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="联系电话" :label-width="formLabelWidth">
              <el-input v-model="departmentForm.phoneNumber" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="管理员" :label-width="formLabelWidth">
              <el-input v-model="departmentForm.adminName" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="状态" :label-width="formLabelWidth">
              <el-radio-group v-model="departmentForm.state" placeholder="状态" class="ml-4">
                <el-radio :value="1" size="large">正常</el-radio>
                <el-radio :value="0" size="large">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form>
          <template #footer>
            <div class="dialog-footer">
              <el-button @click="dialogDepartmentFormVisible = false">取消</el-button>
              <el-button type="primary" @click="handleRequest">
                确定
              </el-button>
            </div>
          </template>
        </el-dialog>
      </el-container>
    </el-container>
  </div>
</template>


<script>
import {reactive, ref, onMounted, getCurrentInstance} from 'vue';
import {HomeFilled, List, Management, Menu, Orange, TrendCharts, UserFilled} from "@element-plus/icons-vue";
import axios from 'axios';
import {useStore} from "vuex";
import {ElMessage, ElMessageBox} from "element-plus";
import {useRouter} from 'vue-router';

export default {
  components: {Management, UserFilled, HomeFilled, Menu, Orange, List, TrendCharts},
  setup() {
    const tableData = ref([]);
    const loading = ref(false);
    const {proxy} = getCurrentInstance();

    const router = useRouter();
    const total = ref(0);
    const pageNum = ref(1);
    const pageSize = ref(10);
    const inputDeptname = ref('');
    const inputDeptstate = ref('');


    const requestModel = ref('companyAdd')
    const formLabelWidth = '140px'
    const hiddenColumns = ref(['contactName', 'phoneNumber', 'adminName', 'departmentName'])// 初始隐藏的列

    const dialogDepartmentFormVisible = ref(false)
    const dialogCompanyFormVisible = ref(false)
    const companyForm = reactive({
      companyId: 0,
      contactName: '',
      companyName: '',
      phoneNumber: '',
      adminName: '',
      state: 1,
      description: '',
    })

    const store = useStore();
    const loginUser = ref('')
    onMounted(async () => {
      await Promise.all([
        loginUser.value = store.state.user,
      ])
    });

    const departmentForm = reactive({
      departmentId: 0,
      serialId: 0,
      companyId: 0,
      departmentName: '',
      phoneNumber: '',
      adminName: '',
      state: 1,
      description: '',
    })


    const loadData = async () => {
      loading.value = true;
      try {
        const res = await axios.get("http://localhost:8070/company/lists", {
          params: {
            inputDeptname: inputDeptname.value,
            inputDeptstate: inputDeptstate.value,
            pageNum: pageNum.value,
            pageSize: pageSize.value
          }
        });
        const {isOk, data, dataTotal} = res.data
        if (isOk) {
          // 数据处理
          let temp = []
          data.forEach(item => {
            if (inputDeptstate.value === 1) {
              if (item.status !== 0) {
                temp.push(item)
              }
            } else if (inputDeptstate.value === 0) {
              //item.children = []
              temp.push(item)
            } else {
              temp.push(item)
            }
          })

          tableData.value = temp;
          console.log('tableData', tableData.value)
          console.log('total', dataTotal)
          total.value = dataTotal;
        } else {
          tableData.value = [];
          total.value = 0;
        }
      } catch (error) {
        console.error('Error loading data:', error);
        tableData.value = [];
        total.value = 0;
      } finally {
        loading.value = false;
      }
    };

//添加公司的方法
    const openCompanyAddDialog = () => {
      console.log('添加公司')
      if (loginUser.value.role === 'user' || loginUser.value.role === 'admin') {
        proxy.$message.warning('很遗憾!您没有权限添加部门信息!');
        return;
      }
      dialogCompanyFormVisible.value = true; // 打开弹窗
      requestModel.value = 'companyAdd'
      // 清空表单数据
      companyForm.companyName = '';
      companyForm.phoneNumber = '';
      companyForm.contactName = '';
      companyForm.adminName = '';
      companyForm.state = 1; // 默认状态为正常
    };

//添加部门方法
    const openDepartmentAddDialog = (index, row) => {
      console.log('添加部门', index, row)
      if (loginUser.value.role === 'user') {
        proxy.$message.warning('很遗憾!您没有权限添加部门信息!');
        return;
      }
      dialogDepartmentFormVisible.value = true; // 打开弹窗
      requestModel.value = 'departmentAdd'
      // 清空表单数据
      departmentForm.companyId = row.id;
      departmentForm.departmentName = '';
      departmentForm.phoneNumber = '';
      departmentForm.adminName = '';
      departmentForm.state = 1;
    };



    const openEditDialog = (index, row) => {
      console.log('编辑', index, row)
      if (loginUser.value.role === 'user') {
        proxy.$message.warning('很遗憾!您没有权限修改部门信息!');
        return;
      }
      //公司修改
      if (row.children !== null) {
        if (loginUser.value.role === 'admin') {
        proxy.$message.warning('很遗憾!您没有权限修改该子部门信息!');
        return;
      }
        dialogCompanyFormVisible.value = true;
        requestModel.value = 'companyUpdate'
        // 将已有信息填入表单
        companyForm.companyId = row.id;
        companyForm.companyName = row.name;
        companyForm.phoneNumber = row.phoneNumber;
        companyForm.contactName = row.contactName;
        companyForm.adminName = row.adminName;
        companyForm.state = row.status;
      } else { // 部门修改
        //部门权限未做
        dialogDepartmentFormVisible.value = true;
        requestModel.value = 'departmentUpdate'
        departmentForm.departmentId = row.id;
        departmentForm.departmentName = row.name;
        departmentForm.phoneNumber = row.phoneNumber;
        departmentForm.adminName = row.adminName;
        departmentForm.state = row.status;
        departmentForm.serialId = row.sort;
      }
    };


    const handleRequest = async () => {
      if (requestModel.value.includes('company')) {
        console.log('formData', companyForm);
        if (!companyForm) return;
        const res = requestModel.value === 'companyAdd' ? await axios.post('http://localhost:8070/company/add', {...companyForm}) : await axios.put('http://localhost:8070/company/update', {...companyForm});
        const {isOk, data, msg} = res
        console.log(msg, data)
        // if (isOk) {
        //   ElMessage.success(msg)
        // } else {
        //   ElMessage.error(msg);
        // }
        dialogCompanyFormVisible.value = false; // 关闭弹窗
      } else {
        console.log('formData', departmentForm);
        if (!departmentForm) return;
        const res = requestModel.value === 'departmentAdd' ? await axios.post('http://localhost:8070/department/add', {...departmentForm}) : await axios.put('http://localhost:8070/department/update', {...departmentForm});
        const {isOk, data, msg} = res
        console.log(msg, data)
        // if (isOk) {
        //   ElMessage.success(msg)
        // } else {
        //   ElMessage.error(msg);
        // }
        dialogDepartmentFormVisible.value = false; // 关闭弹窗
      }
      await loadData()
      window.location.reload()
    }


    const searchDept = async () => {
      await loadData();

    };

    const handleCurrentChange = (val) => {
      console.log(`当前页: ${val}`);
      pageNum.value = val;
      loadData();
    }


    const resetForm = async () => {
      inputDeptname.value = '';
      inputDeptstate.value = '';
      await loadData();
    };

    const personalCenter = () => {
      // 实现个人中心逻辑
      router.push('/userCenter');
    };

    const back = () => {
      // 实现退出登录逻辑
      router.push('/login');
    };


    const handleDelete = (index, row) => {
      ElMessageBox.confirm(
          '确认是否删除',
          '删除数据',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            // 实现删除逻辑
            if (loginUser.value.role === 'user') {
              proxy.$message.warning('很遗憾!您没有权限添加部门信息!');
              return;
            }
            if (row.children !== null) {
              if (loginUser.value.role === 'admin') {
                proxy.$message.warning('很遗憾!您没有权限添加部门信息!');
                return;
              }
              axios.delete('http://localhost:8070/company/delete/' + row.id,).then(res => {
                const {isOk, msg} = res.data
                if (isOk) {
                  ElMessage.success(msg)
                } else {
                  ElMessage.error(msg);
                }
              })

            } else {
              axios.delete('http://localhost:8070/department/delete/' + row.id,).then(res => {
                const {isOk, msg} = res.data
                if (isOk) {
                  ElMessage.success(msg)
                } else {
                  ElMessage.error(msg);
                }
              })
            }
            loadData()
            window.location.reload()
          })
    };

    loadData();

    const navigateTo = (routeName) => {
      router.push( routeName );
    };
    
    return {
      dialogDepartmentFormVisible,
      dialogCompanyFormVisible,
      tableData,
      loading,
      total,
      pageNum,
      pageSize,
      inputDeptname,
      inputDeptstate,
      formLabelWidth,
      companyForm,
      departmentForm,
      hiddenColumns,
      handleCurrentChange,
      openCompanyAddDialog,
      openEditDialog,
      openDepartmentAddDialog,
      handleRequest,
      searchDept,
      resetForm,
      personalCenter,
      back,
      store,
      navigateTo,
      handleDelete
    };
  }
};
</script>
<!-- // import { Management, UserFilled, HomeFilled, Menu, Orange, List, TrendCharts } from "@element-plus/icons-vue"; -->

<style scoped>
.el-container {
  display: flex;
  height: 100%;
}

.el-header, .el-footer {
  flex: 0 0 auto;
}

.el-main {
  flex: 1 1 auto;
  overflow: auto;
}

.menu-with-shadow {
  box-shadow: 5px 0px 6px rgba(0, 0, 0, 0.5);
}

.header-with-shadow {
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.5);
}

.custom-table .el-table__header th {
  background-color: #e0e0e0;
  color: #6c6c6c;
  text-align: center;
}

html, body {
  margin: 0;
  padding: 0;
  height: 100%;
}
</style>
