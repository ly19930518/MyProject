<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<html>
<body>
<div>我是websocket</div>
<div id="message"></div>
<input id="text" type="text" />
<button onclick="send()">发送</button>
</body>
<script>
    var websocket = null;
    //判断当前游览器是否支持
    if('WebSocket' in window){
        websocket = new WebSocket("ws://192.168.2.15:8900/websocket?userid=b9176a5835044bb4b6a7ecc394e439e3&pwd=8f4b2ff0f31ac9f0");
    }else{
        alert("当前游览器不知此websokcet,请更换游览器");
    }
    //连接发生错误回调
    websocket.onerror = function(){
        var dm = document.getElementById("message")
        dm.innerHTML += "发生了一个错误<br/>"
    }
    websocket.onopen = function(){
        var dm = document.getElementById("message")
        dm.innerHTML += "连接服务器成功<br/>"
    }
    //连接服务器关闭
    websocket.onclose = function (){
        var dm = document.getElementById("message")
        dm.innerHTML += "与服务器连接断开<br/>"
    }
    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        addmessage(event.data);
    }
    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }
    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }
    var addmessage = function(msg){
        console.log(msg);
        msg = eval('('+msg+')');
        var senderName = msg.senderName;
        var senderID = msg.senderID;
        var head = msg.head;
        if(head == '100'){//普通文本消息
            var dm = document.getElementById("message")
            var text = msg.content.text;
            var infos = senderName+":"+text;
            dm.innerHTML += infos+"<br/>"
        }
    }

    //发送消息
    function send() {
        var text = document.getElementById('text').value;
        var message = {
            "content":
                {"text":""+text+""},
            "head":"100",
            "sendTime":"Tue Apr 10 17:23:52 CST 2018",
            "senderID":"system",
            "senderName":"系统"
        }
        websocket.send(JSON.stringify(message));
    }
</script>
</html>
