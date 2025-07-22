package dev.tdh.fruitshop.controller;

import dev.tdh.fruitshop.model.response.ResponseDTO;
import dev.tdh.fruitshop.service.captcha.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CaptchaController {
    private final CaptchaService captchaService;

    @PostMapping("/api/captcha")
    public ResponseEntity<ResponseDTO> checkCaptcha(@RequestParam("g-recaptcha-response") String recaptchaResponse) {
        return captchaService.verify(recaptchaResponse);
    }
}