package br.com.pcvj.awss3app.infra;

import br.com.pcvj.awss3app.core.properties.StorageProperties;
import br.com.pcvj.awss3app.domain.model.FileReference;
import br.com.pcvj.awss3app.domain.service.CloudStorageProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Component
@AllArgsConstructor
@Slf4j
public class S3CloudStorageProvider implements CloudStorageProvider {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final StorageProperties storageProperties;

    @Override
    public URL generatePresignedUploadUrl(FileReference fileReference) {
        var awsRequestOverrideConfiguration = AwsRequestOverrideConfiguration.builder();

        if (fileReference.isPublicAccessible()) {
            awsRequestOverrideConfiguration.putRawQueryParameter("x-amz-acl", "public-read");
        }

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(getBucketName())
                .key(fileReference.getPath())
                .contentType(fileReference.getContentType())
                .contentLength(fileReference.getContentLength())
                .acl(fileReference.isPublicAccessible() ? "public-read" : null)
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

    @Override
    public URL generatePresignedDownloadUrl(FileReference fileReference) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(getBucketName())
                .key(fileReference.getPath())
                .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(30))
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner
                .presignGetObject(getObjectPresignRequest)
                .url();
    }

    @Override
    public boolean fileExists(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            return false;
        }
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(getBucketName())
                .key(filePath)
                .build();

        try {
            s3Client.getObject(request);
            return true;
        } catch (NoSuchKeyException e) {
            log.warn("Arquivo {} não encontrado", filePath);
            return false;
        }
    }

    private String getBucketName() {
        return storageProperties.getS3().getBucketName();
    }
}
