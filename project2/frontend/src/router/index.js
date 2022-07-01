import Vue from 'vue'
import Router from 'vue-router'
import AppIndex from '@/components/home/AppIndex'
import Login from '@/components/Login'
import Main from '@/components/home/Main'
import Query from '@/components/home/Query'
import Fav from '@/components/home/Fav'
import Staffcnt from '@/components/home/Staffcnt'
import Ordcnt from '@/components/home/Ordcnt'
import Concnt from '@/components/home/Concnt'
import Prodbynum from '@/components/home/Prodbynum'
import Avg from '@/components/home/Avg'
import Nvrsold from '@/components/home/Nvrsold'
import Coninfo from '@/components/home/Coninfo'
import Price1 from "@/components/home/Price1";
import Price2 from "@/components/home/Price2";
import Price3 from "@/components/home/Price3";
import Price4 from "@/components/home/Price4";
import Center from '@/components/home/Center'
import Model from '@/components/home/Model'
import Enterprise from '@/components/home/Enterprise'
import ContractQuery from '@/components/home/ContractQuery'
// import HelloWorld from '@/components/HelloWorld'
// import HelloDemo from '@/components/HelloDemo'
// import { component } from 'vue/types/umd'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/ent',
      name: 'Enterprise',
      component: Enterprise
    },
    {
      path: '/model',
      name: 'Model',
      component: Model
    },
    {
      path: '/center',
      name: 'Center',
      component: Center
    },
    {
      path: '/',
      redirect: '/main'
    },
    {
      path: '/coninfo',
      name: 'Coninfo',
      component: Coninfo
    },
    {
      path: '/prodbynum',
      name: 'Prodbynum',
      component: Prodbynum
    },
    {
      path: '/ordcnt',
      name: 'Ordcnt',
      component: Ordcnt
    },
    {
      path: '/concnt',
      name: 'Concnt',
      component: Concnt
    },
    {
      path: '/staffcnt',
      name: 'Staffcnt',
      component: Staffcnt
    },
    {
      path: '/nvrsold',
      name: 'Nvrsold',
      component: Nvrsold
    },
    {
      path: '/avg',
      name: 'Avg',
      component: Avg
    },
    {
      path: '/fav',
      name: 'Fav',
      component: Fav
    },
    {
      path: '/query',
      name: 'Query',
      component: Query
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/index',
      name: 'AppIndex',
      component: AppIndex
    }, {
      path: '/price1',
      name: 'Price1',
      component: Price1
    }, {
      path: '/price2',
      name: 'Price2',
      component: Price2
    }, {
      path: '/price3',
      name: 'Price3',
      component: Price3
    }, {
      path: '/price4',
      name: 'Price4',
      component: Price4
    },
    {
      path: '/main',
      name: 'Main',
      component: Main
    },
    {
      path: '/orderinfo',
      name: 'ContractQuery',
      component: ContractQuery
    }
  ]
})
