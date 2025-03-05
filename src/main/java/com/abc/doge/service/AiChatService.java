package com.abc.doge.service;

import com.abc.doge.dto.ChatRequestDto;
import com.abc.doge.dto.ChatResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AiChatService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${openai.api-url}")
    private String apiUrl;

    @Value("${openai.chatbot.prompt}")
    private String prompt;

    private final int maxResponseLength = 150;
    // Logger 인스턴스
    private final Logger logger = LoggerFactory.getLogger(AiChatService.class);

    public AiChatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    private void init(){
        logger.info("Loaded Prompt: {}", prompt);
    }

    public ChatResponseDto callChatbotApi(ChatRequestDto requestDto, HttpSession session) {
        String userMessage = requestDto.getMessage();
        // 세션에서 대화 히스토리 가져오기
        List<Map<String, String>> conversationHistory = getConversationHistory(session);

        // 기존 대화 기록이 비어있으면, 프롬프트를 첫 메세지로 삽입
        if(conversationHistory.isEmpty()){
            conversationHistory.add(Map.of(
                    "role", "system", "content", prompt));
        }

        // 사용자 메시지 히스토리에 추가
        conversationHistory.add(Map.of(
                "role", "user", "content", userMessage));

        // 프롬프트 확인용 로그
        logger.info("Final Conversation History Before API Call: {}", conversationHistory);

        // OpenAI API 호출 (히스토리, 세션 전달)
        String openAiResponse = callOpenAIAPI(conversationHistory, session);

        ChatResponseDto responseDto = new ChatResponseDto();
        responseDto.setOpenAiResponse(openAiResponse);

        return responseDto;
    }

    private String callOpenAIAPI(List<Map<String, String>> conversationHistory,
                                 HttpSession session) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        try {
            Map<String, Object> requestBodyMap = Map.of(
                    "model", "gpt-3.5-turbo",
                    "messages", conversationHistory,    // messages 파라미터에 대화 히스토리 전달
                    "max_tokens", 100,                  // 응답 시 사용할 토큰의 최대값
                    "temperature", 0.7,
                    "n", 1
            );
            String requestBody = objectMapper.writeValueAsString(requestBodyMap);

            logger.info("Generated JSON Request Body: {}", requestBody);

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            JsonNode responseJson = restTemplate.postForObject(
                    apiUrl,
                    requestEntity,
                    JsonNode.class
            );

            // 응답에서 텍스트 추출
            String aiResponse = extractApiResponseContent(responseJson);
            conversationHistory.add(Map.of(
                    "role", "assistant", "content", aiResponse)); // AI 응답 히스토리에 추가
            // 세션에 업데이트된 히스토리 저장
            session.setAttribute("conversationHistory", conversationHistory);

            return aiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error calling OpenAI API: " + e.getMessage();
        }
    }

    private List<Map<String, String>> getConversationHistory(HttpSession session) { // 세션에서 대화 히스토리 가져오는 메서드
        List<Map<String, String>> conversationHistory = (List<Map<String, String>>) session.getAttribute("conversationHistory");
        if (conversationHistory == null) {
            conversationHistory = new ArrayList<>(); // 세션에 히스토리가 없으면 새로 생성
            session.setAttribute("conversationHistory", conversationHistory); // 세션에 새로 생성된 히스토리 저장
        }
        return conversationHistory;
    }

    private String extractApiResponseContent(JsonNode responseJson) { // 응답에서 텍스트 추출 메서드
        if (responseJson != null && responseJson.has("choices")) {
            JsonNode choices = responseJson.get("choices");
            if (choices.isArray() && !choices.isEmpty()) {
                JsonNode firstChoice = choices.get(0);
                if (firstChoice.has("message") && firstChoice.get("message").has("content")) {
                    return firstChoice.get("message").get("content").asText().trim();
                }
            }
        }
        return "No response from OpenAI API";
    }

}
