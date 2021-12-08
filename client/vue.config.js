module.exports = {
  
  devServer:{
 
    proxy: {
      '/baiduApi': {
        target: 'http://api.fanyi.baidu.com', 
        changeOrigin: true,
        pathRewrite: {
          '^/baiduApi': ''
        }
      }

    }

  }

}