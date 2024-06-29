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
              <el-menu-item @click="routeToCompanyManage">
                租户管理
              </el-menu-item>
              <el-input v-model="searchQuery" style="width: 160px" placeholder="输入以搜索" clearable @input="searchMenu" />
              <el-sub-menu v-for="(company, index) in filteredCompanyList" :key="company.companyId" class="left-align"
                :index="'3-1-' + (index + 1)">
                <template #title>
                  {{ company.companyName }}
                </template>
                <el-menu-item @click="menuControlVisable(company.companyId, department.serialId)"
                  v-for="department in getDepartmentsByCompany(company.companyId)" :key="department.serialId"
                  :index="'3-1-' + (index + 1) + '-' + department.serialId">
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
                <el-button type="text" style="font-size: 15px; color: rgb(0,0,0);position: relative; top: 8px;">{{
                  loginUser.userName }}</el-button>
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
            <!-- 两个文本框及其输入提示信息，分别于inputName和inputPhoneNumber双向绑定 -->
            <span style="color: rgb(150,150,150)"><strong>用户名称</strong></span>
            <el-input v-model="inputUserName" placeholder="请输入内容"
              style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
            <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>手机号码</strong></span>
            <el-input v-model="inputPhoneNumber" placeholder="请输入内容"
              style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
            <!-- 开始时间和结束时间的选择，双向绑定到了inputCreateDat属性（目前只有开始时间 -->
            <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>开始时间</strong></span>
            <el-date-picker v-model="inputCreateDate" type="date" placeholder="选择日期" style="margin-left: 5px;">
            </el-date-picker>
            <!-- 通过用户的状态进行搜索 -->
            <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>用户状态</strong></span>
            <el-select v-model="enabled" clearable placeholder="Select" size="large"
              style="margin-left: 5px;width: 200px;height: 40px;">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
            <!-- 查询和重置按钮 -->
            <el-button type="primary" @click="searchUser" style="margin-left: 15px;" icon="Search">搜索</el-button>
            <el-button plain style="margin-left: 15px;" icon="Refresh" @click="handle">重置</el-button>
          </div>

          <!-- 一行中四个按钮，用来新增、删除修改、导出（暂时没有绑定方法） -->
          <el-row style="text-align: left;">
            <el-button type="primary" @click="addButton" plain icon="Plus" style="margin-top: 5px">新增</el-button>
            <el-button type="danger" @click="handleDelete()" plain icon="Delete" style="margin-top: 5px">删除</el-button>
            <el-button type="warning" plain icon="Download" style="margin-top: 5px">导出</el-button>
          </el-row>

          <!-- 用户菜单 -->
          <div class="custom-table" style="margin-top: 10px;">
            <el-table ref="multipleTable" :data="pagedUserTable" tooltip-effect="dark" style="width: 100%"
              @selection-change="handleSelectionChange">
              <!-- 表单列 -->
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="userId" label="用户编号" width="100" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="realName" label="用户姓名" width="150" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="userName" label="用户昵称" width="150" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="departmentId" label="所属部门" width="150" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="phoneNumber" label="手机号码" width="200" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="enabled" label="状态" width="100" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="createTime" label="创建时间" width="200" header-align="center"
                align="center"></el-table-column>
              <el-table-column prop="operate" label="操作" header-align="center" align="center">
                <!-- template插槽，用于向菜单的最后一列中插入两个操作按钮 -->
                <template #default="scope">
                  <el-button type="success" icon="Edit" @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                  <el-button type="danger" icon="Delete" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-main>

        <!-- 同样的容器，但是是背景图片 -->
        <div class="container" style="grid-template-rows: auto 1fr auto;margin-left: 500px">
          <el-pagination background layout="prev, pager, next" :total="totalPages" v-model:current-page="currentPage"
            class="pagination" style="grid-row: 3; margin-bottom: 10px"></el-pagination>
        </div>

        <!-- //用户添加的表单 -->
        <el-dialog v-model="dialogVisible" title="添加用户" :close-on-click-modal="true" style="height: 650px">
          <el-form ref="form" :model="addForm" :rules="rules" label-width="150px">
            <el-row :gutter="3"> <!-- 这里的 gutter 控制列之间的间距 -->
              <el-col :span="10">
                <el-form-item label="用户昵称" prop="userName" size="large" style="height: 60px">
                  <el-input id="userName" type="text" v-model="addForm.userName" placeholder="请输入用户昵称"
                    style="width: 300px;height: 40px" />
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="归属部门" prop="department" size="large" style="height: 60px">
                  <el-cascader v-model="addForm.department" :options="departmentOptions" placeholder="请选择部门"
                    clearable />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="3"> <!-- 这里的 gutter 控制列之间的间距 -->
              <el-col :span="10">
                <el-form-item label="手机号码" prop="phoneNumber" size="large" style="height: 60px">
                  <el-input id="phoneNumber" type="text" v-model="addForm.phoneNumber" placeholder="请输入手机号码"
                    style="width: 200px;height: 40px" />
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="邮箱" prop="email" size="large" style="height: 60px">
                  <el-input id="email" type="text" v-model="addForm.email" placeholder="请输入邮箱"
                    style="width: 200px;height: 40px" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="3"> <!-- 这里的 gutter 控制列之间的间距 -->
              <el-col :span="10">
                <el-form-item label="用户名称" prop="realName" size="large" style="height: 60px">
                  <el-input id="realName" type="text" v-model="addForm.realName" placeholder="请输入用户名称"
                    style="width: 200px;height: 40px" />
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="用户密码" prop="password" size="large" style="height: 60px">
                  <el-input id="password" type="text" v-model="addForm.password" placeholder="请输入密码"
                    style="width: 200px;height: 40px" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="3"> <!-- 这里的 gutter 控制列之间的间距 -->
              <el-col :span="10">
                <el-form-item label="用户性别" prop="gender" size="large" style="height: 60px">
                  <el-cascader v-model="addForm.gender" :options="genderOptions" placeholder="请选择用户性别" clearable />
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="状态" prop="enabled" size="large" style="height: 60px">
                  <div class="mb-2 flex items-center text-sm">
                    <el-radio-group v-model="addForm.enabled" class="ml-4">
                      <el-radio value="1" size="large">Option 1</el-radio>
                      <el-radio value="2" size="large">Option 2</el-radio>
                    </el-radio-group>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="3"> <!-- 这里的 gutter 控制列之间的间距 -->
              <el-col :span="10">
                <el-form-item label="岗位" prop="career" size="large" style="height: 60px">
                  <el-cascader v-model="addForm.career" :options="careerOptions" placeholder="请选择岗位" clearable />
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="角色" prop="role" size="large" style="height: 60px">
                  <el-cascader v-model="addForm.role" :options="roleOptions" placeholder="请选择权限" clearable />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="3"> <!-- 这里的 gutter 控制列之间的间距 -->
              <el-col :span="10">
                <el-form-item label="公司名称" prop="company" size="large" style="height: 60px">
                  <el-input id="company" type="text" v-model="addForm.companyId" placeholder="请输入公司"
                    style="width: 200px;height: 40px" />
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="描述" prop="description" size="large" style="height: 60px">
                  <el-input v-model="addForm.description" style="width: 200px; height: 60px" :rows="2" type="textarea"
                    placeholder="Please input" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <!-- 对话框底部按钮 -->
          <template #footer>
            <div class="dialog-footer">
              <el-button @click="clearForm()">清除</el-button>
              <el-button @click="closeDialog()">取消</el-button>
              <el-button type="primary" @click="addUser">确认添加</el-button>
            </div>
          </template>
        </el-dialog>



        <!-- 用户修改的表单 -->
        <el-dialog v-model="updateDialogVisible" title="修改用户" :close-on-click-modal="true" style="height: 650px">

          <el-form ref="form" :model="addForm" :rules="rules" label-width="150px">
            <el-row :gutter="3"> <!-- 这里的 gutter 控制列之间的间距 -->
              <el-col :span="10">
                <el-form-item label="用户昵称" prop="userName" size="large" style="height: 60px">
                  <el-input id="userName" type="text" v-model="addForm.userName" placeholder="请输入用户昵称"
                    style="width: 200px;height: 40px" />
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="归属部门" prop="department" size="large" style="height: 60px">
                  <el-cascader v-model="addForm.department" :options="excludedDepartments" placeholder="请选择部门"
                    clearable />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="3"> <!-- 这里的 gutter 控制列之间的间距 -->
              <el-col :span="10">
                <el-form-item label="手机号码" prop="phoneNumber" size="large" style="height: 60px">
                  <el-input id="phoneNumber" type="text" v-model="addForm.phoneNumber" placeholder="请输入手机号码"
                    style="width: 200px;height: 40px" />
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="邮箱" prop="email" size="large" style="height: 60px">
                  <el-input id="email" type="text" v-model="addForm.email" placeholder="请输入邮箱"
                    style="width: 200px;height: 40px" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="3"> <!-- 这里的 gutter 控制列之间的间距 -->
              <el-col :span="10">
                <el-form-item label="用户性别" prop="gender" size="large" style="height: 60px">
                  <el-cascader v-model="addForm.gender" :options="genderOptions" placeholder="请选择用户性别" clearable />
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="状态" prop="enabled" size="large" style="height: 60px">
                  <div class="mb-2 flex items-center text-sm">
                    <el-radio-group v-model="addForm.enabled" class="ml-4">
                      <el-radio value="1" size="large">Option 1</el-radio>
                      <el-radio value="2" size="large">Option 2</el-radio>
                    </el-radio-group>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="3"> <!-- 这里的 gutter 控制列之间的间距 -->
              <el-col :span="10">
                <el-form-item label="岗位" prop="career" size="large" style="height: 60px">
                  <el-cascader v-model="addForm.career" :options="careerOptions" placeholder="请选择岗位" clearable />
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="角色" prop="role" size="large" style="height: 60px">
                  <el-cascader v-model="addForm.role" :options="roleOptions" placeholder="请选择权限" clearable />
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="描述" prop="description" size="large" style="height: 60px">
              <el-input v-model="addForm.description" style="width: 400px; height: 60px" :rows="2" type="textarea"
                placeholder="Please input" />
            </el-form-item>
          </el-form>
          <!-- 对话框底部按钮 -->
          <template #footer>
            <div class="dialog-footer">
              <el-button @click="clearForm()">清除</el-button>
              <el-button @click="closeDialog()">取消</el-button>
              <el-button type="primary" @click="editNews">确认修改</el-button>
            </div>
          </template>
        </el-dialog>

      </el-container>
    </el-container>
  </div>
</template>

<script>
import { onMounted, ref, watch, reactive, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { Management, UserFilled } from "@element-plus/icons-vue";
import { ElMessageBox, ElMessage } from 'element-plus';
import { useStore } from 'vuex';
export default {
  components: { Management, UserFilled },
  setup() {
    // 用来存放数据的表格
    const options = [
      {
        value: '在岗',
        label: '在岗',
      },
      {
        value: '休假',
        label: '休假',
      },
    ]

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

    // 导入路由和vuex存储
    const router = useRouter();
    const store = useStore();

    //登陆者相关属性
    const loginUser = ref('')

    //用户查询相关属性
    const inputUserName = ref('');
    const inputPhoneNumber = ref('');
    const inputCreateDate = ref('');
    const enabled = ref('')

    //彩蛋搜索相关属性
    const searchQuery = ref('')
    const excludedDepartments = ['运维部门', '测试部门', '财务部门', '研发部门', '市场部门'];

    //用户数据显示和分页相关组件
    const userTable = ref([]);
    const filteredUserTable = ref([]);
    const pagedUserTable = ref([]);
    const pageSize = ref()
    const currentPage = ref(1)
    const totalPages = computed(() => {
      return filteredUserTable.value.length
    })

    //菜单相关属性
    const companyList = ref([]);
    const filteredCompanyList = ref([]);
    const departmentList = ref([]);
    const filteredDepartmentList = ref([]);
    const multipleTable = ref(null);

    //对话框相关属性
    const dialogVisible = ref(false);
    const updateDialogVisible = ref(false);
    const addForm = reactive({
      companyId: '',
      userName: '',
      department: '',
      phoneNumber: '',
      email: '',
      realName: '',
      password: '',
      gender: '',
      enabled: '1',
      career: '',
      role: '',
      description: ''
    })
    const genderOptions = [{
      value: 0,
      label: "男"
    }, {
      vluse: 1,
      label: "女"
    }]
    const careerOptions = [{
      value: '项目经理',
      label: '项目经理'
    }, {
      value: '测试',
      label: '测试'
    }, {
      value: '运维',
      label: '运维'
    }, {
      value: '开发',
      label: '开发'
    }]
    const roleOptions = [{
      value: 'root',
      label: '超级管理员'
    }, {
      value: 'admin',
      label: '租户管理员'
    }, {
      value: 'user',
      label: '普通用户'
      }]
    const departmentOptions = [{
      value: '1',
      label:'研发部门'
    },{
      value: '2',
      label:'市场部门'
    },{
      value: '3',
      label:'测试部门'
    },{
      value: '4',
      label:'财务部门'
    },{
      value: '5',
      label:'运维部门'
    },]
    // const addRules = reactive({
    //   name: [{ required: true, message: 'Please input name', trigger: 'blur' }],
    //   age: [{ required: true, message: 'Please input age', trigger: 'blur' }],
    //   sex: [{ required: true, message: 'Please select sex', trigger: 'change' }]
    // });

    //勾选框相关方法
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
      // console.log(pickerOptions.value.multipleSelection)
    };

    const addButton = () => {
      if (dialogVisible.value == false) dialogVisible.value = true;
    }

    const clearForm = () => {
      console.log("role", addForm.role)
    }

    // const addUser = () => {
    //   if (loginUser.data.role == root) {

    //   } else if (loginUser.data.role == admin) {
    //     let fd = new FormData()
    //     fd.append("addForm", addForm)
    //     fd.append("companyId")
    //     axios()
    //   }

    // }

    const confirmDelete = () => {
      
    }

    //重置按钮
    const handle = () => {
      console.log(totalPages.value)
    }

    // 修改按钮
    const handleEdit = () => {
      if (updateDialogVisible.value == false) updateDialogVisible.value = true;
    };

    // 删除按钮
    const handleDelete = (index, row) => {
      ElMessageBox.confirm(
        '此操作将永久删除该条记录, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).then(() => {
        console.log("删除行", row.userName)
        let fd = new FormData()
        fd.append("userId", row.userId)
        axios.post("http://localhost:8070/user/deleteUserById", fd)
          .then(response => {
            if (response.data.isOk) {
              ElMessage({
                showClose: true,
                message: '删除成功！',
                type: 'success'
              });
            }
            initUserTable()
          })
          .catch(error => {
            console.log(error)
          })
      }).catch(() => {
        console.log("删除取消")
      })
    };

    // 路由到用户中心
    const personalCenter = () => {
      router.push('/userCenter');
    };

    // 返回登录界面
    const back = () => {
      router.push('/login');
    };

    const routeToCompanyManage = () => {
      router.push("/CompanyManage")
    }

    //分页选择显示方法
    const searchUserDataByPage = page => {
      pagedUserTable.value = filteredUserTable.value.slice((page - 1) * 10, (10 * page));
    }

    //用户搜索相关方法
    const searchUser = () => {
      // 检查所有输入框是否都为空
      if (!inputUserName.value && !inputPhoneNumber.value && !inputCreateDate.value && !enabled.value) {
        // 如果都为空，则重置 filteredUserTable 为 userTable 的值
        filteredUserTable.value = userTable.value;
      } else {
        // 否则，根据输入的条件进行过滤
        filteredUserTable.value = userTable.value.filter(user => {
          return (
            (!inputUserName.value || (user.userName && user.userName.includes(inputUserName.value))) &&
            (!inputPhoneNumber.value || (user.phoneNumber && user.phoneNumber.includes(inputPhoneNumber.value))) &&
            (!inputCreateDate.value || (user.createTime && user.createTime.includes(inputCreateDate.value))) &&
            (!enabled.value || (user.enabled && user.enabled === enabled.value))
          );
        });
      }
    }

    //用户管理菜单选择方法
    const menuControlVisable = (companyId, serialId) => {
      let fd = new FormData;
      fd.append("companyId", companyId)
      fd.append("departmentId", serialId)
      axios.post("http://localhost:8070/user/searchByCompanyInformation", fd)
        .then(response => {
          if (response.data.isOk) filteredUserTable.value = response.data.users;
          console.log(companyId, serialId)
          searchUserDataByPage(currentPage.value)
        })
    }

    //用户管理菜单搜索方法
    const searchMenu = () => {
      if (!searchQuery.value) {
        filteredCompanyList.value = companyList.value
        filteredDepartmentList.value = departmentList.value
      } else if (!isDepartmentMatch(searchQuery.value)) {
        filteredCompanyList.value = companyList.value.filter(company =>
          company.companyName.includes(searchQuery.value)
        )
      } else {
        console.log(searchQuery.value)
        let fd = new FormData();
        fd.append("query", searchQuery.value);
        axios.post("http://localhost:8070/menu/search", fd)
          .then(response => {
            if (response.data.isOk) {
              filteredCompanyList.value = response.data.companies
              filteredDepartmentList.value = response.data.departments
              console.log("菜单部门数据查询成功", filteredCompanyList)
            }
          })
      }
    }

    //根据公司获取底下部门信息
    const getDepartmentsByCompany = (companyId) => {
      return filteredDepartmentList.value.filter(department => department.companyId === companyId);
    }

    //判断输入框内容是否为部门信息
    const isDepartmentMatch = (query) => {
      for (const department of excludedDepartments) {
        if (query === department || department.includes(query)) return true;
      }
      return false;
    }

    // 用来将函数初始加载到companyList数组中
    const initCompanyList = () => {
      if (loginUser.value.role == "root") {

        axios.get("http://localhost:8070/company/list")
          .then(response => {
            if (response.data.isOk) {
              console.log("根管理员菜单公司数据初始化成功")
              companyList.value = response.data.companies;
              filteredCompanyList.value = companyList.value
            }
          })
      } else if (loginUser.value.role == "admin") {
        let fd = new FormData();
        fd.append("companyId", loginUser.value.companyId);
        axios.post("http://localhost:8070/company/searchByCompanyId", fd)
          .then(response => {
            if (response.data.isOk) {
              console.log("管理员菜单公司数据初始化成功")
              companyList.value = response.data.company;
              filteredCompanyList.value = companyList.value
            }
          })
      }
    }

    //初始化部门列表信息
    const initDepartmentList = () => {
      // if (loginUser.value.role === "root") {
      axios.get("http://localhost:8070/department/list")
        .then(response => {
          if (response.data.isOk) {
            console.log("菜单部门数据初始化成功")
            departmentList.value = response.data.departments;
            filteredDepartmentList.value = departmentList.value
          }
        })
    }

    //初始化用户表格和相关属性
    const initUserTable = () => {
      if (loginUser.value.role == "root") {
        axios.get("http://localhost:8070/user/list")
          .then(response => {
            if (response.data.isOk) {
              response.data.users.forEach(user => {
                if (user.enabled == 1) {
                  user.enabled = "在岗"
                }
                else user.enabled = "休假"
              })
              console.log("表格用户数据读取成功")
              userTable.value = response.data.users;
              filteredUserTable.value = userTable.value;
            } else {
              console.error('表格用户数据读取失败', response);
            }
          })
      } else if (loginUser.value.role == "admin") {
        let fd = new FormData();
        fd.append("companyId", loginUser.value.companyId);
        axios.post("http://localhost:8070/user/searchByCompanyId", fd)
          .then(response => {
            if (response.data.isOk) {
              response.data.users.forEach(user => {
                if (user.enabled == 1) {
                  user.enabled = "在岗"
                }
                else user.enabled = "休假"
              })
              console.log("表格用户数据读取成功")
              userTable.value = response.data.users;
              filteredUserTable.value = userTable.value;
            } else {
              console.error('表格用户数据读取失败', response);
            }
          })
      }
    }

    //监测当前页变化，并进行分页内容显示
    watch(currentPage, (newPage) => {
      searchUserDataByPage(newPage)
    });

    //钩子函数，在浏览器渲染页面时执行
    onMounted(async () => {
      await Promise.all([
        loginUser.value = store.state.user,
        initCompanyList(),
        initDepartmentList(),
        initUserTable(),
      ]),
        setTimeout(searchUserDataByPage(currentPage.value), 1000)
    });

    // setup的返回值
    return {
      loginUser,

      options,
      pickerOptions,
      multipleTable,

      inputUserName,
      inputPhoneNumber,
      inputCreateDate,
      enabled,

      userTable,
      filteredUserTable,
      pagedUserTable,
      pageSize,
      currentPage,
      totalPages,

      companyList,
      filteredCompanyList,
      departmentList,
      filteredDepartmentList,
      searchQuery,
      excludedDepartments,

      dialogVisible,
      updateDialogVisible,
      addForm,
      genderOptions,
      roleOptions,
      careerOptions,
      departmentOptions,

      handle,
      menuControlVisable,
      toggleSelection,
      handleSelectionChange,
      confirmDelete,

      addButton,
      clearForm,

      handleEdit,
      handleDelete,
      personalCenter,
      back,
      routeToCompanyManage,
      searchUser,
      isDepartmentMatch,
      getDepartmentsByCompany,
      searchMenu,
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