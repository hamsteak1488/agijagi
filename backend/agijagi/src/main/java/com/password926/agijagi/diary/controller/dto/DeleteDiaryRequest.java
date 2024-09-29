package com.password926.agijagi.diary.controller.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class DeleteDiaryRequest {

    private List<Long> removeMediaIdList;

}
