package org.fits.hms.web.rest;

import org.fits.hms.HospitalManagementSystemApp;

import org.fits.hms.domain.SleepTypes;
import org.fits.hms.repository.SleepTypesRepository;
import org.fits.hms.service.SleepTypesService;
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
 * Test class for the SleepTypesResource REST controller.
 *
 * @see SleepTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementSystemApp.class)
public class SleepTypesResourceIntTest {

    private static final String DEFAULT_SLEEP_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SLEEP_TYPE = "BBBBBBBBBB";

    @Autowired
    private SleepTypesRepository sleepTypesRepository;

    @Autowired
    private SleepTypesService sleepTypesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSleepTypesMockMvc;

    private SleepTypes sleepTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SleepTypesResource sleepTypesResource = new SleepTypesResource(sleepTypesService);
        this.restSleepTypesMockMvc = MockMvcBuilders.standaloneSetup(sleepTypesResource)
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
    public static SleepTypes createEntity(EntityManager em) {
        SleepTypes sleepTypes = new SleepTypes()
            .sleepType(DEFAULT_SLEEP_TYPE);
        return sleepTypes;
    }

    @Before
    public void initTest() {
        sleepTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createSleepTypes() throws Exception {
        int databaseSizeBeforeCreate = sleepTypesRepository.findAll().size();

        // Create the SleepTypes
        restSleepTypesMockMvc.perform(post("/api/sleep-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sleepTypes)))
            .andExpect(status().isCreated());

        // Validate the SleepTypes in the database
        List<SleepTypes> sleepTypesList = sleepTypesRepository.findAll();
        assertThat(sleepTypesList).hasSize(databaseSizeBeforeCreate + 1);
        SleepTypes testSleepTypes = sleepTypesList.get(sleepTypesList.size() - 1);
        assertThat(testSleepTypes.getSleepType()).isEqualTo(DEFAULT_SLEEP_TYPE);
    }

    @Test
    @Transactional
    public void createSleepTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sleepTypesRepository.findAll().size();

        // Create the SleepTypes with an existing ID
        sleepTypes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSleepTypesMockMvc.perform(post("/api/sleep-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sleepTypes)))
            .andExpect(status().isBadRequest());

        // Validate the SleepTypes in the database
        List<SleepTypes> sleepTypesList = sleepTypesRepository.findAll();
        assertThat(sleepTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSleepTypes() throws Exception {
        // Initialize the database
        sleepTypesRepository.saveAndFlush(sleepTypes);

        // Get all the sleepTypesList
        restSleepTypesMockMvc.perform(get("/api/sleep-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sleepTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].sleepType").value(hasItem(DEFAULT_SLEEP_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getSleepTypes() throws Exception {
        // Initialize the database
        sleepTypesRepository.saveAndFlush(sleepTypes);

        // Get the sleepTypes
        restSleepTypesMockMvc.perform(get("/api/sleep-types/{id}", sleepTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sleepTypes.getId().intValue()))
            .andExpect(jsonPath("$.sleepType").value(DEFAULT_SLEEP_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSleepTypes() throws Exception {
        // Get the sleepTypes
        restSleepTypesMockMvc.perform(get("/api/sleep-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSleepTypes() throws Exception {
        // Initialize the database
        sleepTypesService.save(sleepTypes);

        int databaseSizeBeforeUpdate = sleepTypesRepository.findAll().size();

        // Update the sleepTypes
        SleepTypes updatedSleepTypes = sleepTypesRepository.findOne(sleepTypes.getId());
        // Disconnect from session so that the updates on updatedSleepTypes are not directly saved in db
        em.detach(updatedSleepTypes);
        updatedSleepTypes
            .sleepType(UPDATED_SLEEP_TYPE);

        restSleepTypesMockMvc.perform(put("/api/sleep-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSleepTypes)))
            .andExpect(status().isOk());

        // Validate the SleepTypes in the database
        List<SleepTypes> sleepTypesList = sleepTypesRepository.findAll();
        assertThat(sleepTypesList).hasSize(databaseSizeBeforeUpdate);
        SleepTypes testSleepTypes = sleepTypesList.get(sleepTypesList.size() - 1);
        assertThat(testSleepTypes.getSleepType()).isEqualTo(UPDATED_SLEEP_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSleepTypes() throws Exception {
        int databaseSizeBeforeUpdate = sleepTypesRepository.findAll().size();

        // Create the SleepTypes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSleepTypesMockMvc.perform(put("/api/sleep-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sleepTypes)))
            .andExpect(status().isCreated());

        // Validate the SleepTypes in the database
        List<SleepTypes> sleepTypesList = sleepTypesRepository.findAll();
        assertThat(sleepTypesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSleepTypes() throws Exception {
        // Initialize the database
        sleepTypesService.save(sleepTypes);

        int databaseSizeBeforeDelete = sleepTypesRepository.findAll().size();

        // Get the sleepTypes
        restSleepTypesMockMvc.perform(delete("/api/sleep-types/{id}", sleepTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SleepTypes> sleepTypesList = sleepTypesRepository.findAll();
        assertThat(sleepTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SleepTypes.class);
        SleepTypes sleepTypes1 = new SleepTypes();
        sleepTypes1.setId(1L);
        SleepTypes sleepTypes2 = new SleepTypes();
        sleepTypes2.setId(sleepTypes1.getId());
        assertThat(sleepTypes1).isEqualTo(sleepTypes2);
        sleepTypes2.setId(2L);
        assertThat(sleepTypes1).isNotEqualTo(sleepTypes2);
        sleepTypes1.setId(null);
        assertThat(sleepTypes1).isNotEqualTo(sleepTypes2);
    }
}
