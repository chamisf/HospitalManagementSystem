package org.fits.hms.service.impl;

import org.fits.hms.service.BowelHabitsService;
import org.fits.hms.domain.BowelHabits;
import org.fits.hms.repository.BowelHabitsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BowelHabits.
 */
@Service
@Transactional
public class BowelHabitsServiceImpl implements BowelHabitsService{

    private final Logger log = LoggerFactory.getLogger(BowelHabitsServiceImpl.class);

    private final BowelHabitsRepository bowelHabitsRepository;

    public BowelHabitsServiceImpl(BowelHabitsRepository bowelHabitsRepository) {
        this.bowelHabitsRepository = bowelHabitsRepository;
    }

    /**
     * Save a bowelHabits.
     *
     * @param bowelHabits the entity to save
     * @return the persisted entity
     */
    @Override
    public BowelHabits save(BowelHabits bowelHabits) {
        log.debug("Request to save BowelHabits : {}", bowelHabits);
        return bowelHabitsRepository.save(bowelHabits);
    }

    /**
     * Get all the bowelHabits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BowelHabits> findAll(Pageable pageable) {
        log.debug("Request to get all BowelHabits");
        return bowelHabitsRepository.findAll(pageable);
    }

    /**
     * Get one bowelHabits by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BowelHabits findOne(Long id) {
        log.debug("Request to get BowelHabits : {}", id);
        return bowelHabitsRepository.findOne(id);
    }

    /**
     * Delete the bowelHabits by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BowelHabits : {}", id);
        bowelHabitsRepository.delete(id);
    }
}
