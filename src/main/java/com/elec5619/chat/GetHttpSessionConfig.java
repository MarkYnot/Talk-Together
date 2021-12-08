package com.elec5619.chat;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Map;

public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // get httpsession object
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        // store httpsession object
        Map<String, Object> userProperties = sec.getUserProperties();
        userProperties.put(HttpSession.class.getName(), httpSession);
    }
}
