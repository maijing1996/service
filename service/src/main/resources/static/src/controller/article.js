/**

 @Name：order 会员模块控制
 @Author：偶木
 @Date：2018-09-05
    
 */


layui.define(['table', 'form'], function(exports){
	var $ = layui.$,
	admin = layui.admin,
	view = layui.view,
	table = layui.table,
	form = layui.form;
	
	table.render({
		elem: '#OM-article-details',
		url: '/manager/article/details/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号'},
			{field: 'title', title: '标题'},
			{field: 'cat_name', title: '所属栏目'},
			{field: 'zan', title: '人气'},
			{field: 'pic', title: '图片'},
			{field: 'url', title: '外链'},
			{field: 'is_jp', title: '精品'},
			{field: 'is_show', title: '发布'},
			{title: '操作', width: 200, align:'center', fixed: 'right', toolbar: '#table-article-details'}
		]],
		page: true,
		limit: 30,
		height: 'full-320',

		//ajax
		contentType: 'application/json; charset=UTF-8',
		method: 'post',
		request: {
			limitName: 'size'
		},
		response: {
			statusCode: 200
		},
		
		text: '对不起，加载出现异常！'
	});
  
	//会员列表_监听工具条
	table.on('tool(OM-article-details)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此用户信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/article/details/delete",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify(req),
					success: function(data){
						layer.open({
	                        content: data.desc,
	                        btn: ['确认'],
	                        yes: function(index, layero) {
	                        	layer.close(index);
	                        	table.reload('OM-article-details', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
		    console.log(data);
			view("OM-article-details-temp").render('article/component/article-edit', data).done(function(){
				console.log(data)
				form.render(null, 'popup-form-edit');
    
				//监听提交
				/*form.on('submit(LAY-user-back-submit)', function(data){
					var field = data.field; //获取提交的字段

					//提交 Ajax 成功后，关闭当前弹层并重载表格
					//$.ajax({});
					layui.table.reload('OM-member-level'); //重载表格
					layer.close(index); //执行关闭 
				});*/
			});
		}
	});
	
	table.render({
		elem: '#OM-article-categroy',
		url: '/manager/article/categroy/info',
		cols: [[
			{type: 'checkbox', fixed: 'left'},
			{field: 'id', title: '编号', width: 150},
			{field: 'title', title: '名称'},
			{field: 'wap_title', title: '手机名称'},
			{field: 'sequence', title: '排序'},
			{field: 'is_top', title: '是否推荐'},
			{field: 'is_show', title: '是否显示'},
			{title: '操作', width: 350, align:'center', fixed: 'right', toolbar: '#table-article-categroy'}
		]],
		page: true,
		limit: 30,
		height: 'full-320',

		//ajax
		contentType: 'application/json; charset=UTF-8',
		method: 'post',
		request: {
			limitName: 'size'
		},
		response: {
			statusCode: 200
		},
		
		text: '对不起，加载出现异常！'
	});
  
	table.on('tool(OM-article-categroy)', function(obj){
		var data = obj.data;
		if(obj.event === 'del'){
			layer.confirm('确定删除此用户信息？', function(index){
				layer.close(index);
				
				//数据封装
				var req = {"id": data.id};
				//执行 Ajax 后重载
	          	$.ajax({
					type: "POST",
					url: "/manager/article/categroy/delete",
					dataType:'json',//预期服务器返回的数据类型
					contentType: "application/json; charset=utf-8",
					async: false,
					data: JSON.stringify(req),
					success: function(data){
						layer.open({
	                        content: data.desc,
	                        btn: ['确认'],
	                        yes: function(index, layero) {
	                        	layer.close(index);
	                        	table.reload('OM-article-categroy', null);
	                        }
	                    });
					}, error: function(){
						location.hash='/system/404';
					}
		    	});
			});
		} else if(obj.event === 'edit') {
			//location.hash='/system/404';
			console.log("id:"+this.id);
			//console.log(data);
			view(null).render('member/component/member_details', data).done(function(){
				console.log(data)
				form.render(null, 'popup-form-edit');
    
				//监听提交
				/*form.on('submit(LAY-user-back-submit)', function(data){
					var field = data.field; //获取提交的字段

					//提交 Ajax 成功后，关闭当前弹层并重载表格
					//$.ajax({});
					layui.table.reload('OM-member-level'); //重载表格
					layer.close(index); //执行关闭 
				});*/
			});
		}
	});
	
	exports('article', {})
});