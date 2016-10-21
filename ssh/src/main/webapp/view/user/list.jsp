<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/view/common/taglib.jsp"%>
<html>
<head>
    <title>userlist</title>
    <script src="${ctx}/static/js/jquery/jquery-1.8.3.min.js"></script>
</head>
<body>
userlist
<script>
        $.ajax({
            url: '${ctx}/user/del.html',
            success: function (data) {
                alert(data);
            }

        })
</script>
</body>
</html>
