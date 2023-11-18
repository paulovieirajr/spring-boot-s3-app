package br.com.pcvj.awss3app.domain.service;

import java.util.UUID;

public record UploadRequestResult(
        UUID fileReferenceId,
        String uploadSignedUrl
) {
}
