package br.com.pcvj.awss3app.domain.service;


import br.com.pcvj.awss3app.domain.model.Ebook;
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
    private final FileVerificator fileVerificator;

    @Transactional
    public Ebook create(Ebook ebook) {
        Objects.requireNonNull(ebook);
        fileVerificator.verifyIfCoverExists(ebook);
        fileVerificator.verifyIfAttachmentExists(ebook);
        setTempToFalse(ebook);
        ebookRepository.save(ebook);
        return ebook;
    }

    static void setTempToFalse(Ebook ebook) {
        ebook.getCover().setTemp(false);
        ebook.getAttachment().setTemp(false);
    }

    @Transactional
    public Ebook update(Ebook ebookUpdated) {
        Objects.requireNonNull(ebookUpdated);

        Ebook existingEbook = ebookRepository.findById(ebookUpdated.getId()).orElseThrow(EntityNotFoundException::new);

        existingEbook.update(ebookUpdated);
        ebookRepository.save(existingEbook);
        ebookRepository.flush();

        return existingEbook;
    }
}
