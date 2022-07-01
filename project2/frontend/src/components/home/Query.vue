<template>
    <!-- <table>
    <thead>
        <tr>
            <th colspan="2">The table header</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>The table body</td>
            <td>with two columns</td>
        </tr>
    </tbody>
</table> -->
<button v-on:click="goback">goback</button>
</template>
<script>
export default {
    name: "query",
    data(){
        return{
            data:[],
            columns:[
        {field: 'id', title: '编号', width: 80, titleAlign: 'center', columnAlign: 'center',isResize:true},
        {field: 'email', title: '邮箱', width: 150, titleAlign: 'center', columnAlign: 'center',isResize:true},
        {field: 'password', title: '密码', width: 150, titleAlign: 'center', columnAlign: 'center',isResize:true},
        {field: 'username', title: '姓名', width: 280, titleAlign: 'center', columnAlign: 'left',isResize:true}

            ]
        }
    },
    created(){
        this.$ajax('http://localhost:8080/do/user/userAll').then(res=>{
            this.data=res.data
            console.log(res.data);
        }).catch(function (error){
            console.log(error);
        })
    },
    methods:{
        goback(){
        this.$router.replace({path:'/main'})
    },
    }
}
</script>

<style scoped>
table,
td {
    border: 1px solid #333;
}

thead,
tfoot {
    background-color: #333;
    color: #fff;
}

</style>