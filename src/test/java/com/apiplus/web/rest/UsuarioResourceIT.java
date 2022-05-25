package com.apiplus.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apiplus.IntegrationTest;
import com.apiplus.domain.Usuario;
import com.apiplus.domain.enumeration.Lesao;
import com.apiplus.domain.enumeration.Situacao;
import com.apiplus.domain.enumeration.Uso;
import com.apiplus.repository.UsuarioRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link UsuarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UsuarioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRENOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRENOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DOCUMENTO = 1;
    private static final Integer UPDATED_DOCUMENTO = 2;

    private static final Lesao DEFAULT_LESAO = Lesao.PARAPLEGIA;
    private static final Lesao UPDATED_LESAO = Lesao.TETRAPLEGIA;

    private static final Situacao DEFAULT_SITUACAO = Situacao.TEMPORARIA;
    private static final Situacao UPDATED_SITUACAO = Situacao.PERMANTENTE;

    private static final Uso DEFAULT_USO = Uso.PROPRIO;
    private static final Uso UPDATED_USO = Uso.DEPENDENTE;

    private static final String DEFAULT_NOME_DEPENDENTE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DEPENDENTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NASCIMENTO_DEPENDENTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NASCIMENTO_DEPENDENTE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TERMO = false;
    private static final Boolean UPDATED_TERMO = true;

    private static final Boolean DEFAULT_TREINAMENTO = false;
    private static final Boolean UPDATED_TREINAMENTO = true;

    private static final byte[] DEFAULT_FOTO_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO_DOC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_DOC_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FOTO_COM_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO_COM_DOC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_COM_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_COM_DOC_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/usuarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioMockMvc;

    private Usuario usuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .nome(DEFAULT_NOME)
            .sobrenome(DEFAULT_SOBRENOME)
            .nascimento(DEFAULT_NASCIMENTO)
            .documento(DEFAULT_DOCUMENTO)
            .lesao(DEFAULT_LESAO)
            .situacao(DEFAULT_SITUACAO)
            .uso(DEFAULT_USO)
            .nomeDependente(DEFAULT_NOME_DEPENDENTE)
            .nascimentoDependente(DEFAULT_NASCIMENTO_DEPENDENTE)
            .telefone(DEFAULT_TELEFONE)
            .facebook(DEFAULT_FACEBOOK)
            .instagram(DEFAULT_INSTAGRAM)
            .termo(DEFAULT_TERMO)
            .treinamento(DEFAULT_TREINAMENTO)
            .fotoDoc(DEFAULT_FOTO_DOC)
            .fotoDocContentType(DEFAULT_FOTO_DOC_CONTENT_TYPE)
            .fotoComDoc(DEFAULT_FOTO_COM_DOC)
            .fotoComDocContentType(DEFAULT_FOTO_COM_DOC_CONTENT_TYPE);
        return usuario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createUpdatedEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .nascimento(UPDATED_NASCIMENTO)
            .documento(UPDATED_DOCUMENTO)
            .lesao(UPDATED_LESAO)
            .situacao(UPDATED_SITUACAO)
            .uso(UPDATED_USO)
            .nomeDependente(UPDATED_NOME_DEPENDENTE)
            .nascimentoDependente(UPDATED_NASCIMENTO_DEPENDENTE)
            .telefone(UPDATED_TELEFONE)
            .facebook(UPDATED_FACEBOOK)
            .instagram(UPDATED_INSTAGRAM)
            .termo(UPDATED_TERMO)
            .treinamento(UPDATED_TREINAMENTO)
            .fotoDoc(UPDATED_FOTO_DOC)
            .fotoDocContentType(UPDATED_FOTO_DOC_CONTENT_TYPE)
            .fotoComDoc(UPDATED_FOTO_COM_DOC)
            .fotoComDocContentType(UPDATED_FOTO_COM_DOC_CONTENT_TYPE);
        return usuario;
    }

    @BeforeEach
    public void initTest() {
        usuario = createEntity(em);
    }

    @Test
    @Transactional
    void createUsuario() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();
        // Create the Usuario
        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isCreated());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate + 1);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testUsuario.getSobrenome()).isEqualTo(DEFAULT_SOBRENOME);
        assertThat(testUsuario.getNascimento()).isEqualTo(DEFAULT_NASCIMENTO);
        assertThat(testUsuario.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testUsuario.getLesao()).isEqualTo(DEFAULT_LESAO);
        assertThat(testUsuario.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
        assertThat(testUsuario.getUso()).isEqualTo(DEFAULT_USO);
        assertThat(testUsuario.getNomeDependente()).isEqualTo(DEFAULT_NOME_DEPENDENTE);
        assertThat(testUsuario.getNascimentoDependente()).isEqualTo(DEFAULT_NASCIMENTO_DEPENDENTE);
        assertThat(testUsuario.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testUsuario.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testUsuario.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testUsuario.getTermo()).isEqualTo(DEFAULT_TERMO);
        assertThat(testUsuario.getTreinamento()).isEqualTo(DEFAULT_TREINAMENTO);
        assertThat(testUsuario.getFotoDoc()).isEqualTo(DEFAULT_FOTO_DOC);
        assertThat(testUsuario.getFotoDocContentType()).isEqualTo(DEFAULT_FOTO_DOC_CONTENT_TYPE);
        assertThat(testUsuario.getFotoComDoc()).isEqualTo(DEFAULT_FOTO_COM_DOC);
        assertThat(testUsuario.getFotoComDocContentType()).isEqualTo(DEFAULT_FOTO_COM_DOC_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createUsuarioWithExistingId() throws Exception {
        // Create the Usuario with an existing ID
        usuario.setId(1L);

        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUsuarios() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sobrenome").value(hasItem(DEFAULT_SOBRENOME)))
            .andExpect(jsonPath("$.[*].nascimento").value(hasItem(DEFAULT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].lesao").value(hasItem(DEFAULT_LESAO.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].uso").value(hasItem(DEFAULT_USO.toString())))
            .andExpect(jsonPath("$.[*].nomeDependente").value(hasItem(DEFAULT_NOME_DEPENDENTE)))
            .andExpect(jsonPath("$.[*].nascimentoDependente").value(hasItem(DEFAULT_NASCIMENTO_DEPENDENTE.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].termo").value(hasItem(DEFAULT_TERMO.booleanValue())))
            .andExpect(jsonPath("$.[*].treinamento").value(hasItem(DEFAULT_TREINAMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].fotoDocContentType").value(hasItem(DEFAULT_FOTO_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotoDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_DOC))))
            .andExpect(jsonPath("$.[*].fotoComDocContentType").value(hasItem(DEFAULT_FOTO_COM_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotoComDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO_COM_DOC))));
    }

    @Test
    @Transactional
    void getUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get the usuario
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL_ID, usuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuario.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sobrenome").value(DEFAULT_SOBRENOME))
            .andExpect(jsonPath("$.nascimento").value(DEFAULT_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO))
            .andExpect(jsonPath("$.lesao").value(DEFAULT_LESAO.toString()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()))
            .andExpect(jsonPath("$.uso").value(DEFAULT_USO.toString()))
            .andExpect(jsonPath("$.nomeDependente").value(DEFAULT_NOME_DEPENDENTE))
            .andExpect(jsonPath("$.nascimentoDependente").value(DEFAULT_NASCIMENTO_DEPENDENTE.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM))
            .andExpect(jsonPath("$.termo").value(DEFAULT_TERMO.booleanValue()))
            .andExpect(jsonPath("$.treinamento").value(DEFAULT_TREINAMENTO.booleanValue()))
            .andExpect(jsonPath("$.fotoDocContentType").value(DEFAULT_FOTO_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.fotoDoc").value(Base64Utils.encodeToString(DEFAULT_FOTO_DOC)))
            .andExpect(jsonPath("$.fotoComDocContentType").value(DEFAULT_FOTO_COM_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.fotoComDoc").value(Base64Utils.encodeToString(DEFAULT_FOTO_COM_DOC)));
    }

    @Test
    @Transactional
    void getNonExistingUsuario() throws Exception {
        // Get the usuario
        restUsuarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario
        Usuario updatedUsuario = usuarioRepository.findById(usuario.getId()).get();
        // Disconnect from session so that the updates on updatedUsuario are not directly saved in db
        em.detach(updatedUsuario);
        updatedUsuario
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .nascimento(UPDATED_NASCIMENTO)
            .documento(UPDATED_DOCUMENTO)
            .lesao(UPDATED_LESAO)
            .situacao(UPDATED_SITUACAO)
            .uso(UPDATED_USO)
            .nomeDependente(UPDATED_NOME_DEPENDENTE)
            .nascimentoDependente(UPDATED_NASCIMENTO_DEPENDENTE)
            .telefone(UPDATED_TELEFONE)
            .facebook(UPDATED_FACEBOOK)
            .instagram(UPDATED_INSTAGRAM)
            .termo(UPDATED_TERMO)
            .treinamento(UPDATED_TREINAMENTO)
            .fotoDoc(UPDATED_FOTO_DOC)
            .fotoDocContentType(UPDATED_FOTO_DOC_CONTENT_TYPE)
            .fotoComDoc(UPDATED_FOTO_COM_DOC)
            .fotoComDocContentType(UPDATED_FOTO_COM_DOC_CONTENT_TYPE);

        restUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUsuario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUsuario))
            )
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testUsuario.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testUsuario.getNascimento()).isEqualTo(UPDATED_NASCIMENTO);
        assertThat(testUsuario.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testUsuario.getLesao()).isEqualTo(UPDATED_LESAO);
        assertThat(testUsuario.getSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testUsuario.getUso()).isEqualTo(UPDATED_USO);
        assertThat(testUsuario.getNomeDependente()).isEqualTo(UPDATED_NOME_DEPENDENTE);
        assertThat(testUsuario.getNascimentoDependente()).isEqualTo(UPDATED_NASCIMENTO_DEPENDENTE);
        assertThat(testUsuario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testUsuario.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testUsuario.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testUsuario.getTermo()).isEqualTo(UPDATED_TERMO);
        assertThat(testUsuario.getTreinamento()).isEqualTo(UPDATED_TREINAMENTO);
        assertThat(testUsuario.getFotoDoc()).isEqualTo(UPDATED_FOTO_DOC);
        assertThat(testUsuario.getFotoDocContentType()).isEqualTo(UPDATED_FOTO_DOC_CONTENT_TYPE);
        assertThat(testUsuario.getFotoComDoc()).isEqualTo(UPDATED_FOTO_COM_DOC);
        assertThat(testUsuario.getFotoComDocContentType()).isEqualTo(UPDATED_FOTO_COM_DOC_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsuarioWithPatch() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario using partial update
        Usuario partialUpdatedUsuario = new Usuario();
        partialUpdatedUsuario.setId(usuario.getId());

        partialUpdatedUsuario
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .lesao(UPDATED_LESAO)
            .nomeDependente(UPDATED_NOME_DEPENDENTE)
            .nascimentoDependente(UPDATED_NASCIMENTO_DEPENDENTE)
            .telefone(UPDATED_TELEFONE)
            .instagram(UPDATED_INSTAGRAM)
            .termo(UPDATED_TERMO)
            .treinamento(UPDATED_TREINAMENTO)
            .fotoComDoc(UPDATED_FOTO_COM_DOC)
            .fotoComDocContentType(UPDATED_FOTO_COM_DOC_CONTENT_TYPE);

        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuario))
            )
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testUsuario.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testUsuario.getNascimento()).isEqualTo(DEFAULT_NASCIMENTO);
        assertThat(testUsuario.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testUsuario.getLesao()).isEqualTo(UPDATED_LESAO);
        assertThat(testUsuario.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
        assertThat(testUsuario.getUso()).isEqualTo(DEFAULT_USO);
        assertThat(testUsuario.getNomeDependente()).isEqualTo(UPDATED_NOME_DEPENDENTE);
        assertThat(testUsuario.getNascimentoDependente()).isEqualTo(UPDATED_NASCIMENTO_DEPENDENTE);
        assertThat(testUsuario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testUsuario.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testUsuario.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testUsuario.getTermo()).isEqualTo(UPDATED_TERMO);
        assertThat(testUsuario.getTreinamento()).isEqualTo(UPDATED_TREINAMENTO);
        assertThat(testUsuario.getFotoDoc()).isEqualTo(DEFAULT_FOTO_DOC);
        assertThat(testUsuario.getFotoDocContentType()).isEqualTo(DEFAULT_FOTO_DOC_CONTENT_TYPE);
        assertThat(testUsuario.getFotoComDoc()).isEqualTo(UPDATED_FOTO_COM_DOC);
        assertThat(testUsuario.getFotoComDocContentType()).isEqualTo(UPDATED_FOTO_COM_DOC_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateUsuarioWithPatch() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario using partial update
        Usuario partialUpdatedUsuario = new Usuario();
        partialUpdatedUsuario.setId(usuario.getId());

        partialUpdatedUsuario
            .nome(UPDATED_NOME)
            .sobrenome(UPDATED_SOBRENOME)
            .nascimento(UPDATED_NASCIMENTO)
            .documento(UPDATED_DOCUMENTO)
            .lesao(UPDATED_LESAO)
            .situacao(UPDATED_SITUACAO)
            .uso(UPDATED_USO)
            .nomeDependente(UPDATED_NOME_DEPENDENTE)
            .nascimentoDependente(UPDATED_NASCIMENTO_DEPENDENTE)
            .telefone(UPDATED_TELEFONE)
            .facebook(UPDATED_FACEBOOK)
            .instagram(UPDATED_INSTAGRAM)
            .termo(UPDATED_TERMO)
            .treinamento(UPDATED_TREINAMENTO)
            .fotoDoc(UPDATED_FOTO_DOC)
            .fotoDocContentType(UPDATED_FOTO_DOC_CONTENT_TYPE)
            .fotoComDoc(UPDATED_FOTO_COM_DOC)
            .fotoComDocContentType(UPDATED_FOTO_COM_DOC_CONTENT_TYPE);

        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuario))
            )
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testUsuario.getSobrenome()).isEqualTo(UPDATED_SOBRENOME);
        assertThat(testUsuario.getNascimento()).isEqualTo(UPDATED_NASCIMENTO);
        assertThat(testUsuario.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testUsuario.getLesao()).isEqualTo(UPDATED_LESAO);
        assertThat(testUsuario.getSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testUsuario.getUso()).isEqualTo(UPDATED_USO);
        assertThat(testUsuario.getNomeDependente()).isEqualTo(UPDATED_NOME_DEPENDENTE);
        assertThat(testUsuario.getNascimentoDependente()).isEqualTo(UPDATED_NASCIMENTO_DEPENDENTE);
        assertThat(testUsuario.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testUsuario.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testUsuario.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testUsuario.getTermo()).isEqualTo(UPDATED_TERMO);
        assertThat(testUsuario.getTreinamento()).isEqualTo(UPDATED_TREINAMENTO);
        assertThat(testUsuario.getFotoDoc()).isEqualTo(UPDATED_FOTO_DOC);
        assertThat(testUsuario.getFotoDocContentType()).isEqualTo(UPDATED_FOTO_DOC_CONTENT_TYPE);
        assertThat(testUsuario.getFotoComDoc()).isEqualTo(UPDATED_FOTO_COM_DOC);
        assertThat(testUsuario.getFotoComDocContentType()).isEqualTo(UPDATED_FOTO_COM_DOC_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(usuario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeDelete = usuarioRepository.findAll().size();

        // Delete the usuario
        restUsuarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, usuario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
