package com.password926.agijagi.member.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.member.controller.dto.request.ModifyMemberRequest;
import com.password926.agijagi.member.controller.dto.request.RegisterMemberRequest;
import com.password926.agijagi.member.domain.MemberDetail;
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
    public ResponseEntity<Void> registerMember(@Valid RegisterMemberRequest request) {
        MediaResource mediaResource = request.getProfileImage() != null
                ? MediaResource.from(request.getProfileImage()) : null;

        long memberId = memberService.registerMember(
                request.getEmail(),
                request.getPassword(),
                request.getNickname(),
                mediaResource
        );

        return ResponseEntity.created(URI.create("/members/" + memberId)).build();
    }

    @Authenticate
    @PatchMapping
    public ResponseEntity<Void> updateMember(
            LoginMember member,
            @Valid @RequestBody ModifyMemberRequest request
    ) {
        memberService.updateMember(member.getId(), request.getEmail(), request.getPassword(), request.getNickname());

        return ResponseEntity.ok().build();
    }

    @Authenticate
    @PostMapping("/profile-image/update")
    public ResponseEntity<Void> updateMemberProfileImage(
            LoginMember member,
            @RequestPart MultipartFile profileImage
    ) {
        memberService.updateMemberProfileImage(member.getId(), MediaResource.from(profileImage));

        return ResponseEntity.ok().build();
    }

    @Authenticate
    @PostMapping("/profile-image/delete")
    public ResponseEntity<Void> deleteMemberProfileImage(
            LoginMember member
    ) {
        memberService.deleteMemberProfileImage(member.getId());

        return ResponseEntity.ok().build();
    }

    @Authenticate
    @DeleteMapping
    public ResponseEntity<Void> deleteMember(LoginMember member) {
        memberService.removeMember(member.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDetail> getProfileDetail(@PathVariable Long memberId) {
        MemberDetail memberDetail = memberService.getMemberDetail(memberId);

        return ResponseEntity.ok(memberDetail);
    }

    @Authenticate
    @GetMapping
    public ResponseEntity<MemberDetail> getSessionProfileDetail(LoginMember member) {
        MemberDetail memberDetail = memberService.getMemberDetail(member.getId());

        return ResponseEntity.ok(memberDetail);
    }
}
