package br.com.pcvj.awss3app.api.model;

import br.com.pcvj.awss3app.domain.model.FileReference;
import br.com.pcvj.awss3app.validation.AllowedContentTypes;
import br.com.pcvj.awss3app.validation.AllowedFileExtensions;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UploadImageRequest(

        @NotBlank
        @AllowedFileExtensions({"png", "jpg"})
        String fileName,

        @NotBlank
        @AllowedContentTypes({"image/png", "image/jpeg"})
        String contentType,

        @NotNull
        @Min(1)
        Long contentLength
) {
    public FileReference toDomain() {
        return FileReference.builder()
                .fileName(fileName)
                .contentType(contentType)
                .contentLength(contentLength)
                .type(FileReference.Type.IMAGE)
                .build();
    }
}
