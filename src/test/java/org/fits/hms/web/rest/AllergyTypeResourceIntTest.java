package org.fits.hms.web.rest;

import org.fits.hms.HospitalManagementSystemApp;

import org.fits.hms.domain.AllergyType;
import org.fits.hms.repository.AllergyTypeRepository;
import org.fits.hms.service.AllergyTypeService;
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
 * Test class for the AllergyTypeResource REST controller.
 *
 * @see AllergyTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementSystemApp.class)
public class AllergyTypeResourceIntTest {

    private static final String DEFAULT_ALLERGY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ALLERGY_TYPE = "BBBBBBBBBB";

    @Autowired
    private AllergyTypeRepository allergyTypeRepository;

    @Autowired
    private AllergyTypeService allergyTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAllergyTypeMockMvc;

    private AllergyType allergyType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllergyTypeResource allergyTypeResource = new AllergyTypeResource(allergyTypeService);
        this.restAllergyTypeMockMvc = MockMvcBuilders.standaloneSetup(allergyTypeResource)
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
    public static AllergyType createEntity(EntityManager em) {
        AllergyType allergyType = new AllergyType()
            .allergyType(DEFAULT_ALLERGY_TYPE);
        return allergyType;
    }

    @Before
    public void initTest() {
        allergyType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllergyType() throws Exception {
        int databaseSizeBeforeCreate = allergyTypeRepository.findAll().size();

        // Create the AllergyType
        restAllergyTypeMockMvc.perform(post("/api/allergy-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergyType)))
            .andExpect(status().isCreated());

        // Validate the AllergyType in the database
        List<AllergyType> allergyTypeList = allergyTypeRepository.findAll();
        assertThat(allergyTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AllergyType testAllergyType = allergyTypeList.get(allergyTypeList.size() - 1);
        assertThat(testAllergyType.getAllergyType()).isEqualTo(DEFAULT_ALLERGY_TYPE);
    }

    @Test
    @Transactional
    public void createAllergyTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allergyTypeRepository.findAll().size();

        // Create the AllergyType with an existing ID
        allergyType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllergyTypeMockMvc.perform(post("/api/allergy-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergyType)))
            .andExpect(status().isBadRequest());

        // Validate the AllergyType in the database
        List<AllergyType> allergyTypeList = allergyTypeRepository.findAll();
        assertThat(allergyTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAllergyTypes() throws Exception {
        // Initialize the database
        allergyTypeRepository.saveAndFlush(allergyType);

        // Get all the allergyTypeList
        restAllergyTypeMockMvc.perform(get("/api/allergy-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allergyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].allergyType").value(hasItem(DEFAULT_ALLERGY_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getAllergyType() throws Exception {
        // Initialize the database
        allergyTypeRepository.saveAndFlush(allergyType);

        // Get the allergyType
        restAllergyTypeMockMvc.perform(get("/api/allergy-types/{id}", allergyType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allergyType.getId().intValue()))
            .andExpect(jsonPath("$.allergyType").value(DEFAULT_ALLERGY_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAllergyType() throws Exception {
        // Get the allergyType
        restAllergyTypeMockMvc.perform(get("/api/allergy-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllergyType() throws Exception {
        // Initialize the database
        allergyTypeService.save(allergyType);

        int databaseSizeBeforeUpdate = allergyTypeRepository.findAll().size();

        // Update the allergyType
        AllergyType updatedAllergyType = allergyTypeRepository.findOne(allergyType.getId());
        // Disconnect from session so that the updates on updatedAllergyType are not directly saved in db
        em.detach(updatedAllergyType);
        updatedAllergyType
            .allergyType(UPDATED_ALLERGY_TYPE);

        restAllergyTypeMockMvc.perform(put("/api/allergy-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllergyType)))
            .andExpect(status().isOk());

        // Validate the AllergyType in the database
        List<AllergyType> allergyTypeList = allergyTypeRepository.findAll();
        assertThat(allergyTypeList).hasSize(databaseSizeBeforeUpdate);
        AllergyType testAllergyType = allergyTypeList.get(allergyTypeList.size() - 1);
        assertThat(testAllergyType.getAllergyType()).isEqualTo(UPDATED_ALLERGY_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAllergyType() throws Exception {
        int databaseSizeBeforeUpdate = allergyTypeRepository.findAll().size();

        // Create the AllergyType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAllergyTypeMockMvc.perform(put("/api/allergy-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergyType)))
            .andExpect(status().isCreated());

        // Validate the AllergyType in the database
        List<AllergyType> allergyTypeList = allergyTypeRepository.findAll();
        assertThat(allergyTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAllergyType() throws Exception {
        // Initialize the database
        allergyTypeService.save(allergyType);

        int databaseSizeBeforeDelete = allergyTypeRepository.findAll().size();

        // Get the allergyType
        restAllergyTypeMockMvc.perform(delete("/api/allergy-types/{id}", allergyType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AllergyType> allergyTypeList = allergyTypeRepository.findAll();
        assertThat(allergyTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AllergyType.class);
        AllergyType allergyType1 = new AllergyType();
        allergyType1.setId(1L);
        AllergyType allergyType2 = new AllergyType();
        allergyType2.setId(allergyType1.getId());
        assertThat(allergyType1).isEqualTo(allergyType2);
        allergyType2.setId(2L);
        assertThat(allergyType1).isNotEqualTo(allergyType2);
        allergyType1.setId(null);
        assertThat(allergyType1).isNotEqualTo(allergyType2);
    }
}
