package br.com.pcvj.awss3app.infra;

import br.com.pcvj.awss3app.core.properties.StorageProperties;
import br.com.pcvj.awss3app.domain.model.FileReference;
import br.com.pcvj.awss3app.domain.service.CloudStorageProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Component
@AllArgsConstructor
public class S3CloudStorageProvider implements CloudStorageProvider {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final StorageProperties storageProperties;

    @Override
    public URL generatePresignedUploadUrl(FileReference fileReference) {
        var awsRequestOverrideConfiguration = AwsRequestOverrideConfiguration.builder();

        if (fileReference.isPublicAccess()) {
            awsRequestOverrideConfiguration.putRawQueryParameter("x-amz-acl", "public-read");
        }

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(getBucketName())
                .key(fileReference.getPath())
                .contentType(fileReference.getContentType())
                .contentLength(fileReference.getContentLength())
                .acl(fileReference.isPublicAccess() ? "public-read" : null)
                .overrideConfiguration(awsRequestOverrideConfiguration.build())
                .build();

        PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(30))
                .putObjectRequest(putObjectRequest)
                .build();

        return s3Presigner
                .presignPutObject(putObjectPresignRequest)
                .url();
    }

    private String getBucketName() {
        return storageProperties.getS3().getBucketName();
    }
}
