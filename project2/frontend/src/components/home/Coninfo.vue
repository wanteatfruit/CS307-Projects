<template>
<div>
    <div>
    <vue-good-table
      :columns="contract_number_columns"
      :rows="num_rows"/>
  </div>
<div>
    <vue-good-table
      :columns="enterprise_columns"
      :rows="enterprise_rows"/>
  </div>
    <div>
    <vue-good-table
      :columns="manager_columns"
      :rows="manager_rows"/>
  </div>
  <div>
    <vue-good-table
      :columns="center_columns"
      :rows="center_rows"/>
  </div>
     <div>
    <vue-good-table
      :columns="columns"
      :rows="rows"/>
  </div>
    <div class='b'>
        <br>
        <button v-on:click="goback">Back</button>
        <input type="text" v-model="contract.contract_number" placeholder="contract_number"/>
        <button v-on:click="query">Query</button>
    </div>
</div>
</template>
<script>
export default {
    name: "coninfo",
    data(){
        return{
            contract_number_columns:[{
                label:'contract_number',
                field:'contract_num'
            }],
            enterprise_columns:[{
                label:'enterprise',
                field:'enterprise'
            }],
            manager_columns:[{
                label: 'manager',
                field: 'contract_manager'
            }],
            center_columns:[{
                label:'supply_center',
                field: 'supply_center'
            }],
            columns:[{
                label: 'product_model',
                field: 'product_model'
            },{
                label:'salesman',
                field:'staff_name'
            },{
                label:'quantity',
                field:'quantity'
            },{
                label:'unit_price',
                field:'unit_price'
            },{
                label:'estimate_delivery_date',
                field:'est_date'
            },{
                label:'lodgement_date',
                field:'lod_date'
            }
            ],
            rows:[],
            center_rows:[],
            enterprise_rows:[],
            manager_rows:[],
            num_rows:[],
            contract:{
                contract_number:''
            }
            
        }
    },

    methods:{
        goback(){
        this.$router.replace({path:'/main'})
    },
        query(){
            this.$axios.post('/coninfo',{
            contract_number:this.contract.contract_number
        }).then(resp=>{
            this.num_rows=[resp.data[0]]
            this.enterprise_rows=[resp.data[1]]
            this.manager_rows=[resp.data[2]]
            this.center_rows=[resp.data[3]]
            this.rows=resp.data.slice(4,resp.data.length)
            console.log(resp.data[0]);
        }).catch(fail=>{
            alert('This contract does not exist😅')
        })
        },

    }
}
</script>
