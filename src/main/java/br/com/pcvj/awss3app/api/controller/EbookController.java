package br.com.pcvj.awss3app.api.controller;


import br.com.pcvj.awss3app.api.assembler.EbookModelAssembler;
import br.com.pcvj.awss3app.api.assembler.EbookModelDissembler;
import br.com.pcvj.awss3app.api.model.EbookModel;
import br.com.pcvj.awss3app.api.model.EbookRequest;
import br.com.pcvj.awss3app.domain.model.Ebook;
import br.com.pcvj.awss3app.domain.repository.EbookRepository;
import br.com.pcvj.awss3app.domain.service.EbookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ebooks")
@RequiredArgsConstructor
public class EbookController {

    private final EbookService ebookService;
    private final EbookRepository ebookRepository;
    private final EbookModelAssembler ebookModelAssembler;
    private final EbookModelDissembler ebookModelDissembler;

    @PostMapping
    public EbookModel create(@RequestBody @Valid EbookRequest request) {
        Ebook ebook = ebookModelDissembler.toDomain(request);
        return ebookModelAssembler.toModel(ebookService.create(ebook));
    }

    @GetMapping("{ebookId}")
    public EbookModel getById(@PathVariable UUID ebookId) {
        return ebookModelAssembler.toModel(ebookRepository.findById(ebookId).orElseThrow(EntityNotFoundException::new));
    }

    @PutMapping("{ebookId}")
    public EbookModel atualizar(@PathVariable UUID ebookId,
                                @RequestBody @Valid EbookRequest request) {
        Ebook ebook = ebookModelDissembler.toDomain(request, ebookId);
        return  ebookModelAssembler.toModel(ebookService.update(ebook));
    }

    @GetMapping
    public List<EbookModel> list() {
        return ebookRepository.findAll()
                .stream()
                .map(ebookModelAssembler::toModel)
                .toList();
    }
}
