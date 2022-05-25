package com.apiplus.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apiplus.IntegrationTest;
import com.apiplus.domain.Estabelecimento;
import com.apiplus.repository.EstabelecimentoRepository;
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
 * Integration tests for the {@link EstabelecimentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstabelecimentoResourceIT {

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estabelecimentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstabelecimentoMockMvc;

    private Estabelecimento estabelecimento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estabelecimento createEntity(EntityManager em) {
        Estabelecimento estabelecimento = new Estabelecimento().endereco(DEFAULT_ENDERECO).nome(DEFAULT_NOME);
        return estabelecimento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estabelecimento createUpdatedEntity(EntityManager em) {
        Estabelecimento estabelecimento = new Estabelecimento().endereco(UPDATED_ENDERECO).nome(UPDATED_NOME);
        return estabelecimento;
    }

    @BeforeEach
    public void initTest() {
        estabelecimento = createEntity(em);
    }

    @Test
    @Transactional
    void createEstabelecimento() throws Exception {
        int databaseSizeBeforeCreate = estabelecimentoRepository.findAll().size();
        // Create the Estabelecimento
        restEstabelecimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isCreated());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Estabelecimento testEstabelecimento = estabelecimentoList.get(estabelecimentoList.size() - 1);
        assertThat(testEstabelecimento.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testEstabelecimento.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createEstabelecimentoWithExistingId() throws Exception {
        // Create the Estabelecimento with an existing ID
        estabelecimento.setId(1L);

        int databaseSizeBeforeCreate = estabelecimentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstabelecimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstabelecimentos() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get all the estabelecimentoList
        restEstabelecimentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estabelecimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getEstabelecimento() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        // Get the estabelecimento
        restEstabelecimentoMockMvc
            .perform(get(ENTITY_API_URL_ID, estabelecimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estabelecimento.getId().intValue()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingEstabelecimento() throws Exception {
        // Get the estabelecimento
        restEstabelecimentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEstabelecimento() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();

        // Update the estabelecimento
        Estabelecimento updatedEstabelecimento = estabelecimentoRepository.findById(estabelecimento.getId()).get();
        // Disconnect from session so that the updates on updatedEstabelecimento are not directly saved in db
        em.detach(updatedEstabelecimento);
        updatedEstabelecimento.endereco(UPDATED_ENDERECO).nome(UPDATED_NOME);

        restEstabelecimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstabelecimento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEstabelecimento))
            )
            .andExpect(status().isOk());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
        Estabelecimento testEstabelecimento = estabelecimentoList.get(estabelecimentoList.size() - 1);
        assertThat(testEstabelecimento.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testEstabelecimento.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingEstabelecimento() throws Exception {
        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();
        estabelecimento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstabelecimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estabelecimento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstabelecimento() throws Exception {
        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();
        estabelecimento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstabelecimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstabelecimento() throws Exception {
        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();
        estabelecimento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstabelecimentoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstabelecimentoWithPatch() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();

        // Update the estabelecimento using partial update
        Estabelecimento partialUpdatedEstabelecimento = new Estabelecimento();
        partialUpdatedEstabelecimento.setId(estabelecimento.getId());

        partialUpdatedEstabelecimento.nome(UPDATED_NOME);

        restEstabelecimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstabelecimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstabelecimento))
            )
            .andExpect(status().isOk());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
        Estabelecimento testEstabelecimento = estabelecimentoList.get(estabelecimentoList.size() - 1);
        assertThat(testEstabelecimento.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testEstabelecimento.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void fullUpdateEstabelecimentoWithPatch() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();

        // Update the estabelecimento using partial update
        Estabelecimento partialUpdatedEstabelecimento = new Estabelecimento();
        partialUpdatedEstabelecimento.setId(estabelecimento.getId());

        partialUpdatedEstabelecimento.endereco(UPDATED_ENDERECO).nome(UPDATED_NOME);

        restEstabelecimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstabelecimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstabelecimento))
            )
            .andExpect(status().isOk());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
        Estabelecimento testEstabelecimento = estabelecimentoList.get(estabelecimentoList.size() - 1);
        assertThat(testEstabelecimento.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testEstabelecimento.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingEstabelecimento() throws Exception {
        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();
        estabelecimento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstabelecimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estabelecimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstabelecimento() throws Exception {
        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();
        estabelecimento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstabelecimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstabelecimento() throws Exception {
        int databaseSizeBeforeUpdate = estabelecimentoRepository.findAll().size();
        estabelecimento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstabelecimentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estabelecimento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estabelecimento in the database
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstabelecimento() throws Exception {
        // Initialize the database
        estabelecimentoRepository.saveAndFlush(estabelecimento);

        int databaseSizeBeforeDelete = estabelecimentoRepository.findAll().size();

        // Delete the estabelecimento
        restEstabelecimentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, estabelecimento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estabelecimento> estabelecimentoList = estabelecimentoRepository.findAll();
        assertThat(estabelecimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
