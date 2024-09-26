package com.password926.agijagi.child.controller;

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
            long memberId,
            @PathVariable long childId
    ) {
        return ResponseEntity.ok().body(ReadChildDetailResponse.from(childService.readChildDetail(memberId, childId)));
    }

    @GetMapping
    public ResponseEntity<List<ReadChildDetailResponse>> readChildDetails(long memberId) {
        return ResponseEntity.ok().body(ChildDtoConverter.convert(childService.readChildDetailsByMember(memberId)));
    }

    @PostMapping
    public ResponseEntity<Void> appendChild(
            long memberId,
            @Valid AppendChildRequest request
    ) {
        childService.appendChild(memberId, request.toContent(), request.getImage());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{childId}")
    public ResponseEntity<Void> removeChild(
            long memberId,
            @PathVariable long childId
    ) {
        childService.removeChild(memberId, childId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{childId}")
    public ResponseEntity<Void> updateChild(
            long memberId,
            @PathVariable long childId,
            @RequestBody @Valid UpdateChildRequest request
    ) {
        childService.updateChild(memberId, childId, request.toContent());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{childId}/image")
    public ResponseEntity<Void> updateChildImage(
            long memberId,
            @PathVariable long childId,
            @RequestPart MultipartFile image
    ) {
        childService.updateChildImage(memberId, childId, image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{childId}/image")
    public ResponseEntity<Void> deleteChildImage(
            long memberId,
            @PathVariable long childId
    ) {
        childService.removeChildImage(memberId, childId);
        return ResponseEntity.ok().build();
    }
}
