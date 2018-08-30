<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 2018/4/18
  Time: 下午3:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>error500.jsp</h1>

<%=exception.getClass().getName()%>
<h1>1</h1>
<br/>
<%=exception.fillInStackTrace()%>
<h1>2</h1>
<br/>
<%=exception.toString()%>
<h1>3</h1>
<br/>
<%=exception.getClass().getName()%>

</body>
</html>
