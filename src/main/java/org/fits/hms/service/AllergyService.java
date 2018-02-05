package org.fits.hms.service;

import org.fits.hms.domain.Allergy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Allergy.
 */
public interface AllergyService {

    /**
     * Save a allergy.
     *
     * @param allergy the entity to save
     * @return the persisted entity
     */
    Allergy save(Allergy allergy);

    /**
     * Get all the allergies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Allergy> findAll(Pageable pageable);

    /**
     * Get the "id" allergy.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Allergy findOne(Long id);

    /**
     * Delete the "id" allergy.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
