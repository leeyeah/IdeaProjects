<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<%--@elvariable id="[user,stu]" type=""--%>
<form:form action="/result" modelAttribute="user" method="get">
    <form:input path="name"></form:input>
    <br>
    <form:input path="age"></form:input>
    <br>
    ${stu.school}
    <br>

    <br>
    <button type="submit">submit</button>
</form:form>

</body>
</html>