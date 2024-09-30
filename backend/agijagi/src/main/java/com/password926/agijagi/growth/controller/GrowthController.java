package com.password926.agijagi.growth.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.growth.controller.dto.request.AppendGrowthRequest;
import com.password926.agijagi.growth.service.GrowthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/children")
@RequiredArgsConstructor
@RestController
public class GrowthController {

    private final GrowthService growthService;

    @Authenticate
    @PutMapping("{childId}/growth")
    public ResponseEntity<Void> putGrowth(
            LoginMember member,
            @PathVariable long childId,
            @RequestBody @Valid AppendGrowthRequest request
    ) {
        growthService.appendOrUpdateGrowth(member.getId(), childId, request.toContent());
        return ResponseEntity.ok().build();
    }
}
