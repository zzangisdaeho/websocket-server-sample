package com.example.websocketserverstomp.web_socket;

import com.example.websocketserverstomp.dto.CustomMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/hello")
    public void message(CustomMessage customMessage){
        simpMessageSendingOperations.convertAndSend("/sub/channel/" + customMessage.getChannelId(), customMessage);
    }
}
