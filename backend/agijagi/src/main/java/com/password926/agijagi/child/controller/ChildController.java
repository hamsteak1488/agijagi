package com.password926.agijagi.child.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.child.controller.dto.ChildDtoConverter;
import com.password926.agijagi.child.controller.dto.request.AppendChildRequest;
import com.password926.agijagi.child.controller.dto.request.AppendFollowerRequest;
import com.password926.agijagi.child.controller.dto.request.UpdateChildRequest;
import com.password926.agijagi.child.controller.dto.request.UpdateFollowerRequest;
import com.password926.agijagi.child.controller.dto.response.CreateInvitationCodeRequest;
import com.password926.agijagi.child.controller.dto.response.ReadChildDetailResponse;
import com.password926.agijagi.child.controller.dto.response.ReadFollowerResponse;
import com.password926.agijagi.child.domain.Authority;
import com.password926.agijagi.child.service.ChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/children")
@RequiredArgsConstructor
@RestController
public class ChildController {

    private final ChildService childService;

    @Authenticate
    @GetMapping("/{childId}")
    public ResponseEntity<ReadChildDetailResponse> readChildDetail(
            LoginMember member,
            @PathVariable long childId
    ) {
        return ResponseEntity.ok().body(ReadChildDetailResponse.from(childService.readChildDetail(member.getId(), childId)));
    }

    @Authenticate
    @GetMapping
    public ResponseEntity<List<ReadChildDetailResponse>> readChildDetails(LoginMember member) {
        return ResponseEntity.ok().body(ChildDtoConverter.convert(childService.readChildDetailsByMember(member.getId())));
    }

    @Authenticate
    @GetMapping("/{childId}/followers")
    public ResponseEntity<List<ReadFollowerResponse>> readFollowers(
            LoginMember member,
            @PathVariable long childId
    ) {
        return ResponseEntity.ok().body(ChildDtoConverter.convertFollowers(childService.readFollowers(member.getId(), childId)));
    }

    @Authenticate
    @PostMapping
    public ResponseEntity<Void> appendChild(
            LoginMember member,
            @Valid AppendChildRequest request
    ) {
        childService.appendChild(member.getId(), request.toContent(), request.getImage());
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @PostMapping("/followers")
    public ResponseEntity<Void> appendFollower(
            LoginMember member,
            @RequestBody @Valid AppendFollowerRequest request
    ) {
        childService.appendFollower(member.getId(), request.getInvitationCode());
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @PostMapping("/{childId}/invitation-code")
    public ResponseEntity<CreateInvitationCodeRequest> createInvitationCode(
            LoginMember member,
            @PathVariable long childId
    ) {
        return ResponseEntity.ok().body(CreateInvitationCodeRequest.of(childService.createInvitationCode(member.getId(), childId)));
    }

    @Authenticate
    @PatchMapping("/{childId}")
    public ResponseEntity<Void> updateChild(
            LoginMember member,
            @PathVariable long childId,
            @RequestBody @Valid UpdateChildRequest request
    ) {
        childService.updateChild(member.getId(), childId, request.toContent());
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @PatchMapping("/{childId}/image")
    public ResponseEntity<Void> updateChildImage(
            LoginMember member,
            @PathVariable long childId,
            @RequestPart MultipartFile image
    ) {
        childService.updateChildImage(member.getId(), childId, image);
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @PatchMapping("/{childId}/followers")
    public ResponseEntity<Void> updateFollower(
            LoginMember member,
            @PathVariable long childId,
            @RequestBody @Valid UpdateFollowerRequest request
    ) {
        childService.updateFollower(member.getId(), childId, request.getFollowerId(), Authority.of(request.getAuthority()));
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @DeleteMapping("/{childId}")
    public ResponseEntity<Void> removeChild(
            LoginMember member,
            @PathVariable long childId
    ) {
        childService.removeChild(member.getId(), childId);
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @DeleteMapping("/{childId}/image")
    public ResponseEntity<Void> removeChildImage(
            LoginMember member,
            @PathVariable long childId
    ) {
        childService.removeChildImage(member.getId(), childId);
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @DeleteMapping("/{childId}/followers/{followerId}")
    public ResponseEntity<Void> removeFollower(
            LoginMember member,
            @PathVariable long childId,
            @PathVariable long followerId
    ) {
        childService.removeFollower(member.getId(), childId, followerId);
        return ResponseEntity.ok().build();
    }
}
