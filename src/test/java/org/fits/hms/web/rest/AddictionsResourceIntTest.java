package org.fits.hms.web.rest;

import org.fits.hms.HospitalManagementSystemApp;

import org.fits.hms.domain.Addictions;
import org.fits.hms.repository.AddictionsRepository;
import org.fits.hms.service.AddictionsService;
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
 * Test class for the AddictionsResource REST controller.
 *
 * @see AddictionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementSystemApp.class)
public class AddictionsResourceIntTest {

    private static final String DEFAULT_ADDICTION = "AAAAAAAAAA";
    private static final String UPDATED_ADDICTION = "BBBBBBBBBB";

    @Autowired
    private AddictionsRepository addictionsRepository;

    @Autowired
    private AddictionsService addictionsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAddictionsMockMvc;

    private Addictions addictions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddictionsResource addictionsResource = new AddictionsResource(addictionsService);
        this.restAddictionsMockMvc = MockMvcBuilders.standaloneSetup(addictionsResource)
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
    public static Addictions createEntity(EntityManager em) {
        Addictions addictions = new Addictions()
            .addiction(DEFAULT_ADDICTION);
        return addictions;
    }

    @Before
    public void initTest() {
        addictions = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddictions() throws Exception {
        int databaseSizeBeforeCreate = addictionsRepository.findAll().size();

        // Create the Addictions
        restAddictionsMockMvc.perform(post("/api/addictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addictions)))
            .andExpect(status().isCreated());

        // Validate the Addictions in the database
        List<Addictions> addictionsList = addictionsRepository.findAll();
        assertThat(addictionsList).hasSize(databaseSizeBeforeCreate + 1);
        Addictions testAddictions = addictionsList.get(addictionsList.size() - 1);
        assertThat(testAddictions.getAddiction()).isEqualTo(DEFAULT_ADDICTION);
    }

    @Test
    @Transactional
    public void createAddictionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addictionsRepository.findAll().size();

        // Create the Addictions with an existing ID
        addictions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddictionsMockMvc.perform(post("/api/addictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addictions)))
            .andExpect(status().isBadRequest());

        // Validate the Addictions in the database
        List<Addictions> addictionsList = addictionsRepository.findAll();
        assertThat(addictionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAddictions() throws Exception {
        // Initialize the database
        addictionsRepository.saveAndFlush(addictions);

        // Get all the addictionsList
        restAddictionsMockMvc.perform(get("/api/addictions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addictions.getId().intValue())))
            .andExpect(jsonPath("$.[*].addiction").value(hasItem(DEFAULT_ADDICTION.toString())));
    }

    @Test
    @Transactional
    public void getAddictions() throws Exception {
        // Initialize the database
        addictionsRepository.saveAndFlush(addictions);

        // Get the addictions
        restAddictionsMockMvc.perform(get("/api/addictions/{id}", addictions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(addictions.getId().intValue()))
            .andExpect(jsonPath("$.addiction").value(DEFAULT_ADDICTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAddictions() throws Exception {
        // Get the addictions
        restAddictionsMockMvc.perform(get("/api/addictions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddictions() throws Exception {
        // Initialize the database
        addictionsService.save(addictions);

        int databaseSizeBeforeUpdate = addictionsRepository.findAll().size();

        // Update the addictions
        Addictions updatedAddictions = addictionsRepository.findOne(addictions.getId());
        // Disconnect from session so that the updates on updatedAddictions are not directly saved in db
        em.detach(updatedAddictions);
        updatedAddictions
            .addiction(UPDATED_ADDICTION);

        restAddictionsMockMvc.perform(put("/api/addictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAddictions)))
            .andExpect(status().isOk());

        // Validate the Addictions in the database
        List<Addictions> addictionsList = addictionsRepository.findAll();
        assertThat(addictionsList).hasSize(databaseSizeBeforeUpdate);
        Addictions testAddictions = addictionsList.get(addictionsList.size() - 1);
        assertThat(testAddictions.getAddiction()).isEqualTo(UPDATED_ADDICTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAddictions() throws Exception {
        int databaseSizeBeforeUpdate = addictionsRepository.findAll().size();

        // Create the Addictions

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAddictionsMockMvc.perform(put("/api/addictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addictions)))
            .andExpect(status().isCreated());

        // Validate the Addictions in the database
        List<Addictions> addictionsList = addictionsRepository.findAll();
        assertThat(addictionsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAddictions() throws Exception {
        // Initialize the database
        addictionsService.save(addictions);

        int databaseSizeBeforeDelete = addictionsRepository.findAll().size();

        // Get the addictions
        restAddictionsMockMvc.perform(delete("/api/addictions/{id}", addictions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Addictions> addictionsList = addictionsRepository.findAll();
        assertThat(addictionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Addictions.class);
        Addictions addictions1 = new Addictions();
        addictions1.setId(1L);
        Addictions addictions2 = new Addictions();
        addictions2.setId(addictions1.getId());
        assertThat(addictions1).isEqualTo(addictions2);
        addictions2.setId(2L);
        assertThat(addictions1).isNotEqualTo(addictions2);
        addictions1.setId(null);
        assertThat(addictions1).isNotEqualTo(addictions2);
    }
}
