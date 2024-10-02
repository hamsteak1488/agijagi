package com.password926.agijagi.story.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.story.controller.dto.CreateStoryRequest;
import com.password926.agijagi.story.controller.dto.DeleteStoryRequest;
import com.password926.agijagi.story.entity.StoryPageDetail;
import com.password926.agijagi.story.service.StoryService;
import com.password926.agijagi.story.entity.StoryDetail;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/stories")
@RestController
public class StoryController {

    private final StoryService storyService;

    @Authenticate
    @PostMapping
    public ResponseEntity<Void> createStory(
            LoginMember member,
            CreateStoryRequest createStoryRequest
    ) {
        storyService.createStory(member.getId(), createStoryRequest);
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @GetMapping
    public ResponseEntity<List<StoryDetail>> getAllStory(
            LoginMember member,
            @RequestParam("childId") long childId
    ) {
        return ResponseEntity.ok().body(storyService.getAllStory(member.getId(), childId));
    }

    @Authenticate
    @GetMapping("/{storyId}")
    public ResponseEntity<StoryDetail> getStory(
            LoginMember member,
            @PathVariable long storyId
    ) {
        return ResponseEntity.ok().body(storyService.getStory(member.getId(), storyId));
    }

    @Authenticate
    @GetMapping("/{storyId}/pages")
    public ResponseEntity<List<StoryPageDetail>> getStoryAllPage(
            LoginMember member,
            @PathVariable long storyId
    ) {
        return ResponseEntity.ok().body(storyService.getStoryAllPage(member.getId(), storyId));
    }

    @Authenticate
    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(
            LoginMember member,
            @PathVariable long storyId,
            DeleteStoryRequest deleteStoryRequest
    ) {
        storyService.deleteStory(member.getId(), storyId, deleteStoryRequest);
        return ResponseEntity.ok().build();
    }
}
