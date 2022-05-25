package com.apiplus.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apiplus.IntegrationTest;
import com.apiplus.domain.Funcionario;
import com.apiplus.domain.enumeration.NivelAcesso;
import com.apiplus.repository.FuncionarioRepository;
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
 * Integration tests for the {@link FuncionarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuncionarioResourceIT {

    private static final NivelAcesso DEFAULT_NIVEL_ACESSO = NivelAcesso.ADMINISTRADOR;
    private static final NivelAcesso UPDATED_NIVEL_ACESSO = NivelAcesso.EQUIPE_TECNICA_REMOTA;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRENOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRENOME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/funcionarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuncionarioMockMvc;

    private Funcionario funcionario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionario createEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .nivelAcesso(DEFAULT_NIVEL_ACESSO)
            .nome(DEFAULT_NOME)
            .sobrenome(DEFAULT_SOBRENOME)
            .documento(DEFAULT_DOCUMENTO)
            .telefone(DEFAULT_TELEFONE);
        return funcionario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionario createUpdatedEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .nivelAcesso(UPDATED_NIVEL_ACESSO)
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .documento(UPDATED_DOCUMENTO)
            .telefone(UPDATED_TELEFONE);
        return funcionario;
    }

    @BeforeEach
    public void initTest() {
        funcionario = createEntity(em);
    }

    @Test
    @Transactional
    void createFuncionario() throws Exception {
        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();
        // Create the Funcionario
        restFuncionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionario)))
            .andExpect(status().isCreated());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate + 1);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getNivelAcesso()).isEqualTo(DEFAULT_NIVEL_ACESSO);
        assertThat(testFuncionario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFuncionario.getSobrenome()).isEqualTo(DEFAULT_SOBRENOME);
        assertThat(testFuncionario.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testFuncionario.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    void createFuncionarioWithExistingId() throws Exception {
        // Create the Funcionario with an existing ID
        funcionario.setId(1L);

        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionario)))
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuncionarios() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nivelAcesso").value(hasItem(DEFAULT_NIVEL_ACESSO.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME)))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)));
    }

    @Test
    @Transactional
    void getFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get the funcionario
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL_ID, funcionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funcionario.getId().intValue()))
            .andExpect(jsonPath("$.nivelAcesso").value(DEFAULT_NIVEL_ACESSO.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sobrenome").value(DEFAULT_SOBRENOME))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE));
    }

    @Test
    @Transactional
    void getNonExistingFuncionario() throws Exception {
        // Get the funcionario
        restFuncionarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario
        Funcionario updatedFuncionario = funcionarioRepository.findById(funcionario.getId()).get();
        // Disconnect from session so that the updates on updatedFuncionario are not directly saved in db
        em.detach(updatedFuncionario);
        updatedFuncionario
            .nivelAcesso(UPDATED_NIVEL_ACESSO)
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .documento(UPDATED_DOCUMENTO)
            .telefone(UPDATED_TELEFONE);

        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuncionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getNivelAcesso()).isEqualTo(UPDATED_NIVEL_ACESSO);
        assertThat(testFuncionario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFuncionario.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testFuncionario.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testFuncionario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void putNonExistingFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuncionarioWithPatch() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario using partial update
        Funcionario partialUpdatedFuncionario = new Funcionario();
        partialUpdatedFuncionario.setId(funcionario.getId());

        partialUpdatedFuncionario.sobrenome(UPDATED_SOBRENOME).telefone(UPDATED_TELEFONE);

        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getNivelAcesso()).isEqualTo(DEFAULT_NIVEL_ACESSO);
        assertThat(testFuncionario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFuncionario.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testFuncionario.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testFuncionario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void fullUpdateFuncionarioWithPatch() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario using partial update
        Funcionario partialUpdatedFuncionario = new Funcionario();
        partialUpdatedFuncionario.setId(funcionario.getId());

        partialUpdatedFuncionario
            .nivelAcesso(UPDATED_NIVEL_ACESSO)
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .documento(UPDATED_DOCUMENTO)
            .telefone(UPDATED_TELEFONE);

        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getNivelAcesso()).isEqualTo(UPDATED_NIVEL_ACESSO);
        assertThat(testFuncionario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFuncionario.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testFuncionario.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testFuncionario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void patchNonExistingFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funcionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(funcionario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeDelete = funcionarioRepository.findAll().size();

        // Delete the funcionario
        restFuncionarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, funcionario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
