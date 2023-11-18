package br.com.pcvj.awss3app.infra;

import br.com.pcvj.awss3app.core.properties.S3;
import br.com.pcvj.awss3app.core.properties.StorageProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    @Bean
    public S3Presigner s3Presigner(StorageProperties storageProperties) {
        S3 s3 = storageProperties.getS3();
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(s3.getKeyId(), s3.getSecretKey());
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

        return S3Presigner.builder()
                .region(Region.of(s3.getRegion()))
                .credentialsProvider(staticCredentialsProvider)
                .build();
    }

    @Bean
    public S3Client s3Client(StorageProperties storageProperties) {
        S3 s3 = storageProperties.getS3();
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(s3.getKeyId(), s3.getSecretKey());
        StaticCredentialsProvider staticCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

        return S3Client.builder()
                .region(Region.of(s3.getRegion()))
                .credentialsProvider(staticCredentialsProvider)
                .build();
    }
}
