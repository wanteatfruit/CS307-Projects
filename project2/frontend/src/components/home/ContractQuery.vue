<template>
<div>
  <div clas="b">
  <li>
  <button v-on:click="goback">Back</button>
  </li>
  <br>
  <li>
    <input type="text" v-model="contract.contract_number" placeholder="contract_number"/>
    <input type="text" v-model="contract.product_model" placeholder="product_model"/>
    <input type="text" v-model="contract.enterprise" placeholder="enterprise"/>
  </li>
  <li>
    <button v-on:click="query">Query</button>
  </li>
  </div>
  <div>
    <vue-good-table
      :columns="columns"
      :rows="rows"/>
  </div>
</div>
</template>

<script>
export default {
  name: "ContractQuery",
  data(){
    return{
      columns:[{
        label:'contract_number',
        field:'contract_number'
      },{
        label:'enterprise',
        field:'enterprise'
      },{
        label:'product_model',
        field:'product_model'
      },{
        label:'quantity',
        field:'quantity'
      },{
        label:'quantity',
        field:'quantity'
      },{
        label:'contract_manager',
        field:'contract_manager'
      },{
        label:'contract_date',
        field:'contract_date'
      },{
        label:'est_date',
        field:'est_date'
      },{
        label:'lod_date',
        field:'lod_date'
      },{
        label:'salesman_num',
        field:'salesman_num'
      },{
        label:'contract_type',
        field:'contract_type'
      }
      ],
      rows:[],
      contract:{
        contract_number:'',
        product_model:'',
        enterprise:''
      }
    }
  },
  methods: {
    goback() {
      this.$router.replace({path: '/main'})
    },
    query(){
      this.$axios.post('/orderinfo/q',{
        contract_number:this.contract.contract_number,
        product_model:this.contract.product_model,
        enterprise:this.contract.enterprise
      }).then(res=>{
        this.rows=res.data
        console.log(res.data);
      })
    }
  }
}
</script>

<style scoped>

</style>
