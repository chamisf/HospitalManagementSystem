package org.fits.hms.web.rest;

import org.fits.hms.HospitalManagementSystemApp;

import org.fits.hms.domain.GynaecologicalHistory;
import org.fits.hms.domain.Patient;
import org.fits.hms.repository.GynaecologicalHistoryRepository;
import org.fits.hms.service.GynaecologicalHistoryService;
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
 * Test class for the GynaecologicalHistoryResource REST controller.
 *
 * @see GynaecologicalHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementSystemApp.class)
public class GynaecologicalHistoryResourceIntTest {

    private static final String DEFAULT_AGE_OF_MENARCHE = "AAAAAAAAAA";
    private static final String UPDATED_AGE_OF_MENARCHE = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MENSTRUAL_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MENSTRUAL_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_REGULARITY_OF_CYCLE = "AAAAAAAAAA";
    private static final String UPDATED_REGULARITY_OF_CYCLE = "BBBBBBBBBB";

    @Autowired
    private GynaecologicalHistoryRepository gynaecologicalHistoryRepository;

    @Autowired
    private GynaecologicalHistoryService gynaecologicalHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGynaecologicalHistoryMockMvc;

    private GynaecologicalHistory gynaecologicalHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GynaecologicalHistoryResource gynaecologicalHistoryResource = new GynaecologicalHistoryResource(gynaecologicalHistoryService);
        this.restGynaecologicalHistoryMockMvc = MockMvcBuilders.standaloneSetup(gynaecologicalHistoryResource)
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
    public static GynaecologicalHistory createEntity(EntityManager em) {
        GynaecologicalHistory gynaecologicalHistory = new GynaecologicalHistory()
            .ageOfMenarche(DEFAULT_AGE_OF_MENARCHE)
            .lastMenstrualPeriod(DEFAULT_LAST_MENSTRUAL_PERIOD)
            .regularityOfCycle(DEFAULT_REGULARITY_OF_CYCLE);
        // Add required entity
        Patient patient = PatientResourceIntTest.createEntity(em);
        em.persist(patient);
        em.flush();
        gynaecologicalHistory.setPatient(patient);
        return gynaecologicalHistory;
    }

    @Before
    public void initTest() {
        gynaecologicalHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createGynaecologicalHistory() throws Exception {
        int databaseSizeBeforeCreate = gynaecologicalHistoryRepository.findAll().size();

        // Create the GynaecologicalHistory
        restGynaecologicalHistoryMockMvc.perform(post("/api/gynaecological-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gynaecologicalHistory)))
            .andExpect(status().isCreated());

        // Validate the GynaecologicalHistory in the database
        List<GynaecologicalHistory> gynaecologicalHistoryList = gynaecologicalHistoryRepository.findAll();
        assertThat(gynaecologicalHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        GynaecologicalHistory testGynaecologicalHistory = gynaecologicalHistoryList.get(gynaecologicalHistoryList.size() - 1);
        assertThat(testGynaecologicalHistory.getAgeOfMenarche()).isEqualTo(DEFAULT_AGE_OF_MENARCHE);
        assertThat(testGynaecologicalHistory.getLastMenstrualPeriod()).isEqualTo(DEFAULT_LAST_MENSTRUAL_PERIOD);
        assertThat(testGynaecologicalHistory.getRegularityOfCycle()).isEqualTo(DEFAULT_REGULARITY_OF_CYCLE);
    }

    @Test
    @Transactional
    public void createGynaecologicalHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gynaecologicalHistoryRepository.findAll().size();

        // Create the GynaecologicalHistory with an existing ID
        gynaecologicalHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGynaecologicalHistoryMockMvc.perform(post("/api/gynaecological-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gynaecologicalHistory)))
            .andExpect(status().isBadRequest());

        // Validate the GynaecologicalHistory in the database
        List<GynaecologicalHistory> gynaecologicalHistoryList = gynaecologicalHistoryRepository.findAll();
        assertThat(gynaecologicalHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGynaecologicalHistories() throws Exception {
        // Initialize the database
        gynaecologicalHistoryRepository.saveAndFlush(gynaecologicalHistory);

        // Get all the gynaecologicalHistoryList
        restGynaecologicalHistoryMockMvc.perform(get("/api/gynaecological-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gynaecologicalHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].ageOfMenarche").value(hasItem(DEFAULT_AGE_OF_MENARCHE.toString())))
            .andExpect(jsonPath("$.[*].lastMenstrualPeriod").value(hasItem(DEFAULT_LAST_MENSTRUAL_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].regularityOfCycle").value(hasItem(DEFAULT_REGULARITY_OF_CYCLE.toString())));
    }

    @Test
    @Transactional
    public void getGynaecologicalHistory() throws Exception {
        // Initialize the database
        gynaecologicalHistoryRepository.saveAndFlush(gynaecologicalHistory);

        // Get the gynaecologicalHistory
        restGynaecologicalHistoryMockMvc.perform(get("/api/gynaecological-histories/{id}", gynaecologicalHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gynaecologicalHistory.getId().intValue()))
            .andExpect(jsonPath("$.ageOfMenarche").value(DEFAULT_AGE_OF_MENARCHE.toString()))
            .andExpect(jsonPath("$.lastMenstrualPeriod").value(DEFAULT_LAST_MENSTRUAL_PERIOD.toString()))
            .andExpect(jsonPath("$.regularityOfCycle").value(DEFAULT_REGULARITY_OF_CYCLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGynaecologicalHistory() throws Exception {
        // Get the gynaecologicalHistory
        restGynaecologicalHistoryMockMvc.perform(get("/api/gynaecological-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGynaecologicalHistory() throws Exception {
        // Initialize the database
        gynaecologicalHistoryService.save(gynaecologicalHistory);

        int databaseSizeBeforeUpdate = gynaecologicalHistoryRepository.findAll().size();

        // Update the gynaecologicalHistory
        GynaecologicalHistory updatedGynaecologicalHistory = gynaecologicalHistoryRepository.findOne(gynaecologicalHistory.getId());
        // Disconnect from session so that the updates on updatedGynaecologicalHistory are not directly saved in db
        em.detach(updatedGynaecologicalHistory);
        updatedGynaecologicalHistory
            .ageOfMenarche(UPDATED_AGE_OF_MENARCHE)
            .lastMenstrualPeriod(UPDATED_LAST_MENSTRUAL_PERIOD)
            .regularityOfCycle(UPDATED_REGULARITY_OF_CYCLE);

        restGynaecologicalHistoryMockMvc.perform(put("/api/gynaecological-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGynaecologicalHistory)))
            .andExpect(status().isOk());

        // Validate the GynaecologicalHistory in the database
        List<GynaecologicalHistory> gynaecologicalHistoryList = gynaecologicalHistoryRepository.findAll();
        assertThat(gynaecologicalHistoryList).hasSize(databaseSizeBeforeUpdate);
        GynaecologicalHistory testGynaecologicalHistory = gynaecologicalHistoryList.get(gynaecologicalHistoryList.size() - 1);
        assertThat(testGynaecologicalHistory.getAgeOfMenarche()).isEqualTo(UPDATED_AGE_OF_MENARCHE);
        assertThat(testGynaecologicalHistory.getLastMenstrualPeriod()).isEqualTo(UPDATED_LAST_MENSTRUAL_PERIOD);
        assertThat(testGynaecologicalHistory.getRegularityOfCycle()).isEqualTo(UPDATED_REGULARITY_OF_CYCLE);
    }

    @Test
    @Transactional
    public void updateNonExistingGynaecologicalHistory() throws Exception {
        int databaseSizeBeforeUpdate = gynaecologicalHistoryRepository.findAll().size();

        // Create the GynaecologicalHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGynaecologicalHistoryMockMvc.perform(put("/api/gynaecological-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gynaecologicalHistory)))
            .andExpect(status().isCreated());

        // Validate the GynaecologicalHistory in the database
        List<GynaecologicalHistory> gynaecologicalHistoryList = gynaecologicalHistoryRepository.findAll();
        assertThat(gynaecologicalHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGynaecologicalHistory() throws Exception {
        // Initialize the database
        gynaecologicalHistoryService.save(gynaecologicalHistory);

        int databaseSizeBeforeDelete = gynaecologicalHistoryRepository.findAll().size();

        // Get the gynaecologicalHistory
        restGynaecologicalHistoryMockMvc.perform(delete("/api/gynaecological-histories/{id}", gynaecologicalHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GynaecologicalHistory> gynaecologicalHistoryList = gynaecologicalHistoryRepository.findAll();
        assertThat(gynaecologicalHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GynaecologicalHistory.class);
        GynaecologicalHistory gynaecologicalHistory1 = new GynaecologicalHistory();
        gynaecologicalHistory1.setId(1L);
        GynaecologicalHistory gynaecologicalHistory2 = new GynaecologicalHistory();
        gynaecologicalHistory2.setId(gynaecologicalHistory1.getId());
        assertThat(gynaecologicalHistory1).isEqualTo(gynaecologicalHistory2);
        gynaecologicalHistory2.setId(2L);
        assertThat(gynaecologicalHistory1).isNotEqualTo(gynaecologicalHistory2);
        gynaecologicalHistory1.setId(null);
        assertThat(gynaecologicalHistory1).isNotEqualTo(gynaecologicalHistory2);
    }
}
