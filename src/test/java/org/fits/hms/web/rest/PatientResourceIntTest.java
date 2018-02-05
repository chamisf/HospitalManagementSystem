package org.fits.hms.web.rest;

import org.fits.hms.HospitalManagementSystemApp;

import org.fits.hms.domain.Patient;
import org.fits.hms.repository.PatientRepository;
import org.fits.hms.service.PatientService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.fits.hms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.fits.hms.domain.enumeration.Gender;
import org.fits.hms.domain.enumeration.MaritalStatus;
import org.fits.hms.domain.enumeration.BloodGroup;
/**
 * Test class for the PatientResource REST controller.
 *
 * @see PatientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementSystemApp.class)
public class PatientResourceIntTest {

    private static final String DEFAULT_PATIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOSPITAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HOSPITAL_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REGISTERED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REGISTERED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_PATIENT_HEIGHT = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_HEIGHT = "BBBBBBBBBB";

    private static final Integer DEFAULT_PATIENT_WIGHT = 1;
    private static final Integer UPDATED_PATIENT_WIGHT = 2;

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.MARRIED;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.SINGLE;

    private static final String DEFAULT_NIC_NUMBER = "380608287X";
    private static final String UPDATED_NIC_NUMBER = "601328110v";

    private static final String DEFAULT_TELEPHONE_NUMBER = "+94399267978";
    private static final String UPDATED_TELEPHONE_NUMBER = "0059895153";

    private static final String DEFAULT_EMERGENCY_CONTACT_NUMBER = "+94332546774";
    private static final String UPDATED_EMERGENCY_CONTACT_NUMBER = "+94617529500";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final BloodGroup DEFAULT_BLOOD_GROUP = BloodGroup.A_PLUS;
    private static final BloodGroup UPDATED_BLOOD_GROUP = BloodGroup.A_MINUS;

    private static final String DEFAULT_GUARDIAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GUARDIAN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GUARDIAN_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_GUARDIAN_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_REFERRING_PHYSICIAN = "AAAAAAAAAA";
    private static final String UPDATED_REFERRING_PHYSICIAN = "BBBBBBBBBB";

    private static final String DEFAULT_REFERRING_HOSPITAL = "AAAAAAAAAA";
    private static final String UPDATED_REFERRING_HOSPITAL = "BBBBBBBBBB";

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPatientMockMvc;

    private Patient patient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientResource patientResource = new PatientResource(patientService);
        this.restPatientMockMvc = MockMvcBuilders.standaloneSetup(patientResource)
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
    public static Patient createEntity(EntityManager em) {
        Patient patient = new Patient()
            .patientName(DEFAULT_PATIENT_NAME)
            .hospitalNumber(DEFAULT_HOSPITAL_NUMBER)
            .registeredDate(DEFAULT_REGISTERED_DATE)
            .age(DEFAULT_AGE)
            .birthDate(DEFAULT_BIRTH_DATE)
            .gender(DEFAULT_GENDER)
            .patientHeight(DEFAULT_PATIENT_HEIGHT)
            .patientWight(DEFAULT_PATIENT_WIGHT)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .nicNumber(DEFAULT_NIC_NUMBER)
            .telephoneNumber(DEFAULT_TELEPHONE_NUMBER)
            .emergencyContactNumber(DEFAULT_EMERGENCY_CONTACT_NUMBER)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .occupation(DEFAULT_OCCUPATION)
            .bloodGroup(DEFAULT_BLOOD_GROUP)
            .guardianName(DEFAULT_GUARDIAN_NAME)
            .guardianAddress(DEFAULT_GUARDIAN_ADDRESS)
            .referringPhysician(DEFAULT_REFERRING_PHYSICIAN)
            .referringHospital(DEFAULT_REFERRING_HOSPITAL);
        return patient;
    }

    @Before
    public void initTest() {
        patient = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getPatientName()).isEqualTo(DEFAULT_PATIENT_NAME);
        assertThat(testPatient.getHospitalNumber()).isEqualTo(DEFAULT_HOSPITAL_NUMBER);
        assertThat(testPatient.getRegisteredDate()).isEqualTo(DEFAULT_REGISTERED_DATE);
        assertThat(testPatient.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPatient.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPatient.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatient.getPatientHeight()).isEqualTo(DEFAULT_PATIENT_HEIGHT);
        assertThat(testPatient.getPatientWight()).isEqualTo(DEFAULT_PATIENT_WIGHT);
        assertThat(testPatient.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testPatient.getNicNumber()).isEqualTo(DEFAULT_NIC_NUMBER);
        assertThat(testPatient.getTelephoneNumber()).isEqualTo(DEFAULT_TELEPHONE_NUMBER);
        assertThat(testPatient.getEmergencyContactNumber()).isEqualTo(DEFAULT_EMERGENCY_CONTACT_NUMBER);
        assertThat(testPatient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPatient.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPatient.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testPatient.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testPatient.getGuardianName()).isEqualTo(DEFAULT_GUARDIAN_NAME);
        assertThat(testPatient.getGuardianAddress()).isEqualTo(DEFAULT_GUARDIAN_ADDRESS);
        assertThat(testPatient.getReferringPhysician()).isEqualTo(DEFAULT_REFERRING_PHYSICIAN);
        assertThat(testPatient.getReferringHospital()).isEqualTo(DEFAULT_REFERRING_HOSPITAL);
    }

    @Test
    @Transactional
    public void createPatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient with an existing ID
        patient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPatientNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setPatientName(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHospitalNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setHospitalNumber(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegisteredDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setRegisteredDate(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setGender(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList
        restPatientMockMvc.perform(get("/api/patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientName").value(hasItem(DEFAULT_PATIENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].hospitalNumber").value(hasItem(DEFAULT_HOSPITAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].registeredDate").value(hasItem(DEFAULT_REGISTERED_DATE.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].patientHeight").value(hasItem(DEFAULT_PATIENT_HEIGHT.toString())))
            .andExpect(jsonPath("$.[*].patientWight").value(hasItem(DEFAULT_PATIENT_WIGHT)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].nicNumber").value(hasItem(DEFAULT_NIC_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].telephoneNumber").value(hasItem(DEFAULT_TELEPHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].emergencyContactNumber").value(hasItem(DEFAULT_EMERGENCY_CONTACT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
            .andExpect(jsonPath("$.[*].guardianName").value(hasItem(DEFAULT_GUARDIAN_NAME.toString())))
            .andExpect(jsonPath("$.[*].guardianAddress").value(hasItem(DEFAULT_GUARDIAN_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].referringPhysician").value(hasItem(DEFAULT_REFERRING_PHYSICIAN.toString())))
            .andExpect(jsonPath("$.[*].referringHospital").value(hasItem(DEFAULT_REFERRING_HOSPITAL.toString())));
    }

    @Test
    @Transactional
    public void getPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patient.getId().intValue()))
            .andExpect(jsonPath("$.patientName").value(DEFAULT_PATIENT_NAME.toString()))
            .andExpect(jsonPath("$.hospitalNumber").value(DEFAULT_HOSPITAL_NUMBER.toString()))
            .andExpect(jsonPath("$.registeredDate").value(DEFAULT_REGISTERED_DATE.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.patientHeight").value(DEFAULT_PATIENT_HEIGHT.toString()))
            .andExpect(jsonPath("$.patientWight").value(DEFAULT_PATIENT_WIGHT))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.nicNumber").value(DEFAULT_NIC_NUMBER.toString()))
            .andExpect(jsonPath("$.telephoneNumber").value(DEFAULT_TELEPHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.emergencyContactNumber").value(DEFAULT_EMERGENCY_CONTACT_NUMBER.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.guardianName").value(DEFAULT_GUARDIAN_NAME.toString()))
            .andExpect(jsonPath("$.guardianAddress").value(DEFAULT_GUARDIAN_ADDRESS.toString()))
            .andExpect(jsonPath("$.referringPhysician").value(DEFAULT_REFERRING_PHYSICIAN.toString()))
            .andExpect(jsonPath("$.referringHospital").value(DEFAULT_REFERRING_HOSPITAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatient() throws Exception {
        // Initialize the database
        patientService.save(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        Patient updatedPatient = patientRepository.findOne(patient.getId());
        // Disconnect from session so that the updates on updatedPatient are not directly saved in db
        em.detach(updatedPatient);
        updatedPatient
            .patientName(UPDATED_PATIENT_NAME)
            .hospitalNumber(UPDATED_HOSPITAL_NUMBER)
            .registeredDate(UPDATED_REGISTERED_DATE)
            .age(UPDATED_AGE)
            .birthDate(UPDATED_BIRTH_DATE)
            .gender(UPDATED_GENDER)
            .patientHeight(UPDATED_PATIENT_HEIGHT)
            .patientWight(UPDATED_PATIENT_WIGHT)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .nicNumber(UPDATED_NIC_NUMBER)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .emergencyContactNumber(UPDATED_EMERGENCY_CONTACT_NUMBER)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .occupation(UPDATED_OCCUPATION)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .guardianName(UPDATED_GUARDIAN_NAME)
            .guardianAddress(UPDATED_GUARDIAN_ADDRESS)
            .referringPhysician(UPDATED_REFERRING_PHYSICIAN)
            .referringHospital(UPDATED_REFERRING_HOSPITAL);

        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatient)))
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getPatientName()).isEqualTo(UPDATED_PATIENT_NAME);
        assertThat(testPatient.getHospitalNumber()).isEqualTo(UPDATED_HOSPITAL_NUMBER);
        assertThat(testPatient.getRegisteredDate()).isEqualTo(UPDATED_REGISTERED_DATE);
        assertThat(testPatient.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPatient.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPatient.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatient.getPatientHeight()).isEqualTo(UPDATED_PATIENT_HEIGHT);
        assertThat(testPatient.getPatientWight()).isEqualTo(UPDATED_PATIENT_WIGHT);
        assertThat(testPatient.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testPatient.getNicNumber()).isEqualTo(UPDATED_NIC_NUMBER);
        assertThat(testPatient.getTelephoneNumber()).isEqualTo(UPDATED_TELEPHONE_NUMBER);
        assertThat(testPatient.getEmergencyContactNumber()).isEqualTo(UPDATED_EMERGENCY_CONTACT_NUMBER);
        assertThat(testPatient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPatient.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPatient.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testPatient.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testPatient.getGuardianName()).isEqualTo(UPDATED_GUARDIAN_NAME);
        assertThat(testPatient.getGuardianAddress()).isEqualTo(UPDATED_GUARDIAN_ADDRESS);
        assertThat(testPatient.getReferringPhysician()).isEqualTo(UPDATED_REFERRING_PHYSICIAN);
        assertThat(testPatient.getReferringHospital()).isEqualTo(UPDATED_REFERRING_HOSPITAL);
    }

    @Test
    @Transactional
    public void updateNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Create the Patient

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePatient() throws Exception {
        // Initialize the database
        patientService.save(patient);

        int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Get the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}", patient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Patient.class);
        Patient patient1 = new Patient();
        patient1.setId(1L);
        Patient patient2 = new Patient();
        patient2.setId(patient1.getId());
        assertThat(patient1).isEqualTo(patient2);
        patient2.setId(2L);
        assertThat(patient1).isNotEqualTo(patient2);
        patient1.setId(null);
        assertThat(patient1).isNotEqualTo(patient2);
    }
}
