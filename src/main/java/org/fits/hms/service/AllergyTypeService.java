package org.fits.hms.service;

import org.fits.hms.domain.AllergyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AllergyType.
 */
public interface AllergyTypeService {

    /**
     * Save a allergyType.
     *
     * @param allergyType the entity to save
     * @return the persisted entity
     */
    AllergyType save(AllergyType allergyType);

    /**
     * Get all the allergyTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AllergyType> findAll(Pageable pageable);

    /**
     * Get the "id" allergyType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AllergyType findOne(Long id);

    /**
     * Delete the "id" allergyType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
