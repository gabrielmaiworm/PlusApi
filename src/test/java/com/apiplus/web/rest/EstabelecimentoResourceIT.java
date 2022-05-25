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
import org.springframework.util.Base64Utils;

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

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final String DEFAULT_NOTA_MEDIA = "AAAAAAAAAA";
    private static final String UPDATED_NOTA_MEDIA = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIZACAO = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIZACAO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_PRECO_FILTER = 1;
    private static final Integer UPDATED_PRECO_FILTER = 2;

    private static final String DEFAULT_PLACE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

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
        Estabelecimento estabelecimento = new Estabelecimento()
            .endereco(DEFAULT_ENDERECO)
            .nome(DEFAULT_NOME)
            .telefone(DEFAULT_TELEFONE)
            .categoria(DEFAULT_CATEGORIA)
            .notaMedia(DEFAULT_NOTA_MEDIA)
            .localizacao(DEFAULT_LOCALIZACAO)
            .imagem(DEFAULT_IMAGEM)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE)
            .precoFilter(DEFAULT_PRECO_FILTER)
            .placeId(DEFAULT_PLACE_ID)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return estabelecimento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estabelecimento createUpdatedEntity(EntityManager em) {
        Estabelecimento estabelecimento = new Estabelecimento()
            .endereco(UPDATED_ENDERECO)
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .categoria(UPDATED_CATEGORIA)
            .notaMedia(UPDATED_NOTA_MEDIA)
            .localizacao(UPDATED_LOCALIZACAO)
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .precoFilter(UPDATED_PRECO_FILTER)
            .placeId(UPDATED_PLACE_ID)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
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
        assertThat(testEstabelecimento.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testEstabelecimento.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testEstabelecimento.getNotaMedia()).isEqualTo(DEFAULT_NOTA_MEDIA);
        assertThat(testEstabelecimento.getLocalizacao()).isEqualTo(DEFAULT_LOCALIZACAO);
        assertThat(testEstabelecimento.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testEstabelecimento.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testEstabelecimento.getPrecoFilter()).isEqualTo(DEFAULT_PRECO_FILTER);
        assertThat(testEstabelecimento.getPlaceId()).isEqualTo(DEFAULT_PLACE_ID);
        assertThat(testEstabelecimento.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testEstabelecimento.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
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
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].notaMedia").value(hasItem(DEFAULT_NOTA_MEDIA)))
            .andExpect(jsonPath("$.[*].localizacao").value(hasItem(DEFAULT_LOCALIZACAO)))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].precoFilter").value(hasItem(DEFAULT_PRECO_FILTER)))
            .andExpect(jsonPath("$.[*].placeId").value(hasItem(DEFAULT_PLACE_ID)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
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
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA))
            .andExpect(jsonPath("$.notaMedia").value(DEFAULT_NOTA_MEDIA))
            .andExpect(jsonPath("$.localizacao").value(DEFAULT_LOCALIZACAO))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.precoFilter").value(DEFAULT_PRECO_FILTER))
            .andExpect(jsonPath("$.placeId").value(DEFAULT_PLACE_ID))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
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
        updatedEstabelecimento
            .endereco(UPDATED_ENDERECO)
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .categoria(UPDATED_CATEGORIA)
            .notaMedia(UPDATED_NOTA_MEDIA)
            .localizacao(UPDATED_LOCALIZACAO)
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .precoFilter(UPDATED_PRECO_FILTER)
            .placeId(UPDATED_PLACE_ID)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

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
        assertThat(testEstabelecimento.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testEstabelecimento.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testEstabelecimento.getNotaMedia()).isEqualTo(UPDATED_NOTA_MEDIA);
        assertThat(testEstabelecimento.getLocalizacao()).isEqualTo(UPDATED_LOCALIZACAO);
        assertThat(testEstabelecimento.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testEstabelecimento.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testEstabelecimento.getPrecoFilter()).isEqualTo(UPDATED_PRECO_FILTER);
        assertThat(testEstabelecimento.getPlaceId()).isEqualTo(UPDATED_PLACE_ID);
        assertThat(testEstabelecimento.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEstabelecimento.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
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

        partialUpdatedEstabelecimento
            .nome(UPDATED_NOME)
            .categoria(UPDATED_CATEGORIA)
            .notaMedia(UPDATED_NOTA_MEDIA)
            .localizacao(UPDATED_LOCALIZACAO)
            .precoFilter(UPDATED_PRECO_FILTER)
            .placeId(UPDATED_PLACE_ID)
            .latitude(UPDATED_LATITUDE);

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
        assertThat(testEstabelecimento.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testEstabelecimento.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testEstabelecimento.getNotaMedia()).isEqualTo(UPDATED_NOTA_MEDIA);
        assertThat(testEstabelecimento.getLocalizacao()).isEqualTo(UPDATED_LOCALIZACAO);
        assertThat(testEstabelecimento.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testEstabelecimento.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testEstabelecimento.getPrecoFilter()).isEqualTo(UPDATED_PRECO_FILTER);
        assertThat(testEstabelecimento.getPlaceId()).isEqualTo(UPDATED_PLACE_ID);
        assertThat(testEstabelecimento.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEstabelecimento.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
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

        partialUpdatedEstabelecimento
            .endereco(UPDATED_ENDERECO)
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .categoria(UPDATED_CATEGORIA)
            .notaMedia(UPDATED_NOTA_MEDIA)
            .localizacao(UPDATED_LOCALIZACAO)
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .precoFilter(UPDATED_PRECO_FILTER)
            .placeId(UPDATED_PLACE_ID)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

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
        assertThat(testEstabelecimento.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testEstabelecimento.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testEstabelecimento.getNotaMedia()).isEqualTo(UPDATED_NOTA_MEDIA);
        assertThat(testEstabelecimento.getLocalizacao()).isEqualTo(UPDATED_LOCALIZACAO);
        assertThat(testEstabelecimento.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testEstabelecimento.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testEstabelecimento.getPrecoFilter()).isEqualTo(UPDATED_PRECO_FILTER);
        assertThat(testEstabelecimento.getPlaceId()).isEqualTo(UPDATED_PLACE_ID);
        assertThat(testEstabelecimento.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEstabelecimento.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
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
