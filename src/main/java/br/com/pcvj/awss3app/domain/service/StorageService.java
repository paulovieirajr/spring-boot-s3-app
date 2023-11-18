package br.com.pcvj.awss3app.domain.service;

import br.com.pcvj.awss3app.domain.exception.StorageCloudException;
import br.com.pcvj.awss3app.domain.model.FileReference;
import br.com.pcvj.awss3app.domain.repository.FileReferenceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class StorageService {

    private final CloudStorageProvider cloudStorageProvider;
    private FileReferenceRepository fileReferenceRepository;

    public UploadRequestResult generateUploadUrl(FileReference fileReference) {
        Objects.requireNonNull(fileReference);
        fileReferenceRepository.save(fileReference);
        var presignedUploadUrl = cloudStorageProvider.generatePresignedUploadUrl(fileReference);
        return new UploadRequestResult(fileReference.getId(), presignedUploadUrl.toString());
    }

    public DownloadRequestResult generateDownloadUrl(FileReference fileReference) {
        Objects.requireNonNull(fileReference);
        URL url = cloudStorageProvider.generatePresignedDownloadUrl(fileReference);
        return new DownloadRequestResult(url.toString());
    }

    public boolean fileExists(FileReference fileReference) {
        Objects.requireNonNull(fileReference);
        return this.cloudStorageProvider.fileExists(fileReference.getPath());
    }

    public void softDelete(FileReference fileReference) {
        Objects.requireNonNull(fileReference);
        this.cloudStorageProvider
                .moveFile(fileReference.getPath(), "deleted/" + fileReference.getPath());
    }

    @Scheduled(fixedDelay = 1800000)
    @Transactional
    public void deleteTempFiles() {
        var files = fileReferenceRepository
                .findAllByTempIsTrueAndCreatedAtBefore(OffsetDateTime.now().minus(Duration.ofDays(1)));

        for (FileReference file : files) {
            fileReferenceRepository.delete(file);
            fileReferenceRepository.flush();
            try {
                cloudStorageProvider.removeFile(file.getPath());
            } catch (StorageCloudException e) {
                log.warn("Erro ao remover arquivo temporário {}", file.getPath(), e);
            }
        }
    }

}
