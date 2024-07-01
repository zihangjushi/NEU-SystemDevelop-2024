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
                    <el-menu-item index="3-1" @click="routeToCompanyManage"><el-icon>
                            <OfficeBuilding />
                        </el-icon>租户管理</el-menu-item>
                    <el-menu-item index="3-2" @click="routeToUserManage"><el-icon>
                            <UserFilled />
                        </el-icon>用户管理</el-menu-item>
                    <el-menu-item index="3-3" @click="routeToDepartmentManage"><el-icon>
                            <Management />
                        </el-icon>部门管理</el-menu-item>
                    <el-menu-item index="3-4" @click="routeToNewsManage"><el-icon>
                            <Orange />
                        </el-icon>行业动态管理</el-menu-item>
                    <el-menu-item index="3-5" @click="routeToCourseManage" ><el-icon>
                            <List />
                        </el-icon>课程管理</el-menu-item>
                    <el-menu-item index="3-6" @click="routeToMeetingManage"><el-icon>
                            <TrendCharts />
                        </el-icon>会议管理</el-menu-item>
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
                                style="font-size: 15px; color: rgb(0,0,0);position: relative; top: 8px;">{{
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

                <!-- 搜索框，用来进行搜索 -->
                <div style="text-align: left;">
                    <!--租户标识的输入框 -->
                    <span style="color: rgb(150,150,150)"><strong>租户标识</strong></span>
                    <el-input v-model="inputCompanyId" placeholder="请输入内容"
                        style="margin-left: 5px;width: 200px;height: 30px;"></el-input>

                    <!-- 联系人姓名的输入框 -->
                    <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>联系人</strong></span>
                    <el-input v-model="inputContactName" placeholder="请输入内容"
                        style="margin-left: 5px;width: 200px;height: 30px;"></el-input>

                    <!--电话号码的输入框 -->
                    <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>电话</strong></span>
                    <el-input v-model="inputPhoneNumber" placeholder="请输入内容"
                        style="margin-left: 5px;width: 200px;height: 30px;"></el-input>

                    <!-- 租户名称的输入框 -->
                    <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>租户名称</strong></span>
                    <el-input v-model="inputCompanyName" placeholder="请输入内容"
                        style="margin-left: 5px;width: 200px;height: 30px;"></el-input>

                    <!-- 查询和重置按钮 -->
                    <el-button type="primary" @click="searchCompany" style="margin-left: 15px;"
                        icon="Search">搜索</el-button>
                    <el-button plain style="margin-left: 15px;" icon="Refresh" @click="handle">重置</el-button>
                </div>

                <!-- 一行中两个按钮，用来删除修改、导出（暂时没有绑定方法） -->
                <el-row style="text-align: left;">
                    <el-button type="danger" @click="handleDelete()" plain icon="Delete"
                        style="margin-top: 5px">删除</el-button>
                    <el-button type="warning" plain icon="Download" style="margin-top: 5px">导出</el-button>
                </el-row>

                <!-- 用户菜单 -->
                <div class="custom-table" style="margin-top: 10px;">
                    <el-table ref="multipleTable" :data="pagedCompanyTable" tooltip-effect="dark" style="width: 100%"
                        @selection-change="handleSelectionChange">
                        <!-- 表单列 -->
                        <el-table-column type="selection" width="55"></el-table-column>
                        <el-table-column prop="companyId" label="租户标识" width="200" header-align="center"
                            align="center"></el-table-column>
                        <el-table-column prop="contactName" label="联系人" width="200" header-align="center"
                            align="center"></el-table-column>
                        <el-table-column prop="phoneNumber" label="电话" width="250" header-align="center"
                            align="center"></el-table-column>
                        <el-table-column prop="companyName" label="租户名称" width="250" header-align="center"
                            align="center"></el-table-column>
                        <el-table-column prop="adminName" label="管理员" width="200" header-align="center"
                            align="center"></el-table-column>
                        <el-table-column prop="operate" label="操作" header-align="center" align="center">
                            <!-- template插槽，用于向菜单的最后一列中插入两个操作按钮 -->
                            <template #default="scope">
                                <el-button type="success" icon="Edit"
                                    @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                                <el-button type="danger" icon="Delete"
                                    @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </el-main>

            <!-- 分页器 -->
            <div class="container" style="grid-template-rows: auto 1fr auto;margin-left: 500px">
                <el-pagination background layout="prev, pager, next" :total="totalPages"
                    v-model:current-page="currentPage" class="pagination"
                    style="grid-row: 3; margin-bottom: 10px"></el-pagination>
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
                                <el-cascader v-model="addForm.department" :options="departmentOptions"
                                    placeholder="请选择部门" clearable />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="3"> <!-- 这里的 gutter 控制列之间的间距 -->
                        <el-col :span="10">
                            <el-form-item label="手机号码" prop="phoneNumber" size="large" style="height: 60px">
                                <el-input id="phoneNumber" type="text" v-model="addForm.phoneNumber"
                                    placeholder="请输入手机号码" style="width: 200px;height: 40px" />
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
                                <el-cascader v-model="addForm.gender" :options="genderOptions" placeholder="请选择用户性别"
                                    clearable />
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
                                <el-cascader v-model="addForm.career" :options="careerOptions" placeholder="请选择岗位"
                                    clearable />
                            </el-form-item>
                        </el-col>
                        <el-col :span="10">
                            <el-form-item label="角色" prop="role" size="large" style="height: 60px">
                                <el-cascader v-model="addForm.role" :options="roleOptions" placeholder="请选择权限"
                                    clearable />
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
                                <el-input v-model="addForm.description" style="width: 200px; height: 60px" :rows="2"
                                    type="textarea" placeholder="Please input" />
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
            <el-dialog v-model="updateDialogVisible" title="修改用户" :close-on-click-modal="true"
                style="height: 650px">

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
                                <el-cascader v-model="addForm.department" :options="excludedDepartments"
                                    placeholder="请选择部门" clearable />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row :gutter="3"> <!-- 这里的 gutter 控制列之间的间距 -->
                        <el-col :span="10">
                            <el-form-item label="手机号码" prop="phoneNumber" size="large" style="height: 60px">
                                <el-input id="phoneNumber" type="text" v-model="addForm.phoneNumber"
                                    placeholder="请输入手机号码" style="width: 200px;height: 40px" />
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
                                <el-cascader v-model="addForm.gender" :options="genderOptions" placeholder="请选择用户性别"
                                    clearable />
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
                                <el-cascader v-model="addForm.career" :options="careerOptions" placeholder="请选择岗位"
                                    clearable />
                            </el-form-item>
                        </el-col>
                        <el-col :span="10">
                            <el-form-item label="角色" prop="role" size="large" style="height: 60px">
                                <el-cascader v-model="addForm.role" :options="roleOptions" placeholder="请选择权限"
                                    clearable />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item label="描述" prop="description" size="large" style="height: 60px">
                        <el-input v-model="addForm.description" style="width: 400px; height: 60px" :rows="2"
                            type="textarea" placeholder="Please input" />
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
    const inputCompanyId = ref('');
    const inputPhoneNumber = ref('');
    const inputContactName = ref('');
    const inputCompanyName = ref('')

    //彩蛋搜索相关属性
    const searchQuery = ref('')
    const excludedDepartments = ['运维部门', '测试部门', '财务部门', '研发部门', '市场部门'];

    //用户数据显示和分页相关组件
    const companyTable = ref([]);
    const filteredCompanyTable = ref([]);
    const pagedCompanyTable = ref([]);
    const pageSize = ref()
    const currentPage = ref(1)
    const totalPages = computed(() => {
        return filteredCompanyTable.value.length
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
        label: '研发部门'
    }, {
        value: '2',
        label: '市场部门'
    }, {
        value: '3',
        label: '测试部门'
    }, {
        value: '4',
        label: '财务部门'
    }, {
        value: '5',
        label: '运维部门'
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

    //路由相关方法
    const routeToUserManage = () => {
        router.push('/userManage')
    }
    const routeToMeetingManage = () => {
        router.push("/userManage")
    }

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
        console.log(currentPage.value)
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
                    initCompanyTable()
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

    //分页选择显示方法
  const searchUserDataByPage = page => {
        console.log(page, filteredCompanyTable.value.slice((page - 1) * 10, (10 * page)))
        pagedCompanyTable.value = filteredCompanyTable.value.slice((page - 1) * 10, (10 * page));
    }

    //公司搜索相关方法
    const searchCompany = () => {
        // 检查所有输入框是否都为空
        if (!inputCompanyId.value && !inputContactName.value && !inputPhoneNumber.value && !inputCompanyName.value) {
            // 如果都为空，则重置 filteredCompanyTable 为 userTable 的值
          filteredCompanyTable.value = companyTable.value;
          searchUserDataByPage(currentPage.value)
        } else {
            // 否则，根据输入的条件进行过滤
            filteredCompanyTable.value = companyTable.value.filter(company => {
                return (
                    (!inputCompanyId.value || (company.companyId && company.companyId == inputCompanyId.value)) &&
                    (!inputContactName.value || (company.companyName &&(company.companyName.includes(inputCompanyName.value)))) &&
                    (!inputPhoneNumber.value || (company.phoneNumber && company.phoneNumber.includes(inputPhoneNumber.value))) &&
                    (!inputCompanyName.value || (company.companyName && company.companyName === inputCompanyName.value))
                );
            });
            searchUserDataByPage(currentPage.value)
        }
    }

    //根据公司获取底下部门信息
    const getDepartmentsByCompany = (companyId) => {
        return filteredDepartmentList.value.filter(department => department.companyId === companyId);
    }

    //初始化公司表格和相关属性
    const initCompanyTable = () => {
            axios.get("http://localhost:8070/company/list")
                .then(response => {
                    if (response.data.isOk) {
                        console.log("表格公司数据读取成功")
                        companyTable.value = response.data.companies;
                      filteredCompanyTable.value = companyTable.value;
                      searchUserDataByPage(currentPage.value)
                    } else {
                        console.error('表格用户数据读取失败', response);
                    }
                })
    }

    //监测当前页变化，并进行分页内容显示
    watch(currentPage, (newPage) => {
        searchUserDataByPage(newPage)
    });

    //钩子函数，在浏览器渲染页面时执行
    onMounted(async () => {
        await Promise.all([
            loginUser.value = store.state.user,
            initCompanyTable(),
        ])
    });

    // setup的返回值
    return {
        loginUser,

        options,
        pickerOptions,
        multipleTable,

        inputCompanyId,
        inputContactName,
        inputPhoneNumber,
        inputCompanyName,

        companyTable,
        filteredCompanyTable,
        pagedCompanyTable,
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
        toggleSelection,
        handleSelectionChange,
        confirmDelete,

        // 路由控制相关方法
        routeToUserManage,
        routeToMeetingManage,

        addButton,
        clearForm,

        handleEdit,
        handleDelete,
        personalCenter,
        back,
        searchCompany,
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