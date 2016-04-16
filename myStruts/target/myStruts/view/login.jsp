
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>login</title>
</head>
<body>
    <div>
        <form action="${pageContext.request.contextPath}/login.action" method="post">
            <input type="text" name="userName"/>
            <input type="text" name="password"/>
            <input type="submit" value="登录"/>
        </form>

    </div>
</body>
</html>
