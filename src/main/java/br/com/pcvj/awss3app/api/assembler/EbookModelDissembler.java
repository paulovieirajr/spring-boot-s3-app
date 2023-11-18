package br.com.pcvj.awss3app.api.assembler;

import br.com.pcvj.awss3app.api.model.EbookRequest;
import br.com.pcvj.awss3app.domain.model.Ebook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EbookModelDissembler {

    public Ebook toDomain(EbookRequest request) {
        return Ebook.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .build();
    }

    public Ebook toDomain(EbookRequest request, UUID ebookId) {
        return Ebook.builder()
                .id(ebookId)
                .title(request.getTitle())
                .author(request.getAuthor())
                .build();
    }
}
