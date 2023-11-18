package br.com.pcvj.awss3app.api.controller;

import br.com.pcvj.awss3app.api.model.UploadDocumentRequest;
import br.com.pcvj.awss3app.api.model.UploadImageRequest;
import br.com.pcvj.awss3app.domain.model.FileReference;
import br.com.pcvj.awss3app.domain.service.StorageService;
import br.com.pcvj.awss3app.domain.service.UploadRequestResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/uploads")
@AllArgsConstructor
public class UploadController {

    private final StorageService storageService;

    @PostMapping(value = "/documents", consumes = "multipart/form-data")
    public UploadRequestResult newDocumentUploadMultiPart(
            @RequestParam("file") MultipartFile file
    ) {
        var request = mapToUploadDocumentRequest(file);
        return storageService.generateUploadUrl(request.toDomain());
    }

    private UploadDocumentRequest mapToUploadDocumentRequest(MultipartFile file) {
        var contentType = file.getContentType();
        var fileName = file.getOriginalFilename();
        var contentLength = file.getSize();
        return new UploadDocumentRequest(fileName, contentType, contentLength);
    }

    @PostMapping(value = "/images", consumes = "multipart/form-data")
    public UploadRequestResult newImageUploadMultiPart(
            @RequestParam("file") MultipartFile file
    ) {
        var request = mapToUploadImageRequest(file);
        return storageService.generateUploadUrl(request.toDomain());
    }

    private UploadImageRequest mapToUploadImageRequest(MultipartFile file) {
        var contentType = file.getContentType();
        var fileName = file.getOriginalFilename();
        var contentLength = file.getSize();
        return new UploadImageRequest(fileName, contentType, contentLength);
    }
}
