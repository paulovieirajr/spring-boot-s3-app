package br.com.pcvj.awss3app.domain.service;


import br.com.pcvj.awss3app.domain.model.Ebook;
import br.com.pcvj.awss3app.domain.repository.EbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EbookService {

    private final EbookRepository ebookRepository;

    @Transactional
    public Ebook create(Ebook ebook) {
        Objects.requireNonNull(ebook);

        ebookRepository.save(ebook);

        return ebook;
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
