package br.com.pcvj.awss3app.api.assembler;

import br.com.pcvj.awss3app.api.model.EbookRequest;
import br.com.pcvj.awss3app.domain.exception.BusinessException;
import br.com.pcvj.awss3app.domain.model.Ebook;
import br.com.pcvj.awss3app.domain.model.FileReference;
import br.com.pcvj.awss3app.domain.repository.FileReferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EbookModelDissembler {

    private final FileReferenceRepository fileReferenceRepository;

    public Ebook toDomain(EbookRequest request) {
        return Ebook.builder()
                .title(request.title())
                .author(request.author())
                .cover(findFileReferenceById(request.coverId(), "Arquivo de capa não encontrado %s"))
                .attachment(findFileReferenceById(request.attachmentId(), "Arquivo de anexo não encontrado %s"))
                .build();
    }

    public Ebook toDomain(EbookRequest request, UUID ebookId) {
        return Ebook.builder()
                .id(ebookId)
                .title(request.title())
                .author(request.author())
                .cover(findFileReferenceById(request.coverId(), "Arquivo de capa não encontrado %s"))
                .attachment(findFileReferenceById(request.attachmentId(), "Arquivo de anexo não encontrado %s"))
                .build();
    }

    private FileReference findFileReferenceById(UUID request, String format) {
        return fileReferenceRepository.findById(request)
                .orElseThrow(() -> new BusinessException(String.format(format, request)));
    }
}
