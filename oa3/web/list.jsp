<%@ page import="java.util.List" %>
<%@ page import="com.bjpowernode.oa.bean.Dept" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>部门列表页面</title>
	</head>
	<body>

	<%--显示一个登录名--%>
	<h3>欢迎,在线人数${onclinecount}</h3>
	<%--退出系统超链接--%>
	<a href="${pageContext.request.contextPath}/user/exit">退出系统</a>
<script type="text/javascript">
	function del(dno){
		var ok = window.confirm("亲，删了不可恢复哦！");
		if(ok){
			document.location.href = "${pageContext.request.contextPath}/dept/delete?deptno="+dno;
		}
	}
</script>

		<h1 align="center">部门列表</h1>
		<hr >
		<table border="1px" align="center" width="50%">
			<tr>
				<th>序号</th>
				<th>部门编号</th>
				<th>部门名称</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${deptlist}" varStatus="deptStatus" var="dept">
				<tr>
					<td>${deptStatus.count}</td>
					<td>${dept.deptno}</td>
					<td>${dept.dname}</td>
					<td>
						<a href="javascript:void(0)" onclick="del(${dept.deptno})">删除</a>
						<a href="${pageContext.request.contextPath}/dept/detail?f=m&dno=${dept.deptno}">修改</a>
						<a href="${pageContext.request.contextPath}/dept/detail?f=d&dno=${dept.deptno}">详情</a>
				</td>
				</tr>
			</c:forEach>

		</table>
		
		<hr >
		<a href="${pageContext.request.contextPath}/add.jsp">新增部门</a>
		
	</body>
</html>
