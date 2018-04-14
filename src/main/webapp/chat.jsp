<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta charset="UTF-8">
  <title>聊天室</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport"
        content="width=device-width, initial-scale=1">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <link rel="alternate icon" type="image/png" href="assets/i/favicon.png">
  <link rel="stylesheet" href="assets/css/amazeui.min.css"/>
  
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/amazeui.min.js"></script>
<!-- UM相关资源 -->
<link href="assets/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="assets/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="assets/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="assets/umeditor/lang/zh-cn/zh-cn.js"></script>

 <!-- 引入 Bootstrap -->
      <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
 
      <!-- HTML5 Shiv 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
      <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
      <!--[if lt IE 9]>
         <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
         <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
      <![endif]-->

</head>
<body onload="init()">
<nav class="navbar navbar-default" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">以物易物</a>
		</div>
		<div>

			<ul class="nav navbar-nav " id="loginandregister">
				<li><a href="/login">登录</a></li>
				<li><a href="#">注册</a></li>
			</ul>
			<ul class="nav navbar-nav " id="logined">
				<li><a href="/login" id="username_href"></a></li>
			</ul>
			<div class="">
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search" id="searchCondition">
					</div>
					<button type="button" class="btn btn-default" id="searchButton">搜索</button>
				</form>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="/UserInformationManagement.html" class="dropdown-toggle" data-toggle="dropdown">
						个人信息
						<b class="caret"></b>
					</a>

					<ul class="dropdown-menu">
						<li><a href="/UserInformationManagement.html">设置</a></li>
						<li><a href="/ItemUpshelfManagement.html">上架物品</a></li>
						<li><a href="/AlreadyUpshelfItem.html">已上架物品</a></li>
						<li><a href="/AlreadyDoneOrder.html">已完成订单</a></li>
						<li><a href="/WaitingForAcceptOrder.html">待接受订单</a></li>
						<li><a href="/UnreadyOrder.html">未完成订单</a></li>
					</ul>
				</li>
				<li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span>  购物车</a></li>
			</ul>
		</div>
	</div>
</nav>
	<div id="main">
		<!-- 聊天内容展示区域 -->
	<div id="ChatBox" class="am-g am-g-fixed" >
	  <div class="am-u-lg-12" style="height:400px;border:1px solid #999;overflow-y:scroll;">
		<ul id="chatContent" class="am-comments-list am-comments-list-flip">
			<li id="msgtmp" class="am-comment" style="display:none;">
			    <a href="">
			        <img ff="usephoto" class="am-comment-avatar" src="assets/images/other.jpg" alt=""/>
			    </a>
			    <div class="am-comment-main" >
			        <header class="am-comment-hd">
			            <div class="am-comment-meta">
			              <a ff="nickname" href="#link-to-user" class="am-comment-author">某人</a>
			              <time ff="msgdate" datetime="" title="">2014-7-12 15:30</time>
			            </div>
			        </header>
			     <div ff="content" class="am-comment-bd">此处是消息内容</div>
			    </div>
			</li>
		</ul>
	  </div>
	</div>
	<!-- 聊天内容发送区域 -->
	<div id="EditBox" class="am-g am-g-fixed">
	<!--style给定宽度可以影响编辑器的最终宽度-->
	<script type="text/plain" id="myEditor" style="width:100%;height:140px;"></script>
		<button id="send" type="button" class="am-btn am-btn-primary am-btn-block">发送</button>
	</div>
  
</div>
<script type="text/javascript">
//先获取用户信息
            $.ajax({
                type :"post",
                url:"/getUI",
                contentType : "application/json;charset=utf-8",
                dataType:"json",
                success:function(commResult){
                    var userInfo =commResult;
                    userInfo = JSON.stringify(userInfo.data);
                    sessionStorage.setItem("userInfo",userInfo);
                },
                error:function(commResult){
					alert("用户信息获取失败");
                }
            });


$(function(){


	//实例化编辑器
    var um = UM.getEditor('myEditor',{
    	initialContent:"",
    	autoHeightEnabled:false,
    	toolbar:[
            'source | undo redo | bold italic underline strikethrough | forecolor backcolor | removeformat |',
            'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize' ,
            '| justifyleft justifycenter justifyright justifyjustify |',
            'link unlink | emotion '
        ]
    });
    
    var userInfo = sessionStorage.getItem("userInfo");
    console.log(userInfo);
    userInfo = JSON.parse(userInfo);
    console.log(userInfo);
    var nickname = userInfo.username;
    var uid = userInfo.uid;
    var ChatRoomId =  sessionStorage.getItem("ChatRoomId");
	var socket = new WebSocket("ws://${pageContext.request.getServerName()}:${pageContext.request.getServerPort()}${pageContext.request.contextPath}/websocket/"+ChatRoomId+"/"+userInfo.uid);
    //接收服务器的消息
    socket.onmessage=function(ev){
    	var obj = eval(   '('+ev.data+')'   );
    	addMessage(obj);
    }
    
    $("#send").click(function(){
    	if (!um.hasContents()) {  // 判断消息输入框是否为空
            // 消息输入框获取焦点
            um.focus();
            // 添加抖动效果
            $('.edui-container').addClass('am-animation-shake');
            setTimeout("$('.edui-container').removeClass('am-animation-shake')", 1000);
        } else {
        	//获取输入框的内容
        	var txt = um.getContent();
        	//构建一个标准格式的JSON对象
        	var obj = JSON.stringify({
	    		nickname:nickname,
	    		content:txt
	    	});
        	alert("发送的消息"+obj);
            // 发送消息
            socket.send(obj);
            // 清空消息输入框
            um.setContent('');
            // 消息输入框获取焦点
            um.focus();
        }
    
    });
    
    
    
    
    
});

//人名nickname，时间date，是否自己isSelf，内容content
function addMessage(msg){

	var box = $("#msgtmp").clone(); 	//复制一份模板，取名为box
	box.show();							//设置box状态为显示
	box.appendTo("#chatContent");		//把box追加到聊天面板中
	box.find('[ff="nickname"]').html(msg.nickname); //在box中设置昵称
	box.find('[ff="msgdate"]').html(msg.date); 		//在box中设置时间
	box.find('[ff="content"]').html(msg.content); 	//在box中设置内容
	box.find('[ff="usephoto"]').attr("src",'/userphoto/'+msg.uid+'/userphoto.png'); // 设置头像，头像地址格式/userphoto/2/userphoto.png
	box.addClass(msg.isSelf? 'am-comment-flip':'');	//右侧显示
	box.addClass(msg.isSelf? 'am-comment-warning':'am-comment-success');//颜色
	box.css((msg.isSelf? 'margin-left':'margin-right'),"20%");//外边距
	
	$("#ChatBox div:eq(0)").scrollTop(999999); 	//滚动条移动至最底部
	
}


</script>

</body>

   <!-- 包括所有已编译的插件 -->
   <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
   <script type="text/javascript">
   	var url={
   			
   			url:function (){
   				return "";
   			}
   	}
   </script>
<script type="application/javascript">
    var logined = $("#logined");//已登录显示的组件
    logined.hide();
    var loginandregister = $("#loginandregister");
    var username_href = $("#username_href");
    var username = "";
    var phone = "";
    var email = "";
    var balance =　"";
    function init(){
        $.post("/getUI","",function (json,status) {
            var obj = json;
            if(obj.message == "isLogin"){
                username = obj.data.username;
                phone = obj.data.phone;
                email = obj.data.email;
                balance = obj.data.balance;
                loginandregister.hide();
                document.getElementById("username_href").innerHTML = "Hi,"+username+"!";
                logined.show();
            }else if (obj.message == "isNotLogin"){
            }
        },"json");
        $("#searchButton").click(function () {
            var searchCondition = $("#searchCondition").val();
            alert(searchCondition);
            localStorage.setItem("searchCondition",searchCondition);
            window.location.href='/page_search.html';
        });
    }
</script>
</html>
