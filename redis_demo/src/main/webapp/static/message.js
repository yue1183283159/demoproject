//服务端推送消息处理，websocket

var websocket = null;
var reconnectCount = 0;

function connectSocket() {
    var data = basicConfig();

    if (!data.websocketEnable) {
        return;
    }

    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        if (data.localIp && data.localIp !== "" && data.serverPort && data.serverPort !== "") {
            websocket = new WebSocket("ws://" + data.localIp + ":" + data.serverPort + data.serverContextPath + "/websocket");
        } else {
            return;
        }
    } else {
        alert('当前浏览器 不支持WebSocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        console.log("连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        reconnectCount = 0;
        console.log("连接成功");
    };

    //接收到消息的回调方法，此处添加处理接收消息方法，当前是将接收到的信息显示在网页上
    websocket.onmessage = function (event) {
        console.log("receive message：" + event.data);
    };

    //连接关闭的回调方法
    websocket.onclose = function () {
        console.log("连接关闭,如需登录请刷新页面。");
        if (reconnectCount === 3) {
            reconnectCount = 0;
            return;
        }
        connectSocket();
        basicConfig();
        reconnectCount++;
    };

    //添加事件监听
    websocket.addEventListener('open', function () {
        websocket.send(data.userCode);
    });

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        console.log("closeWebSocket");
    };

}

function basicConfig() {
    var result = {};
    $.ajax({
        type: 'get',
        async: false,
        url: '/websocket/config',
        data: {rnd: Math.random()},
        success: function (data) {
            result = data;
        }
    });

    return result;
}

connectSocket();