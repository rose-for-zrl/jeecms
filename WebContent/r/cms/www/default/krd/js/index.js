

//页面加载完成之后执行的方法
$(document).ready(function () {
	
	var sWidth = $("#focus").width(); //获取焦点图的宽度（显示面积）
	var len = $("#focus ul li").length; //获取焦点图个数
	var index = 0;
	var picTimer;
	
	//以下代码添加数字按钮和按钮后的半透明条，还有上一页、下一页两个按钮
	var btn = "<div class='btnBg'></div><div class='btn'>";
	for(var i=0; i < len; i++) {
		btn += "<span></span>";
	}
	btn += "</div>";
	$("#focus").append(btn);
	$("#focus .btnBg").css("opacity","1");

	//为小按钮添加鼠标滑入事件，以显示相应的内容
	$("#focus .btn span").css("opacity",0.4).mouseover(function() {
		index = $("#focus .btn span").index(this);
		showPics(index);
	}).eq(0).trigger("mouseover");

	//上一页、下一页按钮透明度处理
	$("#focus .preNext").css("opacity",0.2).hover(function() {
		$(this).stop(true,false).animate({"opacity":"0.5"},300);
	},function() {
		$(this).stop(true,false).animate({"opacity":"0.2"},300);
	});

	//上一页按钮
	$("#focus .pre").click(function() {
		index -= 1;
		if(index == -1) {index = len - 1;}
		showPics(index);
	});

	//下一页按钮
	$("#focus .next").click(function() {
		index += 1;
		if(index == len) {index = 0;}
		showPics(index);
	});

	//本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
	$("#focus ul").css("width",sWidth * (len));
	
	//鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
	$("#focus").hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			showPics(index);
			index++;
			if(index == len) {index = 0;}
		},3000); //此4000代表自动播放的间隔，单位：毫秒
	}).trigger("mouseleave");
	
	//显示图片函数，根据接收的index值显示相应的内容
	function showPics(index) { //普通切换
		var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
		$("#focus ul").stop(true,false).animate({"left":nowLeft},300); //通过animate()调整ul元素滚动到计算出的position
		//$("#focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
		$("#focus .btn span").stop(true,false).animate({"opacity":"0.4"},300).eq(index).stop(true,false).animate({"opacity":"1"},300); //为当前的按钮切换到选中的效果
	}
	
	$("#content #title-box .title#hangye").hover(function(){
		var id = $(this).attr("id");
		$("#content #title-box .title").removeClass("cur");
		$(this).addClass("cur");
		//$(".newslist-box").hide();
		$("#news-box-group").stop().animate({left:"-542px"},"normal");
		
	});
	$("#content #title-box .title#qiye").hover(function(){
		var id = $(this).attr("id");
		$("#content #title-box .title").removeClass("cur");
		$(this).addClass("cur");
		//$(".newslist-box").hide();
		$("#news-box-group").stop().animate({left:"0"},"normal");
		
	});	
	
	$("#about-content2 #left-nav ul li").click(function(){
		$("#about-content2 #left-nav ul li ul").hide();		
		$("ul",this).slideDown();
		$("ul",this).show();
	});
	
		
	/***顶部导航弹出**/

	$(".new").mouseover(function(){
		clearTimeout(timer);
		$(".new").show(); 
	})
	$(".new").mouseout(function(){
		timer = setTimeout(function () {
		$(".new").hide();},300);
		});	
		
	
		
	/********旗下产业*******/
	var content1_right=$(".content1_right ul li");
	var c1=$(".content1_right ul li i")
	var n1=$(".js_xk i");	
	var i = 0;
	for (i = 0; i < c1.length; i++) {
	 content1_right[i].index = i;
	 content1_right[i].onmouseover = function () {
		 for (i = 0; i < c1.length; i++) {
			 c1[i].style.display = 'none';
   			 n1[i].style.display = 'none';
			 $(".content1_right ul li").css({'color':'#4f4f4f'});
			 };
			  c1[this.index].style.display = 'block';
			  n1[this.index].style.display = 'block';
			  $(".content1_right ul li").eq(this.index).css({'color':'#0384ce'});
	 }
	};
	
	

 

$(".img_box ul .li1").mouseover(function(){
	clearInterval(t); 
	})

//上一张： 
$(".prev").click(function(){ 
clearInterval(t); 
var index=$(".num_box a.c").index()-1; 
index=index<0?$(".num_box a").length-1:index; 
slider(index); 
}) 


//下一张： 
$(".next").click(function(){ 
clearInterval(t); 
var index=$(".num_box a.c").index()+1; 
index=index>=$(".num_box a").length?0:index; 
slider(index); 
})




function slider1(index){ 
$(".img_box1 ul").stop().css({marginLeft:-index*107}); 
$(".num_box1 a").removeClass("c").eq(index).addClass("c"); 
} 

$(".img_box1 ul li").mouseover(function(){
	clearInterval(t); 
	})

//上一张： 
$(".prev1").click(function(){ 
clearInterval(t); 
var index=$(".num_box1 a.c").index()-1; 
index=index<0?$(".num_box1 a").length-1:index; 
slider1(index); 
}) 

//下一张： 
$(".next1").click(function(){ 
clearInterval(t); 
var index=$(".num_box1 a.c").index()+1; 
index=index>=$(".num_box1 a").length?0:index; 
slider1(index); 

}) 

//点击圆点向右滚动： 
$(".num_box1 a").click(function(){ 
clearInterval(t); 
var index=$(this).index(); 
slider1(index); 
}) 

//鼠标移出按钮和圆点： 
$(".num_box1 a,.next1,.prev1,.img_box1 li,.img_box1 ul li").mouseout(function(){ 
clearInterval(t); 
t=setInterval(function(){ 
m=$(".num_box1 a.c").index()+1; 
m=m>=$(".num_box1 a").length?0:m; 
slider1(m); 
},3000); 
})
var n=0; 
function slider2(index){ 
$(".img_box2 ul").stop().animate({marginLeft:-index*170}); 
$(".num_box2 a").removeClass("c").eq(index).addClass("c"); 
} 
//自动播放： 
t=setInterval(function(){ 
m=$(".num_box2 a.c").index()+1; 
m=m>=$(".num_box2 a").length?0:m; 
slider2(m); 
},3000); 

$(".img_box2 ul li").mouseover(function(){
	clearInterval(t); 
	})

//上一张： 
$(".prev2").click(function(){ 
clearInterval(t); 
var index=$(".num_box2 a.c").index()-1; 
index=index<0?$(".num_box2 a").length-1:index; 
slider2(index); 
}) 

//下一张： 
$(".next2").click(function(){ 
clearInterval(t); 
var index=$(".num_box2 a.c").index()+1; 
index=index>=$(".num_box2 a").length?0:index; 
slider2(index); 

}) 

//点击圆点向右滚动： 
$(".num_box2 a").click(function(){ 
clearInterval(t); 
var index=$(this).index(); 
slider2(index); 
}) 

//鼠标移出按钮和圆点： 
$(".num_box2 a,.next2,.prev2,.img_box2 li,.img_box2 ul li").mouseout(function(){ 
clearInterval(t); 
t=setInterval(function(){ 
m=$(".num_box2 a.c").index()+1; 
m=m>=$(".num_box2 a").length?0:m; 
slider2(m); 
},3000); 
})
/**********返回顶部***********/
   $(".em_1 i").click(function () {
        $('body,html').stop().animate({scrollTop: 0 }, 500);

    });
	
	var Resu=$(".result")
	var reB=$(".result .result_top")
	reB.click(function(){
		Resu.animate({
		height:'840px'},300,function(){
		$(".result .result_top i").html('-');
		});
		if(Resu.height()==840){
		Resu.animate({
		height:'40px'},300,function(){$(".result .result_top i").html('+');
		});
		};
		});
		
//		var Resu1=$(".result1")
//	var reB1=$(".result1 .result_top")
//	reB1.click(function(){
//		Resu1.animate({
//		height:'840px'},300,function(){
//		$(".result1 .result_top i").html('-');
//		});
//		if(Resu1.height()==840){
//		Resu1.animate({
//		height:'40px'},300,function(){$(".result1 .result_top i").html('+');
//		});
//		};
//		});
//		var Resu2=$(".result2")
//	var reB2=$(".result2 .result_top")
//	reB2.click(function(){
//		Resu2.animate({
//		height:'840px'},300,function(){
//		$(".result2 .result_top i").html('-');
//		});
//		if(Resu2.height()==840){
//		Resu2.animate({
//		height:'40px'},300,function(){$(".result2 .result_top i").html('+');
//		});
//		};
//		});
//		var Resu3=$(".result3")
//	var reB3=$(".result3 .result_top")
//	reB3.click(function(){
//		Resu3.animate({
//		height:'840px'},300,function(){
//		$(".result3 .result_top i").html('-');
//		});
//		if(Resu3.height()==840){
//		Resu3.animate({
//		height:'40px'},300,function(){$(".result3 .result_top i").html('+');
//		});
//		};
//		});
		var n=0; 
function slider5(index){ 
$(".img_box5 ul").stop().animate({marginLeft:-index*620},500); 
$(".num_box5 a").removeClass("c").eq(index).addClass("c"); 
} 
//自动播放： 
t1=setInterval(function(){ 
m=$(".num_box5 a.c").index()+1; 
m=m>=$(".num_box5 a").length?0:m; 
slider5(m); 
},3000); 

$(".img_box5 ul li").mouseover(function(){
	clearInterval(t1); 
	})

//上一张： 
$(".prev5,.prev7").click(function(){ 
clearInterval(t1); 
var index=$(".num_box5 a.c").index()-1; 
index=index<0?$(".num_box5 a").length-1:index; 
slider5(index); 
}) 

//下一张： 
$(".next5,.next7").click(function(){ 
clearInterval(t1); 
var index=$(".num_box5 a.c").index()+1; 
index=index>=$(".num_box5 a").length?0:index; 
slider5(index); 

}) 

//点击圆点向右滚动： 
$(".num_box5 a").click(function(){ 
clearInterval(t1); 
var index=$(this).index(); 
slider5(index); 
}) 

//鼠标移出按钮和圆点： 
$(".num_box5 a,.next5,.next7.prev5,.prev7,.img_box5 li,.img_box5 ul li").mouseout(function(){ 
clearInterval(t1); 
t1=setInterval(function(){ 
m=$(".num_box5 a.c").index()+1; 
m=m>=$(".num_box5 a").length?0:m; 
slider5(m); 
},3000); 
})
	var nr=$(".nr_1");
	var A_i=$(".xi");
	var i = 0;
	for (i = 0; i < nr.length; i++) {
	 A_i[i].index = i;
	 A_i[i].onclick = function () {
		 for (i = 0; i < nr.length; i++) {
			 nr.css({'display':'none'});
			  A_i.css({'background':'#e1e3e4','border-bottom':'solid 1px #ccc'});
			 };
			 nr.eq(this.index).css({'display':'block'});
			 A_i.eq(this.index).css({'background':'#F1F3F5','border-bottom':'solid 1px #F1F3F5'});
	 		}
		};				

	

/***********集团简介***************/
/*var Cmli = $(".Cmain_left ul li")
var Cmh2 = $(".Cmain_left ul li h2")
var i = 0;
	for (i = 0; i < Cmli.length; i++) {
	 Cmli[i].index = i;
	 
	 Cmli[i].onmouseover = function () {
		 
		 for (i = 0; i < Cmli.length; i++) {
			Cmh2.css({'background':'#fff'});
			 };
			Cmh2.eq(this.index).css({'background':'#f60'});
	 };
	};*/
});

