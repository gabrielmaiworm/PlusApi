package com.apiplus.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apiplus.IntegrationTest;
import com.apiplus.domain.ContaBancaria;
import com.apiplus.domain.enumeration.Tipo;
import com.apiplus.repository.ContaBancariaRepository;
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
 * Integration tests for the {@link ContaBancariaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContaBancariaResourceIT {

    private static final String DEFAULT_BANCO = "AAAAAAAAAA";
    private static final String UPDATED_BANCO = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGENCIA = 1;
    private static final Integer UPDATED_AGENCIA = 2;

    private static final Integer DEFAULT_NUMERO_CONTA = 1;
    private static final Integer UPDATED_NUMERO_CONTA = 2;

    private static final Tipo DEFAULT_TIPO = Tipo.CONTA_CORRENTE;
    private static final Tipo UPDATED_TIPO = Tipo.CONTA_POUPANCA;

    private static final String ENTITY_API_URL = "/api/conta-bancarias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContaBancariaMockMvc;

    private ContaBancaria contaBancaria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContaBancaria createEntity(EntityManager em) {
        ContaBancaria contaBancaria = new ContaBancaria()
            .banco(DEFAULT_BANCO)
            .agencia(DEFAULT_AGENCIA)
            .numeroConta(DEFAULT_NUMERO_CONTA)
            .tipo(DEFAULT_TIPO);
        return contaBancaria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContaBancaria createUpdatedEntity(EntityManager em) {
        ContaBancaria contaBancaria = new ContaBancaria()
            .banco(UPDATED_BANCO)
            .agencia(UPDATED_AGENCIA)
            .numeroConta(UPDATED_NUMERO_CONTA)
            .tipo(UPDATED_TIPO);
        return contaBancaria;
    }

    @BeforeEach
    public void initTest() {
        contaBancaria = createEntity(em);
    }

    @Test
    @Transactional
    void createContaBancaria() throws Exception {
        int databaseSizeBeforeCreate = contaBancariaRepository.findAll().size();
        // Create the ContaBancaria
        restContaBancariaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contaBancaria)))
            .andExpect(status().isCreated());

        // Validate the ContaBancaria in the database
        List<ContaBancaria> contaBancariaList = contaBancariaRepository.findAll();
        assertThat(contaBancariaList).hasSize(databaseSizeBeforeCreate + 1);
        ContaBancaria testContaBancaria = contaBancariaList.get(contaBancariaList.size() - 1);
        assertThat(testContaBancaria.getBanco()).isEqualTo(DEFAULT_BANCO);
        assertThat(testContaBancaria.getAgencia()).isEqualTo(DEFAULT_AGENCIA);
        assertThat(testContaBancaria.getNumeroConta()).isEqualTo(DEFAULT_NUMERO_CONTA);
        assertThat(testContaBancaria.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    void createContaBancariaWithExistingId() throws Exception {
        // Create the ContaBancaria with an existing ID
        contaBancaria.setId(1L);

        int databaseSizeBeforeCreate = contaBancariaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContaBancariaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contaBancaria)))
            .andExpect(status().isBadRequest());

        // Validate the ContaBancaria in the database
        List<ContaBancaria> contaBancariaList = contaBancariaRepository.findAll();
        assertThat(contaBancariaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContaBancarias() throws Exception {
        // Initialize the database
        contaBancariaRepository.saveAndFlush(contaBancaria);

        // Get all the contaBancariaList
        restContaBancariaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contaBancaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].banco").value(hasItem(DEFAULT_BANCO)))
            .andExpect(jsonPath("$.[*].agencia").value(hasItem(DEFAULT_AGENCIA)))
            .andExpect(jsonPath("$.[*].numeroConta").value(hasItem(DEFAULT_NUMERO_CONTA)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    void getContaBancaria() throws Exception {
        // Initialize the database
        contaBancariaRepository.saveAndFlush(contaBancaria);

        // Get the contaBancaria
        restContaBancariaMockMvc
            .perform(get(ENTITY_API_URL_ID, contaBancaria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contaBancaria.getId().intValue()))
            .andExpect(jsonPath("$.banco").value(DEFAULT_BANCO))
            .andExpect(jsonPath("$.agencia").value(DEFAULT_AGENCIA))
            .andExpect(jsonPath("$.numeroConta").value(DEFAULT_NUMERO_CONTA))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingContaBancaria() throws Exception {
        // Get the contaBancaria
        restContaBancariaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContaBancaria() throws Exception {
        // Initialize the database
        contaBancariaRepository.saveAndFlush(contaBancaria);

        int databaseSizeBeforeUpdate = contaBancariaRepository.findAll().size();

        // Update the contaBancaria
        ContaBancaria updatedContaBancaria = contaBancariaRepository.findById(contaBancaria.getId()).get();
        // Disconnect from session so that the updates on updatedContaBancaria are not directly saved in db
        em.detach(updatedContaBancaria);
        updatedContaBancaria.banco(UPDATED_BANCO).agencia(UPDATED_AGENCIA).numeroConta(UPDATED_NUMERO_CONTA).tipo(UPDATED_TIPO);

        restContaBancariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContaBancaria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContaBancaria))
            )
            .andExpect(status().isOk());

        // Validate the ContaBancaria in the database
        List<ContaBancaria> contaBancariaList = contaBancariaRepository.findAll();
        assertThat(contaBancariaList).hasSize(databaseSizeBeforeUpdate);
        ContaBancaria testContaBancaria = contaBancariaList.get(contaBancariaList.size() - 1);
        assertThat(testContaBancaria.getBanco()).isEqualTo(UPDATED_BANCO);
        assertThat(testContaBancaria.getAgencia()).isEqualTo(UPDATED_AGENCIA);
        assertThat(testContaBancaria.getNumeroConta()).isEqualTo(UPDATED_NUMERO_CONTA);
        assertThat(testContaBancaria.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    void putNonExistingContaBancaria() throws Exception {
        int databaseSizeBeforeUpdate = contaBancariaRepository.findAll().size();
        contaBancaria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContaBancariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contaBancaria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contaBancaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContaBancaria in the database
        List<ContaBancaria> contaBancariaList = contaBancariaRepository.findAll();
        assertThat(contaBancariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContaBancaria() throws Exception {
        int databaseSizeBeforeUpdate = contaBancariaRepository.findAll().size();
        contaBancaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContaBancariaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contaBancaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContaBancaria in the database
        List<ContaBancaria> contaBancariaList = contaBancariaRepository.findAll();
        assertThat(contaBancariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContaBancaria() throws Exception {
        int databaseSizeBeforeUpdate = contaBancariaRepository.findAll().size();
        contaBancaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContaBancariaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contaBancaria)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContaBancaria in the database
        List<ContaBancaria> contaBancariaList = contaBancariaRepository.findAll();
        assertThat(contaBancariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContaBancariaWithPatch() throws Exception {
        // Initialize the database
        contaBancariaRepository.saveAndFlush(contaBancaria);

        int databaseSizeBeforeUpdate = contaBancariaRepository.findAll().size();

        // Update the contaBancaria using partial update
        ContaBancaria partialUpdatedContaBancaria = new ContaBancaria();
        partialUpdatedContaBancaria.setId(contaBancaria.getId());

        partialUpdatedContaBancaria.banco(UPDATED_BANCO).agencia(UPDATED_AGENCIA).numeroConta(UPDATED_NUMERO_CONTA);

        restContaBancariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContaBancaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContaBancaria))
            )
            .andExpect(status().isOk());

        // Validate the ContaBancaria in the database
        List<ContaBancaria> contaBancariaList = contaBancariaRepository.findAll();
        assertThat(contaBancariaList).hasSize(databaseSizeBeforeUpdate);
        ContaBancaria testContaBancaria = contaBancariaList.get(contaBancariaList.size() - 1);
        assertThat(testContaBancaria.getBanco()).isEqualTo(UPDATED_BANCO);
        assertThat(testContaBancaria.getAgencia()).isEqualTo(UPDATED_AGENCIA);
        assertThat(testContaBancaria.getNumeroConta()).isEqualTo(UPDATED_NUMERO_CONTA);
        assertThat(testContaBancaria.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    void fullUpdateContaBancariaWithPatch() throws Exception {
        // Initialize the database
        contaBancariaRepository.saveAndFlush(contaBancaria);

        int databaseSizeBeforeUpdate = contaBancariaRepository.findAll().size();

        // Update the contaBancaria using partial update
        ContaBancaria partialUpdatedContaBancaria = new ContaBancaria();
        partialUpdatedContaBancaria.setId(contaBancaria.getId());

        partialUpdatedContaBancaria.banco(UPDATED_BANCO).agencia(UPDATED_AGENCIA).numeroConta(UPDATED_NUMERO_CONTA).tipo(UPDATED_TIPO);

        restContaBancariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContaBancaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContaBancaria))
            )
            .andExpect(status().isOk());

        // Validate the ContaBancaria in the database
        List<ContaBancaria> contaBancariaList = contaBancariaRepository.findAll();
        assertThat(contaBancariaList).hasSize(databaseSizeBeforeUpdate);
        ContaBancaria testContaBancaria = contaBancariaList.get(contaBancariaList.size() - 1);
        assertThat(testContaBancaria.getBanco()).isEqualTo(UPDATED_BANCO);
        assertThat(testContaBancaria.getAgencia()).isEqualTo(UPDATED_AGENCIA);
        assertThat(testContaBancaria.getNumeroConta()).isEqualTo(UPDATED_NUMERO_CONTA);
        assertThat(testContaBancaria.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    void patchNonExistingContaBancaria() throws Exception {
        int databaseSizeBeforeUpdate = contaBancariaRepository.findAll().size();
        contaBancaria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContaBancariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contaBancaria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contaBancaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContaBancaria in the database
        List<ContaBancaria> contaBancariaList = contaBancariaRepository.findAll();
        assertThat(contaBancariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContaBancaria() throws Exception {
        int databaseSizeBeforeUpdate = contaBancariaRepository.findAll().size();
        contaBancaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContaBancariaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contaBancaria))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContaBancaria in the database
        List<ContaBancaria> contaBancariaList = contaBancariaRepository.findAll();
        assertThat(contaBancariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContaBancaria() throws Exception {
        int databaseSizeBeforeUpdate = contaBancariaRepository.findAll().size();
        contaBancaria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContaBancariaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contaBancaria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContaBancaria in the database
        List<ContaBancaria> contaBancariaList = contaBancariaRepository.findAll();
        assertThat(contaBancariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContaBancaria() throws Exception {
        // Initialize the database
        contaBancariaRepository.saveAndFlush(contaBancaria);

        int databaseSizeBeforeDelete = contaBancariaRepository.findAll().size();

        // Delete the contaBancaria
        restContaBancariaMockMvc
            .perform(delete(ENTITY_API_URL_ID, contaBancaria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContaBancaria> contaBancariaList = contaBancariaRepository.findAll();
        assertThat(contaBancariaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
