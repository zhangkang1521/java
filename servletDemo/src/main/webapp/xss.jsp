<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>xss demo</title>
    <%--<script src="static/js/getCookie.js"></script>--%>
</head>
<body>
    ${content}
</body>
</html>
<script>
    function test() {
        var str = document.getElementById("text").value;
        document.getElementById("t").innerHTML = "<a href='"+str+"'>testLink</a>";
    }
</script>
