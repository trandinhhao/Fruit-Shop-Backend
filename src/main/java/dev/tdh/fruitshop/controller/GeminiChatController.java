package dev.tdh.fruitshop.controller;

import dev.tdh.fruitshop.model.response.ResponseDTO;
import dev.tdh.fruitshop.service.chatbot.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class GeminiChatController {
    private final GeminiService geminiService;

    @PostMapping("/api/gemini-chat")
    public ResponseEntity<ResponseDTO> chat(@RequestBody Map<String, String> body) {
        return geminiService.askGemini(body);
    }
}