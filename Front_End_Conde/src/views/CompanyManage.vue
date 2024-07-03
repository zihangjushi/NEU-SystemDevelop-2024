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
                <el-menu-item index="2" @click="navigateTo('/')">
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
            <el-button type="primary" @click="searchCompany" style="margin-left: 15px;" icon="Search">搜索</el-button>
            <el-button plain style="margin-left: 15px;" icon="Refresh" @click="handle">重置</el-button>
          </div>

          <!-- 一行中两个按钮，用来删除修改、导出（暂时没有绑定方法） -->
          <el-row style="text-align: left;">
            <el-button type="danger" @click="confirmDelete()" plain icon="Delete" style="margin-top: 5px">删除</el-button>
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
                  <el-button type="success" icon="Edit" @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                  <el-button type="danger" icon="Delete" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-main>

        <!-- 分页器 -->
        <div class="container" style="grid-template-rows: auto 1fr auto;margin-left: 500px">
          <el-pagination background layout="prev, pager, next" :total="totalPages" v-model:current-page="currentPage"
            class="pagination" style="grid-row: 3; margin-bottom: 10px"></el-pagination>
        </div>


        <!-- 租户修改的表单 -->
        <el-dialog v-model="dialogVisible" title="修改租户" :close-on-click-modal="false">

          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="租户名称" prop="companyName">
              <el-input id="companyName" type="text" v-model="companyName" placeholder="请输入租户名称"
                style="width: 500px;height: 31px" />
            </el-form-item>
            <!-- 点击按钮上传图片 -->
            <el-form-item label="租户图标" prop="imageUrl">
              <input type="file" @change="handleFileChange" ref="fileInput" />
              <button type="button" @click="handleSubmit">上传</button>
              <img v-if="previewImageUrl" :src="previewImageUrl" class="avatar"
                style="width: 100px; height: 100px; margin-top: 10px;">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              <button type="button" @click="cancelUpload">取消上传</button>
            </el-form-item>
            <el-form-item label="联系人" prop="contactName">
              <el-input id="contactName" type="text" v-model="contactName" placeholder="请输入联系人"
                style="width: 500px;height: 31px" />
            </el-form-item>
            <el-form-item label="电话" prop="phoneNumber">
              <el-input id="phoneNumber" type="text" v-model="phoneNumber" placeholder="请输入电话号码"
                style="width: 500px;height: 31px" />
            </el-form-item>
            <el-form-item label="管理员" prop="adminName">
              <el-input id="adminName" type="text" v-model="adminName" placeholder="请输入租户管理员"
                style="width: 500px;height: 31px" />
            </el-form-item>
            <el-form-item label="备注" prop="content">
              <Editor v-model="content" editorStyle="height: 320px; width: 700px" placeholder="请输入资讯内容">
                <template v-slot:toolbar>
                  <span class="ql-formats">
                    <select class="ql-size">
                      <option value="small"></option>
                      <option selected></option>
                      <option value="large"></option>
                      <option value="huge"></option>
                    </select>
                    <select class="ql-font">
                      <option selected></option>
                      <option value="serif"></option>
                      <option value="monospace"></option>
                    </select>
                  </span>
                  <span class="ql-formats">
                    <button class="ql-bold"></button>
                    <button class="ql-italic"></button>
                    <button class="ql-underline"></button>
                    <button class="ql-strike"></button>
                    <button class="ql-link"></button>
                    <button class="ql-image"></button>
                    <button class="ql-code-block"></button>
                  </span>
                  <span class="ql-formats">
                    <button class="ql-clean"></button>
                    <button class="ql-align" value=""></button> <!-- 默认对齐 -->
                    <button class="ql-align" value="center"></button> <!-- 居中对齐 -->
                    <button class="ql-align" value="justify"></button> <!-- 两端对齐 -->
                    <button class="ql-align" value="right"></button> <!-- 右对齐 -->
                    <button class="ql-align" value="left"></button> <!-- 左对齐 -->
                    <button class="ql-indent"></button> <!-- 增加缩进 -->
                    <button class="ql-outdent"></button> <!-- 减少缩进 -->
                  </span>

                </template>

              </Editor>
            </el-form-item>
            <el-form-item label="当前租户" prop="tenant">
              <span>{{ tenant }}</span>
            </el-form-item>

          </el-form>

          <!-- 对话框底部按钮 -->
          <template #footer>
            <div class="dialog-footer">
              <el-button @click="clearForm()">清除</el-button>
              <el-button @click="closeDialog()">取消</el-button>
              <el-button type="primary" v-if="isChange === 0" @click="addNews">确认添加</el-button>
              <el-button type="primary" v-else @click="editNews">确认修改</el-button>
            </div>
          </template>

        </el-dialog>

      </el-container>
    </el-container>
  </div>
</template>

<script>
import Editor from 'primevue/editor';
import { onMounted, ref, watch, reactive, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { Management, UserFilled } from "@element-plus/icons-vue";
import { ElMessageBox, ElMessage } from 'element-plus';
import { useStore } from 'vuex';
export default {
  components: { Management, UserFilled, Editor },
  setup() {
    // 用来选择日期和勾选框的组件
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

    //租户查询相关属性
    const inputCompanyId = ref('');
    const inputPhoneNumber = ref('');
    const inputContactName = ref('');
    const inputCompanyName = ref('')

    //租户表格数据显示和分页相关组件
    const companyTable = ref([]);
    const filteredCompanyTable = ref([]);
    const pagedCompanyTable = ref([]);
    const pageSize = ref()
    const currentPage = ref(1)
    const totalPages = computed(() => {
      return filteredCompanyTable.value.length
    })

    //勾选框相关属性
    const multipleTable = ref(null);

    //对话框相关属性
    const dialogVisible = ref(false);
    const selectedFile = ref(null);
    const previewImageUrl = ref('');
    const updateForm = reactive({
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

    // 掌握勾选框属性变化
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
    const routeToUserCenter = () => {
      router.push("/userCenter")
    }
    const addButton = () => {
      if (dialogVisible.value == false) dialogVisible.value = true;
    }

    const clearForm = () => {

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

    //重置按钮
    const handle = () => {
      console.log(currentPage.value)
    }

    // 修改按钮
    const handleEdit = () => {
      if (dialogVisible.value == false) dialogVisible.value = true;
    };

    //勾选框删除
    const confirmDelete = async () => {
      const idsToDelete = pickerOptions.value.multipleSelection.map(row => row.companyId);
      ElMessageBox.confirm(
        '此操作将永久删除该条记录, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).then(() => {
        idsToDelete.forEach(row => {
          let fd = new FormData()
          fd.append("companyId", row)
          axios.post('http://localhost:8070/company/deleteCompanyById', fd)
            .then(response => {
              console.log("删除成功",response)
              initCompanyTable()
          })
        })
        ElMessage.success('删除成功');
      }).catch(() => {
        console.log("删除取消")
      })
    };

    // 行删除按钮
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
        let fd = new FormData()
        fd.append("companyId", row.companyId)
        axios.post("http://localhost:8070/company/deleteCompanyById", fd)
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

    const handleFileChange = (event) => {
				selectedFile.value = event.target.files[0];
				if (!selectedFile.value) return;

				const reader = new FileReader();
				reader.onload = (e) => {
					previewImageUrl.value = e.target.result; // 将读取的图片数据赋值给预览 URL
				};
				reader.readAsDataURL(selectedFile.value);
		};

    //导航到资讯管理页面 
    const routeToNewsManage = () => {
					if (loginUser.value.role === 'admin') {
						router.push('/mynews');
					} else if (loginUser.value.role === 'root') {
						router.push('/news');
					} else {
						alert('无权访问该页面');
					}
    };

    // 返回登录界面
    const back = () => {
      router.push('/login');
    };

    //分页选择显示方法
    const searchUserDataByPage = page => {
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
            (!inputContactName.value || (company.contactName && (company.contactName.includes(inputContactName.value)))) &&
            (!inputPhoneNumber.value || (company.phoneNumber && company.phoneNumber.includes(inputPhoneNumber.value))) &&
            (!inputCompanyName.value || (company.companyName && company.companyName.includes(inputCompanyName.value)))
          );
        });
        searchUserDataByPage(currentPage.value)
      }
    }

    //获取公司的管理员信息


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

      dialogVisible,
      updateForm,

      handle,
      handleEdit,
      handleDelete,
      confirmDelete,

      handleFileChange,

      back,
      searchCompany,

      toggleSelection,
      handleSelectionChange,
      

      // 路由控制相关方法
      routeToUserManage,
      routeToMeetingManage,
      routeToUserCenter,
      routeToNewsManage,

      // 表单相关方法
      addButton,
      clearForm,

      
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