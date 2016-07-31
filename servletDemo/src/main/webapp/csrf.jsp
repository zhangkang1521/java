<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>csrf test</title>
</head>
<body>
    <img src="http://www.a.com:8080/servlet/param.s?ss=s"/>
    <%--<form id="form1" action="http://www.a.com:8080/servlet/param.s">
        <input type="hidden" name="action" value="del">
    </form>
    <script>
        //document.getElementById("form1").submit();
    </script>--%>

    <iframe id="ifr"></iframe>
</body>
</html>
