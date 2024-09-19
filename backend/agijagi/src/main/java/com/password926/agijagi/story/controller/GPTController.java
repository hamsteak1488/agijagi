package com.password926.agijagi.story.controller;

import com.password926.agijagi.story.controller.dto.CompletionRequest;
import com.password926.agijagi.story.service.GPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/chatGpt")
@RestController
public class GPTController {

    private final GPTService GPTService;

    @GetMapping("/modelList")
    public ResponseEntity<List<Map<String, Object>>> selectModelList() {
        List<Map<String, Object>> result = GPTService.modelList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/model")
    public ResponseEntity<Map<String, Object>> isValidModel(@RequestParam(name = "modelName") String modelName) {
        Map<String, Object> result = GPTService.isValidModel(modelName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/prompt")
    public ResponseEntity<Map<String, Object>> selectPrompt(@RequestBody CompletionRequest completionRequest) {
        Map<String, Object> result = GPTService.prompt(completionRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}