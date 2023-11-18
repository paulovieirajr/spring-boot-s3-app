package br.com.pcvj.awss3app.api.assembler;

import br.com.pcvj.awss3app.api.model.EbookModel;
import br.com.pcvj.awss3app.api.model.FileReferenceModel;
import br.com.pcvj.awss3app.core.properties.StorageProperties;
import br.com.pcvj.awss3app.domain.model.Ebook;
import br.com.pcvj.awss3app.domain.model.FileReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EbookModelAssembler {

    private final StorageProperties storageProperties;

    public EbookModel toModel(Ebook ebook) {
        EbookModel.EbookModelBuilder builder = EbookModel.builder()
                .id(ebook.getId())
                .author(ebook.getAuthor())
                .title(ebook.getTitle());

        if(ebook.getCover() != null) {
            builder.cover(createCover(ebook.getCover()));
        }

        if(ebook.getAttachment() != null) {
            builder.attachment(createAttachment(ebook.getAttachment()));
        }

        return builder.build();
    }

    private FileReferenceModel createCover(FileReference cover) {
        String downloadUrl = storageProperties.getImage().getDownloadUrl().toString() + "/" + cover.getPath();
        return FileReferenceModel.builder()
                .id(cover.getId())
                .name(cover.getFileName())
                .contentType(cover.getContentType())
                .contentLength(cover.getContentLength())
                .publicAccessible(cover.isPublicAccessible())
                .downloadUrl(downloadUrl)
                .build();
    }

    private FileReferenceModel createAttachment(FileReference attachment) {
        String downloadUrl = storageProperties.getDocument().getDownloadUrl().toString() + "/" + attachment.getPath();
        return FileReferenceModel.builder()
                .id(attachment.getId())
                .name(attachment.getFileName())
                .contentType(attachment.getContentType())
                .contentLength(attachment.getContentLength())
                .publicAccessible(attachment.isPublicAccessible())
                .downloadUrl(downloadUrl)
                .build();
    }
}
