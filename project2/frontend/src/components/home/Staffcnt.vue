<template>
<div>
     <div>
    <vue-good-table
      :columns="columns"
      :rows="rows"/>
  </div>
    <div class='b'>
        <br>
        <button v-on:click="goback">Back</button>
    </div>
</div>
</template>
<script>
export default {
    name: "fav",
    data(){
        return{
            columns:[{
                label: 'staff_type',
                field: 'type'
            },{
                label:'count',
                field:'count'
            }
            ],
            rows:[]
            
        }
    },
    mounted(){ //address need change
        this.$axios.get('/staffcnt').then(res=>{

            this.rows=Object.values(res.data)
            console.log(res.data);
        }).catch(failResponse=>{
            alert('failed')
        })
    },
    methods:{
        goback(){
        this.$router.replace({path:'/main'})
    },
    }
}
</script>
