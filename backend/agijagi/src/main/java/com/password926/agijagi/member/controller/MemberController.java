package com.password926.agijagi.member.controller;

import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.member.controller.dto.request.ModifyMemberRequest;
import com.password926.agijagi.member.controller.dto.request.RegisterMemberRequest;
import com.password926.agijagi.member.domain.ProfileDetail;
import com.password926.agijagi.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Long> registerMember(@Valid @RequestBody RegisterMemberRequest request) {
        long memberId = memberService.registerMember(request.toMemberProfile(), request.getPassword());

        return ResponseEntity.created(URI.create("/members/" + memberId)).build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ProfileDetail> readProfileDetail(@PathVariable Long memberId) {
        ProfileDetail profileDetail = memberService.readMemberProfileDetail(memberId);

        return ResponseEntity.ok(profileDetail);
    }

    @PatchMapping
    public ResponseEntity<Void> modifyMember(LoginMember member, @Valid @RequestBody ModifyMemberRequest request) {
        memberService.modifyMemberProfileDetail(member.getId(), request.toMemberProfile());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(LoginMember member) {
        memberService.removeMember(member.getId());

        return ResponseEntity.ok().build();
    }
}
