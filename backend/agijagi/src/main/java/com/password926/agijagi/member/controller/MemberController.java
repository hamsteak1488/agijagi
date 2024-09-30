package com.password926.agijagi.member.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.member.controller.dto.request.ModifyMemberRequest;
import com.password926.agijagi.member.controller.dto.request.RegisterMemberRequest;
import com.password926.agijagi.member.domain.ProfileDetail;
import com.password926.agijagi.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> registerMember(@Valid @RequestBody RegisterMemberRequest request) {
        long memberId = memberService.registerMember(
                ProfileDetail.of(request.getEmail(), request.getPassword()),
                request.getPassword()
        );

        return ResponseEntity.created(URI.create("/members/" + memberId)).build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ProfileDetail> getProfileDetail(@PathVariable Long memberId) {
        ProfileDetail profileDetail = memberService.getMemberProfileDetail(memberId);

        return ResponseEntity.ok(profileDetail);
    }

    @Authenticate
    @PatchMapping
    public ResponseEntity<Void> modifyMemberProfileDetail(
            LoginMember member,
            @Valid @RequestBody ModifyMemberRequest request
    ) {
        memberService.modifyMemberProfileDetail(member.getId(), ProfileDetail.of(request.getEmail(), request.getNickname()));

        return ResponseEntity.ok().build();
    }

    @Authenticate
    @PatchMapping("/profile-image")
    public ResponseEntity<Void> modifyMemberProfileImage(
            LoginMember member,
            @RequestPart("profileImage") MultipartFile profileImage
    ) {
        memberService.modifyMemberProfileImage(
                member.getId(), MediaResource.from(profileImage)
        );

        return ResponseEntity.ok().build();
    }

    @Authenticate
    @DeleteMapping
    public ResponseEntity<Void> deleteMember(LoginMember member) {
        memberService.removeMember(member.getId());

        return ResponseEntity.ok().build();
    }
}
