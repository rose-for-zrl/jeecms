// JavaScript Document
$(document).ready(function(){
	
//	$(".Nr_Con_stop ul li:first-child a").attr("class","current");
	$(".Nr_Con_stop ul li a.current").parent().find("div").slideToggle("slow"); // Slide down the current menu item's sub menu
	
	$(".Nr_Con_stop ul li a").click( // When a top menu item is clicked...
		function () {
			var classname=$(this).attr("class");
			if(classname=="current"){
				$(this).attr("class","professional");
				$(".Nr_Con_stop  ul li div").slideUp("normal"); // Slide up all sub menus except the one clicked
			}else{
				$(".Nr_Con_stop  ul li a").attr("class","professional");
				$(this).attr("class","current");
				$(".Nr_Con_stop  ul li div").slideUp("normal"); // Slide up all sub menus except the one clicked
				$(this).next().slideToggle("normal"); // Slide down the clicked sub menu
			}
		});
});
function changeimg(a){
			var num=$(".Ny_Banner img").length;
			for(var i=1;i<=num;i++)
			{
				if(i==a)
				{
				$(".changeimg" + i).fadeIn(800);
				}
				else
				{
				$(".changeimg" + i).fadeOut(10);
				}
			}
		}