<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录验证</title>
</head>
<body>
<form action="/user/login">

	<input type="text" id= "username" name="username" value="" ><span class="color:red">${msg1}</span><br>
	<input type="password" id= "password" name="password" value="" ><span class="color:red">${msg2}</span><br>
	<input type="submit" value="登录">
	<input type="reset"><br>
	
</form>
<a href="register.jsp">跳转至注册页面</a>

</body>
</html>