package br.com.pcvj.awss3app.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EbookRequest(
        @NotBlank
        String title,
        @NotBlank
        String author,
        @NotNull
        UUID coverId,
        @NotNull
        UUID attachmentId
) {
}
