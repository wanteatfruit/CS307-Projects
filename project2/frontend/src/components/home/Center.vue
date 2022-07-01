<template>
<div>
        <div class='b'>
            <ul>
                <li>
<button v-on:click="goback">Back</button>
        <button v-on:click="showAll">Refresh Data</button>
                </li>
                <li>
        <input type="text" v-model="center.name" placeholder="name/id"/>
        <button v-on:click="query">Query</button>
                </li>
                <li>
        <input type="text" v-model="center.id" placeholder="id"/>
        <input type="text" v-model="center.name_in" placeholder="name"/>
        <button v-on:click="insert">Insert</button>

                </li>
                <li>
        <input type="text" v-model="center.name_del" placeholder="name"/>
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
                label:'Name',
                field:'name'
            }
            ],
            rows:[],
            center:{
                name:'',
                name_in:'',
                name_del:'',
                id:''
            }
            
        }
    },
    mounted(){ //address need change
        this.$axios.get('/center').then(res=>{

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
        this.$axios.post('/center/delete',{
        name:this.center.name_del,
        
        }).then(res=>{
            this.showAll()
        })

    },
    showAll(){
               this.$axios.get('/center').then(res=>{

            this.rows=Object.values(res.data)
            console.log(res.data);
        }).catch(failResponse=>{
            alert('failed')
        }) 
    },
    query(){
        this.$axios.post('/center/sbyname',{
            name:this.center.name,
            id:this.center.name
        }).then(res=>{
            this.rows=res.data
            console.log(res.data);
        })
    },
    insert(){
        this.$axios.post('/center/insert',{
            name:this.center.name_in,
            id:this.center.id
        }).then(res=>{
            this.showAll()
        })
    }

    }
}
</script>
