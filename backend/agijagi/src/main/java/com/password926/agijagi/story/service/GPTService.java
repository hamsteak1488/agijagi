package com.password926.agijagi.story.service;

import com.password926.agijagi.story.controller.dto.CompletionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface GPTService {

    public List<Map<String, Object>> modelList();

    public Map<String, Object> prompt(CompletionRequest request);

    public Map<String, Object> isValidModel(String modelName);

}
