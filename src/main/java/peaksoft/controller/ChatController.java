package peaksoft.controller;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @MessageMapping("/chat.sendMessage") // Принимает сообщение от клиента
    @SendTo("/topic/public") // Отправляет сообщение всем подписанным на /topic/public
    public Message sendMessage(Message message) {
        return message;
    }
}
