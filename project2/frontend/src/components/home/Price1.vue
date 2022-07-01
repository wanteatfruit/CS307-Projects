
<template>
<!--  <div v-if=" this.$data!== undefined && this.columns.length > 0">-->
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
  name: "Price1",
  data() {
    return {
      columns: [{
        label: 'Enterprise Name',
        field: 'text'
      }, {
        label: 'Sum Cost',
        field: 'sum'
      }
      ],
      rows: []
    }
  },
  mounted() {
    this.$axios.get('/price1').then(res => {
      this.rows = Object.values(res.data)
      console.log(res.data)
    }).catch(failResponse => {
      alert('failed')
    })
  },
  methods: {
    goback() {
      this.$router.replace({path: '/main'})
    },
  }
}
</script>

<!--<style scoped>-->

<!--</style>-->
