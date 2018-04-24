
window.onload = function() {
	var swidth = window.innerWidth;
	var sheight = window.innerHeight;
	$("html").css("width", swidth); //html的高度和宽度
	$("html").css("height", sheight);
	$("body").css("width", swidth); //body的高度和宽度
	$("body").css("height", sheight);

	$(".layui-container").css("width", swidth);
	$(".layui-container .layui-row:eq(1)").css("height", sheight - 60);
	$(".layui-col-xs7").css("height", sheight - 60);
	$(".layui-col-xs2").css("height", sheight - 60);
	$(".layui-col-xs3").css("height", sheight - 60);
}

var map = {
	"user_manager": "../template/user/user_list.html",
	"role":"../template/role/role_list.html",
	"organize":"../template/organize/organize_list.html",
	"permission_menu":"../template/organize/permission_menu.html",
	"permission":"../template/permission/permission.html",
	"blog":"../template/blog/blog_list.html"
}

layui.use('element', function() {
	var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

	//监听导航点击
	element.on('nav(menu_tree)', function(elem) {
		var url = map[elem[0].children[0].id];
		if(url == undefined) url = 'https://www.baidu.com';

		if($("li[lay-id='" + elem.text() + "']").length == 0) {
			element.tabAdd('table', {
				title: elem.text(),
				content: "<iframe frameborder='0' src='" + url + "' style='width:100%;height:" + rightHeight + "px;'></iframe>", //支持传入html
				id: elem.text()
			});
		}
		element.tabChange('table', elem.text());
		$(".layui-tab-title li:eq(0) i").remove();
		//		    layer.msg(elem.text());
	});
});