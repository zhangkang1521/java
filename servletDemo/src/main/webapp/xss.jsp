<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>xss demo</title>
    <%--<script src="static/js/getCookie.js"></script>--%>
    <script src="static/js/jquery/jquery-1.8.3.min.js"></script>
    <script src="static/js/jquery.md5.js"></script>

</head>
<body>
<script>
    console.log($.md5('123456'));
    $(function () {
        console.log($.md5('111111'));
    });

    /*function test() {
     var str = document.getElementById("text").value;
     document.getElementById("t").innerHTML = "<a href='"+str+"'>testLink</a>";
     }*/
</script>
    ${content}
<script src="static/js/jquery/jquery-1.8.3.min.js"></script>
</body>
</html>

