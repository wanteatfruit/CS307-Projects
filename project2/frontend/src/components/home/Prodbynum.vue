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
        <input type="text" v-model="model.product_number" placeholder="product_number"/>
        <button v-on:click="query">Query</button>
    </div>
</div>
</template>
<script>
export default {
    name: "fav",
    data(){
        return{
            columns:[{
                label: 'supply_center',
                field: 'supply_center'
            },{
                label:'product_model',
                field:'product_model'
            },{
                label:'quantity',
                field:'quantity'
            }
            ],
            rows:[],
            model:{
                product_number:''
            }
            
        }
    },
    
    methods:{
        goback(){
        this.$router.replace({path:'/main'})
    },
        query(){
            this.$axios.post('/prodbynum',{
            product_number:this.model.product_number
        }).then(resp=>{
            this.rows=resp.data
            console.log(resp.data);
        }).catch(fail=>{
            alert('failed')
        })
        }

    }
}
</script>
