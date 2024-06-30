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
                <el-breadcrumb-item><a href="/">会议管理</a></el-breadcrumb-item>
                <el-breadcrumb-item>添加会议</el-breadcrumb-item>
              </el-breadcrumb>
            </div>
            <el-dropdown :hide-on-click="false">
              <div style="display: flex; align-items: center;">
                <el-avatar icon="UserFilled"
                           style="font-size: 20px; margin-right: 10px;position: relative; top: 8px;"></el-avatar>
                <el-button type="text" style="font-size: 15px; color: rgb(0,0,0);position: relative; top: 8px;">
                  {{loginUser.userName}}
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

        <el-card style="height: 630px; margin-top: 10px;">

          <el-form :model="form">
            <el-form-item label="会议名称" :label-width="formLabelWidth">
              <el-input v-model="form.meetingTitle" placeholder="请输入会议名称" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="会议封面" :label-width="formLabelWidth">
              <el-upload
                  class="uploader"
                  v-model:file-list="fileList"
                  list-type="picture-card"
                  action="http://localhost:8070/api/upload"
                  :auto-upload="false"
                  :on-change="getFilesj"
                  :before-upload="beforeAvatarUpload"
                  :on-preview="handlePictureCardPreview"
                  :on-remove="handleRemove"
                  :limit='1'
                  style="text-align: left"
                  ref="uploadImg"
                  accept="image/png, image/jpg, image/jpeg"
                  :disabled="form.meetingTitle === ''"
                  @click="handleUploadClick"
              >
                <el-icon>
                  <Plus/>
                </el-icon>
                <template #tip>
                  <div style="text-align: left;">
                    请上传大小不超过<span style="color: red;">5MB</span>格式为<span
                      style="color: red;">png/jpg/jpeg</span>的文件
                  </div>
                </template>
              </el-upload>

              <el-dialog v-model="dialogVisible" title="预览">
                <img w-full :src="dialogImageUrl" style="display: block;max-width: 500px;height: 500px;margin: 0 auto;"
                     alt="Preview Image"/>
              </el-dialog>

            </el-form-item>
            <el-form-item>
              <div style="text-align: left;margin-left: 65px;height:10px;margin-top:-20px">
                请上传大小不超过<span style="color: red;">5MB</span>格式为<span
                  style="color: red;">png/jpg/jpeg</span>的文件
              </div>
            </el-form-item>
            <el-form-item label="会议内容" :label-width="formLabelWidth">
              <Editor content-type='html' v-model="content"
                      :content='content' :options='options' style="width: 1168px;height: 150px"></Editor>
            </el-form-item>
            <el-form-item label="创建人" :label-width="formLabelWidth" style="margin-top:70px;">
              <el-input v-model="form.organizer" style="width:220px;margin-left:14px;" placeholder="请输入创建人"
                        autocomplete="off"/>
            </el-form-item>
            <el-form-item label="开始日期" style="width:300px;">
              <div class="block">
                <el-date-picker
                    v-model="value1"
                    type="date"
                    placeholder="选择会议开始日期"
                    :size="size"
                />
              </div>
            </el-form-item>
            <el-form-item label="起止时间" style="width:300px;">
              <div class="block">
                <el-time-picker
                    v-model="value2"
                    is-range
                    arrow-control
                    range-separator="To"
                    start-placeholder="Start time"
                    end-placeholder="End time"
                />
              </div>
            </el-form-item>
          </el-form>


          <div class="dialog-footer" style="text-align: right;margin-right:30px;">
            <el-button style="margin-top: -100px" @click="backMeeting">取消</el-button>
            <el-button type="primary" style="margin-top: -100px" @click="handleSubmit">
              确定
            </el-button>
          </div>

        </el-card>

      </el-container>
    </el-container>
  </div>

</template>

<script lang="ts" setup>
import {ref, reactive, onMounted} from 'vue';
import {useRouter} from 'vue-router';
import {Management, UserFilled, Delete, Plus, ZoomIn} from "@element-plus/icons-vue";
import axios from 'axios';
import {UploadProps, UploadUserFile, ElMessage, ElMessageBox} from 'element-plus';

import Editor from 'primevue/editor';
import 'quill/dist/quill.core.css';
import {useStore} from "vuex"; // 导入 Quill 样式


const size = ref<'default' | 'large' | 'small'>('default');
const value1 = ref('');
const value2 = ref<[Date, Date]>([
  new Date(2016, 9, 10, 0, 0),
  new Date(2016, 9, 10, 1, 0),
]);


// 定义响应式变量
const content = ref(''); // 绑定的富文本内容

let options = {
  modules: {
    toolbar: [
      ['bold', 'italic', 'underline', 'strike'], // 加粗 斜体 下划线 删除线
      [{color: []}, {background: []}], // 字体颜色、字体背景颜色
      [{align: []}], // 对齐方式
      [{size: ['small', false, 'large', 'huge']}], // 字体大小
      [{font: []}], // 字体种类
      [{header: [1, 2, 3, 4, 5, 6, false]}], // 标题
      [{direction: 'ltl'}], // 文本方向
      [{direction: 'rtl'}], // 文本方向
      [{indent: '-1'}, {indent: '+1'}], // 缩进
      [{list: 'ordered'}, {list: 'bullet'}], // 有序、无序列表
      [{script: 'sub'}, {script: 'super'}], // 上标/下标
      ['blockquote', 'code-block'], // 引用  代码块
      ['clean'], // 清除文本格式
      ['link', 'image', 'video'], // 链接、图片、视频
    ],
  },
};

const fileList = ref<UploadUserFile[]>([]);
const dialogImageUrl = ref('');
const dialogVisible = ref(false);

const router = useRouter();

const personalCenter = () => {
  router.push('/userCenter');
};

const back = () => {
  router.push('/login');
};

const backMeeting = () => {
  router.push('/meeting');
};

const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles);
};
const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url!;
  dialogVisible.value = true;
};

const beforeAvatarUpload: UploadProps['beforeUpload'] = (file: File) => {
  const isJPG = file.type === 'image/jpeg';
  const isPNG = file.type === 'image/png';
  const isJPEG = file.type === 'image/jpeg';
  const isLt5M = file.size / 1024 / 1024 < 5;

  if (!isJPG && !isPNG && !isJPEG) {
    ElMessage.error('上传图片只能是 JPG/PNG/JPEG 格式!');
    return false;
  }
  if (!isLt5M) {
    ElMessage.error('上传图片大小不能超过 5MB!');
    return false;
  }
  return true;
};

// 声明 reactive 对象 form 和 ref 变量 listst，显式指定类型为字符串
const form = reactive({
  meetingTitle: '' as string, // 会议名称，根据实际情况命名
  driverPhoto: '' as string,
  description: '' as string, // 会议描述
  organizer: '' as string,    //会议创建人
  startTime: '' as string, // 开始时间
  endTime: '' as string, // 结束时间
});

const listst = ref<File | null>(null);


// 将图片转换为 Base64 字符串的方法
const uploadImgToBase64 = (file: Blob): Promise<string> => {
  return new Promise<string>((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => {
      resolve(reader.result as string);
    };
    reader.onerror = reject;
    reader.readAsDataURL(file);
  });
};

//必须填写会议名称才可上传图片
const handleUploadClick = () => {
  if (form.meetingTitle === '') {
    ElMessage.error('请先填写会议标题！');
  }
};

// 处理上传图片的方法

const getFilesj: UploadProps['onChange'] = async (file, fileList) => {
  const isLt5M = file.size / 1024 / 1024 < 5;
  if (form.meetingTitle === "") {
    ElMessage.error("请先填写会议名称!");
    return;
  }
  if (isLt5M) {
    const newFileName = `${form.meetingTitle}.${file.name.split('.').pop()}`;
    const renamedFile = new File([file.raw as Blob], newFileName, {type: file.type});
    listst.value = renamedFile;
  } else {
    ElMessage.error("上传的图片不能超过5MB!");
  }
  //console.log("fileList", fileList);
  //console.log("file", file);
};

// 提交表单的方法
const handleSubmit = async () => {
  // 检查所有表单数据是否为空
  if (!form.meetingTitle || !content.value || !form.organizer || !value1.value || !value2.value[0] || !value2.value[1]) {
    ElMessage.error('请填写完整的会议信息');
    return;
  }
  if (listst.value !== null) {
    const formData = new FormData();
    formData.append('file', listst.value); // 使用 'file' 作为键名
    // 1. 发送会议封面
    try {
      const response = await axios.post('http://localhost:8070/api/upload', formData, {});

      if (response.status === 200) {
        //form.driverPhoto = response.data.data;
        form.description = content.value; // 获取富文本编辑器的内容作为会议描述
        // 将日期和时间组合成datetime格式
        if (value1.value && value2.value[0] && value2.value[1]) {
          const startDate = new Date(value1.value); // 确保 startDate 是一个 Date 对象
          const [startTime, endTime] = value2.value;

          const startDateTime = new Date(
              startDate.getFullYear(),
              startDate.getMonth(),
              startDate.getDate(),
              startTime.getHours() + 8,
              startTime.getMinutes(),
              startTime.getSeconds()
          );

          const endDateTime = new Date(
              startDate.getFullYear(),
              startDate.getMonth(),
              startDate.getDate(),
              endTime.getHours() + 8,
              endTime.getMinutes(),
              endTime.getSeconds()
          );

          form.startTime = startDateTime.toISOString();
          form.endTime = endDateTime.toISOString();
        } else {
          ElMessage.error("请选择完整的日期和时间范围");
          return;
        }
        // 2. 发送会议信息
        const meetingData = {
          meetingTitle: form.meetingTitle,
          meetingDescription: content.value,
          organizer: form.organizer, // 假设 startTime 对应开始时间
          startTime: form.startTime, // 假设 startTime 对应开始时间
          endTime: form.endTime // 假设 endTime 对应结束时间
        };
        const responseMeeting = await axios.post('http://localhost:8070/api/meetings/add', meetingData);

        if (responseMeeting.data.msg === "成功") {
          ElMessage.success("会议添加成功!");
          router.push('/meeting');
        } else {
          ElMessage.success("会议添加失败!请检查输入!");
        }
      } else {
        ElMessageBox.alert(`${response.data.msg} (提示代码: ${response.data.code})`, "提示");
      }
    } catch (error) {
      ElMessage.error('提交表单失败');
    }
  } else {
    ElMessage.error("先上传会议封面!");
  }
};

const store = useStore();
const loginUser = ref('')
onMounted(async () => {
  await Promise.all([
    loginUser.value = store.state.user,

  ])
});

</script>

<style scoped>
.demo-date-picker {
  display: flex;
  width: 100%;
  padding: 0;
  flex-wrap: wrap;
}

.menu-with-shadow {
  box-shadow: 5px 0px 6px rgba(0.5, 0.5, 0, 0.5);
}

.header-with-shadow {
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.5);
}

.demo-date-picker .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  flex: 1;
}

.demo-date-picker .block:last-child {
  border-right: none;
}

.demo-date-picker .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}

.demo-range .el-date-editor {
  margin: 8px;
}

.demo-range .el-range-separator {
  box-sizing: content-box;
}

.uploader {
  width: 150px;
  height: 150px !important;
  overflow: hidden;
}
</style>

