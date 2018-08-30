<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 2018/4/18
  Time: 下午8:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>erroraa</title>
</head>
<body>
<%=exception.getClass().getName()%>
<h1>-------------</h1>
<%=exception.getMessage()%>
<h1>-------------</h1>
<h3>${ex.getMessage()}</h3>
</body>
</html>
