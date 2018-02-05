package org.fits.hms.web.rest;

import org.fits.hms.HospitalManagementSystemApp;

import org.fits.hms.domain.Medication;
import org.fits.hms.domain.Medicine;
import org.fits.hms.repository.MedicationRepository;
import org.fits.hms.service.MedicationService;
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
 * Test class for the MedicationResource REST controller.
 *
 * @see MedicationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementSystemApp.class)
public class MedicationResourceIntTest {

    private static final String DEFAULT_DOSE = "AAAAAAAAAA";
    private static final String UPDATED_DOSE = "BBBBBBBBBB";

    private static final String DEFAULT_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_FREQUENCY = "BBBBBBBBBB";

    private static final String DEFAULT_ROUTE = "AAAAAAAAAA";
    private static final String UPDATED_ROUTE = "BBBBBBBBBB";

    private static final String DEFAULT_MANE_NOCTE = "AAAAAAAAAA";
    private static final String UPDATED_MANE_NOCTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USING_CURRENTLY = false;
    private static final Boolean UPDATED_USING_CURRENTLY = true;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicationMockMvc;

    private Medication medication;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicationResource medicationResource = new MedicationResource(medicationService);
        this.restMedicationMockMvc = MockMvcBuilders.standaloneSetup(medicationResource)
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
    public static Medication createEntity(EntityManager em) {
        Medication medication = new Medication()
            .dose(DEFAULT_DOSE)
            .frequency(DEFAULT_FREQUENCY)
            .route(DEFAULT_ROUTE)
            .maneNocte(DEFAULT_MANE_NOCTE)
            .usingCurrently(DEFAULT_USING_CURRENTLY);
        // Add required entity
        Medicine medicine = MedicineResourceIntTest.createEntity(em);
        em.persist(medicine);
        em.flush();
        medication.setMedicine(medicine);
        return medication;
    }

    @Before
    public void initTest() {
        medication = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedication() throws Exception {
        int databaseSizeBeforeCreate = medicationRepository.findAll().size();

        // Create the Medication
        restMedicationMockMvc.perform(post("/api/medications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medication)))
            .andExpect(status().isCreated());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeCreate + 1);
        Medication testMedication = medicationList.get(medicationList.size() - 1);
        assertThat(testMedication.getDose()).isEqualTo(DEFAULT_DOSE);
        assertThat(testMedication.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testMedication.getRoute()).isEqualTo(DEFAULT_ROUTE);
        assertThat(testMedication.getManeNocte()).isEqualTo(DEFAULT_MANE_NOCTE);
        assertThat(testMedication.isUsingCurrently()).isEqualTo(DEFAULT_USING_CURRENTLY);
    }

    @Test
    @Transactional
    public void createMedicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicationRepository.findAll().size();

        // Create the Medication with an existing ID
        medication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicationMockMvc.perform(post("/api/medications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medication)))
            .andExpect(status().isBadRequest());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedications() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get all the medicationList
        restMedicationMockMvc.perform(get("/api/medications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medication.getId().intValue())))
            .andExpect(jsonPath("$.[*].dose").value(hasItem(DEFAULT_DOSE.toString())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].route").value(hasItem(DEFAULT_ROUTE.toString())))
            .andExpect(jsonPath("$.[*].maneNocte").value(hasItem(DEFAULT_MANE_NOCTE.toString())))
            .andExpect(jsonPath("$.[*].usingCurrently").value(hasItem(DEFAULT_USING_CURRENTLY.booleanValue())));
    }

    @Test
    @Transactional
    public void getMedication() throws Exception {
        // Initialize the database
        medicationRepository.saveAndFlush(medication);

        // Get the medication
        restMedicationMockMvc.perform(get("/api/medications/{id}", medication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medication.getId().intValue()))
            .andExpect(jsonPath("$.dose").value(DEFAULT_DOSE.toString()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.toString()))
            .andExpect(jsonPath("$.route").value(DEFAULT_ROUTE.toString()))
            .andExpect(jsonPath("$.maneNocte").value(DEFAULT_MANE_NOCTE.toString()))
            .andExpect(jsonPath("$.usingCurrently").value(DEFAULT_USING_CURRENTLY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMedication() throws Exception {
        // Get the medication
        restMedicationMockMvc.perform(get("/api/medications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedication() throws Exception {
        // Initialize the database
        medicationService.save(medication);

        int databaseSizeBeforeUpdate = medicationRepository.findAll().size();

        // Update the medication
        Medication updatedMedication = medicationRepository.findOne(medication.getId());
        // Disconnect from session so that the updates on updatedMedication are not directly saved in db
        em.detach(updatedMedication);
        updatedMedication
            .dose(UPDATED_DOSE)
            .frequency(UPDATED_FREQUENCY)
            .route(UPDATED_ROUTE)
            .maneNocte(UPDATED_MANE_NOCTE)
            .usingCurrently(UPDATED_USING_CURRENTLY);

        restMedicationMockMvc.perform(put("/api/medications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedication)))
            .andExpect(status().isOk());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeUpdate);
        Medication testMedication = medicationList.get(medicationList.size() - 1);
        assertThat(testMedication.getDose()).isEqualTo(UPDATED_DOSE);
        assertThat(testMedication.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testMedication.getRoute()).isEqualTo(UPDATED_ROUTE);
        assertThat(testMedication.getManeNocte()).isEqualTo(UPDATED_MANE_NOCTE);
        assertThat(testMedication.isUsingCurrently()).isEqualTo(UPDATED_USING_CURRENTLY);
    }

    @Test
    @Transactional
    public void updateNonExistingMedication() throws Exception {
        int databaseSizeBeforeUpdate = medicationRepository.findAll().size();

        // Create the Medication

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedicationMockMvc.perform(put("/api/medications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medication)))
            .andExpect(status().isCreated());

        // Validate the Medication in the database
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedication() throws Exception {
        // Initialize the database
        medicationService.save(medication);

        int databaseSizeBeforeDelete = medicationRepository.findAll().size();

        // Get the medication
        restMedicationMockMvc.perform(delete("/api/medications/{id}", medication.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Medication> medicationList = medicationRepository.findAll();
        assertThat(medicationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medication.class);
        Medication medication1 = new Medication();
        medication1.setId(1L);
        Medication medication2 = new Medication();
        medication2.setId(medication1.getId());
        assertThat(medication1).isEqualTo(medication2);
        medication2.setId(2L);
        assertThat(medication1).isNotEqualTo(medication2);
        medication1.setId(null);
        assertThat(medication1).isNotEqualTo(medication2);
    }
}
