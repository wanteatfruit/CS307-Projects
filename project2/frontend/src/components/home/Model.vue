<template>
<div>
        <div class='b'>
        <ul>
            <li>
        <button v-on:click="goback">Back</button>
        <button v-on:click="showAll">Refresh Data</button>
            </li>
            <br>
            <li>
        <input type="text" v-model="model.q" placeholder="name/id/model"/>
        <button v-on:click="query">Query</button>
            </li>
            <br>
            <li>
        <input type="text" v-model="model.id" placeholder="model_id"/>
        <input type="text" v-model="model.num_in" placeholder="product_number"/>
        <input type="text" v-model="model.model_in" placeholder="product_model"/>
        <input type="text" v-model="model.name_in" placeholder="product_name"/>
        <input type="text" v-model="model.unit_in" placeholder="unit_price"/>
        <button v-on:click="insert">Insert</button>
            </li>
            <br>
            <li>
        <input type="text" v-model="model.unit_up" placeholder="unit_price"/>
        <input type="text" v-model="model.model_up" placeholder="product_model"/>
        <button v-on:click="update">Update</button>

            </li>
            <br>
            <li>
        <input type="text" v-model="model.model_del" placeholder="product_model"/>
        <button v-on:click="del">Delete</button>
            </li>
        </ul>
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
    name: "fav",
    data(){
        return{
            columns:[{
                label: 'ID',
                field: 'id'
            },{
                label:'Product number',
                field:'product_number'
            },{
                label:'Product model',
                field:'product_model'
            },{
                label:'Product name',
                field:'product_name'
            },{
                label:'Unit price',
                field:'unit_price'
            },

            ],
            rows:[],
            model:{
                q:'',
                id:'',
                name_in:'',
                model_in:'',
                num_in:'',
                unit_in:'',
                unit_up:'',
                model_up:'',
                model_del:''
            }
            
        }
    },
    mounted(){ //address need change
        this.$axios.get('/model').then(res=>{

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
    del(){
        this.$axios.post('/model/delete',{
        product_model:this.model.model_del,
        }).then(res=>{
            this.showAll()
        })

    },
    showAll(){
            this.$axios.get('/model').then(res=>{
            this.rows=Object.values(res.data)
            console.log(res.data);
        }).catch(failResponse=>{
            alert('failed')
        }) 
    },
    query(){
        this.$axios.post('/model/sbyname',{
            product_model:this.model.q,
            product_name:this.model.q,
            id:this.model.q
        }).then(res=>{
            this.rows=res.data
            console.log(res.data);
        })
    },
    insert(){
        this.$axios.post('/model/insert',{
            product_name:this.model.name_in,
            id:this.model.id,
            unit_price:this.model.unit_in,
            product_number:this.model.num_in,
            product_model:this.model.model_in
        }).then(res=>{
            this.showAll()
        })
    },
    update(){
        this.$axios.post('/model/update',{
            unit_price:this.model.unit_up,
            product_model:this.model.model_up
        }).then(res=>{
            this.showAll()
        })
    }

    }
}
</script>


<!-- <style>
      button {
        display: inline-block;
        background-color: #7b38d8;
        border-radius: 10px;
        border: 4px double #cccccc;
        color: #eeeeee;
        
        font-size: 20px;
        padding: 10px;
        width: 150px;
        transition: all 0.5s;
        cursor: pointer;
        margin: 5px;
      }
        input {
        display: inline-block;
        background-color: #ffffff;
        border-radius: 10px;
        border: 4px double #cccccc;
        color: #000000;
        text-align: center;
        font-size: 20px;
        padding: 10px;
        width: 150px;
        transition: all 0.5s;
        margin: 5px;
      }
            

      button span {
        cursor: pointer;
        display: inline-block;
        position: relative;
        transition: 0.5s;
      }
      button span:after {
        content: '\00bb';
        position: absolute;
        opacity: 0;
        top: 0;
        right: -20px;
        transition: 0.5s;
      }
      button:hover {
        background-color: #f7c2f9;
      }
      button:hover span {
        padding-right: 25px;
      }
      button:hover span:after {
        opacity: 1;
        right: 0;
      }
    </style> -->