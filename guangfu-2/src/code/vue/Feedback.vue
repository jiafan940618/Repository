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
                  <th v-on:click="sort='userId'"></th>
                  <th v-on:click="sort='title'"></th>
                  <th v-on:click="sort='content'"></th>
                  <th v-on:click="sort='createDtm'"></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for='item in list | orderBy sort'>
                  <td><input v-model="item.isSelected" type="checkbox"></td>
                  <td>{{item.id}}</td>
                  <td><a href="#" v-link="{path:'/product/edit/'+item.id}">{{item.Feedback}}</a></td>
                                    <td>{{item.userId}}</td>
                  <td>{{item.title}}</td>
                  <td>{{item.content}}</td>
                  <td>{{item.createDtm | timeFormat}}</td>

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
      this.$http({url: 'Feedback/selectByExample', method: 'GET', data: { index: this.page }}).then(function (response) {
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
      this.$http({url: 'Feedback/deleteBatch', method: 'GET', data: { list: delList }}).then(function (response) {
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
