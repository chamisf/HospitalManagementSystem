package org.fits.hms.web.rest;

import org.fits.hms.HospitalManagementSystemApp;

import org.fits.hms.domain.BowelHabits;
import org.fits.hms.repository.BowelHabitsRepository;
import org.fits.hms.service.BowelHabitsService;
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
 * Test class for the BowelHabitsResource REST controller.
 *
 * @see BowelHabitsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementSystemApp.class)
public class BowelHabitsResourceIntTest {

    private static final String DEFAULT_BOWEL_HABIT = "AAAAAAAAAA";
    private static final String UPDATED_BOWEL_HABIT = "BBBBBBBBBB";

    @Autowired
    private BowelHabitsRepository bowelHabitsRepository;

    @Autowired
    private BowelHabitsService bowelHabitsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBowelHabitsMockMvc;

    private BowelHabits bowelHabits;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BowelHabitsResource bowelHabitsResource = new BowelHabitsResource(bowelHabitsService);
        this.restBowelHabitsMockMvc = MockMvcBuilders.standaloneSetup(bowelHabitsResource)
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
    public static BowelHabits createEntity(EntityManager em) {
        BowelHabits bowelHabits = new BowelHabits()
            .bowelHabit(DEFAULT_BOWEL_HABIT);
        return bowelHabits;
    }

    @Before
    public void initTest() {
        bowelHabits = createEntity(em);
    }

    @Test
    @Transactional
    public void createBowelHabits() throws Exception {
        int databaseSizeBeforeCreate = bowelHabitsRepository.findAll().size();

        // Create the BowelHabits
        restBowelHabitsMockMvc.perform(post("/api/bowel-habits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bowelHabits)))
            .andExpect(status().isCreated());

        // Validate the BowelHabits in the database
        List<BowelHabits> bowelHabitsList = bowelHabitsRepository.findAll();
        assertThat(bowelHabitsList).hasSize(databaseSizeBeforeCreate + 1);
        BowelHabits testBowelHabits = bowelHabitsList.get(bowelHabitsList.size() - 1);
        assertThat(testBowelHabits.getBowelHabit()).isEqualTo(DEFAULT_BOWEL_HABIT);
    }

    @Test
    @Transactional
    public void createBowelHabitsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bowelHabitsRepository.findAll().size();

        // Create the BowelHabits with an existing ID
        bowelHabits.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBowelHabitsMockMvc.perform(post("/api/bowel-habits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bowelHabits)))
            .andExpect(status().isBadRequest());

        // Validate the BowelHabits in the database
        List<BowelHabits> bowelHabitsList = bowelHabitsRepository.findAll();
        assertThat(bowelHabitsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBowelHabits() throws Exception {
        // Initialize the database
        bowelHabitsRepository.saveAndFlush(bowelHabits);

        // Get all the bowelHabitsList
        restBowelHabitsMockMvc.perform(get("/api/bowel-habits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bowelHabits.getId().intValue())))
            .andExpect(jsonPath("$.[*].bowelHabit").value(hasItem(DEFAULT_BOWEL_HABIT.toString())));
    }

    @Test
    @Transactional
    public void getBowelHabits() throws Exception {
        // Initialize the database
        bowelHabitsRepository.saveAndFlush(bowelHabits);

        // Get the bowelHabits
        restBowelHabitsMockMvc.perform(get("/api/bowel-habits/{id}", bowelHabits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bowelHabits.getId().intValue()))
            .andExpect(jsonPath("$.bowelHabit").value(DEFAULT_BOWEL_HABIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBowelHabits() throws Exception {
        // Get the bowelHabits
        restBowelHabitsMockMvc.perform(get("/api/bowel-habits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBowelHabits() throws Exception {
        // Initialize the database
        bowelHabitsService.save(bowelHabits);

        int databaseSizeBeforeUpdate = bowelHabitsRepository.findAll().size();

        // Update the bowelHabits
        BowelHabits updatedBowelHabits = bowelHabitsRepository.findOne(bowelHabits.getId());
        // Disconnect from session so that the updates on updatedBowelHabits are not directly saved in db
        em.detach(updatedBowelHabits);
        updatedBowelHabits
            .bowelHabit(UPDATED_BOWEL_HABIT);

        restBowelHabitsMockMvc.perform(put("/api/bowel-habits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBowelHabits)))
            .andExpect(status().isOk());

        // Validate the BowelHabits in the database
        List<BowelHabits> bowelHabitsList = bowelHabitsRepository.findAll();
        assertThat(bowelHabitsList).hasSize(databaseSizeBeforeUpdate);
        BowelHabits testBowelHabits = bowelHabitsList.get(bowelHabitsList.size() - 1);
        assertThat(testBowelHabits.getBowelHabit()).isEqualTo(UPDATED_BOWEL_HABIT);
    }

    @Test
    @Transactional
    public void updateNonExistingBowelHabits() throws Exception {
        int databaseSizeBeforeUpdate = bowelHabitsRepository.findAll().size();

        // Create the BowelHabits

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBowelHabitsMockMvc.perform(put("/api/bowel-habits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bowelHabits)))
            .andExpect(status().isCreated());

        // Validate the BowelHabits in the database
        List<BowelHabits> bowelHabitsList = bowelHabitsRepository.findAll();
        assertThat(bowelHabitsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBowelHabits() throws Exception {
        // Initialize the database
        bowelHabitsService.save(bowelHabits);

        int databaseSizeBeforeDelete = bowelHabitsRepository.findAll().size();

        // Get the bowelHabits
        restBowelHabitsMockMvc.perform(delete("/api/bowel-habits/{id}", bowelHabits.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BowelHabits> bowelHabitsList = bowelHabitsRepository.findAll();
        assertThat(bowelHabitsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BowelHabits.class);
        BowelHabits bowelHabits1 = new BowelHabits();
        bowelHabits1.setId(1L);
        BowelHabits bowelHabits2 = new BowelHabits();
        bowelHabits2.setId(bowelHabits1.getId());
        assertThat(bowelHabits1).isEqualTo(bowelHabits2);
        bowelHabits2.setId(2L);
        assertThat(bowelHabits1).isNotEqualTo(bowelHabits2);
        bowelHabits1.setId(null);
        assertThat(bowelHabits1).isNotEqualTo(bowelHabits2);
    }
}
