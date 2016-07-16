<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 7/16/16
  Time: 12:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="static/js/jquery/jquery-3.1.0.js"></script>
    <script src="static/js/jquery/jquery-ext.js"></script>
</head>
<body>
<script>

//    $.ajax({
//        url: 'http://localhost:8080/servlet/test.json',
//        success: function (data) {
//            alert(data);
//        }
//
//    })
    //alert(navigator.userAgent);

//    FF=/a/[-1]=='a'
//    FF3=(function x(){})[-5]=='x'
//    Chr=/source/.test((/a/.toString+' '));

</script>

    <a id="0" href="aa">aa</a>
    <a id="1" href="bb" style="color:red">bb</a>
    <a id="2" href="cc">cc</a>
<script>
    for(var i=0; i<3; i++){
        var aa = document.getElementById(i);
        var color = document.defaultView.getComputedStyle(aa,null).getPropertyValue("color");
        console.log(color);
    }

//    var bb = document.getElementById("bb");
//    var color = document.defaultView.getComputedStyle(bb,null).getPropertyValue("color");
//    console.log(color);
</script>
</body>
</html>
