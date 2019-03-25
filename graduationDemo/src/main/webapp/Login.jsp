<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录验证</title>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
</head>
<body>
<form action="/user/login" method="post">

	<input type="text" id= "username" name="username" value="" ><span style="color:red">${msg1}</span><br>
	<input type="password" id= "password" name="password" value="" ><span style="color:red">${msg2}</span><br>
	<input type="submit" value="登录">
	<input type="reset"><br>
	<a href="${pageContext.request.contextPath}/register.jsp">跳转至注册页面</a>
</form>


</body>
</html>