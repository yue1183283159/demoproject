package com.redis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/websocket/")
public class WebSocket {

    @RequestMapping("config")
    @ResponseBody
    public WebSocketConfig getConfig() {
        WebSocketConfig webSocketConfig = new WebSocketConfig(true, "127.0.0.1", "8085", "/");
        return webSocketConfig;
    }
}

class WebSocketConfig {
    private Boolean websocketEnable;
    private String localIp;
    private String serverPort;
    private String serverContextPath;

    public WebSocketConfig(Boolean websocketEnable, String localIp, String serverPort, String serverContextPath) {
        this.websocketEnable = websocketEnable;
        this.localIp = localIp;
        this.serverPort = serverPort;
        this.serverContextPath = serverContextPath;
    }

    public Boolean getWebsocketEnable() {
        return websocketEnable;
    }

    public void setWebsocketEnable(Boolean websocketEnable) {
        this.websocketEnable = websocketEnable;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerContextPath() {
        return serverContextPath;
    }

    public void setServerContextPath(String serverContextPath) {
        this.serverContextPath = serverContextPath;
    }
}
