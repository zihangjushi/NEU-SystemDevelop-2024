<template>
	<div class="about">
		<!-- 添加资讯对话框 -->
		<el-dialog v-model="dialogVisible" title="添加资讯" :close-on-click-modal="false">

			<el-form ref="form" :model="form" :rules="rules" label-width="100px">
				<el-form-item label="资讯标题" prop="title">
					<el-input id="title" type="text" v-model="title" placeholder="请输入新闻标题"
						style="width: 500px;height: 31px" />
				</el-form-item>
				<!-- 点击按钮上传图片 -->
				<el-form-item label="上传图片" prop="imageUrl">
					<input type="file" @change="handleFileChange" ref="fileInput" />
					<button type="button" @click="handleSubmit">上传</button>
					<img v-if="previewImageUrl" :src="previewImageUrl" class="avatar"
						style="width: 100px; height: 100px; margin-top: 10px;">
					<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					<button type="button" @click="cancelUpload">取消上传</button>
				</el-form-item>
				<el-form-item label="资讯内容" prop="content">
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
				<el-form-item label="作者" prop="author">
					<el-input id="author" type="text" v-model="author" placeholder="请输入作者"
						style="width: 500px;height: 31px" />
				</el-form-item>
				<el-form-item label="新闻简介" prop="summary">
					<el-input id="summary" type="text" v-model="summary" placeholder="请输入新闻简介"
						style="width: 500px;height: 31px" />
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

		<!-- 成功/失败提示框 -->
		<el-dialog v-model="successDialogVisible" title="提示" :close-on-click-modal="false">
			<p>{{ successMessage }}</p>
			<span slot1="footer" class="dialog-footer">
				<el-button type="primary" @click="successDialogVisible = false">确定</el-button>
			</span>
		</el-dialog>
		<el-dialog v-model="errorDialogVisible" title="错误提示" :close-on-click-modal="false">
			<p>{{ errorMessage }}</p>
			<span slot2="footer" class="dialog-footer">
				<el-button type="primary" @click="errorDialogVisible = false">确定</el-button>
			</span>
		</el-dialog>


		<el-container style="height: 100vh; border: 1px solid #eee">
			<el-aside class="menu-with-shadow" width="200px" style="color: rgb(255,255,255)">
				<el-menu :default-openeds="['3']" :default-active="'3-3'">
					<el-menu-item index="1">
						<template #title>
							<img src="@/assets/logo1.png" style="width: 40px; height: 40px; margin-right: 5px;">
							<span style="font-size: 20px;"><strong>测盟汇</strong></span>
						</template>
					</el-menu-item>

					<el-menu-item index="2">
						<template #title>
							<el-icon>
								<HomeFilled />
							</el-icon>首页
						</template>
					</el-menu-item>

					<el-sub-menu index="3">
						<template #title><el-icon>
								<Menu />
							</el-icon>管理</template>
						<el-menu-item-group>
							<el-menu-item index="3-1" @click="gotoCompanyManage"><el-icon>
							<OfficeBuilding />
							</el-icon>租户管理</el-menu-item>
							<el-menu-item index="3-2" @click="gotoUserManage"><el-icon>
									<UserFilled />
								</el-icon>用户管理</el-menu-item>
							<el-menu-item index="3-3"><el-icon>
									<Management />
								</el-icon>部门管理</el-menu-item>
							<el-menu-item index="3-4" ><el-icon>
									<Orange />
								</el-icon>行业动态管理</el-menu-item>
							<el-menu-item index="3-5" ><el-icon>
									<List />
								</el-icon>课程管理</el-menu-item>
							<el-menu-item index="3-6" @click="gotoMeeting"><el-icon>
									<TrendCharts />
								</el-icon>会议管理</el-menu-item>
						</el-menu-item-group>
					</el-sub-menu>
				</el-menu>
			</el-aside>

			<el-container>
				<el-header class="header-with-shadow" style="font-size: 12px">
					<div style="display: flex; justify-content: space-between; width: 100%;">
						<div style="text-align: left;position: relative; top: 20px;">
							<el-breadcrumb separator="/">
								<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
								<el-breadcrumb-item><a href="/">行业动态管理</a></el-breadcrumb-item>
								<el-breadcrumb-item>列表</el-breadcrumb-item>
								<el-breadcrumb-item>详情</el-breadcrumb-item>
							</el-breadcrumb>
						</div>
						<el-dropdown :hide-on-click="false">
							<div style="display: flex; align-items: center;">
								<el-avatar icon="UserFilled"
									style="font-size: 20px; margin-right: 10px;position: relative; top: 8px;"></el-avatar>
								<el-button type="text"
									style="font-size: 15px; color: rgb(0,0,0);position: relative; top: 8px;">徐洋</el-button>
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
					<div style="text-align: left;">
						<span style="color: rgb(150,150,150)"><strong>新闻标题</strong></span>
						<el-input v-model="input1" placeholder="请输入内容"
							style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
						<span style="color: rgb(150,150,150);margin-left: 15px;"><strong>作者</strong></span>
						<el-input v-model="input2" placeholder="请输入内容"
							style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
						<span style="color: rgb(150,150,150);margin-left: 15px;"><strong>新闻简介</strong></span>
						<el-input v-model="input3" placeholder="请输入内容"
							style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
						<span style="color: rgb(150,150,150);margin-left: 15px;"><strong>选择日期</strong></span>
						<el-date-picker v-model="value1" type="date" placeholder="选择日期" style="margin-left: 5px;">
						</el-date-picker>
						<el-button type="primary" style="margin-left: 15px;" icon="Search"
							@click="searchNews">搜索</el-button>
						<el-button plain style="margin-left: 15px;" icon="Refresh" @click="resetSearch">重置</el-button>
					</div>
					<el-row style="text-align: left;">
						<el-button type="primary" plain icon="Plus" style="margin-top: 5px"
							@click="handleAdd">新增</el-button>
						<!-- 						<el-button type="success" plain icon="Edit" style="margin-top: 5px"
							@click="handleEditNews">修改</el-button> -->
						<el-button type="danger" plain icon="Delete" style="margin-top: 5px"
							@click="deleteNews">删除</el-button>
						<el-button type="warning" plain icon="Download" style="margin-top: 5px"
							@click="exportNews">导出</el-button>
					</el-row>
					<div class="custom-table" style="margin-top: 10px;">
						<el-table ref="multipleTable" :data="pagedTableData" tooltip-effect="dark" style="width: 100%"
							@selection-change="handleSelectionChange">
							<el-table-column type="selection" width="55"></el-table-column>
							<el-table-column prop="newsId" label="ID" width="100" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="title" label="新闻标题" width="300" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="author" label="作者" width="300" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="introduction" label="新闻简介" width="400" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="createTime" label="发布时间" width="100" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="operate" label="操作" header-align="center" align="center">
								<template #default="scope">
									<el-button size="mini" type="success" icon="Edit"
										@click="handleEdit(scope.row)">修改</el-button>
									<el-button size="mini" type="danger" icon="Delete"
										@click="handleDelete(scope.row)">删除</el-button>
								</template>
							</el-table-column>
						</el-table>
					</div>
				</el-main>
				<div class="container" style="grid-template-rows: auto 1fr auto;margin-left: 500px">
					<el-pagination background layout="prev, pager, next" :total="total" :page-size="10"
						@current-change="handleCurrentChange" class="pagination"
						style="grid-row: 3;margin-bottom: 10px"></el-pagination>
				</div>
			</el-container>
		</el-container>
	</div>
</template>

<script>
	import Editor from 'primevue/editor';
	import {
		ref,
		onMounted
	} from 'vue';
	import {
		useRouter
	} from 'vue-router';
	import {
		Management,
		UserFilled
	} from '@element-plus/icons-vue';
	import axios from 'axios';
	import dayjs from 'dayjs';
	import {
		ElMessage,ElMessageBox
	} from 'element-plus';
	import {
		saveAs
	} from 'file-saver';
	import * as XLSX from 'xlsx';
    import { useStore } from 'vuex';
	export default {
		components: {
			Management,
			UserFilled,
			Editor
		},
		setup() {
						const store = useStore();
			const router = useRouter();
			const isChange = ref(0);
			// 响应式数据
			const dialogVisible = ref(false); // 添加资讯对话框可见性
			const successDialogVisible = ref(false); // 成功提示框可见性
			const errorDialogVisible = ref(false); // 错误提示框可见性
			const successMessage = ref(''); // 成功提示信息
			const errorMessage = ref(''); // 错误提示信息
			const selectedFile = ref(null);
			const previewImageUrl = ref('');
			const selectedRows = ref([]);

			const title = ref('');
			const imageUrl = ref('');
			const content = ref('');
			const author = ref('');
			const summary = ref('');
			const tenant = ref('default');

			const editId = ref('');




			const companies = ref([]);

			const fetchCompanies = async () => {
				try {
					const response = await axios.get('http://localhost:9000/user/companies');
					companies.value = response.data.companies;
					console.log(companies.value);
				} catch (error) {
					console.error('Error fetching companies:', error);
				}
			};

			const handleUploadClick = () => {
				const fileInput = document.getElementById('fileInput');
				if (fileInput) {
					fileInput.click();
				}
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

			const handleSubmit = () => {
				if (selectedFile.value) {
					const formData = new FormData();
					formData.append('file', selectedFile.value);

					axios.post('http://localhost:9000/upload', formData, {
							headers: {
								'Content-Type': 'multipart/form-data'
							}
						})
						.then(response => {
							imageUrl.value = response.data;
						})
						.catch(() => {
							errorMessage.value = '图片上传失败';
							errorDialogVisible.value = true;
						});
				}
			};

			// 表单验证规则
			const rules = {
				title: [{
						required: true,
						message: '请输入资讯标题',
						trigger: ['blur', 'change']
					},
					{
						whitespace: true,
						message: '资讯标题不能为空格',
						trigger: 'blur'
					},
				],
				imageUrl: [{
					required: true,
					message: '请上传封面图片',
					trigger: ['blur', 'change']
				}, ],
				content: [{
					required: true,
					message: '请输入资讯内容',
					trigger: ['blur', 'change']
				}, ],
				author: [{
						required: true,
						message: '请输入作者',
						trigger: ['blur', 'change']
					},
					{
						whitespace: true,
						message: '作者不能为空格',
						trigger: 'blur'
					},
				],
				summary: [{
						required: true,
						message: '请输入新闻简介',
						trigger: ['blur', 'change']
					},
					{
						whitespace: true,
						message: '新闻简介不能为空格',
						trigger: 'blur'
					},
				],
				tenant: [{
					required: true,
					message: '请选择租户',
					trigger: ['blur', 'change']
				}, ],
			};


			const handleAdd = () => {
				// 打开对话框
				isChange.value = 0;
				openDialog();
			};

			const openDialog = () => {
				// 打开对话框
				dialogVisible.value = true;

			};

			const closeDialog = () => {

				dialogVisible.value = false; // 关闭对话框
			};
			// 表格数据
			const tableData = ref([

			]);
			const total = ref(1);
			const isLoggedIn = ref(false);
			const loginUser = ref('');
			onMounted(async () => {

loginUser.value = store.state.user;

				try {
					const userResponse = await axios.get('http://localhost:9000/user/info', {
						withCredentials: true
					});

					if (userResponse.data.isLogin) {
						tenant.value = userResponse.data.companyname;
						isLoggedIn.value = true;
						
						try {
							const response = await axios.get('http://localhost:9000/news/mynews', {
								withCredentials: true,
								params: {
									companyName: tenant.value
								}
							});
						
							if (!response.data || !response.data.news) {
								ElMessage.warning('用户未登录或未获取到数据');
								return;
							}
						
							tableData.value = response.data.news; // 假设后端返回的数据是一个包含新闻信息的数组
							//total.value=tableData.value.length;
							updatePagedData(tableData.value);
						} catch (error) {
							console.error('获取新闻列表失败', error);
							ElMessage.error('获取新闻列表失败，请稍后重试');
						}
					} else {
						isLoggedIn.value = false;
						alert('用户未登录');
					}
				} catch (error) {
					console.error('获取用户信息失败', error);
					isLoggedIn.value = false;
					alert('用户未登录');
				}



			});

			function refreshNewsList() {
				axios.get('http://localhost:9000/news/mynews', {
						withCredentials: true,
						params: {
							companyName: tenant.value
						}
					})
					.then(response => {
						tableData.value = response.data.news; // 假设后端返回的数据是一个包含新闻信息的数组
						updatePagedData(tableData.value); // 更新分页数据的函数，假设已定义
					})
					.catch(error => {
						console.error('获取新闻列表失败', error);
						// 可以在这里处理获取新闻列表失败的情况，比如显示错误信息给用户
					});
			}



			const value1 = ref('');
			const input1 = ref('');
			const input2 = ref('');
			const input3 = ref('');
			const multipleTable = ref(null);

			// 方法定义
			const toggleSelection = (rows) => {
				if (rows) {
					rows.forEach((row) => {
						multipleTable.value.toggleRowSelection(row);
					});
				} else {
					multipleTable.value.clearSelection();
				}
			};

			const handleSelectionChange = (val) => {
				selectedRows.value = val;
			};


const newsData = ref([]);

			const handleEdit = async (row) => {
				isChange.value = 1;
				editId.value = row.newsId;
				const response = await axios.get('http://localhost:9000/news/getEditNew', {
					params: {
						newsId: row.newsId
					}
				});

				newsData.value= response.data.editNew;

				title.value = newsData.value.title;
				content.value = newsData.value.content;
				author.value = newsData.value.author;
				summary.value = newsData.value.introduction;
				tenant.value = newsData.value.companyName;


				if (newsData.value.image && newsData.value.image !== '') {


					previewImageUrl.value = newsData.value.image;
					imageUrl.value = newsData.value.image;
				}

				openDialog();
			};

			const deleteNews = async () => {
				// 显示确认删除对话框
				ElMessageBox.confirm(
					'此操作将永久删除所选新闻，是否继续？',
					'提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning',
					}
				).then(async () => {
					// 用户点击确定
					const idsToDelete = selectedRows.value.map(row => row.newsId);
					try {
						await axios.post('http://localhost:9000/news/delete', {
							ids: idsToDelete
						});
						tableData.value = tableData.value.filter(item => !idsToDelete.includes(item
							.newsId));
						updatePagedData(tableData.value);
						ElMessage.success('删除成功');
					} catch (error) {
						ElMessage.error('删除失败');
					}
				}).catch(() => {
					// 用户点击取消
					ElMessage.info('已取消删除');
				});
			};



			const handleDelete = async (row) => {
				// 显示确认删除对话框
				ElMessageBox.confirm(
					'此操作将永久删除该新闻，是否继续？',
					'提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning',
					}
				).then(async () => {
					// 用户点击确定
					try {
						await axios.post('http://localhost:9000/news/delete', {
							ids: [row.newsId]
						});
						refreshNewsList();
						updatePagedData(tableData.value);
						ElMessage.success('删除成功');
					} catch (error) {
						ElMessage.error('删除失败');
					}
				}).catch(() => {
					// 用户点击取消
					ElMessage.info('已取消删除');
				});
			};

			const handleCurrentChange = (val) => {
				console.log('Current page:', val);
				currentPage.value = val;
				updatePagedData(tableData.value);
			};
			const pagedTableData = ref([]);
			const pageSize = 10;
			const currentPage = ref(1); // 当前页码
			const updatePagedData = (data) => {
				const startIndex = (currentPage.value - 1) * pageSize;
				pagedTableData.value = data.slice(startIndex, startIndex + pageSize);
				total.value = data.length;
			};

			const formatDate = (date) => {
				return dayjs(date).format('YYYY-MM-DD');
			};



			const searchNews = () => {

				const filteredData = tableData.value.filter(item => {
					const matchesTitle = input1.value ? item.title.toLowerCase().includes(
						input1.value
						.toLowerCase()) : true;
					const matchesAuthor = input2.value ? item.author.toLowerCase().includes(
						input2.value
						.toLowerCase()) : true;
					const matchesIntroduction = input3.value ? item.introduction.toLowerCase()
						.includes(input3
							.value.toLowerCase()) : true;


					const itemDate = item.createTime.split(' ')[0];
					const matchesDate = value1.value ? formatDate(value1.value) === itemDate :
						true;

					console.log(value1.value);

					return matchesTitle && matchesAuthor && matchesIntroduction && matchesDate;
				});

				// 更新分页数据
				//total.value=filteredData.length;
				updatePagedData(filteredData);

			};

			const resetSearch = () => {
				input1.value = '';
				input2.value = '';
				input3.value = '';
				value1.value = null;
				//total.value=tableData.value.length;
				updatePagedData(tableData.value);
			};



			const clearForm = () => {
				// 清空表单数据

				cancelUpload();
				title.value = '';
				content.value = '<p></p>';
				imageUrl.value = '';
				author.value = '';
				summary.value = '';
				tenant.value = '';



			};

			const addNews = () => {

				isChange.value = 0;
				if (!title.value || !content.value || !author.value || !tenant.value) {
					errorMessage.value = '请输入完整后添加';
					errorDialogVisible.value = true;
					return; // 如果有空字段，直接返回，不执行后续的添加操作
				}

				if (selectedFile.value) {
					const formData = new FormData();
					formData.append('file', selectedFile.value);

					imageUrl.value = previewImageUrl.value;
					const requestData = {
						title: title.value,
						imageUrl: imageUrl.value,
						content: content.value,
						author: author.value,
						summary: summary.value,
						tenant: tenant.value,
					};

					// 发送 POST 请求
					axios.post('http://localhost:9000/news/add', requestData)
						.then(response => {
							console.log('新增新闻成功', response.data);

							refreshNewsList();


							// 假设后端返回的新闻数据包含在 response.data 中
							// 将新闻数据添加到 tableData 中
							// tableData.value.push(response.data);

							// 关闭对话框等其他操作可以在这里处理
							dialogVisible.value = false;
							// 显示成功提示框等
							successMessage.value = '新增新闻成功';
							successDialogVisible.value = true;
						})
						.catch(error => {
							console.error('新增新闻失败', error);

							// 显示错误提示框等
							errorMessage.value = '新增新闻失败，请重试';
							errorDialogVisible.value = true;
						});


				} else {
					const requestData = {
						title: title.value,
						imageUrl: imageUrl.value,
						content: content.value,
						author: author.value,
						summary: summary.value,
						tenant: tenant.value,
					};

					// 发送 POST 请求
					axios.post('http://localhost:9000/news/add', requestData)
						.then(response => {
							console.log('新增新闻成功', response.data);
							refreshNewsList();
							// 假设后端返回的新闻数据包含在 response.data 中
							// 将新闻数据添加到 tableData 中
							// tableData.value.push(response.data);

							// 关闭对话框等其他操作可以在这里处理
							dialogVisible.value = false;
							// 显示成功提示框等
							successMessage.value = '新增新闻成功';
							successDialogVisible.value = true;
						})
						.catch(error => {
							console.error('新增新闻失败', error);

							// 显示错误提示框等
							errorMessage.value = '新增新闻失败，请重试';
							errorDialogVisible.value = true;
						});
				}


			};

			const editNews = () => {
				if (!title.value || !content.value || !author.value || !tenant.value) {
					errorMessage.value = '请输入完整后修改';
					errorDialogVisible.value = true;
					return; // 如果有空字段，直接返回，不执行后续的添加操作
				}

				if (selectedFile.value) {
					const formData = new FormData();
					formData.append('file', selectedFile.value);

					imageUrl.value = previewImageUrl.value;
					const requestData = {
						newsId: editId.value,
						title: title.value,
						imageUrl: imageUrl.value,
						content: content.value,
						author: author.value,
						summary: summary.value,
						tenant: tenant.value,
					};

					// 发送 POST 请求
					axios.post('http://localhost:9000/news/edit', requestData)
						.then(response => {
							console.log('修改新闻成功', response.data);

							refreshNewsList();


							// 假设后端返回的新闻数据包含在 response.data 中
							// 将新闻数据添加到 tableData 中
							// tableData.value.push(response.data);

							// 关闭对话框等其他操作可以在这里处理
							dialogVisible.value = false;
							// 显示成功提示框等
							successMessage.value = '修改新闻成功';
							successDialogVisible.value = true;
						})
						.catch(error => {
							console.error('修改新闻失败', error);

							// 显示错误提示框等
							errorMessage.value = '修改新闻失败，请重试';
							errorDialogVisible.value = true;
						});


				} else {
					const requestData = {
						newsId: editId.value,
						title: title.value,
						imageUrl: imageUrl.value,
						content: content.value,
						author: author.value,
						summary: summary.value,
						tenant: tenant.value,
					};

					// 发送 POST 请求
					axios.post('http://localhost:9000/news/edit', requestData)
						.then(response => {
							console.log('修改新闻成功', response.data);
							refreshNewsList();
							// 假设后端返回的新闻数据包含在 response.data 中
							// 将新闻数据添加到 tableData 中
							// tableData.value.push(response.data);

							// 关闭对话框等其他操作可以在这里处理
							dialogVisible.value = false;
							// 显示成功提示框等
							successMessage.value = '修改新闻成功';
							successDialogVisible.value = true;
						})
						.catch(error => {
							console.error('修改新闻失败', error);

							// 显示错误提示框等
							errorMessage.value = '修改新闻失败，请重试';
							errorDialogVisible.value = true;
						});
				}

			};


			const exportNews = () => {

				const exportData = tableData.value.map(row => ({
					ID: row.newsId,
					新闻标题: row.title,
					作者: row.author,
					新闻简介: row.introduction,
					发布时间: row.createTime,
				}));

				const ws = XLSX.utils.json_to_sheet(exportData);
				const wb = XLSX.utils.book_new();
				XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

				const wbout = XLSX.write(wb, {
					bookType: 'xlsx',
					type: 'array'
				});
				saveAs(new Blob([wbout], {
					type: 'application/octet-stream'
				}), 'exported_data.xlsx');

			};

			const personalCenter = () => {
				router.push('/userCenter');
			};

			const back = () => {
				router.push('/login');
			};

			const gotoNewsManage = () => {
				if (loginUser.value === 'admin') {
					router.push('/mynews');
				} else if (loginUser.value === 'root') {
					router.push('/news');
				} else {
					ElMessage.error('无权访问该页面');
				}
			};

			const gotoCompanyManage = () => {
				router.push('/CompanyManage');
			};
			
			const gotoUserManage = () => {
				router.push('/userManage');
			};
			
			const gotoMeeting = () => {
				router.push('/meeting');
			};
			
			const cancelUpload = () => {
				selectedFile.value = null; // 清空已选择的文件
				previewImageUrl.value = ''; // 清空预览图片 URL
				imageUrl.value = '';
				const fileInput = document.querySelector('input[type="file"]');
				if (fileInput) {
					fileInput.value = '';
				}
			};
			return {
				// 返回所有需要在模板中使用的数据和方法
				dialogVisible,
				successDialogVisible,
				errorDialogVisible,
				successMessage,
				errorMessage,
				rules,
				tableData,
				value1,
				input1,
				input2,
				multipleTable,
				toggleSelection,
				handleSelectionChange,
				handleEdit,
				handleDelete,
				handleCurrentChange,
				searchNews,
				resetSearch,
				addNews,
				editNews,
				deleteNews,
				exportNews,
				personalCenter,
				back,
				gotoNewsManage,
				openDialog,
				closeDialog,
				handleUploadClick,
				handleFileChange,
				handleSubmit,
				previewImageUrl,
				cancelUpload,
				fetchCompanies,
				companies,
				clearForm,
				title,
				imageUrl,
				content,
				author,
				summary,
				tenant,
				pagedTableData,
				pageSize,
				currentPage,
				refreshNewsList,
				input3,
				total,
				isChange,
				handleAdd,
				isLoggedIn,
				gotoCompanyManage,
				gotoUserManage,
				gotoMeeting,
				store,
			};
		},


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