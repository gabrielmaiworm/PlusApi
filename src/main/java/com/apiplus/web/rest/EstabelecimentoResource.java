package com.apiplus.web.rest;

import com.apiplus.domain.Estabelecimento;
import com.apiplus.repository.EstabelecimentoRepository;
import com.apiplus.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.apiplus.domain.Estabelecimento}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstabelecimentoResource {

    private final Logger log = LoggerFactory.getLogger(EstabelecimentoResource.class);

    private static final String ENTITY_NAME = "estabelecimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstabelecimentoRepository estabelecimentoRepository;

    public EstabelecimentoResource(EstabelecimentoRepository estabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    /**
     * {@code POST  /estabelecimentos} : Create a new estabelecimento.
     *
     * @param estabelecimento the estabelecimento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estabelecimento, or with status {@code 400 (Bad Request)} if the estabelecimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estabelecimentos")
    public ResponseEntity<Estabelecimento> createEstabelecimento(@RequestBody Estabelecimento estabelecimento) throws URISyntaxException {
        log.debug("REST request to save Estabelecimento : {}", estabelecimento);
        if (estabelecimento.getId() != null) {
            throw new BadRequestAlertException("A new estabelecimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Estabelecimento result = estabelecimentoRepository.save(estabelecimento);
        return ResponseEntity
            .created(new URI("/api/estabelecimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estabelecimentos/:id} : Updates an existing estabelecimento.
     *
     * @param id the id of the estabelecimento to save.
     * @param estabelecimento the estabelecimento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estabelecimento,
     * or with status {@code 400 (Bad Request)} if the estabelecimento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estabelecimento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estabelecimentos/{id}")
    public ResponseEntity<Estabelecimento> updateEstabelecimento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Estabelecimento estabelecimento
    ) throws URISyntaxException {
        log.debug("REST request to update Estabelecimento : {}, {}", id, estabelecimento);
        if (estabelecimento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estabelecimento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estabelecimentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Estabelecimento result = estabelecimentoRepository.save(estabelecimento);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estabelecimento.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estabelecimentos/:id} : Partial updates given fields of an existing estabelecimento, field will ignore if it is null
     *
     * @param id the id of the estabelecimento to save.
     * @param estabelecimento the estabelecimento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estabelecimento,
     * or with status {@code 400 (Bad Request)} if the estabelecimento is not valid,
     * or with status {@code 404 (Not Found)} if the estabelecimento is not found,
     * or with status {@code 500 (Internal Server Error)} if the estabelecimento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estabelecimentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Estabelecimento> partialUpdateEstabelecimento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Estabelecimento estabelecimento
    ) throws URISyntaxException {
        log.debug("REST request to partial update Estabelecimento partially : {}, {}", id, estabelecimento);
        if (estabelecimento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estabelecimento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estabelecimentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Estabelecimento> result = estabelecimentoRepository
            .findById(estabelecimento.getId())
            .map(existingEstabelecimento -> {
                if (estabelecimento.getEndereco() != null) {
                    existingEstabelecimento.setEndereco(estabelecimento.getEndereco());
                }
                if (estabelecimento.getNome() != null) {
                    existingEstabelecimento.setNome(estabelecimento.getNome());
                }
                if (estabelecimento.getTelefone() != null) {
                    existingEstabelecimento.setTelefone(estabelecimento.getTelefone());
                }
                if (estabelecimento.getCategoria() != null) {
                    existingEstabelecimento.setCategoria(estabelecimento.getCategoria());
                }
                if (estabelecimento.getNotaMedia() != null) {
                    existingEstabelecimento.setNotaMedia(estabelecimento.getNotaMedia());
                }
                if (estabelecimento.getLocalizacao() != null) {
                    existingEstabelecimento.setLocalizacao(estabelecimento.getLocalizacao());
                }
                if (estabelecimento.getImagem() != null) {
                    existingEstabelecimento.setImagem(estabelecimento.getImagem());
                }
                if (estabelecimento.getImagemContentType() != null) {
                    existingEstabelecimento.setImagemContentType(estabelecimento.getImagemContentType());
                }
                if (estabelecimento.getPrecoFilter() != null) {
                    existingEstabelecimento.setPrecoFilter(estabelecimento.getPrecoFilter());
                }
                if (estabelecimento.getPlaceId() != null) {
                    existingEstabelecimento.setPlaceId(estabelecimento.getPlaceId());
                }

                return existingEstabelecimento;
            })
            .map(estabelecimentoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estabelecimento.getId().toString())
        );
    }

    /**
     * {@code GET  /estabelecimentos} : get all the estabelecimentos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estabelecimentos in body.
     */
    @GetMapping("/estabelecimentos")
    public List<Estabelecimento> getAllEstabelecimentos() {
        log.debug("REST request to get all Estabelecimentos");
        return estabelecimentoRepository.findAll();
    }

    /**
     * {@code GET  /estabelecimentos/:id} : get the "id" estabelecimento.
     *
     * @param id the id of the estabelecimento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estabelecimento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estabelecimentos/{id}")
    public ResponseEntity<Estabelecimento> getEstabelecimento(@PathVariable Long id) {
        log.debug("REST request to get Estabelecimento : {}", id);
        Optional<Estabelecimento> estabelecimento = estabelecimentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estabelecimento);
    }

    /**
     * {@code DELETE  /estabelecimentos/:id} : delete the "id" estabelecimento.
     *
     * @param id the id of the estabelecimento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estabelecimentos/{id}")
    public ResponseEntity<Void> deleteEstabelecimento(@PathVariable Long id) {
        log.debug("REST request to delete Estabelecimento : {}", id);
        estabelecimentoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
