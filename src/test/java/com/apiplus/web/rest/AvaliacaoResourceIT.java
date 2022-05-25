package com.apiplus.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apiplus.IntegrationTest;
import com.apiplus.domain.Avaliacao;
import com.apiplus.repository.AvaliacaoRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link AvaliacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AvaliacaoResourceIT {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Double DEFAULT_NOTA = 1D;
    private static final Double UPDATED_NOTA = 2D;

    private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ACESSO_ENTRADA_PRINCIPAL = false;
    private static final Boolean UPDATED_ACESSO_ENTRADA_PRINCIPAL = true;

    private static final Boolean DEFAULT_ACESSO_BANHEIRO = false;
    private static final Boolean UPDATED_ACESSO_BANHEIRO = true;

    private static final Boolean DEFAULT_ACESSO_AUDIO_VISUAL = false;
    private static final Boolean UPDATED_ACESSO_AUDIO_VISUAL = true;

    private static final Boolean DEFAULT_ACESSO_CIRCULACAO_INTERNA = false;
    private static final Boolean UPDATED_ACESSO_CIRCULACAO_INTERNA = true;

    private static final Boolean DEFAULT_ACESSO_ESTACIONAMENTO = false;
    private static final Boolean UPDATED_ACESSO_ESTACIONAMENTO = true;

    private static final Boolean DEFAULT_ACESSO_CALCADA = false;
    private static final Boolean UPDATED_ACESSO_CALCADA = true;

    private static final String ENTITY_API_URL = "/api/avaliacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvaliacaoMockMvc;

    private Avaliacao avaliacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avaliacao createEntity(EntityManager em) {
        Avaliacao avaliacao = new Avaliacao()
            .comentario(DEFAULT_COMENTARIO)
            .nota(DEFAULT_NOTA)
            .imagem(DEFAULT_IMAGEM)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE)
            .acessoEntradaPrincipal(DEFAULT_ACESSO_ENTRADA_PRINCIPAL)
            .acessoBanheiro(DEFAULT_ACESSO_BANHEIRO)
            .acessoAudioVisual(DEFAULT_ACESSO_AUDIO_VISUAL)
            .acessoCirculacaoInterna(DEFAULT_ACESSO_CIRCULACAO_INTERNA)
            .acessoEstacionamento(DEFAULT_ACESSO_ESTACIONAMENTO)
            .acessoCalcada(DEFAULT_ACESSO_CALCADA);
        return avaliacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avaliacao createUpdatedEntity(EntityManager em) {
        Avaliacao avaliacao = new Avaliacao()
            .comentario(UPDATED_COMENTARIO)
            .nota(UPDATED_NOTA)
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .acessoEntradaPrincipal(UPDATED_ACESSO_ENTRADA_PRINCIPAL)
            .acessoBanheiro(UPDATED_ACESSO_BANHEIRO)
            .acessoAudioVisual(UPDATED_ACESSO_AUDIO_VISUAL)
            .acessoCirculacaoInterna(UPDATED_ACESSO_CIRCULACAO_INTERNA)
            .acessoEstacionamento(UPDATED_ACESSO_ESTACIONAMENTO)
            .acessoCalcada(UPDATED_ACESSO_CALCADA);
        return avaliacao;
    }

    @BeforeEach
    public void initTest() {
        avaliacao = createEntity(em);
    }

    @Test
    @Transactional
    void createAvaliacao() throws Exception {
        int databaseSizeBeforeCreate = avaliacaoRepository.findAll().size();
        // Create the Avaliacao
        restAvaliacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacao)))
            .andExpect(status().isCreated());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Avaliacao testAvaliacao = avaliacaoList.get(avaliacaoList.size() - 1);
        assertThat(testAvaliacao.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testAvaliacao.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testAvaliacao.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testAvaliacao.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testAvaliacao.getAcessoEntradaPrincipal()).isEqualTo(DEFAULT_ACESSO_ENTRADA_PRINCIPAL);
        assertThat(testAvaliacao.getAcessoBanheiro()).isEqualTo(DEFAULT_ACESSO_BANHEIRO);
        assertThat(testAvaliacao.getAcessoAudioVisual()).isEqualTo(DEFAULT_ACESSO_AUDIO_VISUAL);
        assertThat(testAvaliacao.getAcessoCirculacaoInterna()).isEqualTo(DEFAULT_ACESSO_CIRCULACAO_INTERNA);
        assertThat(testAvaliacao.getAcessoEstacionamento()).isEqualTo(DEFAULT_ACESSO_ESTACIONAMENTO);
        assertThat(testAvaliacao.getAcessoCalcada()).isEqualTo(DEFAULT_ACESSO_CALCADA);
    }

    @Test
    @Transactional
    void createAvaliacaoWithExistingId() throws Exception {
        // Create the Avaliacao with an existing ID
        avaliacao.setId(1L);

        int databaseSizeBeforeCreate = avaliacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvaliacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacao)))
            .andExpect(status().isBadRequest());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAvaliacaos() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList
        restAvaliacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avaliacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA.doubleValue())))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].acessoEntradaPrincipal").value(hasItem(DEFAULT_ACESSO_ENTRADA_PRINCIPAL.booleanValue())))
            .andExpect(jsonPath("$.[*].acessoBanheiro").value(hasItem(DEFAULT_ACESSO_BANHEIRO.booleanValue())))
            .andExpect(jsonPath("$.[*].acessoAudioVisual").value(hasItem(DEFAULT_ACESSO_AUDIO_VISUAL.booleanValue())))
            .andExpect(jsonPath("$.[*].acessoCirculacaoInterna").value(hasItem(DEFAULT_ACESSO_CIRCULACAO_INTERNA.booleanValue())))
            .andExpect(jsonPath("$.[*].acessoEstacionamento").value(hasItem(DEFAULT_ACESSO_ESTACIONAMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].acessoCalcada").value(hasItem(DEFAULT_ACESSO_CALCADA.booleanValue())));
    }

    @Test
    @Transactional
    void getAvaliacao() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get the avaliacao
        restAvaliacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, avaliacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avaliacao.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA.doubleValue()))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.acessoEntradaPrincipal").value(DEFAULT_ACESSO_ENTRADA_PRINCIPAL.booleanValue()))
            .andExpect(jsonPath("$.acessoBanheiro").value(DEFAULT_ACESSO_BANHEIRO.booleanValue()))
            .andExpect(jsonPath("$.acessoAudioVisual").value(DEFAULT_ACESSO_AUDIO_VISUAL.booleanValue()))
            .andExpect(jsonPath("$.acessoCirculacaoInterna").value(DEFAULT_ACESSO_CIRCULACAO_INTERNA.booleanValue()))
            .andExpect(jsonPath("$.acessoEstacionamento").value(DEFAULT_ACESSO_ESTACIONAMENTO.booleanValue()))
            .andExpect(jsonPath("$.acessoCalcada").value(DEFAULT_ACESSO_CALCADA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAvaliacao() throws Exception {
        // Get the avaliacao
        restAvaliacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAvaliacao() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();

        // Update the avaliacao
        Avaliacao updatedAvaliacao = avaliacaoRepository.findById(avaliacao.getId()).get();
        // Disconnect from session so that the updates on updatedAvaliacao are not directly saved in db
        em.detach(updatedAvaliacao);
        updatedAvaliacao
            .comentario(UPDATED_COMENTARIO)
            .nota(UPDATED_NOTA)
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .acessoEntradaPrincipal(UPDATED_ACESSO_ENTRADA_PRINCIPAL)
            .acessoBanheiro(UPDATED_ACESSO_BANHEIRO)
            .acessoAudioVisual(UPDATED_ACESSO_AUDIO_VISUAL)
            .acessoCirculacaoInterna(UPDATED_ACESSO_CIRCULACAO_INTERNA)
            .acessoEstacionamento(UPDATED_ACESSO_ESTACIONAMENTO)
            .acessoCalcada(UPDATED_ACESSO_CALCADA);

        restAvaliacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAvaliacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAvaliacao))
            )
            .andExpect(status().isOk());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
        Avaliacao testAvaliacao = avaliacaoList.get(avaliacaoList.size() - 1);
        assertThat(testAvaliacao.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testAvaliacao.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testAvaliacao.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testAvaliacao.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testAvaliacao.getAcessoEntradaPrincipal()).isEqualTo(UPDATED_ACESSO_ENTRADA_PRINCIPAL);
        assertThat(testAvaliacao.getAcessoBanheiro()).isEqualTo(UPDATED_ACESSO_BANHEIRO);
        assertThat(testAvaliacao.getAcessoAudioVisual()).isEqualTo(UPDATED_ACESSO_AUDIO_VISUAL);
        assertThat(testAvaliacao.getAcessoCirculacaoInterna()).isEqualTo(UPDATED_ACESSO_CIRCULACAO_INTERNA);
        assertThat(testAvaliacao.getAcessoEstacionamento()).isEqualTo(UPDATED_ACESSO_ESTACIONAMENTO);
        assertThat(testAvaliacao.getAcessoCalcada()).isEqualTo(UPDATED_ACESSO_CALCADA);
    }

    @Test
    @Transactional
    void putNonExistingAvaliacao() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();
        avaliacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvaliacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avaliacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avaliacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAvaliacao() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();
        avaliacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avaliacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAvaliacao() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();
        avaliacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAvaliacaoWithPatch() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();

        // Update the avaliacao using partial update
        Avaliacao partialUpdatedAvaliacao = new Avaliacao();
        partialUpdatedAvaliacao.setId(avaliacao.getId());

        partialUpdatedAvaliacao
            .comentario(UPDATED_COMENTARIO)
            .nota(UPDATED_NOTA)
            .acessoEntradaPrincipal(UPDATED_ACESSO_ENTRADA_PRINCIPAL)
            .acessoCirculacaoInterna(UPDATED_ACESSO_CIRCULACAO_INTERNA)
            .acessoEstacionamento(UPDATED_ACESSO_ESTACIONAMENTO);

        restAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvaliacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvaliacao))
            )
            .andExpect(status().isOk());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
        Avaliacao testAvaliacao = avaliacaoList.get(avaliacaoList.size() - 1);
        assertThat(testAvaliacao.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testAvaliacao.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testAvaliacao.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testAvaliacao.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testAvaliacao.getAcessoEntradaPrincipal()).isEqualTo(UPDATED_ACESSO_ENTRADA_PRINCIPAL);
        assertThat(testAvaliacao.getAcessoBanheiro()).isEqualTo(DEFAULT_ACESSO_BANHEIRO);
        assertThat(testAvaliacao.getAcessoAudioVisual()).isEqualTo(DEFAULT_ACESSO_AUDIO_VISUAL);
        assertThat(testAvaliacao.getAcessoCirculacaoInterna()).isEqualTo(UPDATED_ACESSO_CIRCULACAO_INTERNA);
        assertThat(testAvaliacao.getAcessoEstacionamento()).isEqualTo(UPDATED_ACESSO_ESTACIONAMENTO);
        assertThat(testAvaliacao.getAcessoCalcada()).isEqualTo(DEFAULT_ACESSO_CALCADA);
    }

    @Test
    @Transactional
    void fullUpdateAvaliacaoWithPatch() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();

        // Update the avaliacao using partial update
        Avaliacao partialUpdatedAvaliacao = new Avaliacao();
        partialUpdatedAvaliacao.setId(avaliacao.getId());

        partialUpdatedAvaliacao
            .comentario(UPDATED_COMENTARIO)
            .nota(UPDATED_NOTA)
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .acessoEntradaPrincipal(UPDATED_ACESSO_ENTRADA_PRINCIPAL)
            .acessoBanheiro(UPDATED_ACESSO_BANHEIRO)
            .acessoAudioVisual(UPDATED_ACESSO_AUDIO_VISUAL)
            .acessoCirculacaoInterna(UPDATED_ACESSO_CIRCULACAO_INTERNA)
            .acessoEstacionamento(UPDATED_ACESSO_ESTACIONAMENTO)
            .acessoCalcada(UPDATED_ACESSO_CALCADA);

        restAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvaliacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvaliacao))
            )
            .andExpect(status().isOk());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
        Avaliacao testAvaliacao = avaliacaoList.get(avaliacaoList.size() - 1);
        assertThat(testAvaliacao.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testAvaliacao.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testAvaliacao.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testAvaliacao.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testAvaliacao.getAcessoEntradaPrincipal()).isEqualTo(UPDATED_ACESSO_ENTRADA_PRINCIPAL);
        assertThat(testAvaliacao.getAcessoBanheiro()).isEqualTo(UPDATED_ACESSO_BANHEIRO);
        assertThat(testAvaliacao.getAcessoAudioVisual()).isEqualTo(UPDATED_ACESSO_AUDIO_VISUAL);
        assertThat(testAvaliacao.getAcessoCirculacaoInterna()).isEqualTo(UPDATED_ACESSO_CIRCULACAO_INTERNA);
        assertThat(testAvaliacao.getAcessoEstacionamento()).isEqualTo(UPDATED_ACESSO_ESTACIONAMENTO);
        assertThat(testAvaliacao.getAcessoCalcada()).isEqualTo(UPDATED_ACESSO_CALCADA);
    }

    @Test
    @Transactional
    void patchNonExistingAvaliacao() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();
        avaliacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, avaliacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avaliacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAvaliacao() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();
        avaliacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avaliacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAvaliacao() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();
        avaliacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(avaliacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAvaliacao() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        int databaseSizeBeforeDelete = avaliacaoRepository.findAll().size();

        // Delete the avaliacao
        restAvaliacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, avaliacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
