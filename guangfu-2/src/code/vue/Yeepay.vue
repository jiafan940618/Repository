<template>
	<section class="content">
		<div class="box">
            <div class="box-header">
              <h3 class="box-title">商品管理</h3>
            </div>
            <tool-toolbar ></tool-toolbar>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example1" class="table table-bordered table-striped">
                <thead>
                <tr>
                  <th>#</th>
                  
                  <th v-on:click="sort='id'"></th>
                  <th v-on:click="sort='merchantaccount'"></th>
                  <th v-on:click="sort='yborderid'"></th>
                  <th v-on:click="sort='orderid'"></th>
                  <th v-on:click="sort='bankcode'"></th>
                  <th v-on:click="sort='bank'"></th>
                  <th v-on:click="sort='lastno'"></th>
                  <th v-on:click="sort='cardtype'"></th>
                  <th v-on:click="sort='amount'"></th>
                  <th v-on:click="sort='status'"></th>
                  <th v-on:click="sort='sign'"></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for='item in list | orderBy sort'>
                  <td><input v-model="item.isSelected" type="checkbox"></td>
                  <td>{{item.id}}</td>
                  <td><a href="#" v-link="{path:'/product/edit/'+item.id}">{{item.Yeepay}}</a></td>
                                    <td>{{item.merchantaccount}}</td>
                  <td>{{item.yborderid}}</td>
                  <td>{{item.orderid}}</td>
                  <td>{{item.bankcode}}</td>
                  <td>{{item.bank}}</td>
                  <td>{{item.lastno}}</td>
                  <td>{{item.cardtype}}</td>
                  <td>{{item.amount}}</td>
                  <td>{{item.status}}</td>
                  <td>{{item.sign}}</td>

                </tr>
                </tbody>
                <tfoot>
                <tr>
                </tr>
                </tfoot>
              </table>
            </div>
            <!-- /.box-body -->
          </div>
	</div>
	<!-- DataTables -->

</template>
<script>
import toolbar from '../tool/toolbar.vue'

export default {
  name: 'Container',
  data () {
    return {
      sort: 'cost',
      page: 1,
      list: [],
      total: 1
    }
  },
  filter (time, value) {
    return value + '123'
  },
  methods: {
    show () {
      this.showAlert = true
    },
    init () {
      this.$http({url: 'Yeepay/selectByExample', method: 'GET', data: { index: this.page }}).then(function (response) {
        var data = response.data
        if (data.code !== 200) {
          window.alert('fail')
        } else {
          this.$set('list', data.data.list)
          this.$set('total', data.data.total)
        }
      }, function (response) {
        console.log('fail')
      })
    }
  },
  events: {
    delete () {
      var list = []
      for (var i = this.list.length - 1; i >= 0; i--) {
        if (this.list[i].isSelected) {
          list.push(this.list[i])
        }
      }
      var delList = []
      for (var b = list.length - 1; b >= 0; b--) {
        delList += list[b]['id'] + ','
      }
      this.$http({url: 'Yeepay/deleteBatch', method: 'GET', data: { list: delList }}).then(function (response) {
        var data = response.data
        if (data.code !== 200) {
          window.alert('fail')
        } else {
          this.init()
          window.swal(
          '操作完成',
          '',
          'success'
          )
        }
      }, function (response) {
        console.log('fail')
      })
    },
    out_up () {
    },
    out_down () {
    },
    refresh () {
      this.init()
    },
    page_up () {
      this.init(this.page -= 1)
    },
    page_down () {
      this.init(this.page += 1)
    }
  },
  components: {
    'tool-toolbar': toolbar
  },
  ready () {
    this.init()
  }
}

</script>
