webpackJsonp([12],{"+H3F":function(e,t,a){e.exports=a.p+"static/img/waite@3x.6f5abf3.png"},Ib9m:function(e,t){e.exports=function(e){e.options.__i18n=e.options.__i18n||[],e.options.__i18n.push('{"en":{"RemoveBattery":"Remove Portable battery","JustMoment":"Portable battery is popping up. Just a moment, please.","unit":"S","customerService":"If not, please wait 1-3 minutes. If not, please re-lease or contact customer service","credit":"Sorry, your credit is running low","rechargeTheBalance":"In order to normally use the filling charge, please recharge the balance first.","RechargeBalance":"Recharge balance","Reminder":"Reminder","borrowedTheBattery":"Have borrowed the battery, please return it and try again","OK":"OK","isNotAvailable":"The equipment is not available for the time being, please change it."},"zh":{"RemoveBattery":"取充电宝","JustMoment":"充电宝正在弹出，请稍等","unit":"秒","customerService":"如未吐出充电宝，请耐心等待1-3分钟，如仍未吐出，请重新租借或联系客服","credit":"余额不足","rechargeTheBalance":"为了正常使用座充，请先充值余额","RechargeBalance":"充值余额","Reminder":"提示","borrowedTheBattery":"当前已经借了充电宝，请归还充电宝后，再重试","OK":"好的","isNotAvailable":"该设备暂无可借充电宝，请换台设备使用"}}'),delete e.options._Ctor}},aEW7:function(e,t,a){e.exports=a.p+"static/img/baby-icon.c31fbe2.png"},r7T4:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=a("Dd8w"),r=a.n(i),o=a("NYxO"),n=a("BD25"),s=a("Kh6f"),c=a("G+9V"),l={name:"takingCharger",data:function(){return{timer:null,timer2:null,time:60,toastTip:!1}},components:{headTop:n.a,messageBox:s.a,toast:c.a},computed:r()({},Object(o.c)(["chargerId"])),mounted:function(){var e=this;this.$nextTick(function(){e.clientHeight=document.documentElement.clientHeight,e.$refs.takeChanger.style.height=document.documentElement.clientHeight}),this.countdown(),this.dataInteractionResponse(function(t){if("0x5206"===t.msgType)if(t.result)e.$router.push("/takeSuccessfully");else switch(t.data.errorCode){case"0x830104":e.setGlobalBox({bol:!0,id:7});break;default:var a=setTimeout(function(){e.$router.push("/takeFailure"),clearTimeout(a)},2e3)}else if("0x5214"===t.msgType)if(t.result)e.$store.state.user.scanFlag=!1,e.$router.push("/takeSuccessfully");else switch(t.data.errorCode){case"0x830104":e.setGlobalBox({bol:!0,id:7});break;default:var i=setTimeout(function(){e.$router.push("/takeFailure"),clearTimeout(i)},2e3)}}),this.dataInteractionRequest({msgType:"0x5213",imei:this.chargerId.imei,type:"LENT"}),this.ISDEBUG},methods:r()({},Object(o.b)(["setGlobalBox"]),{countdown:function(){var e=this;this.timer=setInterval(function(){--e.time<=0&&(clearInterval(e.timer),e.$router.push({path:"/takeFailure"}))},1e3)},boxHandle:function(e,t,a){switch(a){case 7:e&&this.$router.push({path:"/recharge"}),this.setGlobalBox({bol:!1,id:0});break;case 8:this.setGlobalBox({bol:!1,id:0})}},goBackHander:function(){this.$router.back()}}),beforeDestroy:function(){clearInterval(this.timer),clearTimeout(this.timer2)}},u={render:function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{ref:"takeChanger",staticClass:"takingCharger"},[i("head-top",{attrs:{title:e.$t("RemoveBattery"),"custom-back":!0,proportion:"15:70:15"},on:{goBackHander:e.goBackHander}}),e._v(" "),i("div",{staticClass:"container"},[1===e.Version||3===e.Version?i("img",{attrs:{src:a("aEW7"),alt:""}}):e._e(),e._v(" "),2===e.Version?i("img",{attrs:{src:a("+H3F"),alt:""}}):e._e(),e._v(" "),i("span",[e._v(e._s(e.$t("JustMoment")))]),e._v(" "),i("div",{staticClass:"countdown"},[i("b",[i("strong",[e._v(e._s(e.time))]),e._v(e._s(e.$t("unit"))+"\n      ")]),e._v(" "),i("div",{staticClass:"countdown-bg"})]),e._v(" "),i("div",{ref:"sizehuawei",staticClass:"describe"},[e._v(e._s(e.$t("customerService")))])]),e._v(" "),i("message-box",{attrs:{id:7,icon:"defic",title:e.$t("credit"),describe:e.$t("rechargeTheBalance"),alert:e.$t("RechargeBalance")},on:{boxHandle:e.boxHandle}}),e._v(" "),i("message-box",{attrs:{id:8,icon:"baby",title:e.$t("Reminder"),describe:e.$t("borrowedTheBattery"),alert:e.$t("OK")},on:{boxHandle:e.boxHandle}}),e._v(" "),i("toast",{attrs:{toastTip:e.toastTip,describe:e.$t("isNotAvailable"),position:"bottom"},on:{"update:toastTip":function(t){e.toastTip=t}}})],1)},staticRenderFns:[]};var d=a("VU/8")(l,u,!1,function(e){a("wWSF")},"data-v-4f188222",null),h=a("Ib9m");h&&h.__esModule&&(h=h.default),"function"==typeof h&&h(d);t.default=d.exports},wWSF:function(e,t){}});
//# sourceMappingURL=12.3b74f7eb22048eb25eef.js.map