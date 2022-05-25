package com.apiplus.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apiplus.IntegrationTest;
import com.apiplus.domain.Bateria;
import com.apiplus.domain.enumeration.Status;
import com.apiplus.repository.BateriaRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BateriaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BateriaResourceIT {

    private static final Integer DEFAULT_NUMERO_SERIE = 1;
    private static final Integer UPDATED_NUMERO_SERIE = 2;

    private static final Status DEFAULT_STATUS = Status.EM_FUNCINAMENTO;
    private static final Status UPDATED_STATUS = Status.EM_MANUTENCAO;

    private static final Integer DEFAULT_CARGA = 1;
    private static final Integer UPDATED_CARGA = 2;

    private static final String ENTITY_API_URL = "/api/baterias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BateriaRepository bateriaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBateriaMockMvc;

    private Bateria bateria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bateria createEntity(EntityManager em) {
        Bateria bateria = new Bateria().numeroSerie(DEFAULT_NUMERO_SERIE).status(DEFAULT_STATUS).carga(DEFAULT_CARGA);
        return bateria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bateria createUpdatedEntity(EntityManager em) {
        Bateria bateria = new Bateria().numeroSerie(UPDATED_NUMERO_SERIE).status(UPDATED_STATUS).carga(UPDATED_CARGA);
        return bateria;
    }

    @BeforeEach
    public void initTest() {
        bateria = createEntity(em);
    }

    @Test
    @Transactional
    void createBateria() throws Exception {
        int databaseSizeBeforeCreate = bateriaRepository.findAll().size();
        // Create the Bateria
        restBateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bateria)))
            .andExpect(status().isCreated());

        // Validate the Bateria in the database
        List<Bateria> bateriaList = bateriaRepository.findAll();
        assertThat(bateriaList).hasSize(databaseSizeBeforeCreate + 1);
        Bateria testBateria = bateriaList.get(bateriaList.size() - 1);
        assertThat(testBateria.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testBateria.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBateria.getCarga()).isEqualTo(DEFAULT_CARGA);
    }

    @Test
    @Transactional
    void createBateriaWithExistingId() throws Exception {
        // Create the Bateria with an existing ID
        bateria.setId(1L);

        int databaseSizeBeforeCreate = bateriaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bateria)))
            .andExpect(status().isBadRequest());

        // Validate the Bateria in the database
        List<Bateria> bateriaList = bateriaRepository.findAll();
        assertThat(bateriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBaterias() throws Exception {
        // Initialize the database
        bateriaRepository.saveAndFlush(bateria);

        // Get all the bateriaList
        restBateriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bateria.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].carga").value(hasItem(DEFAULT_CARGA)));
    }

    @Test
    @Transactional
    void getBateria() throws Exception {
        // Initialize the database
        bateriaRepository.saveAndFlush(bateria);

        // Get the bateria
        restBateriaMockMvc
            .perform(get(ENTITY_API_URL_ID, bateria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bateria.getId().intValue()))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.carga").value(DEFAULT_CARGA));
    }

    @Test
    @Transactional
    void getNonExistingBateria() throws Exception {
        // Get the bateria
        restBateriaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBateria() throws Exception {
        // Initialize the database
        bateriaRepository.saveAndFlush(bateria);

        int databaseSizeBeforeUpdate = bateriaRepository.findAll().size();

        // Update the bateria
        Bateria updatedBateria = bateriaRepository.findById(bateria.getId()).get();
        // Disconnect from session so that the updates on updatedBateria are not directly saved in db
        em.detach(updatedBateria);
        updatedBateria.numeroSerie(UPDATED_NUMERO_SERIE).status(UPDATED_STATUS).carga(UPDATED_CARGA);

        restBateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBateria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBateria))
            )
            .andExpect(status().isOk());

        // Validate the Bateria in the database
        List<Bateria> bateriaList = bateriaRepository.findAll();
        assertThat(bateriaList).hasSize(databaseSizeBeforeUpdate);
        Bateria testBateria = bateriaList.get(bateriaList.size() - 1);
        assertThat(testBateria.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testBateria.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBateria.getCarga()).isEqualTo(UPDATED_CARGA);
    }

    @Test
    @Transactional
    void putNonExistingBateria() throws Exception {
        int databaseSizeBeforeUpdate = bateriaRepository.findAll().size();
        bateria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bateria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bateria))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bateria in the database
        List<Bateria> bateriaList = bateriaRepository.findAll();
        assertThat(bateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBateria() throws Exception {
        int databaseSizeBeforeUpdate = bateriaRepository.findAll().size();
        bateria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bateria))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bateria in the database
        List<Bateria> bateriaList = bateriaRepository.findAll();
        assertThat(bateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBateria() throws Exception {
        int databaseSizeBeforeUpdate = bateriaRepository.findAll().size();
        bateria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBateriaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bateria)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bateria in the database
        List<Bateria> bateriaList = bateriaRepository.findAll();
        assertThat(bateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBateriaWithPatch() throws Exception {
        // Initialize the database
        bateriaRepository.saveAndFlush(bateria);

        int databaseSizeBeforeUpdate = bateriaRepository.findAll().size();

        // Update the bateria using partial update
        Bateria partialUpdatedBateria = new Bateria();
        partialUpdatedBateria.setId(bateria.getId());

        partialUpdatedBateria.status(UPDATED_STATUS).carga(UPDATED_CARGA);

        restBateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBateria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBateria))
            )
            .andExpect(status().isOk());

        // Validate the Bateria in the database
        List<Bateria> bateriaList = bateriaRepository.findAll();
        assertThat(bateriaList).hasSize(databaseSizeBeforeUpdate);
        Bateria testBateria = bateriaList.get(bateriaList.size() - 1);
        assertThat(testBateria.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testBateria.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBateria.getCarga()).isEqualTo(UPDATED_CARGA);
    }

    @Test
    @Transactional
    void fullUpdateBateriaWithPatch() throws Exception {
        // Initialize the database
        bateriaRepository.saveAndFlush(bateria);

        int databaseSizeBeforeUpdate = bateriaRepository.findAll().size();

        // Update the bateria using partial update
        Bateria partialUpdatedBateria = new Bateria();
        partialUpdatedBateria.setId(bateria.getId());

        partialUpdatedBateria.numeroSerie(UPDATED_NUMERO_SERIE).status(UPDATED_STATUS).carga(UPDATED_CARGA);

        restBateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBateria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBateria))
            )
            .andExpect(status().isOk());

        // Validate the Bateria in the database
        List<Bateria> bateriaList = bateriaRepository.findAll();
        assertThat(bateriaList).hasSize(databaseSizeBeforeUpdate);
        Bateria testBateria = bateriaList.get(bateriaList.size() - 1);
        assertThat(testBateria.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testBateria.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBateria.getCarga()).isEqualTo(UPDATED_CARGA);
    }

    @Test
    @Transactional
    void patchNonExistingBateria() throws Exception {
        int databaseSizeBeforeUpdate = bateriaRepository.findAll().size();
        bateria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bateria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bateria))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bateria in the database
        List<Bateria> bateriaList = bateriaRepository.findAll();
        assertThat(bateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBateria() throws Exception {
        int databaseSizeBeforeUpdate = bateriaRepository.findAll().size();
        bateria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bateria))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bateria in the database
        List<Bateria> bateriaList = bateriaRepository.findAll();
        assertThat(bateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBateria() throws Exception {
        int databaseSizeBeforeUpdate = bateriaRepository.findAll().size();
        bateria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBateriaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bateria)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bateria in the database
        List<Bateria> bateriaList = bateriaRepository.findAll();
        assertThat(bateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBateria() throws Exception {
        // Initialize the database
        bateriaRepository.saveAndFlush(bateria);

        int databaseSizeBeforeDelete = bateriaRepository.findAll().size();

        // Delete the bateria
        restBateriaMockMvc
            .perform(delete(ENTITY_API_URL_ID, bateria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bateria> bateriaList = bateriaRepository.findAll();
        assertThat(bateriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
