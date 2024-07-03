<template>
    <div v-if="show" class="modal">
      <div class="modal-content">
        <span class="close" @click="$emit('close')">&times;</span>
        <p>{{ data }}</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'HomeWindows',
    props: {
      show: {
        type: Boolean,
        required: true
      },
      data: {
        type: String,
        required: false,
        default: ''
      }
    }
  }
  </script>
  
  <style scoped>
  .modal {
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0,0,0,0.5);
  }
  .modal-content {
    background-color: #fefefe;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
  }
  .close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
  }
  .close:hover,
  .close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
  }
  </style>
  