package org.fits.hms.web.rest;

import org.fits.hms.HospitalManagementSystemApp;

import org.fits.hms.domain.DrugHistory;
import org.fits.hms.domain.Patient;
import org.fits.hms.repository.DrugHistoryRepository;
import org.fits.hms.service.DrugHistoryService;
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
 * Test class for the DrugHistoryResource REST controller.
 *
 * @see DrugHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementSystemApp.class)
public class DrugHistoryResourceIntTest {

    private static final Boolean DEFAULT_IS_COMPLIANT_PATIENT = false;
    private static final Boolean UPDATED_IS_COMPLIANT_PATIENT = true;

    private static final String DEFAULT_ADDITIONAL_MEDICATION_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_MEDICATION_INFORMATION = "BBBBBBBBBB";

    @Autowired
    private DrugHistoryRepository drugHistoryRepository;

    @Autowired
    private DrugHistoryService drugHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDrugHistoryMockMvc;

    private DrugHistory drugHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DrugHistoryResource drugHistoryResource = new DrugHistoryResource(drugHistoryService);
        this.restDrugHistoryMockMvc = MockMvcBuilders.standaloneSetup(drugHistoryResource)
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
    public static DrugHistory createEntity(EntityManager em) {
        DrugHistory drugHistory = new DrugHistory()
            .isCompliantPatient(DEFAULT_IS_COMPLIANT_PATIENT)
            .additionalMedicationInformation(DEFAULT_ADDITIONAL_MEDICATION_INFORMATION);
        // Add required entity
        Patient patient = PatientResourceIntTest.createEntity(em);
        em.persist(patient);
        em.flush();
        drugHistory.setPatient(patient);
        return drugHistory;
    }

    @Before
    public void initTest() {
        drugHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrugHistory() throws Exception {
        int databaseSizeBeforeCreate = drugHistoryRepository.findAll().size();

        // Create the DrugHistory
        restDrugHistoryMockMvc.perform(post("/api/drug-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugHistory)))
            .andExpect(status().isCreated());

        // Validate the DrugHistory in the database
        List<DrugHistory> drugHistoryList = drugHistoryRepository.findAll();
        assertThat(drugHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        DrugHistory testDrugHistory = drugHistoryList.get(drugHistoryList.size() - 1);
        assertThat(testDrugHistory.isIsCompliantPatient()).isEqualTo(DEFAULT_IS_COMPLIANT_PATIENT);
        assertThat(testDrugHistory.getAdditionalMedicationInformation()).isEqualTo(DEFAULT_ADDITIONAL_MEDICATION_INFORMATION);
    }

    @Test
    @Transactional
    public void createDrugHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drugHistoryRepository.findAll().size();

        // Create the DrugHistory with an existing ID
        drugHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrugHistoryMockMvc.perform(post("/api/drug-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugHistory)))
            .andExpect(status().isBadRequest());

        // Validate the DrugHistory in the database
        List<DrugHistory> drugHistoryList = drugHistoryRepository.findAll();
        assertThat(drugHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDrugHistories() throws Exception {
        // Initialize the database
        drugHistoryRepository.saveAndFlush(drugHistory);

        // Get all the drugHistoryList
        restDrugHistoryMockMvc.perform(get("/api/drug-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drugHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].isCompliantPatient").value(hasItem(DEFAULT_IS_COMPLIANT_PATIENT.booleanValue())))
            .andExpect(jsonPath("$.[*].additionalMedicationInformation").value(hasItem(DEFAULT_ADDITIONAL_MEDICATION_INFORMATION.toString())));
    }

    @Test
    @Transactional
    public void getDrugHistory() throws Exception {
        // Initialize the database
        drugHistoryRepository.saveAndFlush(drugHistory);

        // Get the drugHistory
        restDrugHistoryMockMvc.perform(get("/api/drug-histories/{id}", drugHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drugHistory.getId().intValue()))
            .andExpect(jsonPath("$.isCompliantPatient").value(DEFAULT_IS_COMPLIANT_PATIENT.booleanValue()))
            .andExpect(jsonPath("$.additionalMedicationInformation").value(DEFAULT_ADDITIONAL_MEDICATION_INFORMATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDrugHistory() throws Exception {
        // Get the drugHistory
        restDrugHistoryMockMvc.perform(get("/api/drug-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrugHistory() throws Exception {
        // Initialize the database
        drugHistoryService.save(drugHistory);

        int databaseSizeBeforeUpdate = drugHistoryRepository.findAll().size();

        // Update the drugHistory
        DrugHistory updatedDrugHistory = drugHistoryRepository.findOne(drugHistory.getId());
        // Disconnect from session so that the updates on updatedDrugHistory are not directly saved in db
        em.detach(updatedDrugHistory);
        updatedDrugHistory
            .isCompliantPatient(UPDATED_IS_COMPLIANT_PATIENT)
            .additionalMedicationInformation(UPDATED_ADDITIONAL_MEDICATION_INFORMATION);

        restDrugHistoryMockMvc.perform(put("/api/drug-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDrugHistory)))
            .andExpect(status().isOk());

        // Validate the DrugHistory in the database
        List<DrugHistory> drugHistoryList = drugHistoryRepository.findAll();
        assertThat(drugHistoryList).hasSize(databaseSizeBeforeUpdate);
        DrugHistory testDrugHistory = drugHistoryList.get(drugHistoryList.size() - 1);
        assertThat(testDrugHistory.isIsCompliantPatient()).isEqualTo(UPDATED_IS_COMPLIANT_PATIENT);
        assertThat(testDrugHistory.getAdditionalMedicationInformation()).isEqualTo(UPDATED_ADDITIONAL_MEDICATION_INFORMATION);
    }

    @Test
    @Transactional
    public void updateNonExistingDrugHistory() throws Exception {
        int databaseSizeBeforeUpdate = drugHistoryRepository.findAll().size();

        // Create the DrugHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDrugHistoryMockMvc.perform(put("/api/drug-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugHistory)))
            .andExpect(status().isCreated());

        // Validate the DrugHistory in the database
        List<DrugHistory> drugHistoryList = drugHistoryRepository.findAll();
        assertThat(drugHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDrugHistory() throws Exception {
        // Initialize the database
        drugHistoryService.save(drugHistory);

        int databaseSizeBeforeDelete = drugHistoryRepository.findAll().size();

        // Get the drugHistory
        restDrugHistoryMockMvc.perform(delete("/api/drug-histories/{id}", drugHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DrugHistory> drugHistoryList = drugHistoryRepository.findAll();
        assertThat(drugHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrugHistory.class);
        DrugHistory drugHistory1 = new DrugHistory();
        drugHistory1.setId(1L);
        DrugHistory drugHistory2 = new DrugHistory();
        drugHistory2.setId(drugHistory1.getId());
        assertThat(drugHistory1).isEqualTo(drugHistory2);
        drugHistory2.setId(2L);
        assertThat(drugHistory1).isNotEqualTo(drugHistory2);
        drugHistory1.setId(null);
        assertThat(drugHistory1).isNotEqualTo(drugHistory2);
    }
}
