package br.com.pcvj.awss3app.domain.service;

import br.com.pcvj.awss3app.domain.model.FileReference;

import java.net.URL;

public interface CloudStorageProvider {

    URL generatePresignedUploadUrl(FileReference fileReference);
}