package com.apiplus.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apiplus.IntegrationTest;
import com.apiplus.domain.Parceiro;
import com.apiplus.repository.ParceiroRepository;
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
 * Integration tests for the {@link ParceiroResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParceiroResourceIT {

    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_FANTASIA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_FANTASIA = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_INSCRICAO_ESTADUAL = "AAAAAAAAAA";
    private static final String UPDATED_INSCRICAO_ESTADUAL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_SERVICO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SERVICO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_GESTOR = "AAAAAAAAAA";
    private static final String UPDATED_NOME_GESTOR = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRENOME_GESTOR = "AAAAAAAAAA";
    private static final String UPDATED_SOBRENOME_GESTOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_DOCUMENTO_GESTOR = 1;
    private static final Integer UPDATED_DOCUMENTO_GESTOR = 2;

    private static final String DEFAULT_TELEFONE_GESTOR = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_GESTOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parceiros";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParceiroRepository parceiroRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParceiroMockMvc;

    private Parceiro parceiro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parceiro createEntity(EntityManager em) {
        Parceiro parceiro = new Parceiro()
            .razaoSocial(DEFAULT_RAZAO_SOCIAL)
            .nomeFantasia(DEFAULT_NOME_FANTASIA)
            .cnpj(DEFAULT_CNPJ)
            .inscricaoEstadual(DEFAULT_INSCRICAO_ESTADUAL)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE)
            .tipoServico(DEFAULT_TIPO_SERVICO)
            .nomeGestor(DEFAULT_NOME_GESTOR)
            .sobrenomeGestor(DEFAULT_SOBRENOME_GESTOR)
            .documentoGestor(DEFAULT_DOCUMENTO_GESTOR)
            .telefoneGestor(DEFAULT_TELEFONE_GESTOR);
        return parceiro;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parceiro createUpdatedEntity(EntityManager em) {
        Parceiro parceiro = new Parceiro()
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .cnpj(UPDATED_CNPJ)
            .inscricaoEstadual(UPDATED_INSCRICAO_ESTADUAL)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .tipoServico(UPDATED_TIPO_SERVICO)
            .nomeGestor(UPDATED_NOME_GESTOR)
            .sobrenomeGestor(UPDATED_SOBRENOME_GESTOR)
            .documentoGestor(UPDATED_DOCUMENTO_GESTOR)
            .telefoneGestor(UPDATED_TELEFONE_GESTOR);
        return parceiro;
    }

    @BeforeEach
    public void initTest() {
        parceiro = createEntity(em);
    }

    @Test
    @Transactional
    void createParceiro() throws Exception {
        int databaseSizeBeforeCreate = parceiroRepository.findAll().size();
        // Create the Parceiro
        restParceiroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parceiro)))
            .andExpect(status().isCreated());

        // Validate the Parceiro in the database
        List<Parceiro> parceiroList = parceiroRepository.findAll();
        assertThat(parceiroList).hasSize(databaseSizeBeforeCreate + 1);
        Parceiro testParceiro = parceiroList.get(parceiroList.size() - 1);
        assertThat(testParceiro.getRazaoSocial()).isEqualTo(DEFAULT_RAZAO_SOCIAL);
        assertThat(testParceiro.getNomeFantasia()).isEqualTo(DEFAULT_NOME_FANTASIA);
        assertThat(testParceiro.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testParceiro.getInscricaoEstadual()).isEqualTo(DEFAULT_INSCRICAO_ESTADUAL);
        assertThat(testParceiro.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testParceiro.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testParceiro.getTipoServico()).isEqualTo(DEFAULT_TIPO_SERVICO);
        assertThat(testParceiro.getNomeGestor()).isEqualTo(DEFAULT_NOME_GESTOR);
        assertThat(testParceiro.getSobrenomeGestor()).isEqualTo(DEFAULT_SOBRENOME_GESTOR);
        assertThat(testParceiro.getDocumentoGestor()).isEqualTo(DEFAULT_DOCUMENTO_GESTOR);
        assertThat(testParceiro.getTelefoneGestor()).isEqualTo(DEFAULT_TELEFONE_GESTOR);
    }

    @Test
    @Transactional
    void createParceiroWithExistingId() throws Exception {
        // Create the Parceiro with an existing ID
        parceiro.setId(1L);

        int databaseSizeBeforeCreate = parceiroRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParceiroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parceiro)))
            .andExpect(status().isBadRequest());

        // Validate the Parceiro in the database
        List<Parceiro> parceiroList = parceiroRepository.findAll();
        assertThat(parceiroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParceiros() throws Exception {
        // Initialize the database
        parceiroRepository.saveAndFlush(parceiro);

        // Get all the parceiroList
        restParceiroMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parceiro.getId().intValue())))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].nomeFantasia").value(hasItem(DEFAULT_NOME_FANTASIA)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].inscricaoEstadual").value(hasItem(DEFAULT_INSCRICAO_ESTADUAL)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].tipoServico").value(hasItem(DEFAULT_TIPO_SERVICO)))
            .andExpect(jsonPath("$.[*].nomeGestor").value(hasItem(DEFAULT_NOME_GESTOR)))
            .andExpect(jsonPath("$.[*].sobrenomeGestor").value(hasItem(DEFAULT_SOBRENOME_GESTOR)))
            .andExpect(jsonPath("$.[*].documentoGestor").value(hasItem(DEFAULT_DOCUMENTO_GESTOR)))
            .andExpect(jsonPath("$.[*].telefoneGestor").value(hasItem(DEFAULT_TELEFONE_GESTOR)));
    }

    @Test
    @Transactional
    void getParceiro() throws Exception {
        // Initialize the database
        parceiroRepository.saveAndFlush(parceiro);

        // Get the parceiro
        restParceiroMockMvc
            .perform(get(ENTITY_API_URL_ID, parceiro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parceiro.getId().intValue()))
            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL))
            .andExpect(jsonPath("$.nomeFantasia").value(DEFAULT_NOME_FANTASIA))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.inscricaoEstadual").value(DEFAULT_INSCRICAO_ESTADUAL))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.tipoServico").value(DEFAULT_TIPO_SERVICO))
            .andExpect(jsonPath("$.nomeGestor").value(DEFAULT_NOME_GESTOR))
            .andExpect(jsonPath("$.sobrenomeGestor").value(DEFAULT_SOBRENOME_GESTOR))
            .andExpect(jsonPath("$.documentoGestor").value(DEFAULT_DOCUMENTO_GESTOR))
            .andExpect(jsonPath("$.telefoneGestor").value(DEFAULT_TELEFONE_GESTOR));
    }

    @Test
    @Transactional
    void getNonExistingParceiro() throws Exception {
        // Get the parceiro
        restParceiroMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewParceiro() throws Exception {
        // Initialize the database
        parceiroRepository.saveAndFlush(parceiro);

        int databaseSizeBeforeUpdate = parceiroRepository.findAll().size();

        // Update the parceiro
        Parceiro updatedParceiro = parceiroRepository.findById(parceiro.getId()).get();
        // Disconnect from session so that the updates on updatedParceiro are not directly saved in db
        em.detach(updatedParceiro);
        updatedParceiro
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .cnpj(UPDATED_CNPJ)
            .inscricaoEstadual(UPDATED_INSCRICAO_ESTADUAL)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .tipoServico(UPDATED_TIPO_SERVICO)
            .nomeGestor(UPDATED_NOME_GESTOR)
            .sobrenomeGestor(UPDATED_SOBRENOME_GESTOR)
            .documentoGestor(UPDATED_DOCUMENTO_GESTOR)
            .telefoneGestor(UPDATED_TELEFONE_GESTOR);

        restParceiroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParceiro.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedParceiro))
            )
            .andExpect(status().isOk());

        // Validate the Parceiro in the database
        List<Parceiro> parceiroList = parceiroRepository.findAll();
        assertThat(parceiroList).hasSize(databaseSizeBeforeUpdate);
        Parceiro testParceiro = parceiroList.get(parceiroList.size() - 1);
        assertThat(testParceiro.getRazaoSocial()).isEqualTo(UPDATED_RAZAO_SOCIAL);
        assertThat(testParceiro.getNomeFantasia()).isEqualTo(UPDATED_NOME_FANTASIA);
        assertThat(testParceiro.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testParceiro.getInscricaoEstadual()).isEqualTo(UPDATED_INSCRICAO_ESTADUAL);
        assertThat(testParceiro.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testParceiro.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testParceiro.getTipoServico()).isEqualTo(UPDATED_TIPO_SERVICO);
        assertThat(testParceiro.getNomeGestor()).isEqualTo(UPDATED_NOME_GESTOR);
        assertThat(testParceiro.getSobrenomeGestor()).isEqualTo(UPDATED_SOBRENOME_GESTOR);
        assertThat(testParceiro.getDocumentoGestor()).isEqualTo(UPDATED_DOCUMENTO_GESTOR);
        assertThat(testParceiro.getTelefoneGestor()).isEqualTo(UPDATED_TELEFONE_GESTOR);
    }

    @Test
    @Transactional
    void putNonExistingParceiro() throws Exception {
        int databaseSizeBeforeUpdate = parceiroRepository.findAll().size();
        parceiro.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParceiroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parceiro.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parceiro))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parceiro in the database
        List<Parceiro> parceiroList = parceiroRepository.findAll();
        assertThat(parceiroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParceiro() throws Exception {
        int databaseSizeBeforeUpdate = parceiroRepository.findAll().size();
        parceiro.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParceiroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parceiro))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parceiro in the database
        List<Parceiro> parceiroList = parceiroRepository.findAll();
        assertThat(parceiroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParceiro() throws Exception {
        int databaseSizeBeforeUpdate = parceiroRepository.findAll().size();
        parceiro.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParceiroMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parceiro)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parceiro in the database
        List<Parceiro> parceiroList = parceiroRepository.findAll();
        assertThat(parceiroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParceiroWithPatch() throws Exception {
        // Initialize the database
        parceiroRepository.saveAndFlush(parceiro);

        int databaseSizeBeforeUpdate = parceiroRepository.findAll().size();

        // Update the parceiro using partial update
        Parceiro partialUpdatedParceiro = new Parceiro();
        partialUpdatedParceiro.setId(parceiro.getId());

        partialUpdatedParceiro
            .inscricaoEstadual(UPDATED_INSCRICAO_ESTADUAL)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .sobrenomeGestor(UPDATED_SOBRENOME_GESTOR)
            .documentoGestor(UPDATED_DOCUMENTO_GESTOR);

        restParceiroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParceiro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParceiro))
            )
            .andExpect(status().isOk());

        // Validate the Parceiro in the database
        List<Parceiro> parceiroList = parceiroRepository.findAll();
        assertThat(parceiroList).hasSize(databaseSizeBeforeUpdate);
        Parceiro testParceiro = parceiroList.get(parceiroList.size() - 1);
        assertThat(testParceiro.getRazaoSocial()).isEqualTo(DEFAULT_RAZAO_SOCIAL);
        assertThat(testParceiro.getNomeFantasia()).isEqualTo(DEFAULT_NOME_FANTASIA);
        assertThat(testParceiro.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testParceiro.getInscricaoEstadual()).isEqualTo(UPDATED_INSCRICAO_ESTADUAL);
        assertThat(testParceiro.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testParceiro.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testParceiro.getTipoServico()).isEqualTo(DEFAULT_TIPO_SERVICO);
        assertThat(testParceiro.getNomeGestor()).isEqualTo(DEFAULT_NOME_GESTOR);
        assertThat(testParceiro.getSobrenomeGestor()).isEqualTo(UPDATED_SOBRENOME_GESTOR);
        assertThat(testParceiro.getDocumentoGestor()).isEqualTo(UPDATED_DOCUMENTO_GESTOR);
        assertThat(testParceiro.getTelefoneGestor()).isEqualTo(DEFAULT_TELEFONE_GESTOR);
    }

    @Test
    @Transactional
    void fullUpdateParceiroWithPatch() throws Exception {
        // Initialize the database
        parceiroRepository.saveAndFlush(parceiro);

        int databaseSizeBeforeUpdate = parceiroRepository.findAll().size();

        // Update the parceiro using partial update
        Parceiro partialUpdatedParceiro = new Parceiro();
        partialUpdatedParceiro.setId(parceiro.getId());

        partialUpdatedParceiro
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .cnpj(UPDATED_CNPJ)
            .inscricaoEstadual(UPDATED_INSCRICAO_ESTADUAL)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .tipoServico(UPDATED_TIPO_SERVICO)
            .nomeGestor(UPDATED_NOME_GESTOR)
            .sobrenomeGestor(UPDATED_SOBRENOME_GESTOR)
            .documentoGestor(UPDATED_DOCUMENTO_GESTOR)
            .telefoneGestor(UPDATED_TELEFONE_GESTOR);

        restParceiroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParceiro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParceiro))
            )
            .andExpect(status().isOk());

        // Validate the Parceiro in the database
        List<Parceiro> parceiroList = parceiroRepository.findAll();
        assertThat(parceiroList).hasSize(databaseSizeBeforeUpdate);
        Parceiro testParceiro = parceiroList.get(parceiroList.size() - 1);
        assertThat(testParceiro.getRazaoSocial()).isEqualTo(UPDATED_RAZAO_SOCIAL);
        assertThat(testParceiro.getNomeFantasia()).isEqualTo(UPDATED_NOME_FANTASIA);
        assertThat(testParceiro.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testParceiro.getInscricaoEstadual()).isEqualTo(UPDATED_INSCRICAO_ESTADUAL);
        assertThat(testParceiro.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testParceiro.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testParceiro.getTipoServico()).isEqualTo(UPDATED_TIPO_SERVICO);
        assertThat(testParceiro.getNomeGestor()).isEqualTo(UPDATED_NOME_GESTOR);
        assertThat(testParceiro.getSobrenomeGestor()).isEqualTo(UPDATED_SOBRENOME_GESTOR);
        assertThat(testParceiro.getDocumentoGestor()).isEqualTo(UPDATED_DOCUMENTO_GESTOR);
        assertThat(testParceiro.getTelefoneGestor()).isEqualTo(UPDATED_TELEFONE_GESTOR);
    }

    @Test
    @Transactional
    void patchNonExistingParceiro() throws Exception {
        int databaseSizeBeforeUpdate = parceiroRepository.findAll().size();
        parceiro.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParceiroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parceiro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parceiro))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parceiro in the database
        List<Parceiro> parceiroList = parceiroRepository.findAll();
        assertThat(parceiroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParceiro() throws Exception {
        int databaseSizeBeforeUpdate = parceiroRepository.findAll().size();
        parceiro.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParceiroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parceiro))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parceiro in the database
        List<Parceiro> parceiroList = parceiroRepository.findAll();
        assertThat(parceiroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParceiro() throws Exception {
        int databaseSizeBeforeUpdate = parceiroRepository.findAll().size();
        parceiro.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParceiroMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(parceiro)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parceiro in the database
        List<Parceiro> parceiroList = parceiroRepository.findAll();
        assertThat(parceiroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParceiro() throws Exception {
        // Initialize the database
        parceiroRepository.saveAndFlush(parceiro);

        int databaseSizeBeforeDelete = parceiroRepository.findAll().size();

        // Delete the parceiro
        restParceiroMockMvc
            .perform(delete(ENTITY_API_URL_ID, parceiro.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Parceiro> parceiroList = parceiroRepository.findAll();
        assertThat(parceiroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
