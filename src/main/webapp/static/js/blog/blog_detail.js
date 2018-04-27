window.onload = function() {
	var swidth = window.innerWidth;
	var sheight = window.innerHeight;
	$("html").css("width", swidth); //html的高度和宽度
	$("html").css("height", sheight);
	$("body").css("width", swidth); //body的高度和宽度
	$("body").css("height", sheight);

	$(".layui-container").css("width", swidth);
	$(".layui-container .layui-row").css("width", swidth);
	$(".layui-container .layui-row:eq(1)").css("height", sheight - 60);
	$(".layui-col-xs2").css("height", sheight - 60);
	$(".layui-col-xs7").css("height", sheight - 60);
	$(".layui-col-xs3").css("height", sheight - 60);
}
