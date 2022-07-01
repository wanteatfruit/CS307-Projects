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
        <input type="text" v-model="ent.q" placeholder="id/name"/>
        <button v-on:click="query">Query</button>
            </li>
            <br>
            <li>
        <input type="text" v-model="ent.id_in" placeholder="id"/>
        <input type="text" v-model="ent.name_in" placeholder="enterprise_name"/>
        <input type="text" v-model="ent.country_in" placeholder="country"/>
        <input type="text" v-model="ent.city_in" placeholder="city"/>
        <input type="text" v-model="ent.supply_in" placeholder="supply_center"/>
        <input type="text" v-model="ent.industry_in" placeholder="industry"/>
        <button v-on:click="insert">Insert</button>
            </li>
            <br>
            <li>
        <input type="text" v-model="ent.industry_up" placeholder="new industry"/>
        <input type="text" v-model="ent.name_up" placeholder="enterprise_name"/>
        <button v-on:click="update">Update</button>
            </li>
            <br>
            <li>
        <input type="text" v-model="ent.name_del" placeholder="ent name"/>
        <button v-on:click="del">Delete</button>
            </li>

        </ul>
        <br>
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
    name: "ent",
    data(){
        return{
            columns:[{
                label: 'ID',
                field: 'id'
            },{
                label:'Name',
                field:'enterprise_name'
            },{
                label:'Country',
                field:'country'
            },{
                label:'City',
                field:'city'
            },{
                label:'Supply Center',
                field:'supply_center'
            },{
                label:'Industry',
                field:'industry'
            }
            
            ],
            rows:[],
            ent:{
                q:'',
                name_up:'',
                name_in:'',
                name_del:'',
                id_in:'',
                country_in:'',
                city_in:'',
                supply_in:'',
                industry_in:'',
                industry_up:'',
            }
            
        }
    },
    mounted(){ //address need change
        this.$axios.get('/ent').then(res=>{

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
        this.$axios.post('/ent/delete',{
        enterprise_name:this.ent.name_del,
        
        }).then(res=>{
            this.showAll()
        })

    },
    showAll(){
               this.$axios.get('/ent').then(res=>{

            this.rows=Object.values(res.data)
            console.log(res.data);
        }).catch(failResponse=>{
            alert('failed')
        }) 
    },
    query(){
        this.$axios.post('/ent/sbyname',{
            enterprise:this.ent.q,
            id:this.ent.q
        }).then(res=>{
            this.rows=res.data
            console.log(res.data);
        })
    },
    insert(){
        this.$axios.post('/ent/insert',{
            
            id:this.ent.id_in,
            enterprise_name:this.ent.name_in,
            city:this.ent.city_in,
            country:this.ent.country_in,
            industry:this.ent.industry_in,
            supply_center:this.ent.supply_in
        }).then(res=>{
            this.showAll()
        })
    },
    update(){
        this.$axios.post('/ent/update',{
            industry:this.ent.industry_up,
            enterprise_name:this.ent.name_up
        }).then(res=>{
            this.showAll()
        })
    }

    }
}
</script>

<style>
      /* button {
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
      } */
            

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
    </style>
