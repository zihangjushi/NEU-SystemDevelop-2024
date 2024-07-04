<template>
    <div class="about">
        <el-container style="height: 100vh; border: 1px solid #eee">
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
                        <el-menu-item @click="routeToCompanyManage"><el-icon>
                                <OfficeBuilding />
                            </el-icon>租户管理</el-menu-item>
                        <el-sub-menu>
                            <template #title>
                                <el-icon>
                                    <UserFilled />
                                </el-icon>
                                <span>用户管理</span>
                            </template>

                            <el-input v-model="searchQuery" style="width: 160px" placeholder="输入以搜索" clearable
                                @input="searchMenu" />
                            <el-sub-menu v-for="(company, index) in filteredCompanyList" :key="company.companyId"
                                class="left-align" :index="'3-1-' + (index + 1)">

                                <template #title>
                                    {{ company.companyName }}
                                </template>
                                <el-menu-item @click="menuControlVisable(company.companyId, department.serialId)"
                                    v-for="department in getDepartmentsByCompany(company.companyId)"
                                    :key="department.serialId"
                                    :index="'3-1-' + (index + 1) + '-' + department.serialId">
                                    {{ department.departmentName }}
                                </el-menu-item>
                            </el-sub-menu>

                        </el-sub-menu>
                        <el-menu-item index="3-2"><el-icon>
                                <Management />
                            </el-icon>部门管理</el-menu-item>
                        <el-menu-item index="3-3" @click="gotoNewsManage"><el-icon>
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

            <el-container>
                <el-header class="header-with-shadow" style="font-size: 12px">
                    <div style="display: flex; justify-content: space-between; width: 100%;">
                        <div style="text-align: left;position: relative; top: 20px;">
                            <!-- 用来存放路径信息的头部栏，可以进行路径的跳转 -->
                            <el-breadcrumb separator="/">
                                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                                <el-breadcrumb-item><a href="/">课程管理</a></el-breadcrumb-item>
                                <el-breadcrumb-item>课程列表</el-breadcrumb-item>
                                <el-breadcrumb-item>课程详情</el-breadcrumb-item>
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

                <el-main>

                    <div style="text-align: left;">
                        <!-- 两个文本框及其输入提示信息，分别于searchCourseName和searchAuthor双向绑定 -->
                        <span style="color: rgb(150,150,150)"><strong>课程名称</strong></span>
                        <el-input v-model="searchCourseName" placeholder="请输入内容"
                            style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
                        <span style="color: rgb(150,150,150)"><strong>课程排序</strong></span>
                        <el-input v-model="searchCourseOrder" placeholder="请输入内容"
                            style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
                        <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>作者</strong></span>
                        <el-input v-model="searchAuthor" placeholder="请输入内容"
                            style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
                        <br>
                        <!-- 开始时间和结束时间的选择 -->
                        <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>开始时间</strong></span>
                        <el-date-picker v-model="searchBeginTime" type="date" placeholder="选择日期"
                            style="margin-left: 5px;">
                        </el-date-picker>
                        <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>结束时间</strong></span>
                        <el-date-picker v-model="searchEndTime" type="date" placeholder="选择日期"
                            style="margin-left: 5px;">
                        </el-date-picker>
                        <!-- 查询和重置按钮 -->
                        <el-button type="primary" style="margin-left: 15px;" icon="Search"
                            @click="searchCourse">搜索</el-button>
                        <el-button plain style="margin-left: 15px;" icon="Refresh"
                            @click="refreshCoursesList">重置</el-button>
                    </div>

                    <!-- 一行中四个按钮，用来新增、删除修改、导出（暂时没有绑定方法） -->
                    <el-row style="text-align: left;">
                        <el-button type="primary" plain icon="Plus" style="margin-top: 5px"
                            @click="dialogAddCourseVisible = true">新增</el-button>
                        <el-button type="danger" plain icon="Delete" style="margin-top: 5px"
                            @click="deleteCourse">删除</el-button>
                        <el-button type="warning" plain icon="Download" style="margin-top: 5px"
                            @click="exportCourse">导出</el-button>
                    </el-row>

                    <el-dialog v-model="dialogAddCourseVisible" title="添加课程" width="700">
                        <el-form :ref="form" :model="form" :rules="rules">
                            <el-form-item label="课程名称" prop="courseName" :label-width="formLabelWidth">
                                <el-input v-model="courseForm.courseName" autocomplete="off" />
                            </el-form-item>
                            <el-form-item label="课程封面" prop="imageUrl">
                                <input type="file" @change="handleImageChange" ref="imageInput" />
                                <img v-if="previewImageUrl" :src="previewImageUrl" class="avatar"
                                    style="width: 100px; height: 100px; margin-top: 10px;">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                <button type="button" @click="cancelImageUpload">取消上传</button>
                            </el-form-item>
                            <el-form-item label="课程简介" prop="description" :label-width="formLabelWidth">
                                <el-input v-model="courseForm.description" autocomplete="off" type="textarea" />
                            </el-form-item>
                            <el-form-item label="课程排序" prop="courseOrder" :label-width="formLabelWidth">
                                <el-input v-model="courseForm.courseOrder" autocomplete="off" type="textarea" />
                            </el-form-item>
                            <el-form-item label="课程视频" prop="videoUrl">
                                <input type="file" @change="handleVideoChange" ref="videoInput" />
                                <img v-if="previewVideoUrl" :src="previewVideoUrl" class="avatar"
                                    style="width: 100px; height: 100px; margin-top: 10px;">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                <button type="button" @click="cancelVideoUpload">取消上传</button>
                            </el-form-item>
                            <el-form-item label="课程作者" prop="author" :label-width="formLabelWidth">
                                <el-input v-model="courseForm.author" autocomplete="off" />
                            </el-form-item>
                        </el-form>
                        <template #footer>
                            <div class="dialog-footer">
                                <el-button @click="clearForm()">清除</el-button>
                                <el-button @click="closeDialog()">取消</el-button>
                                <el-button type="primary" @click="addCourse">确认添加</el-button>
                            </div>
                        </template>
                    </el-dialog>

                    <!-- 修改dialog -->
                    <el-dialog v-model="dialogEditCourseVisible" title="修改课程" width="700">
                        <el-form :ref="form" :model="form" :rules="rules">
                            <el-form-item label="课程名称" prop="courseName" :label-width="formLabelWidth">
                                <el-input v-model="courseForm.courseName" autocomplete="off" />
                            </el-form-item>
                            <el-form-item label="课程封面" prop="imageUrl">
                                <input type="file" @change="handleImageChange" ref="imageInput" />
                                <img v-if="previewImageUrl" :src="previewImageUrl" class="avatar"
                                    style="width: 100px; height: 100px; margin-top: 10px;">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                <button type="button" @click="cancelImageUpload">取消上传</button>
                            </el-form-item>
                            <el-form-item label="课程简介" prop="description" :label-width="formLabelWidth">
                                <el-input v-model="courseForm.description" autocomplete="off" type="textarea" />
                            </el-form-item>
                            <el-form-item label="课程排序" prop="courseOrder" :label-width="formLabelWidth">
                                <el-input v-model="courseForm.courseOrder" autocomplete="off" type="textarea" />
                            </el-form-item>
                            <el-form-item label="课程视频" prop="videoUrl">
                                <input type="file" @change="handleVideoChange" ref="videoInput" />
                                <img v-if="previewVideoUrl" :src="previewVideoUrl" class="avatar"
                                    style="width: 100px; height: 100px; margin-top: 10px;">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                <button type="button" @click="cancelVideoUpload">取消上传</button>
                            </el-form-item>
                            <el-form-item label="课程作者" prop="author" :label-width="formLabelWidth">
                                <el-input v-model="courseForm.author" autocomplete="off" />
                            </el-form-item>
                        </el-form>
                        <template #footer>
                            <div class="dialog-footer">
                                <el-button @click="clearForm()">清除</el-button>
                                <el-button @click="closeDialog()">取消</el-button>
                                <el-button type="primary" @click="editCourse">确认修改</el-button>
                            </div>
                        </template>
                    </el-dialog>

                    <!-- 课程菜单 -->
                    <div class="custom-table" style="margin-top: 10px;">
                        <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%"
                            @selection-change="handleSelectionChange">
                            <!-- 表单列 -->
                            <el-table-column type="selection" width="55"></el-table-column>
                            <el-table-column prop="courseId" label="主键ID" width="100" header-align="center"
                                align="center"></el-table-column>
                            <el-table-column prop="courseName" label="课程名称" width="150" header-align="center"
                                align="center"></el-table-column>
                            <el-table-column prop="description" label="课程简介" width="300" header-align="center"
                                align="center"></el-table-column>
                            <el-table-column prop="courseOrder" label="课程排序" width="100" header-align="center"
                                align="center"></el-table-column>
                            <el-table-column prop="companyName" label="所属公司" width="150" header-align="center"
                                align="center"></el-table-column>
                            <el-table-column prop="author" label="作者" width="100" header-align="center"
                                align="center"></el-table-column>
                            <el-table-column prop="createTime" label="开始时间" width="300" header-align="center"
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

            </el-container>
        </el-container>
    </div>
</template>

<script>
import { reactive, ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { Management, UserFilled } from "@element-plus/icons-vue";
import {
    ElMessage,
    ElMessageBox
} from 'element-plus';
import axios from 'axios';
// import { ElMessage } from 'element-plus';
import { useStore } from 'vuex';

export default {
    components: { Management, UserFilled },
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

        const routeToNewsManage = () => {
            if (loginUser.value.role === 'admin') {
                router.push('/mynews');
            } else if (loginUser.value.role === 'root') {
                router.push('/news');
            } else {
                alert('无权访问该页面');
            }
        };

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
        const store = useStore();
        const loginUser = ref('')
        onMounted(async () => {
            await Promise.all([
                loginUser.value = store.state.user,

            ])
        });


        const navigateTo = (routeName) => {
            router.push(routeName);
        };

        const dialogAddCourseVisible = ref(false);
        const dialogEditCourseVisible = ref(false);
        const courseForm = reactive({
            courseId: '',
            courseName: '',
            companyName: '',
            description: '',
            courseOrder: '',
            author: '',
            createTime: '',
            modifyTime: '',
            imageUrl: '',
            videoUrl: '',
        });

        const rules = {
            courseName: [
                {
                    required: true,
                    message: '请输入课程名称',
                    trigger: ['blur', 'change']
                },
                {
                    whitespace: true,
                    message: '课程名不能为空',
                    trigger: 'blur'
                }
            ],
            imageUrl: [
                {
                    required: true,
                    message: '请上传封面图片',
                    trigger: ['blur', 'change']
                }
            ],
            description: [
                {
                    required: true,
                    message: '请输入课程简介',
                    trigger: ['blur', 'change']
                },
                {
                    whitespace: true,
                    message: '课程简介不能为空',
                    trigger: 'blur'
                }
            ],
            courseOrder: [
                {
                    required: true,
                    message: '请输入课程排序',
                    trigger: ['blur', 'change']
                },
                {
                    whitespace: true,
                    message: '课程排序不能为空',
                    trigger: 'blur'
                }
            ],
            videoUrl: [
                {
                    required: true,
                    message: '请输入视频',
                    trigger: ['blur', 'change']
                },
                {
                    whitespace: true,
                    message: '视频不能为空',
                    trigger: 'blur'
                }
            ],
            author: [
                {
                    required: true,
                    message: '请输入作者',
                    trigger: ['blur', 'change']
                },
                {
                    whitespace: true,
                    message: '作者不能为空',
                    trigger: 'blur'
                }
            ]
        };

        // 用来存放数据的表格
        const tableData = ref([]);


        const clearForm = () => {
            cancelImageUpload();
            cancelVideoUpload();
            courseForm.courseName = '';
            courseForm.description = '';
            courseForm.courseOrder = '';
            courseForm.author = '';
        };

        const closeDialog = () => {
            dialogAddCourseVisible.value = false;
            dialogEditCourseVisible.value = false;
        };

        const formatDateTime = (isoString) => {
            const date = new Date(isoString);

            // 数据库存的是本地时间, 所以不用偏移量
            // // UTC+8 时区偏移量（8 小时 = 8 * 60 分钟 = 480 分钟）
            // const offset = 8 * 60 * 60 * 1000;
            const offset = 0;

            // 将时间转换为 UTC+8
            const localDate = new Date(date.getTime() + offset);

            const year = localDate.getUTCFullYear();
            const month = String(localDate.getUTCMonth() + 1).padStart(2, '0');
            const day = String(localDate.getUTCDate()).padStart(2, '0');
            const hours = String(localDate.getUTCHours()).padStart(2, '0');
            const minutes = String(localDate.getUTCMinutes()).padStart(2, '0');
            const seconds = String(localDate.getUTCSeconds()).padStart(2, '0');

            return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
            // return isoString;
        };

        const formatTableData = () => {
            tableData.value = tableData.value.map(item => ({
                courseId: item.courseId,
                courseName: item.courseName,
                companyName: item.companyName,
                description: item.description,
                courseOrder: item.courseOrder,
                author: item.author,
                createTime: formatDateTime(item.createTime),
                modifyTime: formatDateTime(item.modifyTime),
                imageUrl: item.imageUrl,
                videoUrl: item.videoUrl,
            }));
        };

        const addCourse = () => {
            if (!courseForm.courseName || !courseForm.description || !courseForm.courseOrder || !courseForm.author || !previewImageUrl.value || !previewVideoUrl.value) {
                // alert(courseForm.courseName);
                // alert(courseForm.companyName);
                // alert(courseForm.description);
                // alert(courseForm.courseOrder);
                // alert(courseForm.author);
                // alert(previewImageUrl.value);
                // alert(previewVideoUrl.value);

                // errorMessage.value = '请输入完整后添加';
                // errorDialogVisible.value = true;
                return; // 如果有空字段，直接返回，不执行后续的添加操作
            }

            // alert(courseForm.courseId);
            // alert(courseForm.courseName);
            // alert(courseForm.companyName);
            // alert(courseForm.description);
            // alert(courseForm.courseOrder);
            // alert(courseForm.author);
            // alert(previewImageUrl.value);
            // alert(previewVideoUrl.value);

            const requestData = {
                courseName: courseForm.courseName,
                description: courseForm.description,
                courseOrder: courseForm.courseOrder,
                author: courseForm.author,
                imageUrl: previewImageUrl.value,
                videoUrl: previewVideoUrl.value,
            };

            axios.post('http://localhost:8070/course/add', requestData)
                .then(response => {
                    console.log('新增课程成功', response.data);
                    refreshCoursesList();
                    dialogAddCourseVisible.value = false;
                })
                .catch(error => {
                    console.error('新增课程失败', error);
                });
        };

        // 课程列表
        const refreshCoursesList = () => {
            axios.get('http://localhost:8070/course/list')
                .then(response => {
                    tableData.value = response.data.courses;
                    formatTableData();

                    // updatePagedData(tableData.value); // 更新分页数据的函数，假设已定义
                })
                .catch(error => {
                    console.error('获取课程列表失败', error);
                });
        };


        const searchCourse = () => {
            const requestData = {
                courseName: searchCourseName.value,
                courseOrder: searchCourseOrder.value,
                author: searchAuthor.value,
                // 这里复用course, 将开始时间作为createTime, 结束时间作为modifyTime
                createTime: searchBeginTime.value,
                modifyTime: searchEndTime.value,
            };

            axios.post('http://localhost:8070/course/search', requestData)
                .then(response => {
                    console.log('查询课程成功', response.data);
                    loadCoursesList(response);
                    dialogAddCourseVisible.value = false;
                })
                .catch(error => {
                    console.error('新增课程失败', error);
                });
        }

        const loadCoursesList = (response) => {
            tableData.value = response.data.courses;
            formatTableData();
        };

        const searchBeginTime = ref('');
        const searchEndTime = ref('');
        const searchCourseName = ref('');
        const searchAuthor = ref('');
        const searchCourseOrder = ref('');
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
            selectedRows.value = val;
        };

        const handleEdit = async (index, row) => {
            // console.log(index, row);
            const res = await axios.post('http://localhost:8070/course/searchById', {
                Id: row.courseId,
            });
            courseForm.courseId = res.data.course.courseId;
            setImageUpload(res);
            setVideoUpload(res);
            courseForm.courseName = res.data.course.courseName;
            courseForm.description = res.data.course.description;
            courseForm.courseOrder = res.data.course.courseOrder;
            courseForm.author = res.data.course.author;
            dialogEditCourseVisible.value = true;
        };

        const editCourse = async () => {
            // courseForm.courseId = res.data.course.courseId;
            // setImageUpload(res);
            // setVideoUpload(res);
            // courseForm.courseName = res.data.course.courseName;
            // courseForm.description = res.data.course.description;
            // courseForm.courseOrder = res.data.course.courseOrder;
            // courseForm.author = res.data.course.author;
            const response = await axios.post('http://localhost:8070/course/edit', {
                courseId: courseForm.courseId,
                courseName: courseForm.courseName,
                description: courseForm.description,
                courseOrder: courseForm.courseOrder,
                author: courseForm.author,
                imageUrl: previewImageUrl.value,
                videoUrl: previewVideoUrl.value,
            });
            searchCourse();
            dialogEditCourseVisible.value = false;
        }

        const handleDelete = async (index, row) => {
            const response = await axios.post('http://localhost:8070/course/deleteOne', {
                courseId: row.courseId,
            });
            if (response.data.isOk) {
                alert('删除成功');
                searchCourse();
            } else {
                alert('删除失败');
            }
        };



        const deleteCourse = () => {
            ElMessageBox.confirm(
                '此操作将删除所选课程，是否继续？',
                '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }
            ).then(async () => {
                // 用户点击确定
                const idsToDelete = selectedRows.value.map(row => row.courseId);
                const response = await axios.post('http://localhost:8070/course/deleteList', {
                    ids: idsToDelete
                });
                if (response.data.isOk) {
                    alert('删除成功');
                } else {
                    alert('删除失败');
                }
                searchCourse();
            }).catch(() => {
                // 用户点击取消
                ElMessage.info('已取消删除');
            });
        };

        const exportCourse = () => {

        };





        // image
        const selectedImage = ref(null);
        const previewImageUrl = ref('');
        const selectedRows = ref([]);

        const imageUrl = ref('');

        const handleImageChange = (event) => {
            selectedImage.value = event.target.files[0];
            if (!selectedImage.value) return;

            const reader = new FileReader();
            reader.onload = (e) => {
                previewImageUrl.value = e.target.result; // 将读取的图片数据赋值给预览 URL
            };
            reader.readAsDataURL(selectedImage.value);
        };

        const cancelImageUpload = () => {
            selectedImage.value = null; // 清空已选择的图片
            previewImageUrl.value = ''; // 清空预览图片 URL
            imageUrl.value = '';
            const imageInput = document.querySelectorAll('input[type="file"]')[0];
            if (imageInput) {
                imageInput.value = '';
            }
        };

        const setImageUpload = (res) => {
            selectedImage.value = res.data.course.imageUrl;
            previewImageUrl.value = res.data.course.imageUrl;
            imageUrl.value = res.data.course.imageUrl;
            // const imageInput = document.querySelectorAll('input[type="file"]')[2];
            // imageInput.value = res.data.course.imageUrl;
        };

        // video
        const selectedVideo = ref(null);
        const previewVideoUrl = ref('');

        const videoUrl = ref('');

        const handleVideoChange = (event) => {
            selectedVideo.value = event.target.files[0];
            if (!selectedVideo.value) return;

            const reader = new FileReader();
            reader.onload = (e) => {
                previewVideoUrl.value = e.target.result; // 将读取的图片数据赋值给预览 URL
            };
            reader.readAsDataURL(selectedVideo.value);
        };

        const cancelVideoUpload = () => {
            selectedVideo.value = null; // 清空已选择的视频
            previewVideoUrl.value = ''; // 清空预览视频 URL
            videoUrl.value = '';
            const videoInput = document.querySelectorAll('input[type="file"]')[1];
            if (videoInput) {
                videoInput.value = '';
            }
        };

        const setVideoUpload = (res) => {
            selectedVideo.value = res.data.course.videoUrl;
            previewVideoUrl.value = res.data.course.videoUrl;
            videoUrl.value = res.data.course.videoUrl;
            // const videoInput = document.querySelectorAll('input[type="file"]')[1];
            // videoInput.value = res.data.course.videoUrl;
        };

        return {
            userInfo,
            departmentInfo,
            basicInfoForm,
            passwordForm,
            activeTab,
            personalCenter,
            back,
            navigateTo,
            loginUser,
            routeToNewsManage,
            store,
            useStore,
            loginUser,
            tableData,
            searchBeginTime,
            searchEndTime,
            searchCourseName,
            searchAuthor,
            searchCourseOrder,
            multipleTable,
            toggleSelection,
            handleSelectionChange,
            handleEdit,
            editCourse,
            handleDelete,
            personalCenter,
            back,
            addCourse,
            deleteCourse,
            exportCourse,
            dialogAddCourseVisible,
            dialogEditCourseVisible,
            courseForm,
            clearForm,
            closeDialog,
            rules,
            selectedRows,
            // image
            selectedImage,
            previewImageUrl,
            imageUrl,
            handleImageChange,
            cancelImageUpload,
            setImageUpload,
            // video
            selectedVideo,
            previewVideoUrl,
            videoUrl,
            handleVideoChange,
            cancelVideoUpload,
            setVideoUpload,
            refreshCoursesList,
            loadCoursesList,
            formatDateTime,
            formatTableData,
            searchCourse,
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
