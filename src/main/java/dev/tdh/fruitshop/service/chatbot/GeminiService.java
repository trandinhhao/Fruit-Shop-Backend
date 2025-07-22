package dev.tdh.fruitshop.service.chatbot;

import dev.tdh.fruitshop.model.response.ResponseDTO;
import dev.tdh.fruitshop.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class GeminiService {
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    // Lưu trữ lịch sử hội thoại theo session
    private Map<String, List<Map<String, String>>> conversationHistory = new HashMap<>();

    public ResponseEntity<ResponseDTO> askGemini(Map<String, String> body) {
        String userMsg = body.get("message");
        String sessionId = body.get("sessionId"); // Thêm sessionId vào request body

        // Khởi tạo lịch sử hội thoại nếu chưa có
        if (!conversationHistory.containsKey(sessionId)) {
            conversationHistory.put(sessionId, new ArrayList<>());
        }

        List<Map<String, Object>> products = fetchProducts();

        // Tạo context với thông tin sản phẩm
        StringBuilder context = new StringBuilder();
        context.append("Bạn là một nhân viên tư vấn bán hàng trái cây. Dưới đây là danh sách sản phẩm đang bán:\n");
        for (Map<String, Object> p : products) {
            context.append("- ")
                    .append(p.get("name")).append(", giá: ")
                    .append(p.get("price")).append(" VND, ")
                    .append("giảm giá: ").append(p.get("discount")).append("%\n");
        }
        context.append("Hãy trả lời khách hàng dựa trên thông tin này. Trả lời ngắn gọn, thân thiện và chuyên nghiệp. KHÔNG sử dụng bất kỳ định dạng markdown nào như *, **, _, hoặc danh sách đánh số. Chỉ dùng văn bản thuần túy.\\n\\n");

        // Thêm lịch sử hội thoại vào prompt
        context.append("Lịch sử hội thoại:\n");
        for (Map<String, String> msg : conversationHistory.get(sessionId)) {
            context.append(msg.get("role")).append(": ").append(msg.get("content")).append("\n");
        }

        // Thêm câu hỏi hiện tại
        context.append("Khách hàng hỏi/muốn nhận được câu trả lời cho câu hỏi: ").append(userMsg);

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + geminiApiKey;
        Map<String, Object> part = new HashMap<>();
        part.put("text", context.toString());
        Map<String, Object> content = new HashMap<>();
        content.put("parts", Collections.singletonList(part));
        List<Map<String, Object>> contents = new ArrayList<>();
        contents.add(content);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", contents);

        WebClient webClient = WebClient.create();
        try {
            Map response = webClient.post()
                    .uri(url)
                    .body(Mono.just(requestBody), Map.class)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null && response.containsKey("candidates")) {
                List candidates = (List) response.get("candidates");
                if (!candidates.isEmpty()) {
                    Map first = (Map) candidates.get(0);
                    Map contentMap = (Map) first.get("content");
                    List parts = (List) contentMap.get("parts");
                    if (!parts.isEmpty()) {
                        Map partMap = (Map) parts.get(0);
                        String answer = partMap.get("text").toString();

                        // Lưu câu hỏi và câu trả lời vào lịch sử
                        Map<String, String> userMessage = new HashMap<>();
                        userMessage.put("role", "Khách hàng");
                        userMessage.put("content", userMsg);
                        conversationHistory.get(sessionId).add(userMessage);

                        Map<String, String> botMessage = new HashMap<>();
                        botMessage.put("role", "Nhân viên");
                        botMessage.put("content", answer);
                        conversationHistory.get(sessionId).add(botMessage);

                        // Giới hạn số lượng tin nhắn trong lịch sử (ví dụ: 10 tin nhắn gần nhất)
                        if (conversationHistory.get(sessionId).size() > 10) {
                            conversationHistory.get(sessionId).remove(0);
                            conversationHistory.get(sessionId).remove(0);
                        }

                        return ResponseEntity.ok(ResponseUtils.makeResponse(answer, "success", ""));
                    }
                }
            }
            return ResponseEntity.ok(ResponseUtils.makeResponse("", "Câu hỏi này nằm ngoài phạm vi của tôi", ""));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseUtils.makeResponse("", "Gemini đang lỗi, thử lại sau", e.getMessage()));
        }
    }

    private List<Map<String, Object>> fetchProducts() {
        try {
            WebClient webClient = WebClient.create();
            List products = webClient.get()
                    .uri("http://localhost:8082/api/fruits")
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();

            // Lọc chỉ lấy các trường cần thiết
            List<Map<String, Object>> filteredProducts = new ArrayList<>();
            for (Object product : products) {
                Map<String, Object> p = (Map<String, Object>) product;
                Map<String, Object> filtered = new HashMap<>();
                filtered.put("name", p.get("name"));
                filtered.put("price", p.get("price"));
                filtered.put("discount", p.get("discount"));
                filteredProducts.add(filtered);
            }
            return filteredProducts;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
