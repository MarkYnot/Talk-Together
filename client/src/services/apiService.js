class apiServce{


  baiduTranslate(originText, fromLanguage, toLanguage, appid, salt, sign){
    return axios.get(`/baiduApi/api/trans/vip/translate?q=${originText}&from=${fromLanguage}&to=${toLanguage}&appid=${appid}&salt=${salt}&sign=${sign}`).then(res=>{return res.data.trans_result[0].dst})
       
  }

  baiduSentTranslate(originText, fromLanguage, toLanguage, appid, salt, sign){
    return axios.get(`/baiduApi/api/trans/vip/translate?q=${originText}&from=${fromLanguage}&to=${toLanguage}&appid=${appid}&salt=${salt}&sign=${sign}`).then(res=>{return res.data.trans_result[0].dst})
    
}

}

export default new apiServce();