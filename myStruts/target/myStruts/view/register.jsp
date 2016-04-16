<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>register</title>
</head>
<body>
<div>
    <form action="${pageContext.request.contextPath}/register.action" method="post">
        <input type="text" name="userName"/>
        <input type="text" name="password"/>
        <input type="submit" value="注册"/>
    </form>
</div>
</body>
</html>
