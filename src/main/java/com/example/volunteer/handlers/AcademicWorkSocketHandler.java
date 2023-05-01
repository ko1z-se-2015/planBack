package com.example.volunteer.handlers;


import com.example.volunteer.entities.AcademicWork;
import com.example.volunteer.services.AcademicWorkService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AcademicWorkSocketHandler extends BinaryWebSocketHandler {
//
//    AcademicWorkService academicWorkService = new AcademicWorkService();
//    private final List<WebSocketSession> webSocketSessions = new ArrayList<>();
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//       webSocketSessions.add(session);
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
//        String payload = message.getPayload();
//        Long academicWorkId = Long.parseLong(payload);
//        AcademicWork academicWork = academicWorkService.findById(academicWorkId);
//        // отправляем ответ клиенту
//        ObjectMapper objectMapper = new ObjectMapper();
//        String academicWorkJson = null;
//        try {
//            academicWorkJson = objectMapper.writeValueAsString(academicWork);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            session.sendMessage(new TextMessage(academicWorkJson));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        super.handleTextMessage(session, message);
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        webSocketSessions.remove(session);
//    }
}
