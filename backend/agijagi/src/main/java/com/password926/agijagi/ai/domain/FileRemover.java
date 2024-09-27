package com.password926.agijagi.ai.domain;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class FileRemover {

    public static void remove(File file) {
        if (file.exists()) {
            return;
        }
        if (!file.delete()) {
            log.error("임시 파일을 삭제하는데 실패했습니다: {}", file.getAbsolutePath());
        }
    }
}
