package com.apiplus.web.rest;

import com.apiplus.domain.Kit;
import com.apiplus.repository.KitRepository;
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
 * REST controller for managing {@link com.apiplus.domain.Kit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KitResource {

    private final Logger log = LoggerFactory.getLogger(KitResource.class);

    private static final String ENTITY_NAME = "kit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KitRepository kitRepository;

    public KitResource(KitRepository kitRepository) {
        this.kitRepository = kitRepository;
    }

    /**
     * {@code POST  /kits} : Create a new kit.
     *
     * @param kit the kit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kit, or with status {@code 400 (Bad Request)} if the kit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kits")
    public ResponseEntity<Kit> createKit(@RequestBody Kit kit) throws URISyntaxException {
        log.debug("REST request to save Kit : {}", kit);
        if (kit.getId() != null) {
            throw new BadRequestAlertException("A new kit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Kit result = kitRepository.save(kit);
        return ResponseEntity
            .created(new URI("/api/kits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kits/:id} : Updates an existing kit.
     *
     * @param id the id of the kit to save.
     * @param kit the kit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kit,
     * or with status {@code 400 (Bad Request)} if the kit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kits/{id}")
    public ResponseEntity<Kit> updateKit(@PathVariable(value = "id", required = false) final Long id, @RequestBody Kit kit)
        throws URISyntaxException {
        log.debug("REST request to update Kit : {}, {}", id, kit);
        if (kit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Kit result = kitRepository.save(kit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kit.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /kits/:id} : Partial updates given fields of an existing kit, field will ignore if it is null
     *
     * @param id the id of the kit to save.
     * @param kit the kit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kit,
     * or with status {@code 400 (Bad Request)} if the kit is not valid,
     * or with status {@code 404 (Not Found)} if the kit is not found,
     * or with status {@code 500 (Internal Server Error)} if the kit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kit> partialUpdateKit(@PathVariable(value = "id", required = false) final Long id, @RequestBody Kit kit)
        throws URISyntaxException {
        log.debug("REST request to partial update Kit partially : {}, {}", id, kit);
        if (kit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kit> result = kitRepository
            .findById(kit.getId())
            .map(existingKit -> {
                if (kit.getStatusKit() != null) {
                    existingKit.setStatusKit(kit.getStatusKit());
                }

                return existingKit;
            })
            .map(kitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kit.getId().toString())
        );
    }

    /**
     * {@code GET  /kits} : get all the kits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kits in body.
     */
    @GetMapping("/kits")
    public List<Kit> getAllKits() {
        log.debug("REST request to get all Kits");
        return kitRepository.findAll();
    }

    /**
     * {@code GET  /kits/:id} : get the "id" kit.
     *
     * @param id the id of the kit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kits/{id}")
    public ResponseEntity<Kit> getKit(@PathVariable Long id) {
        log.debug("REST request to get Kit : {}", id);
        Optional<Kit> kit = kitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kit);
    }

    /**
     * {@code DELETE  /kits/:id} : delete the "id" kit.
     *
     * @param id the id of the kit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kits/{id}")
    public ResponseEntity<Void> deleteKit(@PathVariable Long id) {
        log.debug("REST request to delete Kit : {}", id);
        kitRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
