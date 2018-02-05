package org.fits.hms.service;

import org.fits.hms.domain.BowelHabits;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BowelHabits.
 */
public interface BowelHabitsService {

    /**
     * Save a bowelHabits.
     *
     * @param bowelHabits the entity to save
     * @return the persisted entity
     */
    BowelHabits save(BowelHabits bowelHabits);

    /**
     * Get all the bowelHabits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BowelHabits> findAll(Pageable pageable);

    /**
     * Get the "id" bowelHabits.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BowelHabits findOne(Long id);

    /**
     * Delete the "id" bowelHabits.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
