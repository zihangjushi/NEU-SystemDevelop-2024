import { createStore } from 'vuex'

const store = createStore({
  state: {
    user: null
  },
  getters: {
    user: state => state.user
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
    }
  },
  actions: {
    setUser({ commit }, user) {
      commit('setUser', user);
    }
  },
  modules: {
  }
});

export default store;