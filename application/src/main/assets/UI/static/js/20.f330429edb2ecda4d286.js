webpackJsonp([20],{AAae:function(t,a){t.exports=function(t){t.options.__i18n=t.options.__i18n||[],t.options.__i18n.push('{"en":{"TransactionDetails":"Transaction details","Recharge":"Recharge","Consumption":"Consumption","Withdrawal":"Withdrawal","Empty":"Empty","PaymentSuccess":"Payment success","PaymentFailure":"Payment failure","Unpaid":"Unpaid","unit":"￥","Balance":"Balance","WeChat":"WeChat","Alipay":"Alipay","balancePayment":"Balance payment","Charger":"Charger","Bettery":"Bettery","BalanceText":"Balance","Deposit":"Deposit","portableBatteryCharging":"portable battery charging","seatCharging":"seat charging","weChatAccountNumber":"weChat account","alipayAccount":"alipay account"},"zh":{"TransactionDetails":"交易明细","Recharge":"充值","Consumption":"消费","Withdrawal":"提现","Empty":"空空如也","PaymentSuccess":"付款成功","PaymentFailure":"付款失败","Unpaid":"未付款","unit":"元","Balance":"余额消费","WeChat":"微信支付","Alipay":"支付宝支付","balancePayment":"余额支付","Charger":"座充","Bettery":"充电宝","BalanceText":"余额","Deposit":"押金","portableBatteryCharging":"充电宝充电","seatCharging":"座充充电","weChatAccountNumber":"微信账号","alipayAccount":"支付宝账号"}}'),delete t.options._Ctor}},iQkm:function(t,a){},keTk:function(t,a,e){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var s={name:"transaction",data:function(){return{tabs:[{title:this.$t("Recharge"),type:"recharge"},{title:this.$t("Consumption"),type:"consumption"},{title:this.$t("Withdrawal"),type:"withdrawal"}],currentTab:"recharge",list:{recharge:[],consumption:[],withdrawal:[]},page:1}},components:{headTop:e("BD25").a},mounted:function(){var t=this;if(this.dataInteractionResponse(function(a){switch(console.log(a),a.msgType){case"0x5262":a.result&&a.data&&(1===t.page?t.list[t.currentTab]=a.data:a.data.data.map(function(a){t.list[t.currentTab].data.push(a)}))}}),this.dataInteractionRequest({msgType:"0x5261",transactionType:1,page:this.page,count:10}),this.ISDEBUG){window.dataInteractionResponse('{"msgType":"0x5262","result":true,"data":{"transactionType":1,"page":1,"count":10,"total_page":1,"data":[{"fee":1,"recharge_type":1,"pay_type":2,"result":"SHIPPED","time_end": "2018.12.18 15:32:00"}]}}')}},methods:{analogData:function(){var t=parseInt(1e3*Math.random()),a=void 0;switch(this.currentTab){case"recharge":a='{"msgType":"0x5262","result":true,"data":{"transactionType":1,"page":1,"count":10,"total_page":10,"data":[{"fee":'+t+',"recharge_type":1,"pay_type":2,"result":"SHIPPED","time_end": "2018.12.18 15:32:00"}]}}',window.dataInteractionResponse(a);break;case"consumption":a='{"msgType":"0x5262","result":true,"data":{"transactionType":1,"page":1,"count":10,"total_page":10,"data":[{"fee":'+t+',"charging_type":1,"pay_type":2,"result":"SHIPPED","time_end": "2018.12.18 15:32:00"}]}}',window.dataInteractionResponse(a)}},toggleTab:function(t){this.$refs.scroll&&this.$refs.scroll.scrollTo(0,0),this.currentTab=t,this.page=1,this.updatePage(this.currentTab,this.page),this.ISDEBUG&&this.analogData()},onPullingDown:function(){console.log(111),this.page=1,this.updatePage(this.currentTab,this.page),this.ISDEBUG&&this.analogData()},onPullingUp:function(){this.list[this.currentTab].total_page>this.page?(this.updatePage(this.currentTab,++this.page),this.ISDEBUG&&this.analogData()):this.$refs.scroll.forceUpdate()},updatePage:function(t,a){var e=void 0;switch(t){case"recharge":e=1;break;case"consumption":e=2;break;case"withdrawal":e=3}this.dataInteractionRequest({msgType:"0x5261",transactionType:e,page:a,count:10})}}},n={render:function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("div",{staticClass:"transaction"},[s("head-top",{attrs:{title:t.$t("TransactionDetails"),"go-back":!0}}),t._v(" "),s("div",{staticClass:"container"},[s("div",{staticClass:"tabs"},t._l(t.tabs,function(a,e){return s("div",{key:e,class:["tab",{active:t.currentTab===a.type}],on:{click:function(e){t.toggleTab(a.type)}}},[s("span",[t._v(t._s(a.title))])])})),t._v(" "),s("div",{staticClass:"content"},[t.list[t.currentTab].data&&t.list[t.currentTab].data.length?s("div",{staticClass:"list"},[s("scroll",{ref:"scroll",attrs:{data:t.list[t.currentTab].data,pullDownRefresh:!0,pullUpLoad:!0},on:{pullingDown:t.onPullingDown,pullingUp:t.onPullingUp}},[s("div",{staticClass:"warp"},t._l(t.list[t.currentTab].data,function(a,e){return s("div",{key:e,staticClass:"item"},[s("div",{staticClass:"left"},[s("p",["recharge"===t.currentTab?s("span",{staticClass:"text"},[t._v("\n                    "+t._s(1===a.recharge_type?t.$t("BalanceText"):t.$t("Deposit"))+"\n                  ")]):t._e(),t._v(" "),"consumption"===t.currentTab?s("span",{staticClass:"text"},[t._v("\n                    "+t._s(1===a.charging_type?t.$t("portableBatteryCharging"):t.$t("seatCharging"))+"\n                  ")]):t._e(),t._v(" "),"withdrawal"===t.currentTab?s("span",{staticClass:"text"},[t._v(t._s(t.$t("BalanceText")))]):t._e(),t._v(" "),"SUCCESS"===a.result?s("span",{staticClass:"state"},[t._v(t._s(t.$t("PaymentSuccess")))]):t._e(),t._v(" "),"SHIPPED"===a.result?s("span",{staticClass:"state"},[t._v(t._s(t.$t("Unpaid")))]):t._e(),t._v(" "),"FAIL"===a.result?s("span",{staticClass:"state"},[t._v(t._s(t.$t("PaymentFailure")))]):t._e()]),t._v(" "),s("span",{staticClass:"time"},[t._v(t._s(a.time_end))])]),t._v(" "),s("div",{staticClass:"right"},[s("span",{staticClass:"count"},[s("strong",[t._v(t._s(a.fee/100))]),t._v(t._s(t.$t("unit")))]),t._v(" "),"recharge"===t.currentTab?s("span",{staticClass:"type"},[t._v(t._s(1===a.pay_type?t.$t("WeChat"):t.$t("Alipay")))]):t._e(),t._v(" "),"consumption"===t.currentTab?s("span",{staticClass:"type"},[1===a.pay_type?s("em",[t._v(t._s(t.$t("WeChat")))]):t._e(),t._v(" "),2===a.pay_type?s("em",[t._v(t._s(t.$t("Alipay")))]):t._e(),t._v(" "),3===a.pay_type?s("em",[t._v(t._s(t.$t("balancePayment")))]):t._e()]):t._e(),t._v(" "),"withdrawal"===t.currentTab?s("span",{staticClass:"type"},[t._v(t._s(1===a.pay_type?t.$t("weChatAccountNumber"):t.$t("alipayAccount")))]):t._e()])])}))])],1):s("div",{staticClass:"empty"},[s("img",{attrs:{src:e("OuuW"),alt:""}}),t._v(" "),s("span",[t._v(t._s(t.$t("Empty")))])])])])],1)},staticRenderFns:[]};var i=e("VU/8")(s,n,!1,function(t){e("iQkm")},"data-v-082bb169",null),r=e("AAae");r&&r.__esModule&&(r=r.default),"function"==typeof r&&r(i);a.default=i.exports}});
//# sourceMappingURL=20.f330429edb2ecda4d286.js.map