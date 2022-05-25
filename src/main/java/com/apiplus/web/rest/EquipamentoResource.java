package com.apiplus.web.rest;

import com.apiplus.domain.Equipamento;
import com.apiplus.repository.EquipamentoRepository;
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
 * REST controller for managing {@link com.apiplus.domain.Equipamento}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EquipamentoResource {

    private final Logger log = LoggerFactory.getLogger(EquipamentoResource.class);

    private static final String ENTITY_NAME = "equipamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipamentoRepository equipamentoRepository;

    public EquipamentoResource(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    /**
     * {@code POST  /equipamentos} : Create a new equipamento.
     *
     * @param equipamento the equipamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipamento, or with status {@code 400 (Bad Request)} if the equipamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipamentos")
    public ResponseEntity<Equipamento> createEquipamento(@RequestBody Equipamento equipamento) throws URISyntaxException {
        log.debug("REST request to save Equipamento : {}", equipamento);
        if (equipamento.getId() != null) {
            throw new BadRequestAlertException("A new equipamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Equipamento result = equipamentoRepository.save(equipamento);
        return ResponseEntity
            .created(new URI("/api/equipamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipamentos/:id} : Updates an existing equipamento.
     *
     * @param id the id of the equipamento to save.
     * @param equipamento the equipamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipamento,
     * or with status {@code 400 (Bad Request)} if the equipamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipamentos/{id}")
    public ResponseEntity<Equipamento> updateEquipamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Equipamento equipamento
    ) throws URISyntaxException {
        log.debug("REST request to update Equipamento : {}, {}", id, equipamento);
        if (equipamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Equipamento result = equipamentoRepository.save(equipamento);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, equipamento.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /equipamentos/:id} : Partial updates given fields of an existing equipamento, field will ignore if it is null
     *
     * @param id the id of the equipamento to save.
     * @param equipamento the equipamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipamento,
     * or with status {@code 400 (Bad Request)} if the equipamento is not valid,
     * or with status {@code 404 (Not Found)} if the equipamento is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/equipamentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Equipamento> partialUpdateEquipamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Equipamento equipamento
    ) throws URISyntaxException {
        log.debug("REST request to partial update Equipamento partially : {}, {}", id, equipamento);
        if (equipamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Equipamento> result = equipamentoRepository
            .findById(equipamento.getId())
            .map(existingEquipamento -> {
                if (equipamento.getNumeroSerie() != null) {
                    existingEquipamento.setNumeroSerie(equipamento.getNumeroSerie());
                }
                if (equipamento.getNome() != null) {
                    existingEquipamento.setNome(equipamento.getNome());
                }
                if (equipamento.getStatus() != null) {
                    existingEquipamento.setStatus(equipamento.getStatus());
                }
                if (equipamento.getFotoEquipamento() != null) {
                    existingEquipamento.setFotoEquipamento(equipamento.getFotoEquipamento());
                }
                if (equipamento.getFotoEquipamentoContentType() != null) {
                    existingEquipamento.setFotoEquipamentoContentType(equipamento.getFotoEquipamentoContentType());
                }

                return existingEquipamento;
            })
            .map(equipamentoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, equipamento.getId().toString())
        );
    }

    /**
     * {@code GET  /equipamentos} : get all the equipamentos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipamentos in body.
     */
    @GetMapping("/equipamentos")
    public List<Equipamento> getAllEquipamentos(@RequestParam(required = false) String filter) {
        if ("kit-is-null".equals(filter)) {
            log.debug("REST request to get all Equipamentos where kit is null");
            return StreamSupport
                .stream(equipamentoRepository.findAll().spliterator(), false)
                .filter(equipamento -> equipamento.getKit() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Equipamentos");
        return equipamentoRepository.findAll();
    }

    /**
     * {@code GET  /equipamentos/:id} : get the "id" equipamento.
     *
     * @param id the id of the equipamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipamentos/{id}")
    public ResponseEntity<Equipamento> getEquipamento(@PathVariable Long id) {
        log.debug("REST request to get Equipamento : {}", id);
        Optional<Equipamento> equipamento = equipamentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(equipamento);
    }

    /**
     * {@code DELETE  /equipamentos/:id} : delete the "id" equipamento.
     *
     * @param id the id of the equipamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipamentos/{id}")
    public ResponseEntity<Void> deleteEquipamento(@PathVariable Long id) {
        log.debug("REST request to delete Equipamento : {}", id);
        equipamentoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
