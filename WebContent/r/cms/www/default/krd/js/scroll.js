// return top
function flowScroll()
{
	var div = document.getElementById('scrolltop');
	
	function postop()
	{
		
		div.style.top = jQuery(window).height() - div.offsetHeight + jQuery(document).scrollTop() - 25 + 'px';
	}
	function showtop()
	{
		var st = jQuery(document).scrollTop();
		switch(st>0)
		{
			case true : div.style.display = '';postop();break;
			case false : div.style.display = 'none';break;
		}
	}
	jQuery(window).bind('resize',showtop);
	jQuery(window).bind('scroll',showtop);
	showtop();
}

//nav scroll url
function request(paras){ 
	var url = location.href;  
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");  
	var paraObj = {}  
	for (i=0; j=paraString[i]; i++){  
		paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);  
	}  
	var returnValue = paraObj[paras.toLowerCase()];  
	if(typeof(returnValue)=="undefined"){  
		return "";  
	}else{  
		return returnValue; 
	}
}

jQuery(function(){
	flowScroll();
	/*return top scroll*/
	jQuery("#scrolltop a").click(function(){
		jQuery("html,body").animate( { scrollTop:0},800);
	});
	////nav scroll
//	var mao = $("#" + request("scrollid")); //获得锚点
//	if (mao.length > 0) { 
//		var pos = mao.offset().top; 
//		jQuery("html,body").animate({ scrollTop: pos },800); 
//	} 
	
});
	