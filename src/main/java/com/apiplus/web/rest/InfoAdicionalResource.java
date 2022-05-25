package com.apiplus.web.rest;

import com.apiplus.domain.InfoAdicional;
import com.apiplus.repository.InfoAdicionalRepository;
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
 * REST controller for managing {@link com.apiplus.domain.InfoAdicional}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InfoAdicionalResource {

    private final Logger log = LoggerFactory.getLogger(InfoAdicionalResource.class);

    private static final String ENTITY_NAME = "infoAdicional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfoAdicionalRepository infoAdicionalRepository;

    public InfoAdicionalResource(InfoAdicionalRepository infoAdicionalRepository) {
        this.infoAdicionalRepository = infoAdicionalRepository;
    }

    /**
     * {@code POST  /info-adicionals} : Create a new infoAdicional.
     *
     * @param infoAdicional the infoAdicional to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infoAdicional, or with status {@code 400 (Bad Request)} if the infoAdicional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/info-adicionals")
    public ResponseEntity<InfoAdicional> createInfoAdicional(@RequestBody InfoAdicional infoAdicional) throws URISyntaxException {
        log.debug("REST request to save InfoAdicional : {}", infoAdicional);
        if (infoAdicional.getId() != null) {
            throw new BadRequestAlertException("A new infoAdicional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InfoAdicional result = infoAdicionalRepository.save(infoAdicional);
        return ResponseEntity
            .created(new URI("/api/info-adicionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /info-adicionals/:id} : Updates an existing infoAdicional.
     *
     * @param id the id of the infoAdicional to save.
     * @param infoAdicional the infoAdicional to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoAdicional,
     * or with status {@code 400 (Bad Request)} if the infoAdicional is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infoAdicional couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/info-adicionals/{id}")
    public ResponseEntity<InfoAdicional> updateInfoAdicional(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InfoAdicional infoAdicional
    ) throws URISyntaxException {
        log.debug("REST request to update InfoAdicional : {}, {}", id, infoAdicional);
        if (infoAdicional.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infoAdicional.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!infoAdicionalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InfoAdicional result = infoAdicionalRepository.save(infoAdicional);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, infoAdicional.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /info-adicionals/:id} : Partial updates given fields of an existing infoAdicional, field will ignore if it is null
     *
     * @param id the id of the infoAdicional to save.
     * @param infoAdicional the infoAdicional to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infoAdicional,
     * or with status {@code 400 (Bad Request)} if the infoAdicional is not valid,
     * or with status {@code 404 (Not Found)} if the infoAdicional is not found,
     * or with status {@code 500 (Internal Server Error)} if the infoAdicional couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/info-adicionals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InfoAdicional> partialUpdateInfoAdicional(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InfoAdicional infoAdicional
    ) throws URISyntaxException {
        log.debug("REST request to partial update InfoAdicional partially : {}, {}", id, infoAdicional);
        if (infoAdicional.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infoAdicional.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!infoAdicionalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InfoAdicional> result = infoAdicionalRepository
            .findById(infoAdicional.getId())
            .map(existingInfoAdicional -> {
                if (infoAdicional.getNome() != null) {
                    existingInfoAdicional.setNome(infoAdicional.getNome());
                }
                if (infoAdicional.getCpf() != null) {
                    existingInfoAdicional.setCpf(infoAdicional.getCpf());
                }
                if (infoAdicional.getTelefone() != null) {
                    existingInfoAdicional.setTelefone(infoAdicional.getTelefone());
                }
                if (infoAdicional.getDeficiencia() != null) {
                    existingInfoAdicional.setDeficiencia(infoAdicional.getDeficiencia());
                }

                return existingInfoAdicional;
            })
            .map(infoAdicionalRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, infoAdicional.getId().toString())
        );
    }

    /**
     * {@code GET  /info-adicionals} : get all the infoAdicionals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infoAdicionals in body.
     */
    @GetMapping("/info-adicionals")
    public List<InfoAdicional> getAllInfoAdicionals() {
        log.debug("REST request to get all InfoAdicionals");
        return infoAdicionalRepository.findAll();
    }

    /**
     * {@code GET  /info-adicionals/:id} : get the "id" infoAdicional.
     *
     * @param id the id of the infoAdicional to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infoAdicional, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/info-adicionals/{id}")
    public ResponseEntity<InfoAdicional> getInfoAdicional(@PathVariable Long id) {
        log.debug("REST request to get InfoAdicional : {}", id);
        Optional<InfoAdicional> infoAdicional = infoAdicionalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(infoAdicional);
    }

    /**
     * {@code DELETE  /info-adicionals/:id} : delete the "id" infoAdicional.
     *
     * @param id the id of the infoAdicional to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/info-adicionals/{id}")
    public ResponseEntity<Void> deleteInfoAdicional(@PathVariable Long id) {
        log.debug("REST request to delete InfoAdicional : {}", id);
        infoAdicionalRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
