<%@page contentType="text/html;charset=UTF-8"%>
<%--访问jsp的时候不生成sesion对象--%>
<%--<%@page session="false" %>--%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>欢迎使用OA系统</title>
	</head>
	<body>
		<%-- 前端发送请求路径的时候，如果请求路径是绝对路径，要以 / 开始，加项目名。--%>
		<%--以下这样写代码，直接把项目名写死了。这种设计显然是不好的。--%>
<%--		<a href="/oa/list.html">查看部门列表</a>--%>
<%--		<a href="<%=request.getContextPath()%>/dept/list">查看部门列表</a>--%>
	<%--调用哪个对象的方法，可以动态的获取一个应用的根路径--%>
<%--	<%=request.getContextPath()%>  --%>
		<h1>LOGIN PAGE</h1>
		<hr>
	<form action="${pageContext.request.contextPath}/user/login" method="post">
		username :<input type="text" name="username"><br>
		password :<input type="text" name="password"><br>
		<input type="checkbox" name="f" value="1">10天内免登录<br>
		<input type="submit" value="login">
	</body>
</html>
