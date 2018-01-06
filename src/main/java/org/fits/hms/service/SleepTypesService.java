package org.fits.hms.service;

import org.fits.hms.domain.SleepTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SleepTypes.
 */
public interface SleepTypesService {

    /**
     * Save a sleepTypes.
     *
     * @param sleepTypes the entity to save
     * @return the persisted entity
     */
    SleepTypes save(SleepTypes sleepTypes);

    /**
     * Get all the sleepTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SleepTypes> findAll(Pageable pageable);

    /**
     * Get the "id" sleepTypes.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SleepTypes findOne(Long id);

    /**
     * Delete the "id" sleepTypes.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
