(function() {
	$(document).ready(function(){
		$(".comment-list .btn-publish").click(function() {
			if(!$("#content").val()) {
				return;
			}
			var content = $("#content").val();
			$("#content").val("");
			$.post(location.pathname + "/comments", {
				content: content
			}, function(data) {
				var commentElement = $($(".comment-template").html());
				$(commentElement.find(".author-name")).html(data.commentor.name);
				$(commentElement.find(".comment-time")).html(data.createdTime);
				$(commentElement.find(".comment-content")).html(data.content);
				$(".comment-list ul.list-unstyled").append(commentElement);
			});
		});
	});
})();