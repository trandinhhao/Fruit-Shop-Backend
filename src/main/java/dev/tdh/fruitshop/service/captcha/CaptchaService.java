package dev.tdh.fruitshop.service.captcha;

import dev.tdh.fruitshop.model.response.RecaptchaResponse;
import dev.tdh.fruitshop.model.response.ResponseDTO;
import dev.tdh.fruitshop.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CaptchaService {
    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    private static final String GOOGLE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public ResponseEntity<ResponseDTO> verify(String recaptchaResponse) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> body = new HashMap<>();
        body.put("secret", recaptchaSecret);
        body.put("response", recaptchaResponse);

        RecaptchaResponse apiResponse = restTemplate.postForObject(
                GOOGLE_VERIFY_URL + "?secret={secret}&response={response}", null,
                RecaptchaResponse.class, body);

        if (apiResponse != null && apiResponse.isSuccess()) {
            return ResponseEntity.ok(ResponseUtils.makeResponse(true, "Xác minh CAPTCHA thành công", ""));
        } else {
            return ResponseEntity.ok(ResponseUtils.makeResponse(false, "Xác minh CAPTCHA thất bại", ""));
        }
    }
}
