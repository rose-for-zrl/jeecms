$(function() {
	$("#username").focus();
	$("#jvForm").validate();
});

function login(){
	var loginP = $("#password").val();
	var str = md5(loginP);
	$("#password").val(str);
	return true;
}