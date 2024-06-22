<template>
    <div class="PreparePage">
        <el-menu>
            <el-menu-item index="1">
                <!-- //序号为1的侧边栏，用来显示测盟汇和图片组件 -->
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
        </el-menu>
    </div>
</template>

<script>
export default {
    components: {  },
    setup() {
    }
    }
</script>
