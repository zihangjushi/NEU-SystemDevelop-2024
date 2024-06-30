const path = require('path');
const { VueLoaderPlugin } = require('vue-loader');

module.exports = {
    mode: 'development', // 开发模式
    entry: './src/main.js', // 入口文件
    output: {
        path: path.resolve(__dirname, 'dist'), // 输出路径
        filename: 'bundle.js' // 输出文件名
    },
    resolve: {
        extensions: ['.js', '.vue'], // 解析文件扩展名
        alias: {
            '@': path.resolve(__dirname, 'src') // 设置别名，方便引用
        }
    },
    module: {
        rules: [
            {
                test: /\.vue$/, // 匹配.vue文件
                loader: 'vue-loader' // 使用vue-loader处理
            },
            {
                test: /\.js$/, // 匹配.js文件
                exclude: /node_modules/, // 排除node_modules目录
                use: {
                    loader: 'babel-loader' // 使用babel-loader转译
                }
            },
            {
                test: /\.(png|jpg|gif|svg)$/, // 匹配图片文件
                loader: 'file-loader', // 使用file-loader加载
                options: {
                    name: '[name].[ext]?[hash]' // 文件名格式
                }
            }
        ]
    },
    plugins: [
        new VueLoaderPlugin() // 添加VueLoaderPlugin插件
    ],
    devServer: {
        contentBase: './dist' // 开发服务器配置，指定contentBase为dist目录
    }
};
