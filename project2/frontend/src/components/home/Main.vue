<template>
  <div id='building'>
  <div>
    <ul>
      <li>
        <button v-on:click="importOrginal">导入原始数据</button>
        <br></li>
        <li><button v-on:click="goCenter">Center</button>
          <button v-on:click="goModel">Model</button>
    <button v-on:click="goEnterprise">Enterprise</button>
        </li>
        <br>
      <li><input type="text" v-model="infile.filename" placeholder="File name"/>
        <button v-on:click="stockIn">stockIn</button>
      </li>
      <li><input type="text" v-model="infile.placeOrderName" placeholder="File name"/>
        <button v-on:click="placeOrder">placeOrder</button>
      </li>
      <li><input type="text" v-model="infile.updateOrderName" placeholder="File name"/>
        <button v-on:click="updateOrder">updateOrder</button>
      </li>
      <li><input type="text" v-model="infile.deleteOrderName" placeholder="File name"/>
        <button v-on:click="deleteOrder">deleteOrder</button>
      </li>
      <br>
      <li>
        <button v-on:click="getAllStaffCount">getAllStaffCount</button>
        <button v-on:click="getContractCount">getContractCount</button>
        <button v-on:click="getOrderCount">getOrderCount</button>
        <button v-on:click="getNeverSoldProductCount">getNeverSoldProductCount</button>
        <button v-on:click="getFavoriteProductModel">getFavoriteProductModel</button>
        <button v-on:click="getAvgStockByCenter">getAvgStockByCenter</button>
        <button v-on:click="getProductByNumber">getProductByNumber</button>
        <button v-on:click="getContractInfo">getContractInfo</button>
      </li>
      <br>
      <li>
        <button v-on:click="getPrice1">getOrderCostGroupByEnterprise</button>
      </li>
      <li>
        <button v-on:click="getPrice2">getOrderCostGroupByModel</button>
      </li>
      <li>
        <button v-on:click="getPrice3">getStockCostGroupByCenter</button>
      </li>
      <li>
        <button v-on:click="getPrice4">getStockCostGroupByModel</button>
      </li>
      <br>
      <li><input type="text" v-model="infile.insertUnfinished" placeholder="File name"/>
        <button v-on:click="insertUnfinished">insertUnfinished</button>
      </li>
      <br>
      <li><input type="text" v-model="infile.updateUnfinished" placeholder="File name"/>
        <button v-on:click="updateUnfinished">update contract_type through file</button>
      </li>
      <br>
      <li>
      <button v-on:click="updateType">check and update contract_type</button>
      </li>
      <br>
      <li>
        <button v-on:click="goOrderQuery">Order Query</button>
      </li>
    </ul>
  </div>
  </div>
</template>


<script>
export default {
  name: 'Main',
  data() {
    return {
      product: [],
      infile: {
        filename: '',
        placeOrderName: '',
        updateOrderName: '',
        deleteOrderName: '',
        insertUnfinished:'',
        updateUnfinished:''
      }
    }
  },
  methods: { //get is to get data from backend, post is to send data to backend
    importOrginal() {
      this.$axios.get('/import', {}).then(resp => {
        if (resp && resp.status === 200) {
          alert('Original data imported successfully!')
        }
      })
        .catch(failResponse => { //failed connection, not failed import(just for debug)
          alert('Failed😔')
        })
    },
    stockIn() {
      this.$axios.post('/stockin', {
        filename: this.infile.filename
      }).then(resp => {
        if (resp && resp.status === 200) {
          alert('Stock imported successfully!')
        }
      })
        .catch(failResponse => { //failed connection, not failed import(just for debug)
          alert('Failed😔')
        })

    },
    placeOrder() {
      this.$axios.post('/plaord', {
        filename: this.infile.placeOrderName
      }).then(resp => {
        if (resp && resp.status === 200) {
          alert('Placed order successfully!')
        }
      })
        .catch(failResponse => { //failed connection, not failed import(just for debug)
          alert('Failed😔')
        })
    },
    updateOrder() {
      this.$axios.post('/updord', {
        filename: this.infile.updateOrderName
      }).then(resp => {
        if (resp && resp.status === 200) {
          alert('Orders updated successfully!')
        }
      })
        .catch(failResponse => { //failed connection, not failed import(just for debug)
          alert('Failed😔')
        })

    },
    insertUnfinished(){
      this.$axios.post('/insertUnfinished',{
        filename: this.infile.insertUnfinished
      }).then(resp =>{
        if (resp && resp.status === 200) {
          alert('Insert data imported successfully!')
        }
      })
        .catch(failResponse => { //failed connection, not failed import(just for debug)
          alert('Failed😔')
        })
    },
    updateUnfinished(){
      this.$axios.post('/updUnfinish',{
        filename: this.infile.updateUnfinished
      }).then(resp =>{
        if (resp && resp.status === 200) {
          alert('finished successfully!')
        }
      })
        .catch(failResponse => { //failed connection, not failed import(just for debug)
          alert('Failed😔')
        })
    },
    updateType(){
      this.$axios.get('/updtype',{}).then(resp => {
        if (resp && resp.status === 200) {
          alert('Contract_type checked and changed successfully!')
        }
      })
        .catch(failResponse => { //failed connection, not failed import(just for debug)
          alert('Failed😔')
        })
    },
    deleteOrder() {
      this.$axios.post('/delord', {
        filename: this.infile.deleteOrderName
      }).then(resp => {
        if (resp && resp.status === 200) {
          alert('Orders deleted successfully!')
        }
      })
        .catch(failResponse => { //failed connection, not failed import(just for debug)
          alert('Failed😔')
        })

    },

    goOrderQuery(){
      this.$router.replace({path:'/orderinfo'})
    },
    goEnterprise(){
      this.$router.replace({path:'/ent'})
    },
    goModel(){
      this.$router.replace({path:'/model'})
    },
    goCenter(){
      this.$router.replace({path: '/center'})
    },
    getFavoriteProductModel() {
      this.$router.replace({path: '/fav'})
    },
    getAllStaffCount() {
      this.$router.replace({path: '/staffcnt'})
    },
    getContractCount() {
      this.$router.replace({path: '/concnt'})
    },
    getOrderCount() {
      this.$router.replace({path: '/ordcnt'})
    },
    getNeverSoldProductCount() {
      this.$router.replace({path: '/nvrsold'})
    },
    getAvgStockByCenter() {
      this.$router.replace({path: '/avg'})
    },
    getProductByNumber() {
      this.$router.replace({path: '/prodbynum'})
    },
    getContractInfo() {
      this.$router.replace({path: '/coninfo'})
    },
    getPrice1() {
      this.$router.replace({path: '/price1'})
    },
    getPrice2() {
      this.$router.replace({path: '/price2'})
    },
    getPrice3() {
      this.$router.replace({path: '/price3'})
    },
    getPrice4() {
      this.$router.replace({path: '/price4'})
    }
  }

}
</script>

<style>
      button {
        display: inline-block;
        background-color: #7b38d8;
        border-radius: 10px;
        border: 4px double #cccccc;
        color: #eeeeee;
        text-align: center;
        font-size: 20px;
        padding: 10px;
        width: 300px;
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
        width: 300px;
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
      #building{
        background:url("https://pic3.zhimg.com/v2-526e03d542420a445ea3113417d592be_b.jpg");
        width:100%;
        height:100%;
        /*position:fixed;*/
        background-size:100% 100%;
      }
    </style>
