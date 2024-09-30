package com.password926.agijagi.ai.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public final class Base64ToFileConverter {

    private static final String FILE_PATH = "./tmp/";

    public static File convert(Base64Content base64Content) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Content.getBase64Data());
        try {
            Path tempFilePath = Files.createTempFile(Path.of(FILE_PATH), "uploaded-", "." + base64Content.getExtension());
            Files.write(tempFilePath, decodedBytes);
            return tempFilePath.toFile();
        } catch (IOException e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
