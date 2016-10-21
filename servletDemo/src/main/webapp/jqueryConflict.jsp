<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>xss demo</title>
    <script src="static/js/jquery/jquery-1.8.3.min.js"></script>
    <script src="static/js/jquery.md5.js"></script>

</head>
<body>
<script>
    console.log($.md5('123456')); // 不报错
    $(function () {
        console.log($.md5('111111')); //报错，因为文档加载完后又引入了一次jquery
    });

</script>
${content}
<script src="static/js/jquery/jquery-1.8.3.min.js"></script>
</body>
</html>

