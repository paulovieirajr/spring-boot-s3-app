package br.com.pcvj.awss3app.domain.repository;


import br.com.pcvj.awss3app.domain.model.Ebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EbookRepository extends JpaRepository<Ebook, UUID> {
}
