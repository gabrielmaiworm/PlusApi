package com.apiplus.web.rest;

import com.apiplus.domain.Parceiro;
import com.apiplus.repository.ParceiroRepository;
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
 * REST controller for managing {@link com.apiplus.domain.Parceiro}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ParceiroResource {

    private final Logger log = LoggerFactory.getLogger(ParceiroResource.class);

    private static final String ENTITY_NAME = "parceiro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParceiroRepository parceiroRepository;

    public ParceiroResource(ParceiroRepository parceiroRepository) {
        this.parceiroRepository = parceiroRepository;
    }

    /**
     * {@code POST  /parceiros} : Create a new parceiro.
     *
     * @param parceiro the parceiro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parceiro, or with status {@code 400 (Bad Request)} if the parceiro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parceiros")
    public ResponseEntity<Parceiro> createParceiro(@RequestBody Parceiro parceiro) throws URISyntaxException {
        log.debug("REST request to save Parceiro : {}", parceiro);
        if (parceiro.getId() != null) {
            throw new BadRequestAlertException("A new parceiro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Parceiro result = parceiroRepository.save(parceiro);
        return ResponseEntity
            .created(new URI("/api/parceiros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parceiros/:id} : Updates an existing parceiro.
     *
     * @param id the id of the parceiro to save.
     * @param parceiro the parceiro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parceiro,
     * or with status {@code 400 (Bad Request)} if the parceiro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parceiro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parceiros/{id}")
    public ResponseEntity<Parceiro> updateParceiro(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Parceiro parceiro
    ) throws URISyntaxException {
        log.debug("REST request to update Parceiro : {}, {}", id, parceiro);
        if (parceiro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parceiro.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parceiroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Parceiro result = parceiroRepository.save(parceiro);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parceiro.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parceiros/:id} : Partial updates given fields of an existing parceiro, field will ignore if it is null
     *
     * @param id the id of the parceiro to save.
     * @param parceiro the parceiro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parceiro,
     * or with status {@code 400 (Bad Request)} if the parceiro is not valid,
     * or with status {@code 404 (Not Found)} if the parceiro is not found,
     * or with status {@code 500 (Internal Server Error)} if the parceiro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parceiros/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Parceiro> partialUpdateParceiro(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Parceiro parceiro
    ) throws URISyntaxException {
        log.debug("REST request to partial update Parceiro partially : {}, {}", id, parceiro);
        if (parceiro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parceiro.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parceiroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Parceiro> result = parceiroRepository
            .findById(parceiro.getId())
            .map(existingParceiro -> {
                if (parceiro.getRazaoSocial() != null) {
                    existingParceiro.setRazaoSocial(parceiro.getRazaoSocial());
                }
                if (parceiro.getNomeFantasia() != null) {
                    existingParceiro.setNomeFantasia(parceiro.getNomeFantasia());
                }
                if (parceiro.getCnpj() != null) {
                    existingParceiro.setCnpj(parceiro.getCnpj());
                }
                if (parceiro.getInscricaoEstadual() != null) {
                    existingParceiro.setInscricaoEstadual(parceiro.getInscricaoEstadual());
                }
                if (parceiro.getEmail() != null) {
                    existingParceiro.setEmail(parceiro.getEmail());
                }
                if (parceiro.getTelefone() != null) {
                    existingParceiro.setTelefone(parceiro.getTelefone());
                }
                if (parceiro.getTipoServico() != null) {
                    existingParceiro.setTipoServico(parceiro.getTipoServico());
                }
                if (parceiro.getNomeGestor() != null) {
                    existingParceiro.setNomeGestor(parceiro.getNomeGestor());
                }
                if (parceiro.getSobrenomeGestor() != null) {
                    existingParceiro.setSobrenomeGestor(parceiro.getSobrenomeGestor());
                }
                if (parceiro.getDocumentoGestor() != null) {
                    existingParceiro.setDocumentoGestor(parceiro.getDocumentoGestor());
                }
                if (parceiro.getTelefoneGestor() != null) {
                    existingParceiro.setTelefoneGestor(parceiro.getTelefoneGestor());
                }

                return existingParceiro;
            })
            .map(parceiroRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parceiro.getId().toString())
        );
    }

    /**
     * {@code GET  /parceiros} : get all the parceiros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parceiros in body.
     */
    @GetMapping("/parceiros")
    public List<Parceiro> getAllParceiros() {
        log.debug("REST request to get all Parceiros");
        return parceiroRepository.findAll();
    }

    /**
     * {@code GET  /parceiros/:id} : get the "id" parceiro.
     *
     * @param id the id of the parceiro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parceiro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parceiros/{id}")
    public ResponseEntity<Parceiro> getParceiro(@PathVariable Long id) {
        log.debug("REST request to get Parceiro : {}", id);
        Optional<Parceiro> parceiro = parceiroRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parceiro);
    }

    /**
     * {@code DELETE  /parceiros/:id} : delete the "id" parceiro.
     *
     * @param id the id of the parceiro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parceiros/{id}")
    public ResponseEntity<Void> deleteParceiro(@PathVariable Long id) {
        log.debug("REST request to delete Parceiro : {}", id);
        parceiroRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
