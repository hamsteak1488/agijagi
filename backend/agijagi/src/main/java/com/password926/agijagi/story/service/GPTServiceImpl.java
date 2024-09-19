package com.password926.agijagi.story.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.password926.agijagi.story.config.GPTConfig;
import com.password926.agijagi.story.controller.dto.CompletionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class GPTServiceImpl implements GPTService {

    private final GPTConfig gptConfig;
    private final ObjectMapper objectMapper; // ObjectMapper를 의존성 주입으로 사용

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api-url}")
    private String apiUrl; // API URL을 설정 파일에서 가져오도록 변경

    @Override
    public List<Map<String, Object>> modelList() {
        log.debug("[+] 모델 리스트를 조회합니다.");

        HttpHeaders headers = gptConfig.httpHeaders();

        ResponseEntity<String> response = gptConfig.restTemplate()
                .exchange(
                        apiUrl + "/models", // 하드코딩 대신 apiUrl 사용
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        String.class);

        try {
            Map<String, Object> responseData = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
            return (List<Map<String, Object>>) responseData.get("data");
        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON response: {}", e.getMessage());
            throw new RuntimeException("Failed to parse model list response", e);
        }
    }

    @Override
    public Map<String, Object> isValidModel(String modelName) {
        log.debug("[+] 모델이 유효한지 조회합니다. 모델: {}", modelName);

        HttpHeaders headers = gptConfig.httpHeaders();

        ResponseEntity<String> response = gptConfig.restTemplate()
                .exchange(
                        apiUrl + "/models/" + modelName, // 하드코딩 대신 apiUrl 사용
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        String.class);

        try {
            return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON response: {}", e.getMessage());
            throw new RuntimeException("Failed to parse model validation response", e);
        }
    }

    @Override
    public Map<String, Object> prompt(CompletionRequest completionRequest) {
        log.debug("[+] 프롬프트를 수행합니다.");

        HttpHeaders headers = gptConfig.httpHeaders();

        completionRequest.setModel(model);
        completionRequest.setTemperature(0.8f);

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(completionRequest);
        } catch (JsonProcessingException e) {
            log.error("Error creating JSON request body: {}", e.getMessage());
            throw new RuntimeException("Failed to create JSON request body", e);
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = gptConfig.restTemplate()
                .exchange(
                        apiUrl + "/completions", // 하드코딩 대신 apiUrl 사용
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        try {
            return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON response: {}", e.getMessage());
            throw new RuntimeException("Failed to parse prompt response", e);
        }
    }
}
