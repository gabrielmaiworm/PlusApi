package com.apiplus.web.rest;

import com.apiplus.domain.ContaBancaria;
import com.apiplus.repository.ContaBancariaRepository;
import com.apiplus.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.apiplus.domain.ContaBancaria}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContaBancariaResource {

    private final Logger log = LoggerFactory.getLogger(ContaBancariaResource.class);

    private static final String ENTITY_NAME = "contaBancaria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContaBancariaRepository contaBancariaRepository;

    public ContaBancariaResource(ContaBancariaRepository contaBancariaRepository) {
        this.contaBancariaRepository = contaBancariaRepository;
    }

    /**
     * {@code POST  /conta-bancarias} : Create a new contaBancaria.
     *
     * @param contaBancaria the contaBancaria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contaBancaria, or with status {@code 400 (Bad Request)} if the contaBancaria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conta-bancarias")
    public ResponseEntity<ContaBancaria> createContaBancaria(@RequestBody ContaBancaria contaBancaria) throws URISyntaxException {
        log.debug("REST request to save ContaBancaria : {}", contaBancaria);
        if (contaBancaria.getId() != null) {
            throw new BadRequestAlertException("A new contaBancaria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContaBancaria result = contaBancariaRepository.save(contaBancaria);
        return ResponseEntity
            .created(new URI("/api/conta-bancarias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conta-bancarias/:id} : Updates an existing contaBancaria.
     *
     * @param id the id of the contaBancaria to save.
     * @param contaBancaria the contaBancaria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contaBancaria,
     * or with status {@code 400 (Bad Request)} if the contaBancaria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contaBancaria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conta-bancarias/{id}")
    public ResponseEntity<ContaBancaria> updateContaBancaria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContaBancaria contaBancaria
    ) throws URISyntaxException {
        log.debug("REST request to update ContaBancaria : {}, {}", id, contaBancaria);
        if (contaBancaria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contaBancaria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contaBancariaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContaBancaria result = contaBancariaRepository.save(contaBancaria);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contaBancaria.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /conta-bancarias/:id} : Partial updates given fields of an existing contaBancaria, field will ignore if it is null
     *
     * @param id the id of the contaBancaria to save.
     * @param contaBancaria the contaBancaria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contaBancaria,
     * or with status {@code 400 (Bad Request)} if the contaBancaria is not valid,
     * or with status {@code 404 (Not Found)} if the contaBancaria is not found,
     * or with status {@code 500 (Internal Server Error)} if the contaBancaria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/conta-bancarias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContaBancaria> partialUpdateContaBancaria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContaBancaria contaBancaria
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContaBancaria partially : {}, {}", id, contaBancaria);
        if (contaBancaria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contaBancaria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contaBancariaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContaBancaria> result = contaBancariaRepository
            .findById(contaBancaria.getId())
            .map(existingContaBancaria -> {
                if (contaBancaria.getBanco() != null) {
                    existingContaBancaria.setBanco(contaBancaria.getBanco());
                }
                if (contaBancaria.getAgencia() != null) {
                    existingContaBancaria.setAgencia(contaBancaria.getAgencia());
                }
                if (contaBancaria.getNumeroConta() != null) {
                    existingContaBancaria.setNumeroConta(contaBancaria.getNumeroConta());
                }
                if (contaBancaria.getTipo() != null) {
                    existingContaBancaria.setTipo(contaBancaria.getTipo());
                }

                return existingContaBancaria;
            })
            .map(contaBancariaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contaBancaria.getId().toString())
        );
    }

    /**
     * {@code GET  /conta-bancarias} : get all the contaBancarias.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contaBancarias in body.
     */
    @GetMapping("/conta-bancarias")
    public List<ContaBancaria> getAllContaBancarias(@RequestParam(required = false) String filter) {
        if ("parceiro-is-null".equals(filter)) {
            log.debug("REST request to get all ContaBancarias where parceiro is null");
            return StreamSupport
                .stream(contaBancariaRepository.findAll().spliterator(), false)
                .filter(contaBancaria -> contaBancaria.getParceiro() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all ContaBancarias");
        return contaBancariaRepository.findAll();
    }

    /**
     * {@code GET  /conta-bancarias/:id} : get the "id" contaBancaria.
     *
     * @param id the id of the contaBancaria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contaBancaria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conta-bancarias/{id}")
    public ResponseEntity<ContaBancaria> getContaBancaria(@PathVariable Long id) {
        log.debug("REST request to get ContaBancaria : {}", id);
        Optional<ContaBancaria> contaBancaria = contaBancariaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contaBancaria);
    }

    /**
     * {@code DELETE  /conta-bancarias/:id} : delete the "id" contaBancaria.
     *
     * @param id the id of the contaBancaria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conta-bancarias/{id}")
    public ResponseEntity<Void> deleteContaBancaria(@PathVariable Long id) {
        log.debug("REST request to delete ContaBancaria : {}", id);
        contaBancariaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
