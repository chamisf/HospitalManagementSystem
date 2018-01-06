package org.fits.hms.web.rest;

import org.fits.hms.HospitalManagementSystemApp;

import org.fits.hms.domain.AppetiteTypes;
import org.fits.hms.repository.AppetiteTypesRepository;
import org.fits.hms.service.AppetiteTypesService;
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
 * Test class for the AppetiteTypesResource REST controller.
 *
 * @see AppetiteTypesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementSystemApp.class)
public class AppetiteTypesResourceIntTest {

    private static final String DEFAULT_APPETITE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_APPETITE_TYPE = "BBBBBBBBBB";

    @Autowired
    private AppetiteTypesRepository appetiteTypesRepository;

    @Autowired
    private AppetiteTypesService appetiteTypesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAppetiteTypesMockMvc;

    private AppetiteTypes appetiteTypes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppetiteTypesResource appetiteTypesResource = new AppetiteTypesResource(appetiteTypesService);
        this.restAppetiteTypesMockMvc = MockMvcBuilders.standaloneSetup(appetiteTypesResource)
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
    public static AppetiteTypes createEntity(EntityManager em) {
        AppetiteTypes appetiteTypes = new AppetiteTypes()
            .appetiteType(DEFAULT_APPETITE_TYPE);
        return appetiteTypes;
    }

    @Before
    public void initTest() {
        appetiteTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppetiteTypes() throws Exception {
        int databaseSizeBeforeCreate = appetiteTypesRepository.findAll().size();

        // Create the AppetiteTypes
        restAppetiteTypesMockMvc.perform(post("/api/appetite-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appetiteTypes)))
            .andExpect(status().isCreated());

        // Validate the AppetiteTypes in the database
        List<AppetiteTypes> appetiteTypesList = appetiteTypesRepository.findAll();
        assertThat(appetiteTypesList).hasSize(databaseSizeBeforeCreate + 1);
        AppetiteTypes testAppetiteTypes = appetiteTypesList.get(appetiteTypesList.size() - 1);
        assertThat(testAppetiteTypes.getAppetiteType()).isEqualTo(DEFAULT_APPETITE_TYPE);
    }

    @Test
    @Transactional
    public void createAppetiteTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appetiteTypesRepository.findAll().size();

        // Create the AppetiteTypes with an existing ID
        appetiteTypes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppetiteTypesMockMvc.perform(post("/api/appetite-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appetiteTypes)))
            .andExpect(status().isBadRequest());

        // Validate the AppetiteTypes in the database
        List<AppetiteTypes> appetiteTypesList = appetiteTypesRepository.findAll();
        assertThat(appetiteTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAppetiteTypes() throws Exception {
        // Initialize the database
        appetiteTypesRepository.saveAndFlush(appetiteTypes);

        // Get all the appetiteTypesList
        restAppetiteTypesMockMvc.perform(get("/api/appetite-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appetiteTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].appetiteType").value(hasItem(DEFAULT_APPETITE_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getAppetiteTypes() throws Exception {
        // Initialize the database
        appetiteTypesRepository.saveAndFlush(appetiteTypes);

        // Get the appetiteTypes
        restAppetiteTypesMockMvc.perform(get("/api/appetite-types/{id}", appetiteTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appetiteTypes.getId().intValue()))
            .andExpect(jsonPath("$.appetiteType").value(DEFAULT_APPETITE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppetiteTypes() throws Exception {
        // Get the appetiteTypes
        restAppetiteTypesMockMvc.perform(get("/api/appetite-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppetiteTypes() throws Exception {
        // Initialize the database
        appetiteTypesService.save(appetiteTypes);

        int databaseSizeBeforeUpdate = appetiteTypesRepository.findAll().size();

        // Update the appetiteTypes
        AppetiteTypes updatedAppetiteTypes = appetiteTypesRepository.findOne(appetiteTypes.getId());
        // Disconnect from session so that the updates on updatedAppetiteTypes are not directly saved in db
        em.detach(updatedAppetiteTypes);
        updatedAppetiteTypes
            .appetiteType(UPDATED_APPETITE_TYPE);

        restAppetiteTypesMockMvc.perform(put("/api/appetite-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppetiteTypes)))
            .andExpect(status().isOk());

        // Validate the AppetiteTypes in the database
        List<AppetiteTypes> appetiteTypesList = appetiteTypesRepository.findAll();
        assertThat(appetiteTypesList).hasSize(databaseSizeBeforeUpdate);
        AppetiteTypes testAppetiteTypes = appetiteTypesList.get(appetiteTypesList.size() - 1);
        assertThat(testAppetiteTypes.getAppetiteType()).isEqualTo(UPDATED_APPETITE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAppetiteTypes() throws Exception {
        int databaseSizeBeforeUpdate = appetiteTypesRepository.findAll().size();

        // Create the AppetiteTypes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAppetiteTypesMockMvc.perform(put("/api/appetite-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appetiteTypes)))
            .andExpect(status().isCreated());

        // Validate the AppetiteTypes in the database
        List<AppetiteTypes> appetiteTypesList = appetiteTypesRepository.findAll();
        assertThat(appetiteTypesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAppetiteTypes() throws Exception {
        // Initialize the database
        appetiteTypesService.save(appetiteTypes);

        int databaseSizeBeforeDelete = appetiteTypesRepository.findAll().size();

        // Get the appetiteTypes
        restAppetiteTypesMockMvc.perform(delete("/api/appetite-types/{id}", appetiteTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AppetiteTypes> appetiteTypesList = appetiteTypesRepository.findAll();
        assertThat(appetiteTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppetiteTypes.class);
        AppetiteTypes appetiteTypes1 = new AppetiteTypes();
        appetiteTypes1.setId(1L);
        AppetiteTypes appetiteTypes2 = new AppetiteTypes();
        appetiteTypes2.setId(appetiteTypes1.getId());
        assertThat(appetiteTypes1).isEqualTo(appetiteTypes2);
        appetiteTypes2.setId(2L);
        assertThat(appetiteTypes1).isNotEqualTo(appetiteTypes2);
        appetiteTypes1.setId(null);
        assertThat(appetiteTypes1).isNotEqualTo(appetiteTypes2);
    }
}
