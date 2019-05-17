<%@page import="com.wbc.graduation.domain.User"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#delAll").click(function(){
			if (this.checked){  
		        $("input[name='del']:checkbox").each(function(){ 
		              $(this).prop("checked", true);  
		        });
		    } else {   
		        $("input[name='del']:checkbox").each(function() {   
		              $(this).prop("checked",false);  
		        });
		    }  
		});
		$("#checkAll").click(function(){
			var ids = "";
			var a = $("input[name='del']:checked").val([]);
			for(var i = 0; i < a.length; i++){
				ids += a[i].value;
				if(a.length-1 != i){
					ids+=",";
				}
			}
			if(ids==""){
				alert("未选取删除项！")
			}else{
				$.ajax({
					   type: "POST",
					   url: "/admin/deleteMore",
					   data: "ids="+ids,
					   success: function(msg){
						   alert(msg);
						   window.location.reload();
					   }
					});
				
			    //
			}
			
		})
		
	});
</script>

<style>
	.table{
		margin-top:200px;
		background-color:gray;
	}
	
	.table tr:nth-of-type(2n){
		background-color: lightgreen;
	}
	.table tr:nth-of-type(2n+1){
		background-color: lightgray;
	}
	.table tr:nth-child(1){
		background-color: red;
		color: white;
	}
	.table tr:hover{
		color: white;
		background-color: orange;
	}
</style>
</head>
<body>
<%
	ArrayList<User> Users = (ArrayList<User>)request.getAttribute("users");
	String Arr_id = "";
%>
	<form action="userhd?type=delete_manybook" method="post">
		<table border="0" cellpadding="10px" cellspacing="1px" class="table" align="center">
			<tr>
				<td align="center">勾选</td>
				<td align="center">编号</td>
				<td align="center">用户名</td>
				<td align="center">密码</td>
				<td align="center">密钥</td>
				<td align="center">操作</td>
			</tr>
	<%
		for(User a:Users){
	%>
			<tr>
				<td><input type="checkbox" name="del" id="del" value="<%=a.getId() %>"></td>
				<td><%=a.getId() %></td>
				<td><%=a.getUserName() %></td>
				<td><%=a.getPassword() %></td>
				<td><%=a.getHeadImg() %></td>
				<td><a href="javascript: if(window.confirm('是否删除用户《<%=a.getUserName() %>》？')){window.location.href='/admin/deleteOne?id=<%=a.getId() %>';alert('删除成功！')}">删除</a></td>
			</tr>
	<%
		}		
	%> 
			<%-- <c:forEach var="list" items="${ requestScope.Users }" >
				<tr>
					<td><input type="checkbox" name="del" value="${ list.id }"></td>
					<td>${ list.id }</td>
					<td>${ list.userName }</td>
					<td>${ list.password}</td>
					<td>${ list.headImg}</td>
					<td><a href="update_book.jsp?book_id=${ list.book_id }&book_name=${ list.book_name }
								&book_from=${ list.book_from }&book_num=${ list.book_id }">修改</a></td>
					<td><a href="javascript: if(window.confirm('是否删除用户《${ bklist.book_name }》？')){window.location.href='userhd?type=delete_book&book_name=${ list.book_name }'}">删除</a></td>
				</tr>
			</c:forEach> --%>
			<tr>
				<td colspan="2"><input type="checkbox" id="delAll" name="delAll">全选</td>
				<td colspan="5"><input type="button" id="checkAll" value="删除已选项"></td>
			</tr>
		</table>
	<form>
	
	
	
	
	
	<p align="center"><a href="${pageContext.request.contextPath}/NewLogin.jsp">返回</a></p>
	<%-- <p align="center" style="color:red;"><%= request.getAttribute("msg")==null?"":request.getAttribute("msg") %></p> --%>
</body>
</html>