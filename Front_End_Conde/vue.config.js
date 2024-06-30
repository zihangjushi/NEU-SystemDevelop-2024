const { defineConfig } = require('@vue/cli-service');
const webpack = require('webpack'); // 引入 webpack

module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: true,
  configureWebpack: {

    resolve: {
      extensions: ['.js', '.vue', '.json', '.ts', '.tsx'],
    },
    module: {
      rules: [
        {
          test: /\.ts$/,
          loader: 'ts-loader',
          exclude: /node_modules/,
          options: {
            appendTsSuffixTo: [/\.vue$/],
            transpileOnly: true
          },
        },
        {
          test: /\.tsx$/,
          loader: 'ts-loader',
          exclude: /node_modules/,
          options: {
            appendTsxSuffixTo: [/\.vue$/],
            transpileOnly: true
          },
        },
      ],
    },
    // 使用 webpack 来配置 ProvidePlugin
    plugins: [
      new webpack.ProvidePlugin({
        'window.Quill': 'quill/dist/quill.js',
        'Quill': 'quill/dist/quill.js'
      }),
    ],
  },
});
