webpackJsonp([11],{Qv6T:function(t,n){},RIL5:function(t,n,e){t.exports=e.p+"static/img/cash_coupon3@3x.fdd47b1.png"},ULnO:function(t,n,e){t.exports=e.p+"static/img/cash_coupon2@3x.6deb4fb.png"},Vrfl:function(t,n,e){t.exports=e.p+"static/img/cash_coupon1@3x.e415647.png"},jyMu:function(t,n,e){"use strict";Object.defineProperty(n,"__esModule",{value:!0});var s=e("Dd8w"),a=e.n(s),o=e("NYxO"),u={name:"wallet",data:function(){return{deposit:0,amount:0,depositStandard:0,couponData:[{num:20,status:0,type:0,date:"2018-02-03 ~ 2019-05-02"}]}},components:{headTop:e("BD25").a},computed:{coupon:function(){return this.couponData.forEach(function(t){switch(t.num){case 5:t.bg=e("RIL5");break;case 20:t.bg=e("Vrfl");break;case 30:t.bg=e("ULnO")}}),console.log(this.couponData),this.couponData}},mounted:function(){},methods:a()({},Object(o.b)(["setGlobalToast"]),{goBackHander:function(){},withdraw:function(){if(!this.amount)return!1;this.$router.push({path:"/withdraw"})},onPullingDown:function(){},onPullingUp:function(){}})},i={render:function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("div",{staticClass:"wallet"},[e("head-top",{attrs:{title:t.$t("title"),"go-back":!0},on:{goBackHander:t.goBackHander}}),t._v(" "),e("div",{staticClass:"container"},[e("scroll",{ref:"scroll",attrs:{data:t.coupon,pullDownRefresh:!0,pullUpLoad:!0},on:{pullingDown:t.onPullingDown,pullingUp:t.onPullingUp}},[e("div",{staticClass:"warp"},t._l(t.coupon,function(n){return e("li",{key:n.index,style:{backgroundImage:"url("+n.bg+")"}},[e("span",{staticClass:"num"},[e("font",{staticStyle:{fontSize:"21px"}},[t._v("￥")]),t._v(t._s(n.num))],1),t._v(" "),0===n.type?e("span",{staticClass:"type"},[t._v(t._s(t.$t("type1")))]):t._e(),t._v(" "),1===n.type?e("span",{staticClass:"type"},[t._v(t._s(t.$t("type2")))]):t._e(),t._v(" "),e("div",{staticClass:"bottom"},[e("span",[t._v(t._s(t.$t("usedate"))+": ")]),t._v(" "),e("span",{staticClass:"date"},[t._v(t._s(n.date))]),t._v(" "),n.status?t._e():e("span",{staticClass:"status"},[t._v(t._s(t.$t("unuse")))]),t._v(" "),n.status?e("span",{staticClass:"status"},[t._v(t._s(t.$t("used")))]):t._e()])])}))])],1)],1)},staticRenderFns:[]};var c=e("VU/8")(u,i,!1,function(t){e("Qv6T")},"data-v-1e67f5d0",null),p=e("liyV");p&&p.__esModule&&(p=p.default),"function"==typeof p&&p(c);n.default=c.exports},liyV:function(t,n){t.exports=function(t){t.options.__i18n=t.options.__i18n||[],t.options.__i18n.push('{"en":{"title":"My card voucher","usedate":"Service life","used":"Used","unuse":"Unused","type1":"Voucher","type2":"Coupon"},"zh":{"title":"我的卡券","usedate":"使用期限","used":"已使用","unuse":"未使用","type1":"代金券","type2":"优惠券"}}'),delete t.options._Ctor}}});
//# sourceMappingURL=11.5cc9006d3638d0716134.js.map