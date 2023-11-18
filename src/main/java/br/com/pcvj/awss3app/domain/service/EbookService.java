package br.com.pcvj.awss3app.domain.service;


import br.com.pcvj.awss3app.domain.exception.BusinessException;
import br.com.pcvj.awss3app.domain.model.Ebook;
import br.com.pcvj.awss3app.domain.model.FileReference;
import br.com.pcvj.awss3app.domain.repository.EbookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EbookService {

    private final EbookRepository ebookRepository;
    private final StorageService storageService;

    @Transactional
    public Ebook create(Ebook ebook) {
        Objects.requireNonNull(ebook);
        validateFile(ebook);
        setTempToFalse(ebook);
        ebookRepository.save(ebook);
        return ebook;
    }

    private void setTempToFalse(Ebook ebook) {
        ebook.getCover().setTemp(false);
        ebook.getAttachment().setTemp(false);
    }

    @Transactional
    public Ebook update(Ebook ebookUpdated) {
        Objects.requireNonNull(ebookUpdated);
        validateFile(ebookUpdated);
        Ebook existingEbook = ebookRepository.findById(ebookUpdated.getId()).orElseThrow(EntityNotFoundException::new);

        if(!existingEbook.getCover().equals(ebookUpdated.getCover())) {
            ebookUpdated.getCover().setTemp(false);
            storageService.softDelete(existingEbook.getCover());
        }

        if(!existingEbook.getAttachment().equals(ebookUpdated.getAttachment())) {
            ebookUpdated.getAttachment().setTemp(false);
            storageService.softDelete(existingEbook.getAttachment());
        }

        existingEbook.update(ebookUpdated);
        ebookRepository.save(existingEbook);
        ebookRepository.flush();

        return existingEbook;
    }

    private void validateFile(Ebook ebook) {
        if (!storageService.fileExists(ebook.getCover())) {
            throw new BusinessException(String.format("Arquivo %s não encontrado", ebook.getCover().getId()));
        }

        if (!FileReference.Type.IMAGE.equals(ebook.getCover().getType())) {
            throw new BusinessException(String.format("O arquivo de capa %s deve ser uma imagem", ebook.getCover().getId()));
        }

        if (!storageService.fileExists(ebook.getAttachment())) {
            throw new BusinessException(String.format("Arquivo %s não encontrado", ebook.getAttachment().getId()));
        }

        if (!FileReference.Type.DOCUMENT.equals(ebook.getAttachment().getType())) {
            throw new BusinessException(String.format("O arquivo de anexo %s deve ser um documento", ebook.getAttachment().getId()));
        }
    }
}
