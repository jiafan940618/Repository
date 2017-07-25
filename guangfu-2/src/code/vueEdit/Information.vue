<template>
	<section class="content">
  <div class="box box-primary">
    <div class="box-header with-border">
      <h3 class="box-title">商品编辑</h3><loading v-show='isLoading'></loading>
    </div>
   <form role="form">
     <div class="box-body">
       <div class="form-group col-xs-6">
         <label for="exampleInputEmail1">商品名</label>
         <input value="{{data.title}}" type="text" class="form-control" id="" placeholder="请输入商品名"></div>
         
       <div class="form-group col-xs-6">
         <div class="btn btn-default btn-file"> <i class="fa fa-paperclip"></i>
           图片上传
           <input type="file" name="pic"></div>
         </div>
     </div>
     <!-- /.box-body -->
     <div class="box-footer">
       <button type="submit" class="btn btn-primary">提交</button>

     </div>
   </form>
   </div>
 </div>
 </section>
</template>
<script>
import loading from '../tool/loading.vue'
export default {
  name: 'editProduct',
  data () {
    return {
      data: this.data,
      price: this.price,
      id: this.$route.params.id,
      isLoading: true
    }
  },
  components: {
    'loading': loading
  },
  ready () {
    var self = this
    this.$http({url: '/static/data/product.json', method: 'GET'}).then(function (response) {
      var data = response.data
      window.console.log(data)
      if (data.code !== 200) {
        window.alert('fail')
      } else {
        setTimeout(function () {
          self.isLoading = false
        }, 200)
        this.$set('data', data.data)
      }
    }, function (response) {
      console.log(response.data.data.code + response.data.data.msg)
    })
  }
}
</script>
