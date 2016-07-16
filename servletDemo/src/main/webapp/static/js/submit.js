/**
 * Created by root on 7/3/16.
 */
/*var f = document.createElement("form");
f.action = "http://localhost:8080/servlet/param.s";
f.method = "post";
document.body.appendChild(f);

var i1 = document.createElement("input");
i1.name = " ck";
i1.value = " JiuY";
f.appendChild(i1);

var i2 = document.createElement("input");
i2.name = " a";
i2.value = " aaa";
f.appendChild(i2);

f.submit();*/

// xmlHttpRequest
var url = "http://localhost:8080/servlet/param.s";
var data = "username=zk&password=1234";
var ajax = null;
if(window.XMLHttpRequest){
    ajax = new XMLHttpRequest();
} else if(window.ActiveXObject){
    ajax = new ActiveXObject("Microsoft.XMLHTTP");
} else {
    alert('not support ajax');
}
ajax.open("POST", url, true);
ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
ajax.send(data);

ajax.onreadystatechange = function () {
    console.log(ajax);
}



