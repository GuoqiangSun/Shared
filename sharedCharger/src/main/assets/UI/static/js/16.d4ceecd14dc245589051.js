webpackJsonp([16],{"+dMk":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=a("Dd8w"),i=a.n(n),s=a("BD25"),c=a("NYxO"),l={name:"about",data:function(){return{active:1}},components:{headTop:s.a},mounted:function(){"zh"===this.$i18n.locale?this.active=1:this.active=2},methods:i()({},Object(c.b)(["setLanguage"]),{itemHandle:function(t){switch(t){case 1:this.dataInteractionRequest({msgType:"0x5042",language:"zh"});break;case 2:this.dataInteractionRequest({msgType:"0x5042",language:"en"})}this.active=t,this.$router.back()}})},o={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"about"},[n("head-top",{attrs:{title:t.$t("language"),"go-back":!0}}),t._v(" "),n("div",{staticClass:"about-container"},[n("ul",{staticClass:"list"},[n("li",{on:{click:function(e){t.itemHandle(1)}}},[t._v("\n        中文\n        "),n("img",{directives:[{name:"show",rawName:"v-show",value:1===t.active,expression:"active===1"}],attrs:{src:a("WZhK"),alt:""}}),t._v(" "),n("div",{staticClass:"bomPixel"})]),t._v(" "),n("li",{on:{click:function(e){t.itemHandle(2)}}},[t._v("\n        English\n        "),n("img",{directives:[{name:"show",rawName:"v-show",value:2===t.active,expression:"active===2"}],attrs:{src:a("WZhK"),alt:""}}),t._v(" "),n("div",{staticClass:"bomPixel"})])])])],1)},staticRenderFns:[]};var b=a("VU/8")(l,o,!1,function(t){a("FdOf")},"data-v-7522d5f9",null),d=a("Ug2G");d&&d.__esModule&&(d=d.default),"function"==typeof d&&d(b);e.default=b.exports},FdOf:function(t,e){},Ug2G:function(t,e){t.exports=function(t){t.options.__i18n=t.options.__i18n||[],t.options.__i18n.push('{"en":{"language":"Language"},"zh":{"language":"多语言设置"}}'),delete t.options._Ctor}},WZhK:function(t,e){t.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTM4IDc5LjE1OTgyNCwgMjAxNi8wOS8xNC0wMTowOTowMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTcgKFdpbmRvd3MpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkIwRjY4Q0YzOEYyNTExRTg5OERCRjY2QUUxOTRCQ0REIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkIwRjY4Q0Y0OEYyNTExRTg5OERCRjY2QUUxOTRCQ0REIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6QjBGNjhDRjE4RjI1MTFFODk4REJGNjZBRTE5NEJDREQiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6QjBGNjhDRjI4RjI1MTFFODk4REJGNjZBRTE5NEJDREQiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz57tbt1AAAJG0lEQVR42uRb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"}});
//# sourceMappingURL=16.d4ceecd14dc245589051.js.map