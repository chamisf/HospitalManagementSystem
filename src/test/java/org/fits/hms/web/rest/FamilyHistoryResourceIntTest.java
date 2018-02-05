package org.fits.hms.web.rest;

import org.fits.hms.HospitalManagementSystemApp;

import org.fits.hms.domain.FamilyHistory;
import org.fits.hms.domain.Patient;
import org.fits.hms.repository.FamilyHistoryRepository;
import org.fits.hms.service.FamilyHistoryService;
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
 * Test class for the FamilyHistoryResource REST controller.
 *
 * @see FamilyHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementSystemApp.class)
public class FamilyHistoryResourceIntTest {

    private static final String DEFAULT_FATHERS_DISEASES = "AAAAAAAAAA";
    private static final String UPDATED_FATHERS_DISEASES = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHERS_DISEASES = "AAAAAAAAAA";
    private static final String UPDATED_MOTHERS_DISEASES = "BBBBBBBBBB";

    private static final String DEFAULT_SIBLINGS_DISEASES = "AAAAAAAAAA";
    private static final String UPDATED_SIBLINGS_DISEASES = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_RELATIVES_DISEASES = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_RELATIVES_DISEASES = "BBBBBBBBBB";

    @Autowired
    private FamilyHistoryRepository familyHistoryRepository;

    @Autowired
    private FamilyHistoryService familyHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFamilyHistoryMockMvc;

    private FamilyHistory familyHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FamilyHistoryResource familyHistoryResource = new FamilyHistoryResource(familyHistoryService);
        this.restFamilyHistoryMockMvc = MockMvcBuilders.standaloneSetup(familyHistoryResource)
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
    public static FamilyHistory createEntity(EntityManager em) {
        FamilyHistory familyHistory = new FamilyHistory()
            .fathersDiseases(DEFAULT_FATHERS_DISEASES)
            .mothersDiseases(DEFAULT_MOTHERS_DISEASES)
            .siblingsDiseases(DEFAULT_SIBLINGS_DISEASES)
            .otherRelativesDiseases(DEFAULT_OTHER_RELATIVES_DISEASES);
        // Add required entity
        Patient patient = PatientResourceIntTest.createEntity(em);
        em.persist(patient);
        em.flush();
        familyHistory.setPatient(patient);
        return familyHistory;
    }

    @Before
    public void initTest() {
        familyHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamilyHistory() throws Exception {
        int databaseSizeBeforeCreate = familyHistoryRepository.findAll().size();

        // Create the FamilyHistory
        restFamilyHistoryMockMvc.perform(post("/api/family-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyHistory)))
            .andExpect(status().isCreated());

        // Validate the FamilyHistory in the database
        List<FamilyHistory> familyHistoryList = familyHistoryRepository.findAll();
        assertThat(familyHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        FamilyHistory testFamilyHistory = familyHistoryList.get(familyHistoryList.size() - 1);
        assertThat(testFamilyHistory.getFathersDiseases()).isEqualTo(DEFAULT_FATHERS_DISEASES);
        assertThat(testFamilyHistory.getMothersDiseases()).isEqualTo(DEFAULT_MOTHERS_DISEASES);
        assertThat(testFamilyHistory.getSiblingsDiseases()).isEqualTo(DEFAULT_SIBLINGS_DISEASES);
        assertThat(testFamilyHistory.getOtherRelativesDiseases()).isEqualTo(DEFAULT_OTHER_RELATIVES_DISEASES);
    }

    @Test
    @Transactional
    public void createFamilyHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familyHistoryRepository.findAll().size();

        // Create the FamilyHistory with an existing ID
        familyHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyHistoryMockMvc.perform(post("/api/family-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyHistory)))
            .andExpect(status().isBadRequest());

        // Validate the FamilyHistory in the database
        List<FamilyHistory> familyHistoryList = familyHistoryRepository.findAll();
        assertThat(familyHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFamilyHistories() throws Exception {
        // Initialize the database
        familyHistoryRepository.saveAndFlush(familyHistory);

        // Get all the familyHistoryList
        restFamilyHistoryMockMvc.perform(get("/api/family-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].fathersDiseases").value(hasItem(DEFAULT_FATHERS_DISEASES.toString())))
            .andExpect(jsonPath("$.[*].mothersDiseases").value(hasItem(DEFAULT_MOTHERS_DISEASES.toString())))
            .andExpect(jsonPath("$.[*].siblingsDiseases").value(hasItem(DEFAULT_SIBLINGS_DISEASES.toString())))
            .andExpect(jsonPath("$.[*].otherRelativesDiseases").value(hasItem(DEFAULT_OTHER_RELATIVES_DISEASES.toString())));
    }

    @Test
    @Transactional
    public void getFamilyHistory() throws Exception {
        // Initialize the database
        familyHistoryRepository.saveAndFlush(familyHistory);

        // Get the familyHistory
        restFamilyHistoryMockMvc.perform(get("/api/family-histories/{id}", familyHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(familyHistory.getId().intValue()))
            .andExpect(jsonPath("$.fathersDiseases").value(DEFAULT_FATHERS_DISEASES.toString()))
            .andExpect(jsonPath("$.mothersDiseases").value(DEFAULT_MOTHERS_DISEASES.toString()))
            .andExpect(jsonPath("$.siblingsDiseases").value(DEFAULT_SIBLINGS_DISEASES.toString()))
            .andExpect(jsonPath("$.otherRelativesDiseases").value(DEFAULT_OTHER_RELATIVES_DISEASES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFamilyHistory() throws Exception {
        // Get the familyHistory
        restFamilyHistoryMockMvc.perform(get("/api/family-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamilyHistory() throws Exception {
        // Initialize the database
        familyHistoryService.save(familyHistory);

        int databaseSizeBeforeUpdate = familyHistoryRepository.findAll().size();

        // Update the familyHistory
        FamilyHistory updatedFamilyHistory = familyHistoryRepository.findOne(familyHistory.getId());
        // Disconnect from session so that the updates on updatedFamilyHistory are not directly saved in db
        em.detach(updatedFamilyHistory);
        updatedFamilyHistory
            .fathersDiseases(UPDATED_FATHERS_DISEASES)
            .mothersDiseases(UPDATED_MOTHERS_DISEASES)
            .siblingsDiseases(UPDATED_SIBLINGS_DISEASES)
            .otherRelativesDiseases(UPDATED_OTHER_RELATIVES_DISEASES);

        restFamilyHistoryMockMvc.perform(put("/api/family-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFamilyHistory)))
            .andExpect(status().isOk());

        // Validate the FamilyHistory in the database
        List<FamilyHistory> familyHistoryList = familyHistoryRepository.findAll();
        assertThat(familyHistoryList).hasSize(databaseSizeBeforeUpdate);
        FamilyHistory testFamilyHistory = familyHistoryList.get(familyHistoryList.size() - 1);
        assertThat(testFamilyHistory.getFathersDiseases()).isEqualTo(UPDATED_FATHERS_DISEASES);
        assertThat(testFamilyHistory.getMothersDiseases()).isEqualTo(UPDATED_MOTHERS_DISEASES);
        assertThat(testFamilyHistory.getSiblingsDiseases()).isEqualTo(UPDATED_SIBLINGS_DISEASES);
        assertThat(testFamilyHistory.getOtherRelativesDiseases()).isEqualTo(UPDATED_OTHER_RELATIVES_DISEASES);
    }

    @Test
    @Transactional
    public void updateNonExistingFamilyHistory() throws Exception {
        int databaseSizeBeforeUpdate = familyHistoryRepository.findAll().size();

        // Create the FamilyHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFamilyHistoryMockMvc.perform(put("/api/family-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familyHistory)))
            .andExpect(status().isCreated());

        // Validate the FamilyHistory in the database
        List<FamilyHistory> familyHistoryList = familyHistoryRepository.findAll();
        assertThat(familyHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFamilyHistory() throws Exception {
        // Initialize the database
        familyHistoryService.save(familyHistory);

        int databaseSizeBeforeDelete = familyHistoryRepository.findAll().size();

        // Get the familyHistory
        restFamilyHistoryMockMvc.perform(delete("/api/family-histories/{id}", familyHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FamilyHistory> familyHistoryList = familyHistoryRepository.findAll();
        assertThat(familyHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilyHistory.class);
        FamilyHistory familyHistory1 = new FamilyHistory();
        familyHistory1.setId(1L);
        FamilyHistory familyHistory2 = new FamilyHistory();
        familyHistory2.setId(familyHistory1.getId());
        assertThat(familyHistory1).isEqualTo(familyHistory2);
        familyHistory2.setId(2L);
        assertThat(familyHistory1).isNotEqualTo(familyHistory2);
        familyHistory1.setId(null);
        assertThat(familyHistory1).isNotEqualTo(familyHistory2);
    }
}
