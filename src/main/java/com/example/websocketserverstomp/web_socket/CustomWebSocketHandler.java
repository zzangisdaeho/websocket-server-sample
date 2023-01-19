package com.example.websocketserverstomp.web_socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Map<String, Object> attributes = session.getAttributes();

        //세션 저장
        var sessionId = session.getId();
        sessions.put(sessionId, session);

        sessions.values().forEach(s -> {
            try {
                s.sendMessage(new TextMessage(sessionId + attributes.get("username") + attributes.get("username2") + " 님이 합류했슴"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        var id = session.getId();
        sessions.forEach((k, v) -> {
            if(!id.equals(k)) {
                try {
                    v.sendMessage(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //세션 제거
        sessions.remove(session.getId());
        sessions.forEach((k, v) -> {
            try {
                v.sendMessage(new TextMessage(session.getId() + "님이 퇴장했슴돠"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
