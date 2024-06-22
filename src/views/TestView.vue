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

          <el-menu-item index="2">
            <template #title>
              <el-icon><HomeFilled /></el-icon>首页
            </template>
          </el-menu-item>

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

      <el-container>
        <el-header class="header-with-shadow" style="font-size: 12px">
          <div style="display: flex; justify-content: space-between; width: 100%;">
            <div style="text-align: left;position: relative; top: 20px;">
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item><a href="/">会议管理</a></el-breadcrumb-item>
                <el-breadcrumb-item>会议列表</el-breadcrumb-item>
                <el-breadcrumb-item>会议详情</el-breadcrumb-item>
              </el-breadcrumb>
            </div>
            <el-dropdown :hide-on-click="false">
              <div style="display: flex; align-items: center;">
                <el-avatar icon="UserFilled" style="font-size: 20px; margin-right: 10px;position: relative; top: 8px;"></el-avatar>
                <el-button type="text" style="font-size: 15px; color: rgb(0,0,0);position: relative; top: 8px;">徐洋</el-button>
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
            <span style="color: rgb(150,150,150)"><strong>会议名称</strong></span>
            <el-input v-model="input1" placeholder="请输入内容" style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
            <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>创建人</strong></span>
            <el-input v-model="input2" placeholder="请输入内容" style="margin-left: 5px;width: 200px;height: 30px;"></el-input>
            <span style="color: rgb(150,150,150);margin-left: 15px;"><strong>开始时间</strong></span>
            <el-date-picker v-model="value1" type="date" placeholder="选择日期" style="margin-left: 5px;">
            </el-date-picker>
            <el-button type="primary" style="margin-left: 15px;" icon="Search">搜索</el-button>
            <el-button plain style="margin-left: 15px;" icon="Refresh">重置</el-button>
          </div>
          <el-row style="text-align: left;">
            <el-button type="primary" plain icon="Plus" style="margin-top: 5px" @click="dialogVisible = true">新增</el-button>
            <el-button type="success" plain icon="Edit" style="margin-top: 5px">修改</el-button>
            <el-button type="danger" plain icon="Delete" style="margin-top: 5px">删除</el-button>
            <el-button type="warning" plain icon="Download" style="margin-top: 5px">导出</el-button>
          </el-row>
          <div class="custom-table" style="margin-top: 10px;">
            <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="meetingTitle" label="会议名称" width="300" header-align="center" align="center"></el-table-column>
              <el-table-column prop= "organizer" label="创建人" width="100" header-align="center" align="center"></el-table-column>
              <el-table-column prop="state" label="会议状态" width="100" header-align="center" align="center"></el-table-column>
              <el-table-column prop="meetingDescription" label="会议内容" width="300" header-align="center" align="center"></el-table-column>
              <el-table-column prop="startTime" label="开始时间" width="100" header-align="center" align="center"></el-table-column>
              <el-table-column prop="operate" label="操作" header-align="center" align="center">
                <template #default="scope">
                  <el-button size="mini" type="success" icon="Edit" @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                  <el-button size="mini" type="danger" icon="Delete" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-main>
        <div class="container" style="grid-template-rows: auto 1fr auto;margin-left: 500px">
          <el-pagination background layout="prev, pager, next" :total="50" class="pagination" style="grid-row: 3;margin-bottom: 10px"></el-pagination>
        </div>
      </el-container>
    </el-container>
  </div>

  <el-dialog
      v-model="dialogVisible"
      width="700"
  >
    <template #title>
      <div style="text-align: left;">
        添加会议
      </div>
    </template>
    <el-form :model="form">
      <el-form-item label="会议名称：" :label-width="formLabelWidth">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="会议封面：" :label-width="formLabelWidth">

        <el-upload
            v-model:file-list="fileList"
            action="#"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
            :auto-upload="false"
            style="text-align: left">
          <el-icon><Plus /></el-icon>

          <template #file="{ file }">
            <div>
              <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
              <span class="el-upload-list__item-actions">
          <span
              class="el-upload-list__item-preview"
              @click="handlePictureCardPreview(file)"
          >
            <el-icon><zoom-in /></el-icon>
          </span>

          <span
              v-if="!disabled"
              class="el-upload-list__item-delete"
              @click="handleRemove(file)"
          >
            <el-icon><Delete /></el-icon>
          </span>
        </span>
            </div>

          </template>
          <template #tip>
            <div style="text-align: left;">
              请上传大小不超过<span style="color: red;">5MB</span>格式为<span style="color: red;">png/jpg/jpeg</span>的文件
            </div>
          </template>
        </el-upload>

        <el-dialog v-model="dialogVisible1">
          <img w-full :src="dialogImageUrl" alt="Preview Image" />
        </el-dialog>

      </el-form-item>

      <el-form-item label="会议内容：" :label-width="formLabelWidth">
        <template>
          <quill-editor
              content-type='html'
              :content='content'
              :options='options'
              @blur='editorBlur($event)'
          />
        </template>

      </el-form-item>

    </el-form>


    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogVisible = false">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { Management, UserFilled,Delete, Plus, ZoomIn} from "@element-plus/icons-vue";
import axios from 'axios';

export default {
  components: { Management, UserFilled,Delete,Plus, ZoomIn },
  setup() {
    const tableData = ref([]);
    const form = reactive({
      name: '',
      region: '',
      images: [] // 新增此行以保存上传的图片
    });
    const dialogImageUrl = ref('')
    const dialogVisible = ref(false)
    const dialogVisible1 = ref(false)
    const disabled = ref(false)
    const fetchMeetings = async () => {
      try {
        const response = await axios.get('http://localhost:8090/api/meetings/all');
        if (response.data.code === '0') {
          tableData.value = response.data.data.map(meeting => ({
            meetingTitle: meeting.meetingTitle,
            meetingDescription: meeting.meetingDescription,
            organizer: meeting.organizer,
            state: meeting.state,
            startTime: meeting.startTime,
            endTime: meeting.endTime,
          }));
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

    const input1 = ref('');
    const input2 = ref('');
    const value1 = ref('');
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
    };

    const handleEdit = (index, row) => {
      console.log(index, row);
    };

    const handleDelete = (index, row) => {
      console.log(index, row);
    };

    const personalCenter = () => {
      router.push('/userCenter');
    };

    const back = () => {
      router.push('/login');
    };



    const handleRemove = (file) => {
      console.log(file)
    };

    const handlePictureCardPreview = (file) => {
      dialogImageUrl.value = file.url
      dialogVisible.value = true;

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
      dialogVisible,
      dialogVisible1,
      form,
      handlePictureCardPreview,
      handleRemove,
      disabled,
    };
  },

};
</script>


