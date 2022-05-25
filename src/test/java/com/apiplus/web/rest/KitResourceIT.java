package com.apiplus.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apiplus.IntegrationTest;
import com.apiplus.domain.Kit;
import com.apiplus.domain.enumeration.StatusKit;
import com.apiplus.repository.KitRepository;
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
 * Integration tests for the {@link KitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KitResourceIT {

    private static final StatusKit DEFAULT_STATUS_KIT = StatusKit.EM_USO;
    private static final StatusKit UPDATED_STATUS_KIT = StatusKit.EM_ESPERA;

    private static final String ENTITY_API_URL = "/api/kits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KitRepository kitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKitMockMvc;

    private Kit kit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kit createEntity(EntityManager em) {
        Kit kit = new Kit().statusKit(DEFAULT_STATUS_KIT);
        return kit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kit createUpdatedEntity(EntityManager em) {
        Kit kit = new Kit().statusKit(UPDATED_STATUS_KIT);
        return kit;
    }

    @BeforeEach
    public void initTest() {
        kit = createEntity(em);
    }

    @Test
    @Transactional
    void createKit() throws Exception {
        int databaseSizeBeforeCreate = kitRepository.findAll().size();
        // Create the Kit
        restKitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kit)))
            .andExpect(status().isCreated());

        // Validate the Kit in the database
        List<Kit> kitList = kitRepository.findAll();
        assertThat(kitList).hasSize(databaseSizeBeforeCreate + 1);
        Kit testKit = kitList.get(kitList.size() - 1);
        assertThat(testKit.getStatusKit()).isEqualTo(DEFAULT_STATUS_KIT);
    }

    @Test
    @Transactional
    void createKitWithExistingId() throws Exception {
        // Create the Kit with an existing ID
        kit.setId(1L);

        int databaseSizeBeforeCreate = kitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kit)))
            .andExpect(status().isBadRequest());

        // Validate the Kit in the database
        List<Kit> kitList = kitRepository.findAll();
        assertThat(kitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllKits() throws Exception {
        // Initialize the database
        kitRepository.saveAndFlush(kit);

        // Get all the kitList
        restKitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kit.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusKit").value(hasItem(DEFAULT_STATUS_KIT.toString())));
    }

    @Test
    @Transactional
    void getKit() throws Exception {
        // Initialize the database
        kitRepository.saveAndFlush(kit);

        // Get the kit
        restKitMockMvc
            .perform(get(ENTITY_API_URL_ID, kit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kit.getId().intValue()))
            .andExpect(jsonPath("$.statusKit").value(DEFAULT_STATUS_KIT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingKit() throws Exception {
        // Get the kit
        restKitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewKit() throws Exception {
        // Initialize the database
        kitRepository.saveAndFlush(kit);

        int databaseSizeBeforeUpdate = kitRepository.findAll().size();

        // Update the kit
        Kit updatedKit = kitRepository.findById(kit.getId()).get();
        // Disconnect from session so that the updates on updatedKit are not directly saved in db
        em.detach(updatedKit);
        updatedKit.statusKit(UPDATED_STATUS_KIT);

        restKitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKit))
            )
            .andExpect(status().isOk());

        // Validate the Kit in the database
        List<Kit> kitList = kitRepository.findAll();
        assertThat(kitList).hasSize(databaseSizeBeforeUpdate);
        Kit testKit = kitList.get(kitList.size() - 1);
        assertThat(testKit.getStatusKit()).isEqualTo(UPDATED_STATUS_KIT);
    }

    @Test
    @Transactional
    void putNonExistingKit() throws Exception {
        int databaseSizeBeforeUpdate = kitRepository.findAll().size();
        kit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, kit.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kit in the database
        List<Kit> kitList = kitRepository.findAll();
        assertThat(kitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKit() throws Exception {
        int databaseSizeBeforeUpdate = kitRepository.findAll().size();
        kit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(kit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kit in the database
        List<Kit> kitList = kitRepository.findAll();
        assertThat(kitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKit() throws Exception {
        int databaseSizeBeforeUpdate = kitRepository.findAll().size();
        kit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(kit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kit in the database
        List<Kit> kitList = kitRepository.findAll();
        assertThat(kitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKitWithPatch() throws Exception {
        // Initialize the database
        kitRepository.saveAndFlush(kit);

        int databaseSizeBeforeUpdate = kitRepository.findAll().size();

        // Update the kit using partial update
        Kit partialUpdatedKit = new Kit();
        partialUpdatedKit.setId(kit.getId());

        restKitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKit))
            )
            .andExpect(status().isOk());

        // Validate the Kit in the database
        List<Kit> kitList = kitRepository.findAll();
        assertThat(kitList).hasSize(databaseSizeBeforeUpdate);
        Kit testKit = kitList.get(kitList.size() - 1);
        assertThat(testKit.getStatusKit()).isEqualTo(DEFAULT_STATUS_KIT);
    }

    @Test
    @Transactional
    void fullUpdateKitWithPatch() throws Exception {
        // Initialize the database
        kitRepository.saveAndFlush(kit);

        int databaseSizeBeforeUpdate = kitRepository.findAll().size();

        // Update the kit using partial update
        Kit partialUpdatedKit = new Kit();
        partialUpdatedKit.setId(kit.getId());

        partialUpdatedKit.statusKit(UPDATED_STATUS_KIT);

        restKitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKit))
            )
            .andExpect(status().isOk());

        // Validate the Kit in the database
        List<Kit> kitList = kitRepository.findAll();
        assertThat(kitList).hasSize(databaseSizeBeforeUpdate);
        Kit testKit = kitList.get(kitList.size() - 1);
        assertThat(testKit.getStatusKit()).isEqualTo(UPDATED_STATUS_KIT);
    }

    @Test
    @Transactional
    void patchNonExistingKit() throws Exception {
        int databaseSizeBeforeUpdate = kitRepository.findAll().size();
        kit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, kit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kit in the database
        List<Kit> kitList = kitRepository.findAll();
        assertThat(kitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKit() throws Exception {
        int databaseSizeBeforeUpdate = kitRepository.findAll().size();
        kit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(kit))
            )
            .andExpect(status().isBadRequest());

        // Validate the Kit in the database
        List<Kit> kitList = kitRepository.findAll();
        assertThat(kitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKit() throws Exception {
        int databaseSizeBeforeUpdate = kitRepository.findAll().size();
        kit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(kit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Kit in the database
        List<Kit> kitList = kitRepository.findAll();
        assertThat(kitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKit() throws Exception {
        // Initialize the database
        kitRepository.saveAndFlush(kit);

        int databaseSizeBeforeDelete = kitRepository.findAll().size();

        // Delete the kit
        restKitMockMvc.perform(delete(ENTITY_API_URL_ID, kit.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Kit> kitList = kitRepository.findAll();
        assertThat(kitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
