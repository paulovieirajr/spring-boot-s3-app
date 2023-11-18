package br.com.pcvj.awss3app.core.properties;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties("aws.storage")
public class StorageProperties {

    @Valid
    private S3 s3 = new S3();
}
