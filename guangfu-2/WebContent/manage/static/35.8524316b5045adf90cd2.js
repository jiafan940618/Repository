webpackJsonp([35],{122:function(t,o){"use strict";Object.defineProperty(o,"__esModule",{value:!0}),o["default"]={data:function(){return{data:[]}},components:{},methods:{getConfig:function(){var t=this;this.$http.get("systemConfig/select",{id:this.$route.params.id}).then(function(o){t.data=o.data.data})},edit:function(){this.$http.get("systemConfig/update",this.data).then(function(t){200===t.data.code&&window.alert("编辑成功")})}},route:{data:function(){this.getConfig()}}}},192:function(t,o,a){o=t.exports=a(1)(),o.push([t.id,"",""])},245:function(t,o,a){var e=a(192);"string"==typeof e&&(e=[[t.id,e,""]]);a(2)(e,{});e.locals&&(t.exports=e.locals)},312:function(t,o){t.exports='<section class=content _v-92a4d910=""> <div class=box _v-92a4d910=""> <div class=box-header _v-92a4d910=""> <h3 class=box-title _v-92a4d910=""> 系统配置 </h3> </div> <div class=box-body _v-92a4d910=""> <div class="" _v-92a4d910=""> <div class="form-group col-xs-12" _v-92a4d910=""> <label _v-92a4d910="">配置项:</label> <b _v-92a4d910="">{{data.propertyKey}}</b> </div> <div class="form-group col-xs-12" _v-92a4d910=""> <label _v-92a4d910="">值</label> <input v-model=data.propertyValue type=text class=form-control id="" placeholder=值 _v-92a4d910=""> </div> <div class="form-group col-xs-12" _v-92a4d910=""> <label _v-92a4d910="">备注</label> <input v-model=data.remark type=text class=form-control id="" placeholder=备注 _v-92a4d910=""> </div> </div> </div> <div class=box-footer _v-92a4d910=""> <button v-on:click=edit type=button class="btn btn-primary" _v-92a4d910="">提交</button> </div> </div> </section>'},366:function(t,o,a){var e,d;a(245),e=a(122),d=a(312),t.exports=e||{},t.exports.__esModule&&(t.exports=t.exports["default"]),d&&(("function"==typeof t.exports?t.exports.options||(t.exports.options={}):t.exports).template=d)}});