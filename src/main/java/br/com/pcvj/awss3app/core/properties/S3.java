package br.com.pcvj.awss3app.core.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class S3 {
    @NotBlank
    String keyId;
    @NotBlank
    String secretKey;
    @NotBlank
    String bucketName;
    @NotBlank
    String region;
}
