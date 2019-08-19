// 选项卡
function clickSwitch(btn,layer,index,mode) {
	$(btn).eq(index).addClass('current');//默认
	$(layer).eq(index).show();//默认
	if(mode){$(btn).mouseover(Switch);}else {$(btn).click(Switch);}
	function Switch() {var index = $(btn).index(this);$(this).addClass('current').siblings().removeClass('current');$(layer).eq(index).show().siblings().hide();}
}
