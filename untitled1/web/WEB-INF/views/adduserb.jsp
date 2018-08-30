<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="utf-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<form:form action="/adduserb" method="post" modelAttribute="user">
    <form:input path="name"></form:input>
    <form:input path="age"></form:input>
    <button type="submit">submit</button>

</form:form>
<!--
<form action="/adduserb" method="post">
    <input type="text" name="name" value="${user.name}">
    <br>
    <input type="text" name="age" value="${user.age}">
    <br>
    <input type="submit">

</form>
-->
</body>
</html>