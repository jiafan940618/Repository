webpackJsonp([18],{109:function(t,e){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e["default"]={name:"",data:function(){return{}}}},112:function(t,e,a){"use strict";function d(t){return t&&t.__esModule?t:{"default":t}}Object.defineProperty(e,"__esModule",{value:!0});var s=a(353),r=d(s);e["default"]={data:function(){return{orderData:{server:{serverName:"",id:""},user:{username:""}},showEditBox:!1,currentHight:!1,stepA:2,stepB:2,stepC:2,stepD:2,stepE:2,isEditpayWay:!1,paywayType:"",editPaidMoney:!1}},components:{editBox:r["default"]},filters:{payWay:function(t){switch(t){case 0:return"线下付款";case 1:return"易宝移动端";case 2:return"易宝网银";default:return"未支付"}},stepB:function(t){switch(t){case 0:return"已预约";case 1:return"勘查完成";case 2:return"未预约"}},stepC:function(t){switch(t){case 0:return"已申请";case 1:return"申请完成";case 2:return"未申请"}},stepD:function(t){switch(t){case 0:return"已申请";case 1:return"施工完成";case 2:return"未申请"}},stepE:function(t){switch(t){case 0:return"已申请";case 1:return"开始发电";case 2:return"未申请"}}},methods:{getOrderData:function(){var t=this;this.$http.get("order/select",{id:this.$route.params.id}).then(function(e){t.orderData=e.data.data})},sendState:function(t){var e=this;switch(t){case 2:if(0!==this.orderData.stepB)return;this.$http.get("order/update2",{id:this.$route.params.id,StepB:1}).then(function(t){200===t.data.code?e.getOrderData():window.alert(t.msg),window.alert("更改成功")});break;case 3:if(0!==this.orderData.stepC)return;this.$http.get("order/update2",{id:this.$route.params.id,StepC:1}).then(function(t){200===t.data.code?e.getOrderData():window.alert(t.msg),window.alert("更改成功")});break;case 4:if(0!==this.orderData.stepD)return;this.$http.get("order/update2",{id:this.$route.params.id,StepD:1}).then(function(t){200===t.data.code?e.getOrderData():window.alert(t.msg),window.alert("更改成功")});break;case 5:if(0!==this.orderData.stepE)return;this.$http.get("order/update2",{id:this.$route.params.id,StepE:1}).then(function(t){200===t.data.code?e.getOrderData():window.alert(t.msg),window.alert("更改成功")});break;default:window.alert("Fall")}},editOrder:function(){this.showEditBox=~this.showEditBox},isAdmin:function(){this.currentHight=~this.currentHight},changeSetpA:function(t){var e=this;this.$http.get("order/update2",{id:this.$route.params.id,stepA:t}).then(function(t){200===t.data.code?e.getOrderData():window.alert(t.data.msg)})},changeSetpB:function(t){var e=this;this.$http.get("order/update2",{id:this.$route.params.id,stepB:t}).then(function(t){200===t.data.code?e.getOrderData():window.alert(t.data.msg)})},changeSetpC:function(t){var e=this;this.$http.get("order/update2",{id:this.$route.params.id,stepC:t}).then(function(t){200===t.data.code?e.getOrderData():window.alert(t.data.msg)})},changeSetpD:function(t){var e=this;this.$http.get("order/update2",{id:this.$route.params.id,stepD:t}).then(function(t){200===t.data.code?e.getOrderData():window.alert(t.data.msg)})},changeSetpE:function(t){var e=this;this.$http.get("order/update2",{id:this.$route.params.id,stepE:t}).then(function(t){200===t.data.code?e.getOrderData():window.alert(t.data.msg)})},changePayWay:function(){this.isEditpayWay=~this.isEditpayWay},sendPayWay:function(){var t=this;"3"===this.paywayType?(this.stepA=0,this.payWay=4):(this.stepA=1,this.payWay=this.paywayType),this.$http.get("order/update2",{id:this.$route.params.id,stepA:this.stepA,payWay:this.payWay}).then(function(e){200===e.data.code&&(t.changePayWay(),t.getOrderData())})},isEditPaidMoney:function(){this.editPaidMoney=~this.editPaidMoney},sendPaidMoney:function(){var t=this;return this.orderData.paidMoney?void this.$http.get("order/update",{id:this.$route.params.id,paidMoney:this.orderData.paidMoney}).then(function(e){200===e.data.code&&t.isEditPaidMoney()}):!1}},route:{data:function(){this.getOrderData()}}}},171:function(t,e,a){e=t.exports=a(1)(),e.push([t.id,".finishedColor[_v-2eddf0a4]{color:green}.unfinishedColor[_v-2eddf0a4]{color:red}.finisthingColor[_v-2eddf0a4]{color:#3f51b5}.form-label[_v-2eddf0a4]{padding-right:15px}.order-handler ul[_v-2eddf0a4]{list-style:none}.order-handler ul li[_v-2eddf0a4]{float:left;padding:3px 10px}",""])},198:function(t,e,a){e=t.exports=a(1)(),e.push([t.id,".tips[_v-f482b26e]{font-size:12px;color:#a8a8a8}.searching[_v-f482b26e]{color:#a8a8a8}.modal[_v-f482b26e]{display:block}",""])},220:function(t,e,a){var d=a(171);"string"==typeof d&&(d=[[t.id,d,""]]);a(2)(d,{});d.locals&&(t.exports=d.locals)},251:function(t,e,a){var d=a(198);"string"==typeof d&&(d=[[t.id,d,""]]);a(2)(d,{});d.locals&&(t.exports=d.locals)},287:function(t,e){t.exports='<section class=content _v-2eddf0a4=""> <div class=box _v-2eddf0a4=""> <div class="box-header with-border" _v-2eddf0a4=""> <h3 class=box-title _v-2eddf0a4=""> 订单详情 </h3> </div> <div class=box-body _v-2eddf0a4=""> <div class=col-sm-12 _v-2eddf0a4=""> <div class=order-sub-title _v-2eddf0a4=""> <h5 _v-2eddf0a4="">订单概要</h5> </div> <table class="table table-bordered order-table-info" _v-2eddf0a4=""> <tbody _v-2eddf0a4=""> <tr _v-2eddf0a4=""> <td style=width:50% _v-2eddf0a4=""><span class=form-label _v-2eddf0a4="">订单编号:</span><span _v-2eddf0a4="">{{orderData.orderCode}}</span></td> <td _v-2eddf0a4=""><span class=form-label _v-2eddf0a4="">用户昵称:</span><span _v-2eddf0a4="">{{orderData.user.username}}</span></td> </tr> <tr _v-2eddf0a4=""> <td _v-2eddf0a4=""> <span class=form-label _v-2eddf0a4="">付款状态:</span> <span _v-2eddf0a4="">{{orderData.stepA ? \'已支付\' : \'未支付\'}} | </span> <a v-show=!isEditpayWay href=javascript:; @click=changePayWay _v-2eddf0a4="">修改状态</a> <select v-show=isEditpayWay v-model=paywayType _v-2eddf0a4=""> <option value=3 _v-2eddf0a4="">未支付</option> <option value=0 _v-2eddf0a4="">已支付-线下付款</option> <option value=1 _v-2eddf0a4="">已支付-易宝移动端</option> <option value=2 _v-2eddf0a4="">已支付-易宝网银</option> </select> <button class="btn btn-primary" v-show=isEditpayWay @click=sendPayWay _v-2eddf0a4="">保存</button> </td> <td _v-2eddf0a4=""> <span class=form-label _v-2eddf0a4="">付款方式:</span> <span _v-2eddf0a4=""> {{orderData.payWay | payWay}} </span> </td> </tr> <tr _v-2eddf0a4=""> <td _v-2eddf0a4=""><span class=form-label _v-2eddf0a4="">电站地址:</span><span _v-2eddf0a4="">{{orderData.addressText}}</span></td> <td _v-2eddf0a4=""><span class=form-label _v-2eddf0a4="">优惠代码:</span><span _v-2eddf0a4="">{{orderData.privilegeCode}}</span></td> </tr> <tr _v-2eddf0a4=""> <td _v-2eddf0a4=""><span class=form-label _v-2eddf0a4="">电池板型号:</span><span _v-2eddf0a4="">{{orderData.deviceA}}</span></td> <td _v-2eddf0a4=""><span class=form-label _v-2eddf0a4="">逆变器型号:</span><span _v-2eddf0a4="">{{orderData.deviceB}}</span></td> </tr> <tr _v-2eddf0a4=""> <td _v-2eddf0a4=""><span class=form-label _v-2eddf0a4="">汇流箱型号:</span><span _v-2eddf0a4="">{{orderData.deviceC}}</span></td> <td _v-2eddf0a4=""><span class=form-label _v-2eddf0a4="">监控器型号:</span><span _v-2eddf0a4="">{{orderData.deviceD}}</span></td> </tr> <tr _v-2eddf0a4=""> <td _v-2eddf0a4=""><span class=form-label _v-2eddf0a4="">其他设备型号:</span><span _v-2eddf0a4="">{{orderData.deviceE}}</span></td> <td _v-2eddf0a4=""><span class=form-label _v-2eddf0a4="">增值服务:</span><span _v-2eddf0a4="">{{orderData.vasText}} 共计 {{orderData.vasMoney}}元</span></td> </tr> <tr _v-2eddf0a4=""> <td _v-2eddf0a4=""><span class=form-label _v-2eddf0a4="">订单总价:</span><span _v-2eddf0a4="">{{orderData.totalMoney}} 元</span></td> </tr> </tbody> </table> </div> <div class=col-sm-12 _v-2eddf0a4=""> <div class=order-sub-title _v-2eddf0a4=""> <h5 _v-2eddf0a4="">订单操作 <a style="font-size: 12px" href=javascript:; @click=isAdmin _v-2eddf0a4="">高级操作</a></h5> </div> <div class=order-handler _v-2eddf0a4=""> <table class="table table-bordered order-table-info" _v-2eddf0a4=""> <tbody _v-2eddf0a4=""> <tr _v-2eddf0a4=""> <td _v-2eddf0a4=""> <span _v-2eddf0a4="">屋顶勘察:</span> <button class=btn @click=sendState(2) :class="{\'disabled\': orderData.stepB !== 0, \'btn-danger\': orderData.stepB === 2, \'btn-warning\': orderData.stepB === 0, \'btn-success\': orderData.stepB === 1}" _v-2eddf0a4="">{{orderData.stepB | stepB}}</button> <template v-if=currentHight> <select v-model=stepB _v-2eddf0a4=""> <option :value=2 _v-2eddf0a4="">未预约</option> <option :value=0 _v-2eddf0a4="">已预约</option> <option :value=1 _v-2eddf0a4="">已完成</option> </select> <button class="btn btn-primary" @click=changeSetpB(stepB) _v-2eddf0a4="">保存</button> </template> </td> <td _v-2eddf0a4=""> <span _v-2eddf0a4="">并网状态:</span> <button class=btn @click=sendState(3) :class="{\'disabled\': orderData.stepC !== 0, \'btn-danger\': orderData.stepC === 2, \'btn-warning\': orderData.stepC === 0, \'btn-success\': orderData.stepC === 1}" _v-2eddf0a4="">{{orderData.stepC | stepC}}</button> <template v-if=currentHight> <select v-model=stepC _v-2eddf0a4=""> <option :value=2 _v-2eddf0a4="">未预约</option> <option :value=0 _v-2eddf0a4="">已预约</option> <option :value=1 _v-2eddf0a4="">已完成</option> </select> <button class="btn btn-primary" @click=changeSetpC(stepC) _v-2eddf0a4="">保存</button> </template> </td> </tr> <tr _v-2eddf0a4=""> <td _v-2eddf0a4=""> <span _v-2eddf0a4="">施工状态:</span> <button class=btn @click=sendState(4) :class="{\'disabled\': orderData.stepD !== 0, \'btn-danger\': orderData.stepD === 2, \'btn-warning\': orderData.stepD === 0, \'btn-success\': orderData.stepD === 1}" _v-2eddf0a4="">{{orderData.stepD | stepD}}</button> <template v-if=currentHight> <select v-model=stepD _v-2eddf0a4=""> <option :value=2 _v-2eddf0a4="">未预约</option> <option :value=0 _v-2eddf0a4="">已预约</option> <option :value=1 _v-2eddf0a4="">已完成</option> </select> <button class="btn btn-primary" @click=changeSetpD(stepD) _v-2eddf0a4="">保存</button> </template> </td> <td _v-2eddf0a4=""> <span _v-2eddf0a4="">发电状态:</span> <button class=btn @click=sendState(5) :class="{\'disabled\': orderData.stepE !== 0, \'btn-danger\': orderData.stepE === 2, \'btn-warning\': orderData.stepE === 0, \'btn-success\': orderData.stepE === 1}" _v-2eddf0a4="">{{orderData.stepE | stepE}}</button> <template v-if=currentHight> <select v-model=stepE _v-2eddf0a4=""> <option :value=2 _v-2eddf0a4="">未发电</option> <option :value=1 _v-2eddf0a4="">已发电</option> </select> <button class="btn btn-primary" @click=changeSetpE(stepE) _v-2eddf0a4="">保存</button> </template> </td> </tr> </tbody> </table> </div> </div> <div class=col-sm-12 _v-2eddf0a4=""> <div class=order-sub-title _v-2eddf0a4=""> <h5 _v-2eddf0a4="">订单详情</h5> </div> <table class="table table-bordered order-table-details" _v-2eddf0a4=""> <thead _v-2eddf0a4=""> <tr _v-2eddf0a4=""> <td _v-2eddf0a4=""><span _v-2eddf0a4="">装机容量</span></td> <td _v-2eddf0a4=""><span _v-2eddf0a4="">发电总量</span></td> <td _v-2eddf0a4=""><span _v-2eddf0a4="">已付金额</span></td> <td _v-2eddf0a4=""><span _v-2eddf0a4="">服务商</span></td> <td _v-2eddf0a4=""><span _v-2eddf0a4="">联系人</span></td> <td _v-2eddf0a4=""><span _v-2eddf0a4="">联系电话</span></td> </tr> </thead> <tbody _v-2eddf0a4=""> <tr _v-2eddf0a4=""> <td _v-2eddf0a4="">{{orderData.capacity}}</td> <td _v-2eddf0a4="">{{orderData.gross}}</td> <td _v-2eddf0a4=""> <template v-if=!editPaidMoney> {{orderData.paidMoney ? orderData.paidMoney : 0}} | <a href=javascript:; @click=isEditPaidMoney _v-2eddf0a4="">录入</a> </template> <template v-if=editPaidMoney> <input type=text v-model=orderData.paidMoney _v-2eddf0a4=""> <a href=javascript:; @click=sendPaidMoney _v-2eddf0a4="">保存</a> </template> </td> <td _v-2eddf0a4=""><a href=javascript v-link="{path:\'/user/server-details/\'+orderData.server.id}" _v-2eddf0a4="">{{orderData.server.serverName}}</a> </td> <td _v-2eddf0a4="">{{orderData.linkMan}}</td> <td _v-2eddf0a4="">{{orderData.phone}}</td> </tr> </tbody> </table> </div> </div> </div> <div v-show=showEditBox _v-2eddf0a4=""> </div> </section>'},318:function(t,e){t.exports='<div class=modal style="display: block" _v-f482b26e=""> <div class=modal-dialog _v-f482b26e=""> <div class=modal-content _v-f482b26e=""> <div class=modal-header _v-f482b26e=""> <button type=button class=close data-dismiss=modal aria-label=Close _v-f482b26e=""> <span aria-hidden=true @click=closeModal _v-f482b26e="">×</span></button> <h4 class=modal-title _v-f482b26e="">电站编辑</h4> </div> <div class=modal-body _v-f482b26e=""> <div class=form-group style=height:30px _v-f482b26e=""> </div> <div class=form-group _v-f482b26e=""> </div> <p class=tips _v-f482b26e=""></p> </div> <div class=modal-footer _v-f482b26e=""> <button type=button class="btn btn-default pull-left" data-dismiss=modal @click="" _v-f482b26e="">关闭</button> <button type=button class="btn btn-primary" @click=propStation _v-f482b26e="">确定</button> </div> </div> </div> </div>'},353:function(t,e,a){var d,s;a(251),d=a(109),s=a(318),t.exports=d||{},t.exports.__esModule&&(t.exports=t.exports["default"]),s&&(("function"==typeof t.exports?t.exports.options||(t.exports.options={}):t.exports).template=s)},356:function(t,e,a){var d,s;a(220),d=a(112),s=a(287),t.exports=d||{},t.exports.__esModule&&(t.exports=t.exports["default"]),s&&(("function"==typeof t.exports?t.exports.options||(t.exports.options={}):t.exports).template=s)}});