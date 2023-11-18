package br.com.pcvj.awss3app.api.controller;

import br.com.pcvj.awss3app.domain.repository.FileReferenceRepository;
import br.com.pcvj.awss3app.domain.service.StorageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/downloads")
@AllArgsConstructor
public class DownloadController {

    private final FileReferenceRepository fileReferenceRepository;
    private final StorageService storageService;

    @GetMapping("/{fileReferenceId}/{fileName}")
    public ResponseEntity<Void> downloadRequest(@PathVariable UUID fileReferenceId,
                                                @PathVariable String fileName) {
        var fileReference = fileReferenceRepository.findById(fileReferenceId)
                .orElseThrow(EntityNotFoundException::new);

        if (fileReference.isPublicAccessible()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var requestResult = storageService.generateDownloadUrl(fileReference);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", requestResult.downloadSignedUrl());
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

}
