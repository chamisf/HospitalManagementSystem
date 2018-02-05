package org.fits.hms.web.rest;

import org.fits.hms.HospitalManagementSystemApp;

import org.fits.hms.domain.MicturitionTypes;
import org.fits.hms.repository.MicturitionTypesRepository;
import org.fits.hms.service.MicturitionTypesService;
import org.fits.hms.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.fits.hms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MicturitionTypesResource REST controller.
 *
 * @see MicturitionTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementSystemApp.class)
public class MicturitionTypesResourceIntTest {

    private static final String DEFAULT_MICTURITION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MICTURITION_TYPE = "BBBBBBBBBB";

    @Autowired
    private MicturitionTypesRepository micturitionTypesRepository;

    @Autowired
    private MicturitionTypesService micturitionTypesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMicturitionTypesMockMvc;

    private MicturitionTypes micturitionTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MicturitionTypesResource micturitionTypesResource = new MicturitionTypesResource(micturitionTypesService);
        this.restMicturitionTypesMockMvc = MockMvcBuilders.standaloneSetup(micturitionTypesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MicturitionTypes createEntity(EntityManager em) {
        MicturitionTypes micturitionTypes = new MicturitionTypes()
            .micturitionType(DEFAULT_MICTURITION_TYPE);
        return micturitionTypes;
    }

    @Before
    public void initTest() {
        micturitionTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createMicturitionTypes() throws Exception {
        int databaseSizeBeforeCreate = micturitionTypesRepository.findAll().size();

        // Create the MicturitionTypes
        restMicturitionTypesMockMvc.perform(post("/api/micturition-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(micturitionTypes)))
            .andExpect(status().isCreated());

        // Validate the MicturitionTypes in the database
        List<MicturitionTypes> micturitionTypesList = micturitionTypesRepository.findAll();
        assertThat(micturitionTypesList).hasSize(databaseSizeBeforeCreate + 1);
        MicturitionTypes testMicturitionTypes = micturitionTypesList.get(micturitionTypesList.size() - 1);
        assertThat(testMicturitionTypes.getMicturitionType()).isEqualTo(DEFAULT_MICTURITION_TYPE);
    }

    @Test
    @Transactional
    public void createMicturitionTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = micturitionTypesRepository.findAll().size();

        // Create the MicturitionTypes with an existing ID
        micturitionTypes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMicturitionTypesMockMvc.perform(post("/api/micturition-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(micturitionTypes)))
            .andExpect(status().isBadRequest());

        // Validate the MicturitionTypes in the database
        List<MicturitionTypes> micturitionTypesList = micturitionTypesRepository.findAll();
        assertThat(micturitionTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMicturitionTypes() throws Exception {
        // Initialize the database
        micturitionTypesRepository.saveAndFlush(micturitionTypes);

        // Get all the micturitionTypesList
        restMicturitionTypesMockMvc.perform(get("/api/micturition-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(micturitionTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].micturitionType").value(hasItem(DEFAULT_MICTURITION_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getMicturitionTypes() throws Exception {
        // Initialize the database
        micturitionTypesRepository.saveAndFlush(micturitionTypes);

        // Get the micturitionTypes
        restMicturitionTypesMockMvc.perform(get("/api/micturition-types/{id}", micturitionTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(micturitionTypes.getId().intValue()))
            .andExpect(jsonPath("$.micturitionType").value(DEFAULT_MICTURITION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMicturitionTypes() throws Exception {
        // Get the micturitionTypes
        restMicturitionTypesMockMvc.perform(get("/api/micturition-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMicturitionTypes() throws Exception {
        // Initialize the database
        micturitionTypesService.save(micturitionTypes);

        int databaseSizeBeforeUpdate = micturitionTypesRepository.findAll().size();

        // Update the micturitionTypes
        MicturitionTypes updatedMicturitionTypes = micturitionTypesRepository.findOne(micturitionTypes.getId());
        // Disconnect from session so that the updates on updatedMicturitionTypes are not directly saved in db
        em.detach(updatedMicturitionTypes);
        updatedMicturitionTypes
            .micturitionType(UPDATED_MICTURITION_TYPE);

        restMicturitionTypesMockMvc.perform(put("/api/micturition-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMicturitionTypes)))
            .andExpect(status().isOk());

        // Validate the MicturitionTypes in the database
        List<MicturitionTypes> micturitionTypesList = micturitionTypesRepository.findAll();
        assertThat(micturitionTypesList).hasSize(databaseSizeBeforeUpdate);
        MicturitionTypes testMicturitionTypes = micturitionTypesList.get(micturitionTypesList.size() - 1);
        assertThat(testMicturitionTypes.getMicturitionType()).isEqualTo(UPDATED_MICTURITION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMicturitionTypes() throws Exception {
        int databaseSizeBeforeUpdate = micturitionTypesRepository.findAll().size();

        // Create the MicturitionTypes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMicturitionTypesMockMvc.perform(put("/api/micturition-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(micturitionTypes)))
            .andExpect(status().isCreated());

        // Validate the MicturitionTypes in the database
        List<MicturitionTypes> micturitionTypesList = micturitionTypesRepository.findAll();
        assertThat(micturitionTypesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMicturitionTypes() throws Exception {
        // Initialize the database
        micturitionTypesService.save(micturitionTypes);

        int databaseSizeBeforeDelete = micturitionTypesRepository.findAll().size();

        // Get the micturitionTypes
        restMicturitionTypesMockMvc.perform(delete("/api/micturition-types/{id}", micturitionTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MicturitionTypes> micturitionTypesList = micturitionTypesRepository.findAll();
        assertThat(micturitionTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MicturitionTypes.class);
        MicturitionTypes micturitionTypes1 = new MicturitionTypes();
        micturitionTypes1.setId(1L);
        MicturitionTypes micturitionTypes2 = new MicturitionTypes();
        micturitionTypes2.setId(micturitionTypes1.getId());
        assertThat(micturitionTypes1).isEqualTo(micturitionTypes2);
        micturitionTypes2.setId(2L);
        assertThat(micturitionTypes1).isNotEqualTo(micturitionTypes2);
        micturitionTypes1.setId(null);
        assertThat(micturitionTypes1).isNotEqualTo(micturitionTypes2);
    }
}
