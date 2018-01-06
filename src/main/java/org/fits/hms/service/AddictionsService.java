package org.fits.hms.service;

import org.fits.hms.domain.Addictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Addictions.
 */
public interface AddictionsService {

    /**
     * Save a addictions.
     *
     * @param addictions the entity to save
     * @return the persisted entity
     */
    Addictions save(Addictions addictions);

    /**
     * Get all the addictions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Addictions> findAll(Pageable pageable);

    /**
     * Get the "id" addictions.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Addictions findOne(Long id);

    /**
     * Delete the "id" addictions.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
