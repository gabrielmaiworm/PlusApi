package com.apiplus.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apiplus.IntegrationTest;
import com.apiplus.domain.InfoAdicional;
import com.apiplus.domain.enumeration.Deficiencia;
import com.apiplus.repository.InfoAdicionalRepository;
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
 * Integration tests for the {@link InfoAdicionalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InfoAdicionalResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final Deficiencia DEFAULT_DEFICIENCIA = Deficiencia.AUDITIVA;
    private static final Deficiencia UPDATED_DEFICIENCIA = Deficiencia.VISUAL;

    private static final String ENTITY_API_URL = "/api/info-adicionals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InfoAdicionalRepository infoAdicionalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInfoAdicionalMockMvc;

    private InfoAdicional infoAdicional;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoAdicional createEntity(EntityManager em) {
        InfoAdicional infoAdicional = new InfoAdicional()
            .nome(DEFAULT_NOME)
            .cpf(DEFAULT_CPF)
            .telefone(DEFAULT_TELEFONE)
            .deficiencia(DEFAULT_DEFICIENCIA);
        return infoAdicional;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InfoAdicional createUpdatedEntity(EntityManager em) {
        InfoAdicional infoAdicional = new InfoAdicional()
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .telefone(UPDATED_TELEFONE)
            .deficiencia(UPDATED_DEFICIENCIA);
        return infoAdicional;
    }

    @BeforeEach
    public void initTest() {
        infoAdicional = createEntity(em);
    }

    @Test
    @Transactional
    void createInfoAdicional() throws Exception {
        int databaseSizeBeforeCreate = infoAdicionalRepository.findAll().size();
        // Create the InfoAdicional
        restInfoAdicionalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infoAdicional)))
            .andExpect(status().isCreated());

        // Validate the InfoAdicional in the database
        List<InfoAdicional> infoAdicionalList = infoAdicionalRepository.findAll();
        assertThat(infoAdicionalList).hasSize(databaseSizeBeforeCreate + 1);
        InfoAdicional testInfoAdicional = infoAdicionalList.get(infoAdicionalList.size() - 1);
        assertThat(testInfoAdicional.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testInfoAdicional.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testInfoAdicional.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testInfoAdicional.getDeficiencia()).isEqualTo(DEFAULT_DEFICIENCIA);
    }

    @Test
    @Transactional
    void createInfoAdicionalWithExistingId() throws Exception {
        // Create the InfoAdicional with an existing ID
        infoAdicional.setId(1L);

        int databaseSizeBeforeCreate = infoAdicionalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfoAdicionalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infoAdicional)))
            .andExpect(status().isBadRequest());

        // Validate the InfoAdicional in the database
        List<InfoAdicional> infoAdicionalList = infoAdicionalRepository.findAll();
        assertThat(infoAdicionalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInfoAdicionals() throws Exception {
        // Initialize the database
        infoAdicionalRepository.saveAndFlush(infoAdicional);

        // Get all the infoAdicionalList
        restInfoAdicionalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoAdicional.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].deficiencia").value(hasItem(DEFAULT_DEFICIENCIA.toString())));
    }

    @Test
    @Transactional
    void getInfoAdicional() throws Exception {
        // Initialize the database
        infoAdicionalRepository.saveAndFlush(infoAdicional);

        // Get the infoAdicional
        restInfoAdicionalMockMvc
            .perform(get(ENTITY_API_URL_ID, infoAdicional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(infoAdicional.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.deficiencia").value(DEFAULT_DEFICIENCIA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingInfoAdicional() throws Exception {
        // Get the infoAdicional
        restInfoAdicionalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInfoAdicional() throws Exception {
        // Initialize the database
        infoAdicionalRepository.saveAndFlush(infoAdicional);

        int databaseSizeBeforeUpdate = infoAdicionalRepository.findAll().size();

        // Update the infoAdicional
        InfoAdicional updatedInfoAdicional = infoAdicionalRepository.findById(infoAdicional.getId()).get();
        // Disconnect from session so that the updates on updatedInfoAdicional are not directly saved in db
        em.detach(updatedInfoAdicional);
        updatedInfoAdicional.nome(UPDATED_NOME).cpf(UPDATED_CPF).telefone(UPDATED_TELEFONE).deficiencia(UPDATED_DEFICIENCIA);

        restInfoAdicionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInfoAdicional.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInfoAdicional))
            )
            .andExpect(status().isOk());

        // Validate the InfoAdicional in the database
        List<InfoAdicional> infoAdicionalList = infoAdicionalRepository.findAll();
        assertThat(infoAdicionalList).hasSize(databaseSizeBeforeUpdate);
        InfoAdicional testInfoAdicional = infoAdicionalList.get(infoAdicionalList.size() - 1);
        assertThat(testInfoAdicional.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testInfoAdicional.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testInfoAdicional.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testInfoAdicional.getDeficiencia()).isEqualTo(UPDATED_DEFICIENCIA);
    }

    @Test
    @Transactional
    void putNonExistingInfoAdicional() throws Exception {
        int databaseSizeBeforeUpdate = infoAdicionalRepository.findAll().size();
        infoAdicional.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoAdicionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, infoAdicional.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoAdicional))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoAdicional in the database
        List<InfoAdicional> infoAdicionalList = infoAdicionalRepository.findAll();
        assertThat(infoAdicionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInfoAdicional() throws Exception {
        int databaseSizeBeforeUpdate = infoAdicionalRepository.findAll().size();
        infoAdicional.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoAdicionalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infoAdicional))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoAdicional in the database
        List<InfoAdicional> infoAdicionalList = infoAdicionalRepository.findAll();
        assertThat(infoAdicionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInfoAdicional() throws Exception {
        int databaseSizeBeforeUpdate = infoAdicionalRepository.findAll().size();
        infoAdicional.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoAdicionalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infoAdicional)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InfoAdicional in the database
        List<InfoAdicional> infoAdicionalList = infoAdicionalRepository.findAll();
        assertThat(infoAdicionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInfoAdicionalWithPatch() throws Exception {
        // Initialize the database
        infoAdicionalRepository.saveAndFlush(infoAdicional);

        int databaseSizeBeforeUpdate = infoAdicionalRepository.findAll().size();

        // Update the infoAdicional using partial update
        InfoAdicional partialUpdatedInfoAdicional = new InfoAdicional();
        partialUpdatedInfoAdicional.setId(infoAdicional.getId());

        partialUpdatedInfoAdicional.nome(UPDATED_NOME).deficiencia(UPDATED_DEFICIENCIA);

        restInfoAdicionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfoAdicional.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoAdicional))
            )
            .andExpect(status().isOk());

        // Validate the InfoAdicional in the database
        List<InfoAdicional> infoAdicionalList = infoAdicionalRepository.findAll();
        assertThat(infoAdicionalList).hasSize(databaseSizeBeforeUpdate);
        InfoAdicional testInfoAdicional = infoAdicionalList.get(infoAdicionalList.size() - 1);
        assertThat(testInfoAdicional.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testInfoAdicional.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testInfoAdicional.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testInfoAdicional.getDeficiencia()).isEqualTo(UPDATED_DEFICIENCIA);
    }

    @Test
    @Transactional
    void fullUpdateInfoAdicionalWithPatch() throws Exception {
        // Initialize the database
        infoAdicionalRepository.saveAndFlush(infoAdicional);

        int databaseSizeBeforeUpdate = infoAdicionalRepository.findAll().size();

        // Update the infoAdicional using partial update
        InfoAdicional partialUpdatedInfoAdicional = new InfoAdicional();
        partialUpdatedInfoAdicional.setId(infoAdicional.getId());

        partialUpdatedInfoAdicional.nome(UPDATED_NOME).cpf(UPDATED_CPF).telefone(UPDATED_TELEFONE).deficiencia(UPDATED_DEFICIENCIA);

        restInfoAdicionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfoAdicional.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInfoAdicional))
            )
            .andExpect(status().isOk());

        // Validate the InfoAdicional in the database
        List<InfoAdicional> infoAdicionalList = infoAdicionalRepository.findAll();
        assertThat(infoAdicionalList).hasSize(databaseSizeBeforeUpdate);
        InfoAdicional testInfoAdicional = infoAdicionalList.get(infoAdicionalList.size() - 1);
        assertThat(testInfoAdicional.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testInfoAdicional.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testInfoAdicional.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testInfoAdicional.getDeficiencia()).isEqualTo(UPDATED_DEFICIENCIA);
    }

    @Test
    @Transactional
    void patchNonExistingInfoAdicional() throws Exception {
        int databaseSizeBeforeUpdate = infoAdicionalRepository.findAll().size();
        infoAdicional.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoAdicionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, infoAdicional.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infoAdicional))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoAdicional in the database
        List<InfoAdicional> infoAdicionalList = infoAdicionalRepository.findAll();
        assertThat(infoAdicionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInfoAdicional() throws Exception {
        int databaseSizeBeforeUpdate = infoAdicionalRepository.findAll().size();
        infoAdicional.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoAdicionalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infoAdicional))
            )
            .andExpect(status().isBadRequest());

        // Validate the InfoAdicional in the database
        List<InfoAdicional> infoAdicionalList = infoAdicionalRepository.findAll();
        assertThat(infoAdicionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInfoAdicional() throws Exception {
        int databaseSizeBeforeUpdate = infoAdicionalRepository.findAll().size();
        infoAdicional.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfoAdicionalMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(infoAdicional))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InfoAdicional in the database
        List<InfoAdicional> infoAdicionalList = infoAdicionalRepository.findAll();
        assertThat(infoAdicionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInfoAdicional() throws Exception {
        // Initialize the database
        infoAdicionalRepository.saveAndFlush(infoAdicional);

        int databaseSizeBeforeDelete = infoAdicionalRepository.findAll().size();

        // Delete the infoAdicional
        restInfoAdicionalMockMvc
            .perform(delete(ENTITY_API_URL_ID, infoAdicional.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InfoAdicional> infoAdicionalList = infoAdicionalRepository.findAll();
        assertThat(infoAdicionalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
