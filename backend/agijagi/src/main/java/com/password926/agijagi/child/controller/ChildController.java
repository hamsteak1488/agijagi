package com.password926.agijagi.child.controller;

import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.child.controller.dto.ChildDtoConverter;
import com.password926.agijagi.child.controller.dto.request.AppendChildRequest;
import com.password926.agijagi.child.controller.dto.request.UpdateChildRequest;
import com.password926.agijagi.child.controller.dto.response.ReadChildDetailResponse;
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

    @GetMapping("/{childId}")
    public ResponseEntity<ReadChildDetailResponse> readChildDetail(
            LoginMember member,
            @PathVariable long childId
    ) {
        return ResponseEntity.ok().body(ReadChildDetailResponse.from(childService.readChildDetail(member.getId(), childId)));
    }

    @GetMapping
    public ResponseEntity<List<ReadChildDetailResponse>> readChildDetails(LoginMember member) {
        return ResponseEntity.ok().body(ChildDtoConverter.convert(childService.readChildDetailsByMember(member.getId())));
    }

    @PostMapping
    public ResponseEntity<Void> appendChild(
            LoginMember member,
            @Valid AppendChildRequest request
    ) {
        childService.appendChild(member.getId(), request.toContent(), request.getImage());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{childId}")
    public ResponseEntity<Void> removeChild(
            LoginMember member,
            @PathVariable long childId
    ) {
        childService.removeChild(member.getId(), childId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{childId}")
    public ResponseEntity<Void> updateChild(
            LoginMember member,
            @PathVariable long childId,
            @RequestBody @Valid UpdateChildRequest request
    ) {
        childService.updateChild(member.getId(), childId, request.toContent());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{childId}/image")
    public ResponseEntity<Void> updateChildImage(
            LoginMember member,
            @PathVariable long childId,
            @RequestPart MultipartFile image
    ) {
        childService.updateChildImage(member.getId(), childId, image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{childId}/image")
    public ResponseEntity<Void> deleteChildImage(
            LoginMember member,
            @PathVariable long childId
    ) {
        childService.removeChildImage(member.getId(), childId);
        return ResponseEntity.ok().build();
    }
}
