package org.fits.hms.web.rest;

import org.fits.hms.HospitalManagementSystemApp;

import org.fits.hms.domain.PersonalSocialDetails;
import org.fits.hms.domain.Patient;
import org.fits.hms.repository.PersonalSocialDetailsRepository;
import org.fits.hms.service.PersonalSocialDetailsService;
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
 * Test class for the PersonalSocialDetailsResource REST controller.
 *
 * @see PersonalSocialDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementSystemApp.class)
public class PersonalSocialDetailsResourceIntTest {

    private static final String DEFAULT_EDUCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYMENT = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYMENT = "BBBBBBBBBB";

    private static final String DEFAULT_ASSISTANCE_AT_HOME = "AAAAAAAAAA";
    private static final String UPDATED_ASSISTANCE_AT_HOME = "BBBBBBBBBB";

    @Autowired
    private PersonalSocialDetailsRepository personalSocialDetailsRepository;

    @Autowired
    private PersonalSocialDetailsService personalSocialDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonalSocialDetailsMockMvc;

    private PersonalSocialDetails personalSocialDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonalSocialDetailsResource personalSocialDetailsResource = new PersonalSocialDetailsResource(personalSocialDetailsService);
        this.restPersonalSocialDetailsMockMvc = MockMvcBuilders.standaloneSetup(personalSocialDetailsResource)
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
    public static PersonalSocialDetails createEntity(EntityManager em) {
        PersonalSocialDetails personalSocialDetails = new PersonalSocialDetails()
            .education(DEFAULT_EDUCATION)
            .employment(DEFAULT_EMPLOYMENT)
            .assistanceAtHome(DEFAULT_ASSISTANCE_AT_HOME);
        // Add required entity
        Patient patient = PatientResourceIntTest.createEntity(em);
        em.persist(patient);
        em.flush();
        personalSocialDetails.setPatient(patient);
        return personalSocialDetails;
    }

    @Before
    public void initTest() {
        personalSocialDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonalSocialDetails() throws Exception {
        int databaseSizeBeforeCreate = personalSocialDetailsRepository.findAll().size();

        // Create the PersonalSocialDetails
        restPersonalSocialDetailsMockMvc.perform(post("/api/personal-social-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalSocialDetails)))
            .andExpect(status().isCreated());

        // Validate the PersonalSocialDetails in the database
        List<PersonalSocialDetails> personalSocialDetailsList = personalSocialDetailsRepository.findAll();
        assertThat(personalSocialDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PersonalSocialDetails testPersonalSocialDetails = personalSocialDetailsList.get(personalSocialDetailsList.size() - 1);
        assertThat(testPersonalSocialDetails.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testPersonalSocialDetails.getEmployment()).isEqualTo(DEFAULT_EMPLOYMENT);
        assertThat(testPersonalSocialDetails.getAssistanceAtHome()).isEqualTo(DEFAULT_ASSISTANCE_AT_HOME);
    }

    @Test
    @Transactional
    public void createPersonalSocialDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personalSocialDetailsRepository.findAll().size();

        // Create the PersonalSocialDetails with an existing ID
        personalSocialDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalSocialDetailsMockMvc.perform(post("/api/personal-social-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalSocialDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PersonalSocialDetails in the database
        List<PersonalSocialDetails> personalSocialDetailsList = personalSocialDetailsRepository.findAll();
        assertThat(personalSocialDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonalSocialDetails() throws Exception {
        // Initialize the database
        personalSocialDetailsRepository.saveAndFlush(personalSocialDetails);

        // Get all the personalSocialDetailsList
        restPersonalSocialDetailsMockMvc.perform(get("/api/personal-social-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalSocialDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION.toString())))
            .andExpect(jsonPath("$.[*].employment").value(hasItem(DEFAULT_EMPLOYMENT.toString())))
            .andExpect(jsonPath("$.[*].assistanceAtHome").value(hasItem(DEFAULT_ASSISTANCE_AT_HOME.toString())));
    }

    @Test
    @Transactional
    public void getPersonalSocialDetails() throws Exception {
        // Initialize the database
        personalSocialDetailsRepository.saveAndFlush(personalSocialDetails);

        // Get the personalSocialDetails
        restPersonalSocialDetailsMockMvc.perform(get("/api/personal-social-details/{id}", personalSocialDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personalSocialDetails.getId().intValue()))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION.toString()))
            .andExpect(jsonPath("$.employment").value(DEFAULT_EMPLOYMENT.toString()))
            .andExpect(jsonPath("$.assistanceAtHome").value(DEFAULT_ASSISTANCE_AT_HOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonalSocialDetails() throws Exception {
        // Get the personalSocialDetails
        restPersonalSocialDetailsMockMvc.perform(get("/api/personal-social-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonalSocialDetails() throws Exception {
        // Initialize the database
        personalSocialDetailsService.save(personalSocialDetails);

        int databaseSizeBeforeUpdate = personalSocialDetailsRepository.findAll().size();

        // Update the personalSocialDetails
        PersonalSocialDetails updatedPersonalSocialDetails = personalSocialDetailsRepository.findOne(personalSocialDetails.getId());
        // Disconnect from session so that the updates on updatedPersonalSocialDetails are not directly saved in db
        em.detach(updatedPersonalSocialDetails);
        updatedPersonalSocialDetails
            .education(UPDATED_EDUCATION)
            .employment(UPDATED_EMPLOYMENT)
            .assistanceAtHome(UPDATED_ASSISTANCE_AT_HOME);

        restPersonalSocialDetailsMockMvc.perform(put("/api/personal-social-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonalSocialDetails)))
            .andExpect(status().isOk());

        // Validate the PersonalSocialDetails in the database
        List<PersonalSocialDetails> personalSocialDetailsList = personalSocialDetailsRepository.findAll();
        assertThat(personalSocialDetailsList).hasSize(databaseSizeBeforeUpdate);
        PersonalSocialDetails testPersonalSocialDetails = personalSocialDetailsList.get(personalSocialDetailsList.size() - 1);
        assertThat(testPersonalSocialDetails.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testPersonalSocialDetails.getEmployment()).isEqualTo(UPDATED_EMPLOYMENT);
        assertThat(testPersonalSocialDetails.getAssistanceAtHome()).isEqualTo(UPDATED_ASSISTANCE_AT_HOME);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonalSocialDetails() throws Exception {
        int databaseSizeBeforeUpdate = personalSocialDetailsRepository.findAll().size();

        // Create the PersonalSocialDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonalSocialDetailsMockMvc.perform(put("/api/personal-social-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalSocialDetails)))
            .andExpect(status().isCreated());

        // Validate the PersonalSocialDetails in the database
        List<PersonalSocialDetails> personalSocialDetailsList = personalSocialDetailsRepository.findAll();
        assertThat(personalSocialDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonalSocialDetails() throws Exception {
        // Initialize the database
        personalSocialDetailsService.save(personalSocialDetails);

        int databaseSizeBeforeDelete = personalSocialDetailsRepository.findAll().size();

        // Get the personalSocialDetails
        restPersonalSocialDetailsMockMvc.perform(delete("/api/personal-social-details/{id}", personalSocialDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonalSocialDetails> personalSocialDetailsList = personalSocialDetailsRepository.findAll();
        assertThat(personalSocialDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonalSocialDetails.class);
        PersonalSocialDetails personalSocialDetails1 = new PersonalSocialDetails();
        personalSocialDetails1.setId(1L);
        PersonalSocialDetails personalSocialDetails2 = new PersonalSocialDetails();
        personalSocialDetails2.setId(personalSocialDetails1.getId());
        assertThat(personalSocialDetails1).isEqualTo(personalSocialDetails2);
        personalSocialDetails2.setId(2L);
        assertThat(personalSocialDetails1).isNotEqualTo(personalSocialDetails2);
        personalSocialDetails1.setId(null);
        assertThat(personalSocialDetails1).isNotEqualTo(personalSocialDetails2);
    }
}
