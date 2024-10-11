package com.password926.agijagi.media.infrastructure;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.media.domain.MediaResource;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class S3Manager {
    private final S3Operations s3Operations;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public String upload(String key, MediaResource mediaResource) {
        try (InputStream is = mediaResource.getResource().getInputStream()) {
            S3Resource s3Resource = s3Operations.upload(bucket, key, is
                    , ObjectMetadata.builder().contentType(mediaResource.getContentType()).build());
            return s3Resource.getURL().toString();
        } catch (IOException ex) {
            // TODO: 적절한 예외로 추상화하기
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
