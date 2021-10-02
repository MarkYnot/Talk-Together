<template>
  
  <p>{{test[0]["password"]}}</p>
</template>

<script>
import testServce from "../services/testServce";
export default {
  name: "Book.vue",
  data(){
    return{
    
      test:[]

    }
  },

  components:{
    testServce
  },
  created(){
    // testServce.getAllBookData()
    axios.get(`http://localhost:3001/test`).then(result=>{
      console.log(result)
      console.log(result.data)
      this.test = result.data
    })

  },

  method(){


  }



}
</script>

<style scoped>

</style>
