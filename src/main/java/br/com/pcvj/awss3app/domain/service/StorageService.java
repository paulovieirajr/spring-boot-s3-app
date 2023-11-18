package br.com.pcvj.awss3app.domain.service;

import br.com.pcvj.awss3app.domain.model.FileReference;
import br.com.pcvj.awss3app.domain.repository.FileReferenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Objects;

@Service
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
}
