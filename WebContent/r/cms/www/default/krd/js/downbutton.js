function SaveDocument(title){
	$.post(
		'/jsons/download_action.action',
		{"url":window.location.href}, 
		function(result){
			url='/component/download_action!downloadArea.action?url='+result+'&title='+title;
			window.location.href = url;
		}
	);
}