package br.com.pcvj.awss3app.api.assembler;

import br.com.pcvj.awss3app.api.model.EbookModel;
import br.com.pcvj.awss3app.domain.model.Ebook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EbookModelAssembler {

    public EbookModel toModel(Ebook ebook) {
        EbookModel.EbookModelBuilder builder = EbookModel.builder()
                .id(ebook.getId())
                .author(ebook.getAuthor())
                .title(ebook.getTitle());
        return builder.build();
    }
}
