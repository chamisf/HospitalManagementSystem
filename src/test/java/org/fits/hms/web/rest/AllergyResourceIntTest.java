package org.fits.hms.web.rest;

import org.fits.hms.HospitalManagementSystemApp;

import org.fits.hms.domain.Allergy;
import org.fits.hms.domain.Patient;
import org.fits.hms.repository.AllergyRepository;
import org.fits.hms.service.AllergyService;
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
 * Test class for the AllergyResource REST controller.
 *
 * @see AllergyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementSystemApp.class)
public class AllergyResourceIntTest {

    private static final String DEFAULT_ADDITIONAL_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_INFORMATION = "BBBBBBBBBB";

    @Autowired
    private AllergyRepository allergyRepository;

    @Autowired
    private AllergyService allergyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAllergyMockMvc;

    private Allergy allergy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllergyResource allergyResource = new AllergyResource(allergyService);
        this.restAllergyMockMvc = MockMvcBuilders.standaloneSetup(allergyResource)
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
    public static Allergy createEntity(EntityManager em) {
        Allergy allergy = new Allergy()
            .additionalInformation(DEFAULT_ADDITIONAL_INFORMATION);
        // Add required entity
        Patient patient = PatientResourceIntTest.createEntity(em);
        em.persist(patient);
        em.flush();
        allergy.setPatient(patient);
        return allergy;
    }

    @Before
    public void initTest() {
        allergy = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllergy() throws Exception {
        int databaseSizeBeforeCreate = allergyRepository.findAll().size();

        // Create the Allergy
        restAllergyMockMvc.perform(post("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergy)))
            .andExpect(status().isCreated());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeCreate + 1);
        Allergy testAllergy = allergyList.get(allergyList.size() - 1);
        assertThat(testAllergy.getAdditionalInformation()).isEqualTo(DEFAULT_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    public void createAllergyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allergyRepository.findAll().size();

        // Create the Allergy with an existing ID
        allergy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllergyMockMvc.perform(post("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergy)))
            .andExpect(status().isBadRequest());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAllergies() throws Exception {
        // Initialize the database
        allergyRepository.saveAndFlush(allergy);

        // Get all the allergyList
        restAllergyMockMvc.perform(get("/api/allergies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allergy.getId().intValue())))
            .andExpect(jsonPath("$.[*].additionalInformation").value(hasItem(DEFAULT_ADDITIONAL_INFORMATION.toString())));
    }

    @Test
    @Transactional
    public void getAllergy() throws Exception {
        // Initialize the database
        allergyRepository.saveAndFlush(allergy);

        // Get the allergy
        restAllergyMockMvc.perform(get("/api/allergies/{id}", allergy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(allergy.getId().intValue()))
            .andExpect(jsonPath("$.additionalInformation").value(DEFAULT_ADDITIONAL_INFORMATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAllergy() throws Exception {
        // Get the allergy
        restAllergyMockMvc.perform(get("/api/allergies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllergy() throws Exception {
        // Initialize the database
        allergyService.save(allergy);

        int databaseSizeBeforeUpdate = allergyRepository.findAll().size();

        // Update the allergy
        Allergy updatedAllergy = allergyRepository.findOne(allergy.getId());
        // Disconnect from session so that the updates on updatedAllergy are not directly saved in db
        em.detach(updatedAllergy);
        updatedAllergy
            .additionalInformation(UPDATED_ADDITIONAL_INFORMATION);

        restAllergyMockMvc.perform(put("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllergy)))
            .andExpect(status().isOk());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeUpdate);
        Allergy testAllergy = allergyList.get(allergyList.size() - 1);
        assertThat(testAllergy.getAdditionalInformation()).isEqualTo(UPDATED_ADDITIONAL_INFORMATION);
    }

    @Test
    @Transactional
    public void updateNonExistingAllergy() throws Exception {
        int databaseSizeBeforeUpdate = allergyRepository.findAll().size();

        // Create the Allergy

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAllergyMockMvc.perform(put("/api/allergies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(allergy)))
            .andExpect(status().isCreated());

        // Validate the Allergy in the database
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAllergy() throws Exception {
        // Initialize the database
        allergyService.save(allergy);

        int databaseSizeBeforeDelete = allergyRepository.findAll().size();

        // Get the allergy
        restAllergyMockMvc.perform(delete("/api/allergies/{id}", allergy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Allergy> allergyList = allergyRepository.findAll();
        assertThat(allergyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Allergy.class);
        Allergy allergy1 = new Allergy();
        allergy1.setId(1L);
        Allergy allergy2 = new Allergy();
        allergy2.setId(allergy1.getId());
        assertThat(allergy1).isEqualTo(allergy2);
        allergy2.setId(2L);
        assertThat(allergy1).isNotEqualTo(allergy2);
        allergy1.setId(null);
        assertThat(allergy1).isNotEqualTo(allergy2);
    }
}
