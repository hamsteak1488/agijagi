package com.password926.agijagi.diary.controller.dto;

import lombok.*;

import java.util.*;

@Getter
@AllArgsConstructor
public class DeleteDiaryRequest {

    private List<UUID> removeMediaIdList;
}
