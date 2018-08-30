<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 2018/4/18
  Time: 下午4:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>error404.jsp</h1>

<%
    boolean flag = false;
    flag= exception == null?true:false;



%>

<h1><%=flag%></h1>

<h2><%=request.getMethod()%></h2>

</body>
</html>
