package org.fits.hms.service.impl;

import org.fits.hms.service.SleepTypesService;
import org.fits.hms.domain.SleepTypes;
import org.fits.hms.repository.SleepTypesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SleepTypes.
 */
@Service
@Transactional
public class SleepTypesServiceImpl implements SleepTypesService{

    private final Logger log = LoggerFactory.getLogger(SleepTypesServiceImpl.class);

    private final SleepTypesRepository sleepTypesRepository;

    public SleepTypesServiceImpl(SleepTypesRepository sleepTypesRepository) {
        this.sleepTypesRepository = sleepTypesRepository;
    }

    /**
     * Save a sleepTypes.
     *
     * @param sleepTypes the entity to save
     * @return the persisted entity
     */
    @Override
    public SleepTypes save(SleepTypes sleepTypes) {
        log.debug("Request to save SleepTypes : {}", sleepTypes);
        return sleepTypesRepository.save(sleepTypes);
    }

    /**
     * Get all the sleepTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SleepTypes> findAll(Pageable pageable) {
        log.debug("Request to get all SleepTypes");
        return sleepTypesRepository.findAll(pageable);
    }

    /**
     * Get one sleepTypes by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SleepTypes findOne(Long id) {
        log.debug("Request to get SleepTypes : {}", id);
        return sleepTypesRepository.findOne(id);
    }

    /**
     * Delete the sleepTypes by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SleepTypes : {}", id);
        sleepTypesRepository.delete(id);
    }
}
