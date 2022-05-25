package com.apiplus.web.rest;

import com.apiplus.domain.Bateria;
import com.apiplus.repository.BateriaRepository;
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
 * REST controller for managing {@link com.apiplus.domain.Bateria}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BateriaResource {

    private final Logger log = LoggerFactory.getLogger(BateriaResource.class);

    private static final String ENTITY_NAME = "bateria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BateriaRepository bateriaRepository;

    public BateriaResource(BateriaRepository bateriaRepository) {
        this.bateriaRepository = bateriaRepository;
    }

    /**
     * {@code POST  /baterias} : Create a new bateria.
     *
     * @param bateria the bateria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bateria, or with status {@code 400 (Bad Request)} if the bateria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/baterias")
    public ResponseEntity<Bateria> createBateria(@RequestBody Bateria bateria) throws URISyntaxException {
        log.debug("REST request to save Bateria : {}", bateria);
        if (bateria.getId() != null) {
            throw new BadRequestAlertException("A new bateria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bateria result = bateriaRepository.save(bateria);
        return ResponseEntity
            .created(new URI("/api/baterias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /baterias/:id} : Updates an existing bateria.
     *
     * @param id the id of the bateria to save.
     * @param bateria the bateria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bateria,
     * or with status {@code 400 (Bad Request)} if the bateria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bateria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/baterias/{id}")
    public ResponseEntity<Bateria> updateBateria(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bateria bateria)
        throws URISyntaxException {
        log.debug("REST request to update Bateria : {}, {}", id, bateria);
        if (bateria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bateria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bateriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bateria result = bateriaRepository.save(bateria);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bateria.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /baterias/:id} : Partial updates given fields of an existing bateria, field will ignore if it is null
     *
     * @param id the id of the bateria to save.
     * @param bateria the bateria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bateria,
     * or with status {@code 400 (Bad Request)} if the bateria is not valid,
     * or with status {@code 404 (Not Found)} if the bateria is not found,
     * or with status {@code 500 (Internal Server Error)} if the bateria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/baterias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bateria> partialUpdateBateria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bateria bateria
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bateria partially : {}, {}", id, bateria);
        if (bateria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bateria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bateriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bateria> result = bateriaRepository
            .findById(bateria.getId())
            .map(existingBateria -> {
                if (bateria.getNumeroSerie() != null) {
                    existingBateria.setNumeroSerie(bateria.getNumeroSerie());
                }
                if (bateria.getStatus() != null) {
                    existingBateria.setStatus(bateria.getStatus());
                }
                if (bateria.getCarga() != null) {
                    existingBateria.setCarga(bateria.getCarga());
                }

                return existingBateria;
            })
            .map(bateriaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bateria.getId().toString())
        );
    }

    /**
     * {@code GET  /baterias} : get all the baterias.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baterias in body.
     */
    @GetMapping("/baterias")
    public List<Bateria> getAllBaterias(@RequestParam(required = false) String filter) {
        if ("kit-is-null".equals(filter)) {
            log.debug("REST request to get all Baterias where kit is null");
            return StreamSupport
                .stream(bateriaRepository.findAll().spliterator(), false)
                .filter(bateria -> bateria.getKit() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Baterias");
        return bateriaRepository.findAll();
    }

    /**
     * {@code GET  /baterias/:id} : get the "id" bateria.
     *
     * @param id the id of the bateria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bateria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/baterias/{id}")
    public ResponseEntity<Bateria> getBateria(@PathVariable Long id) {
        log.debug("REST request to get Bateria : {}", id);
        Optional<Bateria> bateria = bateriaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bateria);
    }

    /**
     * {@code DELETE  /baterias/:id} : delete the "id" bateria.
     *
     * @param id the id of the bateria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/baterias/{id}")
    public ResponseEntity<Void> deleteBateria(@PathVariable Long id) {
        log.debug("REST request to delete Bateria : {}", id);
        bateriaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
