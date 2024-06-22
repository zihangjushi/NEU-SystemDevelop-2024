<template>
  <el-container>
    <el-aside width="250px" style="background-color: #f2f2f2;">
      <el-input
        v-model="searchQuery"
        placeholder="请输入部门名称"
        clearable
        style="margin: 10px;"
        @input="filterMenu"
      />
      <el-menu
        :default-active="activeIndex"
        class="el-menu-vertical-demo"
        @select="handleSelect"
      >
        <el-sub-menu
          v-for="(company, index) in filteredMenu"
          :key="index"
          :index="index.toString()"
        >
          <template #title>{{ company.name }}</template>
          <el-menu-item
            v-for="(department, subIndex) in company.departments"
            :key="subIndex"
            :index="`${index}-${subIndex}`"
          >
            {{ department }}
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-main>
        <!-- Main content goes here -->
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: '',
      activeIndex: '0',
      menu: [
        {
          name: '深圳总公司',
          departments: ['研发部门', '市场部门', '测试部门', '财务部门', '运维部门'],
        },
        {
          name: '长安分公司',
          departments: ['市场部门', '财务部门'],
        },
      ],
      filteredMenu: [],
    };
  },
  mounted() {
    this.filteredMenu = this.menu;
  },
  methods: {
    filterMenu() {
      const query = this.searchQuery.toLowerCase();
      this.filteredMenu = this.menu.map(company => {
        return {
          ...company,
          departments: company.departments.filter(department =>
            department.toLowerCase().includes(query)
          ),
        };
      }).filter(company => company.departments.length > 0);
    },
    handleSelect(index) {
      this.activeIndex = index;
      // handle selection logic here
    },
  },
};
</script>

<style scoped>
.el-aside {
  background-color: #fff;
}
</style>
