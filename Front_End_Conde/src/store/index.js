import { createStore } from 'vuex'

const store = createStore({
  state: {
    userName: ''
  },
  getters: {
    userName: state => state.userName
  },
  mutations: {
    setUserName(state, userName) {
      state.userName = userName;
    }
  },
  actions: {
    setUserName({ commit }, userName) {
      commit('setUserName', userName);
    }
  },
  modules: {
  }
});

export default store;