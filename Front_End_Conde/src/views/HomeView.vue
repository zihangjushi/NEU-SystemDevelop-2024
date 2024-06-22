<template>
  <div>
    <button @click="openModal">Open Modal</button>
    <HomeWindows :show="isModalVisible" :data="modalData" @close="closeModal" />
  </div>
</template>

<script>
import HomeWindows from '../components/HomeWindow.vue';

export default {
  name: 'HomeWindowView',
  components: {
    HomeWindows
  },
  data() {
    return {
      isModalVisible: false,
      modalData: ''
    };
  },
  methods: {
    openModal() {
      this.modalData = 'This is the data passed to the modal';
      this.isModalVisible = true;
    },
    closeModal() {
      this.isModalVisible = false;
      this.modalData = '';
    }
  }
}
</script>
