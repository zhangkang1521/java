<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>xss demo</title>
    <%--<script src="static/js/getCookie.js"></script>--%>
</head>
<body>
<a href="" onclick="alert(/ss/)" '="">testLink</a>
    <div id="t"></div>
    <input type="text" id="text" value="">
    <input type="button" id="s" value="write" onclick="test()">
    <a href=""></a><img src="static/image/baidu.png" onerror="alert('err')" /><a href=""></a>
    <a href=""></a>
</body>
</html>
<script>
    function test() {
        var str = document.getElementById("text").value;
        document.getElementById("t").innerHTML = "<a href='"+str+"'>testLink</a>";
    }
</script>
