webpackJsonp([8],{"7OOK":function(t,e,n){t.exports=n.p+"static/img/lease.5ae39f2.png"},INX0:function(t,e,n){t.exports=n.p+"static/img/takes@3x.06af939.png"},OUMM:function(t,e){t.exports=function(t){t.options.__i18n=t.options.__i18n||[],t.options.__i18n.push('{"en":{"RemoveBattery":"Remove Portable Battery","ReturnBattery":"Return Portable Battery"},"zh":{"RemoveBattery":"租借充电宝","ReturnBattery":"归还充电宝"}}'),delete t.options._Ctor}},Txt7:function(t,e){},r8m9:function(t,e,n){t.exports=n.p+"static/img/return.61e7b8e.png"},x20d:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=n("Dd8w"),r=n.n(s),a=n("BD25"),i=n("NYxO"),o={name:"charger",data:function(){return{}},components:{headTop:a.a},computed:r()({},Object(i.c)(["unReturndevice"])),created:function(){var t=this;this.ISDEBUG&&window.dataInteractionResponse('{"msgType":"0x5226","result":true,"data":{"type":"RETURN"}}'),this.dataInteractionResponse(function(e){"0x5226"===e.msgType&&e.result&&("LENT"===e.data.type?t.$store.state.user.unReturndevice=!1:"RETURN"===e.data.type&&(t.$store.state.user.unReturndevice=!0))})},mounted:function(){console.log(this.unReturndevice),this.$store.state.user.scanFlag=!1},methods:{goHandle:function(t){switch(t){case 1:this.$router.push({path:"/charging"});break;case 2:this.$router.push({path:"/restituteCharger"})}}}},c={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"charger"},[s("head-top",{attrs:{proportion:"15:70:15",title:t.$t("RemoveBattery"),"go-back":!0}}),t._v(" "),s("div",{staticClass:"container"},[s("div",{staticClass:"column"},[s("div",{staticClass:"wrap"},[t.unReturndevice||1!==t.Version&&3!==t.Version?t._e():s("img",{attrs:{src:n("7OOK"),alt:""}}),t._v(" "),t.unReturndevice||2!==t.Version?t._e():s("img",{attrs:{src:n("INX0"),alt:""}}),t._v(" "),!t.unReturndevice||1!==t.Version&&3!==t.Version?t._e():s("img",{attrs:{src:n("r8m9"),alt:""}}),t._v(" "),t.unReturndevice&&2===t.Version?s("img",{attrs:{src:n("gr+E"),alt:""}}):t._e(),t._v(" "),t.unReturndevice?s("button",{staticClass:"btn",on:{click:function(e){t.goHandle(2)}}},[t._v(t._s(this.$t("ReturnBattery")))]):s("button",{staticClass:"btn",on:{click:function(e){t.goHandle(1)}}},[t._v(t._s(this.$t("RemoveBattery")))])])])])],1)},staticRenderFns:[]};var u=n("VU/8")(o,c,!1,function(t){n("Txt7")},"data-v-cf3fadb6",null),d=n("OUMM");d&&d.__esModule&&(d=d.default),"function"==typeof d&&d(u);e.default=u.exports}});
//# sourceMappingURL=8.3361700d5cce7a441558.js.map