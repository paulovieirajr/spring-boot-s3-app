package br.com.pcvj.awss3app.domain.repository;

import br.com.pcvj.awss3app.domain.model.FileReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileReferenceRepository extends JpaRepository<FileReference, UUID> {
}