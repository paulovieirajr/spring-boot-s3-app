package br.com.pcvj.awss3app.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EbookModel {
    private UUID id;
    private String title;
    private String author;
    private FileReferenceModel cover;
    private FileReferenceModel attachment;
}
