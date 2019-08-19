//document.oncontextmenu=new Function("event.returnValue=false;");
//document.onselectstart=new Function("event.returnValue=false;");

$(function() {
    //中文版页面调用
    $('.banner ul>li').hover(function() {
        if (this.id != "") {
            $('.' + this.id).slideDown(200);
        }
    },
    function() {
        if (this.id != "") {
            $('.' + this.id).stop(true, true).slideUp(200);
        }
    });

    $("#hidtable").hover(function() {
        $("#hidtable>table").slideDown(200);
    },
    function() {
        $("#hidtable>table").stop(true, true).slideUp(200);
    });

   
    $("#webteam").hover(function() {
        $(".wzqhov").slideDown(200);
    },
    function() {
        $(".wzqhov").stop(true, true).slideUp(200);
    });

    $("#newskey>li").click(function() { 
       $(this).find("a").addClass("current");
       $(this).find("dl").css("display", "none");
       $(this).siblings().find("a").removeClass("current");
       $(this).siblings().find("dl").css("display", "none");
    });

    $("#newskey>li>dl>dd").click(function() {
        $(this).parent().css("display", "block");
        $(this).find("a").addClass("current");
        $(this).siblings().find("a").removeClass("current");
        $(this).parent().parent().siblings().find("a").removeClass("current");
    });

    var linum = $("#newskey>li").length;
    for (var i = 0; i < linum; i++) {
        var len = $("#newskey>li:eq(" + i + ")>dl>dd").length;
        if (len >= 1) {
            var url = $("#newskey>li:eq(" + i + ")>dl>dd:eq(0)>a").attr("href");
            $("#newskey>li:eq(" + i + ")>a").attr("href", url);
        }
    }
    /* 二级和三级页面左侧菜单显示文字【start】 */
    var enUrl = $("#lanmuurl").val();
    var arr = ["gxwm/default.htm", "ywly/default.htm", "xwzx/default.htm", "cxyfz/default.htm", "rcqp/default.htm"];
    var cnarr = ['关于我们', '业务领域', '新闻中心', '创新与发展', '人才招聘'];
    if (enUrl != "" && enUrl != null) {
        for (var i = 0; i < arr.length; i++) {
            if (enUrl.indexOf(arr[i]) > 0) {
                $("#lanmuname").html(cnarr[i]);
            }
        }
    }
    /* 二级和三级页面左侧菜单显示文字【end】 */
});

/*
  函数名称：setTab
  函数参数：obj,为鼠标点击的对象
  函数功能：选项卡页面，点击选项卡的不同选项调用不同的页面。
  使用页面：关于我们
*/
function setTab(obj) {
    var objId = obj.id;
    var objLen = objId.length;
    var eObj = objId.substr(0, objLen - 1) + "_" + objId.substr(objLen - 1, objLen);
    $(obj).addClass('active').siblings().removeClass('active');
    $("." + eObj).removeClass('display-n').siblings().addClass('display-n');
    $("#" + eObj).removeClass('display-n').siblings().addClass('display-n');
}

function setTabs(obj) {
    var objId = obj.id;
    $(obj).addClass('active').siblings().removeClass('active');
    $("." + objId).removeClass('display-n');
    $("." + objId).siblings().addClass('display-n');
}

function newsTab(obj) {
    var objId = obj.id;
    var objLen = objId.length;
    var eObj = "";
    if (objId != "" || objId != null) {
        eObj = objId.substr(0, objLen - 1) + "_" + objId.substr(objLen - 1, objLen);
    }
    $("#" + eObj).removeClass('display-n');
    $("#" + eObj).addClass('display-b').siblings().addClass('display-n');
    $(obj).addClass('light-blue-font').siblings().removeClass('light-blue-font');
}

	function changFont(fontSize){
		$(".changeFont").css({"font-size":fontSize});
	}


















































