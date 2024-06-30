<template>
  <div class="about">
    <el-container style="height: 100vh; border: 1px solid #ffffff">
      <el-aside class="menu-with-shadow" width="200px" style="color: rgb(255,255,255);z-index: 10;">
        <el-menu :default-openeds="['3']">
          <el-menu-item index="1">
            <template #title>
              <img src="@/assets/logo1.png" style="width: 40px; height: 40px; margin-right: 5px;">
              <span style="font-size: 20px;"><strong>测盟汇</strong></span>
            </template>
          </el-menu-item>

          <el-menu-item index="2">
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
              <el-menu-item index="3-1">
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
            <div style="text-align: left;position: relative; top: 20px;">
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item><a href="http://localhost:8080/meeting">会议管理</a></el-breadcrumb-item>
                <el-breadcrumb-item>会议列表</el-breadcrumb-item>
              </el-breadcrumb>
            </div>
            <el-dropdown :hide-on-click="false">
              <div style="display: flex; align-items: center;">
                <el-avatar icon="UserFilled"
                           style="font-size: 20px; margin-right: 10px;position: relative; top: 8px;"></el-avatar>
                <el-button type="text" style="font-size: 15px; color: rgb(0,0,0);position: relative; top: 8px;">
                  {{  loginUser.userName}}
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


        <el-main class="main-content">
          <div style="text-align: left;">
            <span style="color: rgb(150,150,150)"><strong>会议名称</strong></span>
            <el-input v-model="input1" placeholder="请输入内容"
                      style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
            <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>创建人</strong></span>
            <el-input v-model="input2" placeholder="请输入内容"
                      style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
            <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>开始时间</strong></span>
            <el-date-picker v-model="value1" type="date" placeholder="选择日期" style="margin-left: 5px;">
            </el-date-picker>
            <el-button type="primary" style="margin-left: 15px;" icon="Search" @click="handleSearch">搜索</el-button>
            <el-button plain style="margin-left: 15px;" icon="Refresh" @click="handleReset">重置</el-button>
          </div>
          <el-row style="text-align: left;">
            <el-button type="primary" plain icon="Plus" style="margin-top: 5px" @click="meetingAdd">新增</el-button>
            <el-button type="success" plain icon="Edit" style="margin-top: 5px" @click="handleBatchEdit">修改
            </el-button>
            <el-button type="danger" plain icon="Delete" style="margin-top: 5px" @click="handleBatchDelete">删除
            </el-button>
            <el-button type="warning" plain icon="Download" style="margin-top: 5px" @click="exportMeetings">导出
            </el-button>
          </el-row>
          <div class="custom-table" style="margin-top: 10px;">
            <el-table ref="multipleTable" :header-cell-style="{height: '30px',background: '#f0f0f0'}" :row-style="{ height: '66px' }"
                      :data="pagedTableData" tooltip-effect="dark" style="width: 100%;" class="custom-table"
                      @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="meetingTitle" label="会议名称" width="250" header-align="center"
                               align="center"></el-table-column>
              <el-table-column prop="organizer" label="创建人" width="100" header-align="center"
                               align="center"></el-table-column>
              <el-table-column prop="state" label="会议状态" width="100" header-align="center" align="center">
                <template #default="scope">
                  {{ computeMeetingState(scope.row.startTime, scope.row.endTime) }}
                </template>
              </el-table-column>

              <el-table-column prop="meetingDescription" label="会议内容" width="250" header-align="center"
                               align="center">
                <template #default="{ row }">
                  {{ formatMeetingDescription(row.meetingDescription) }}
                </template>
              </el-table-column>
              <el-table-column
                  prop="startTime"
                  label="开始时间"
                  width="200"
                  header-align="center"
                  align="center"
              >
                <template #default="scope">
                  {{ formatDate(scope.row.startTime) }}
                </template>
              </el-table-column>
              <el-table-column prop="operate" label="操作" header-align="center" align="center">
                <template #default="scope">
                  <el-button size="mini" type="primary" icon="View" @click="handleView(scope.$index, scope.row);">查看
                  </el-button>
                  <el-button size="mini" type="success" icon="Edit" @click="handleEdit(scope.$index, scope.row)">修改
                  </el-button>
                  <el-button size="mini" type="danger" icon="Delete" @click="handleDelete(scope.$index, scope.row)">
                    删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <el-dialog v-model="dialogVisible" title="会议内容" width="800">
            <div>
              <p style="text-align: left;"><strong>会议名称：</strong>{{ selectedMeeting.meetingTitle }}</p>
              <p style="text-align: left;"><strong>会议封面：</strong>
                <el-image
                    style="width: 100px; height: 100px;"
                    :src="getMeetingCoverUrl(selectedMeeting.meetingTitle)"
                    fit="cover"
                    alt="会议封面"
                ></el-image>
              </p>
              <p style="text-align: left;"><strong>创建人：</strong>{{ selectedMeeting.organizer }}</p>
              <p style="text-align: left;"><strong>开始时间：</strong>{{ formatDate(selectedMeeting.startTime) }}</p>
              <p style="text-align: left;"><strong>结束时间：</strong>{{ formatDate(selectedMeeting.endTime) }}</p>
              <p style="text-align: left;"><strong>会议内容：</strong><span
                  v-html="selectedMeeting.meetingDescription"></span></p>
            </div>
            <template #footer>
              <div class="dialog-footer">

                <el-button type="primary" @click="dialogVisible = false">
                  确定
                </el-button>
              </div>
            </template>
          </el-dialog>

        </el-main>
        <div class="container" style="grid-template-rows: auto 1fr auto;">
          <el-config-provider :locale="zhCn">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage4"
                layout="total, prev, pager, next, jumper"
                :total=totalMeetings
                background
                style="grid-row: 3; margin-bottom: 15px; justify-content: flex-end;margin-right: 50px"
            ></el-pagination>
          </el-config-provider>
        </div>
      </el-container>
    </el-container>
  </div>

</template>





<script>
import {ref, reactive, getCurrentInstance, onMounted} from 'vue';
import {useRouter} from 'vue-router';
import {Management, UserFilled} from "@element-plus/icons-vue";
import axios from 'axios';
import {format, parseISO, isAfter, isBefore} from "date-fns";
import * as XLSX from 'xlsx';
import {useStore} from "vuex";


export default {
  components: {Management, UserFilled},
  setup() {
    const tableData = ref([]);
    const currentPage = ref(1); // 当前页码
    const pageSize = 7; // 每页显示条目数
    const totalMeetings = ref(0); // 总会议条目数
    const input1 = ref('');
    const input2 = ref('');
    const value1 = ref('');
    const pagedTableData = ref([]);
    const store = useStore();
    const userInfo = ref({
      userName: '',
      phoneNumber: '',
      email: '',
      departmentId: '',
      role: '',
      createTime: '',
      password: '',
    });
    const form = reactive({
      name: '',
      region: '',
      images: [] // 新增此行以保存上传的图片
    });
    const dialogImageUrl = ref('');
    const dialogVisible = ref(false);
    const dialogVisible1 = ref(false);
    const disabled = ref(false);

    const fetchMeetings = async () => {
      try {
        const response = await axios.get('http://localhost:8070/api/meetings/all');
        if (response.data.code === '0') {
          tableData.value = response.data.data.map(meeting => ({
            meetingId: meeting.meetingId,
            meetingTitle: meeting.meetingTitle,
            meetingDescription: meeting.meetingDescription,
            organizer: meeting.organizer,
            state: meeting.state,
            startTime: meeting.startTime,
            endTime: meeting.endTime,
          }));
          totalMeetings.value = tableData.value.length;
          updatePagedTableData();
        } else {
          console.error('Failed to fetch meetings:', response.data.msg);
        }
      } catch (error) {
        console.error('Error fetching meetings:', error);
      }
    };

    // 自动获取会议列表
    fetchMeetings();

    const router = useRouter();

    const multipleTable = ref(null);
    const pickerOptions = reactive({
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
      pickerOptions.multipleSelection = val;
      selectedMeetings.value = val;
    };

    const selectedMeeting = ref(null);

    const handleView = (index, row) => {
      selectedMeeting.value = row;
      dialogVisible.value = true;
      console.log(index, row);
    };

    const handleEdit = (index, row) => {
      if (loginUser.value.role === 'user') {
        proxy.$message.warning('很遗憾!您没有权限编辑会议!');
        return;
      }
      router.push({
        path: `/meetingEdit/${row.meetingId}` // 使用动态路径参数
      });
    };

    const handleDelete = (index, row) => {
      if (loginUser.value.role === 'user') {
        proxy.$message.warning('很遗憾!您没有权限删除会议!');
        return;
      }
      // 确认删除操作
      proxy.$confirm(`确认删除会议 "${row.meetingTitle}" 吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        try {
          // 发送删除请求，假设后端接口接受 DELETE 请求，且传递会议 ID
          const response = await axios.delete(`http://localhost:8070/api/meetings/${row.meetingId}`);
          if (response.data.code === '0') {
            // 删除成功后更新前端视图
            const startIndex = (currentPage.value - 1) * pageSize; // 当前页的起始索引
            const dataIndex = startIndex + index; // 获取在 tableData.value 中的索引位置
            tableData.value.splice(dataIndex, 1); // 从 tableData 中删除对应会议
            totalMeetings.value--; // 总会议数减一
            updatePagedTableData(); // 更新分页数据
            // 调用删除文件的接口，假设后端接口为 deleteFile
            let deleteFileResponse = null;
            const formats = ['jpg', 'png', 'jpeg'];
            for (let format of formats) {
              try {
                deleteFileResponse = await axios.delete(`http://localhost:8070/api/deleteFile/${row.meetingTitle}.${format}`);
              } catch (e) {
                // Ignore the error and try the next format
              }
            }
            if (deleteFileResponse.status === 200) {
              proxy.$message.success('会议删除成功');
            } else {
              proxy.$message.error('会议删除成功，但相关文件删除失败，请联系管理员');
            }
          } else {
            proxy.$message.error('删除失败：' + response.data.msg);
          }
        } catch (error) {
          console.error('删除会议失败：', error);
          proxy.$message.error('删除会议失败，请稍后重试');
        }
      }).catch(() => {
        proxy.$message.info('已取消删除');
      });
    };

    const personalCenter = () => {
      router.push('/userCenter');
    };

    const back = () => {
      router.push('/login');
    };

    const handleRemove = (file) => {
      console.log(file);
    };

    const handlePictureCardPreview = (file) => {
      dialogImageUrl.value = file.url;
      dialogVisible.value = true;
    };

    const meetingAdd = () => {
      if (loginUser.value.role === 'user') {
        proxy.$message.warning('很遗憾!您没有权限添加会议!');
        return;
      }
      router.push('/meetingAdd');
    };

    // 更新分页数据
    const updatePagedTableData = () => {
      const start = (currentPage.value - 1) * pageSize;
      const end = start + pageSize;
      pagedTableData.value = tableData.value.slice(start, end);
    };

    // 处理分页器切换页码事件
    const handleCurrentChange = (newPage) => {
      currentPage.value = newPage;
      updatePagedTableData();
    };
    // 格式化会议内容
    const formatMeetingDescription = (description) => {
      if (!description) return '';

      // 将 <img> 标签替换为 [图片]
      const withoutImages = description.replace(/<img[^>]*>/g, "[图片]");

      // 去除 HTML 标签
      const withoutTags = withoutImages.replace(/<[^>]+>/g, '');

      // 截取长度，并添加省略号
      if (withoutTags.length > 32) {
        return withoutTags.substring(0, 32) + '...';
      } else {
        return withoutTags;
      }
    };


    // 格式化日期
    const formatDate = (startTime) => {
      if (!startTime) return ''; // 处理 startTime 为空的情况
      try {
        const parsedDate = parseISO(startTime);
        return format(parsedDate, 'yyyy-MM-dd HH:mm:ss');
      } catch (error) {
        console.error('Error parsing or formatting date:', error);
        return ''; // 如果日期解析或格式化出错，返回空字符串
      }
    };

    const computeMeetingState = (startTime, endTime) => {
      const now = new Date();
      const start = parseISO(startTime);
      const end = parseISO(endTime);

      if (isBefore(now, start)) {
        return '未开始';
      } else if (isAfter(now, end)) {
        return '已结束';
      } else {
        return '进行中';
      }
    };
    const {proxy} = getCurrentInstance();
    // 处理搜索事件
    const handleSearch = () => {
      // 根据输入的会议名称进行筛选
      const meetingTitleTerm = input1.value.trim(); // 获取输入的会议名称并去除首尾空格
      const organizerTerm = input2.value.trim(); // 获取输入的创建人并去除首尾空格
      const startTimeTerm = value1.value ? format(value1.value, 'yyyy-MM-dd') : '';
      if (meetingTitleTerm === '' && organizerTerm === '' && startTimeTerm === '') {
        // 如果搜索框为空，则返回信息
        proxy.$message.error('搜索框为空！');
      } else {
        // 否则，根据会议名称和创建人进行筛选
        tableData.value = tableData.value.filter(meeting =>
            (meetingTitleTerm === '' || meeting.meetingTitle.includes(meetingTitleTerm)) &&
            (organizerTerm === '' || meeting.organizer.includes(organizerTerm)) &&
            (startTimeTerm === '' || meeting.startTime.includes(startTimeTerm))
        );
      }
      // 更新表格数据和总条目数
      totalMeetings.value = tableData.value.length;
      // 重置当前页码为第一页
      currentPage.value = 1;
      updatePagedTableData();
    };

    // 处理重置事件
    const handleReset = () => {
      input1.value = '';
      input2.value = '';
      value1.value = '';
      fetchMeetings(); // 重置后重新获取所有会议列表
    };

    const getMeetingCoverUrl = (meetingTitle) => {
      //const basePath = '@/assets/'; // 替换为实际图片路径
      const formats = ['jpg', 'png', 'jpeg'];
      for (let format of formats) {
        try {
          return require(`@/assets/${meetingTitle}.${format}`);
        } catch (e) {
          // Ignore the error and try the next format
        }
      }
      return `default.jpg`; // 默认图片路径
    };

    const exportMeetings = () => {
      // 获取要导出的数据
      const exportData = tableData.value.map(meeting => ({
        '会议Id': meeting.meetingId,
        '会议名称': meeting.meetingTitle,
        '创建人': meeting.organizer,
        '会议状态': computeMeetingState(meeting.startTime, meeting.endTime),
        '会议内容': meeting.meetingDescription,
        '开始时间': formatDate(meeting.startTime),
        '结束时间': formatDate(meeting.endTime),
      }));

      // 构造确认消息框
      proxy.$confirm('确认下载当前会议列表文件吗？', '确认下载', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info',
      }).then(() => {
        console.log(exportData);
        // 创建工作簿并添加工作表
        const ws = XLSX.utils.json_to_sheet(exportData);
        const wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, ws, '会议列表');
        // 生成 Excel 文件并触发下载
        XLSX.writeFile(wb, '会议列表.xlsx');
      }).catch(() => {
        proxy.$message.info('已取消下载');
      });
    };

    const selectedMeetings = ref([]);
    const handleBatchEdit = () => {
      if (loginUser.value.role === 'user') {
        proxy.$message.warning('很遗憾!您没有权限编辑会议!');
        return;
      }
      if (selectedMeetings.value.length === 0) {
        proxy.$message.warning('请先选择要编辑的会议');
        return;
      }
      // 如果只选中了一个会议，则直接跳转
      if (selectedMeetings.value.length === 1) {
        const meeting = selectedMeetings.value[0];
        router.push({
          path: `/meetingEdit/${meeting.meetingId}` // 使用会议 ID 或其他唯一标识作为动态路径参数
        });
      } else {
        // 如果选中了多个会议，则在新标签页中打开每个会议的编辑页面
        selectedMeetings.value.forEach(meeting => {
          window.open(`/meetingEdit/${meeting.meetingId}`, '_blank');
        });
      }
    };
    const handleBatchDelete = async () => {
      if (loginUser.value.role === 'user') {
        proxy.$message.warning('很遗憾!您没有权限删除会议!');
        return;
      }
      // 检查是否有选中的会议
      if (selectedMeetings.value.length === 0) {
        proxy.$message.warning('请先选择要删除的会议');
        return;
      }
      await proxy
          .$confirm(`确认删除所选中 ${selectedMeetings.value.length} 条会议吗？`, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          })
          .then(async () => {
            try {
              const deletePromises = selectedMeetings.value.map(async (meeting) => {
                try {
                  // 发送删除请求，后端接口接受 DELETE 请求，且传递会议 ID
                  const response = await axios.delete(`http://localhost:8070/api/meetings/${meeting.meetingId}`);
                  if (response.data.code === '0') {
                    // 删除成功后更新前端视图
                    const index = pagedTableData.value.findIndex(item => item.meetingId === meeting.meetingId);
                    if (index !== -1) {
                      const startIndex = (currentPage.value - 1) * pageSize; // 当前页的起始索引
                      const dataIndex = startIndex + index; // 获取在 tableData.value 中的索引位置
                      tableData.value.splice(dataIndex, 1); // 从 tableData 中删除对应会议
                      totalMeetings.value--; // 总会议数减一
                      updatePagedTableData(); // 更新分页数据

                      // 调用删除文件的接口，假设后端接口为 deleteFile
                      let deleteFileResponse = null;
                      const formats = ['jpg', 'png', 'jpeg'];
                      for (let format of formats) {
                        try {
                          deleteFileResponse = await axios.delete(`http://localhost:8070/api/deleteFile/${meeting.meetingTitle}.${format}`);
                        } catch (e) {
                          // Ignore the error and try the next format
                        }
                      }
                      if (deleteFileResponse && deleteFileResponse.status === 200) {
                        proxy.$message.success(`会议 "${meeting.meetingTitle}" 删除成功`);
                      } else {
                        proxy.$message.error(`会议 "${meeting.meetingTitle}" 删除成功，但相关文件删除失败，请联系管理员`);
                      }
                    }
                  } else {
                    proxy.$message.error(`会议 "${meeting.meetingTitle}" 删除失败：` + response.data.msg);
                  }
                } catch (error) {
                  console.error(`会议 "${meeting.meetingTitle}" 删除失败：`, error);
                  proxy.$message.error(`会议 "${meeting.meetingTitle}" 删除失败，请稍后重试`);
                }
              });
              // 等待所有删除操作完成
              await Promise.all(deletePromises);
              // 更新批量删除成功的消息提示
              proxy.$message.success('批量删除成功');
            } catch (error) {
              console.error('批量删除失败：', error);
              proxy.$message.error('批量删除失败，请稍后重试');
            }
          })
          .catch(() => {
            proxy.$message.info('已取消批量删除');
          });
    };

    const loginUser = ref('')
    onMounted(async () => {
      await Promise.all([
        loginUser.value = store.state.user,

      ])
    });

    return {
      tableData,
      pickerOptions,
      value1,
      input1,
      input2,
      multipleTable,
      toggleSelection,
      handleSelectionChange,
      handleView,
      handleEdit,
      handleDelete,
      personalCenter,
      back,
      dialogVisible,
      dialogVisible1,
      form,
      handlePictureCardPreview,
      handleRemove,
      disabled,
      meetingAdd,
      formatDate,
      computeMeetingState,
      pagedTableData,
      currentPage,
      pageSize,
      totalMeetings,
      handleCurrentChange,
      handleSearch,
      handleReset,
      formatMeetingDescription,
      selectedMeeting,
      getMeetingCoverUrl,
      exportMeetings,
      handleBatchEdit,
      handleBatchDelete,
      userInfo,
      store,
      loginUser,
    };
  },
};
</script>

<style scoped>
.main-content {
  overflow: hidden; /* 隐藏主要内容区域的滚动条 */
}

.menu-with-shadow {
  box-shadow: 5px 0px 6px rgba(0.5, 0.5, 0, 0.5);
}

.header-with-shadow {
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.5);
}

about {
  overflow-y: hidden; /* 隐藏垂直滚动条 */
}

</style>

