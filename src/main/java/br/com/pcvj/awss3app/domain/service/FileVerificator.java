package br.com.pcvj.awss3app.domain.service;

import br.com.pcvj.awss3app.domain.exception.BusinessException;
import br.com.pcvj.awss3app.domain.model.Ebook;
import br.com.pcvj.awss3app.domain.model.FileReference;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FileVerificator {
    
    private final StorageService storageService;
    
    void verifyIfCoverExists(Ebook ebook) {
        if (!storageService.fileExists(ebook.getCover())) {
            throw new BusinessException(String.format("Arquivo %s não encontrado", ebook.getCover().getId()));
        }

        if (!FileReference.Type.IMAGE.equals(ebook.getCover().getType())) {
            throw new BusinessException(String.format("O arquivo de capa %s deve ser uma imagem", ebook.getCover().getId()));
        }
    }

    void verifyIfAttachmentExists(Ebook ebook) {
        if (!storageService.fileExists(ebook.getAttachment())) {
            throw new BusinessException(String.format("Arquivo %s não encontrado", ebook.getAttachment().getId()));
        }

        if (!FileReference.Type.DOCUMENT.equals(ebook.getAttachment().getType())) {
            throw new BusinessException(String.format("O arquivo de anexo %s deve ser um documento", ebook.getAttachment().getId()));
        }
    }
}