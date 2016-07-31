<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/view/common/taglib.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${ctx}/token/submit">
        <input type="text" name="token" value="${token}">
        <input type="text" name="username">
        <input type="submit">
    </form>
</body>
</html>
