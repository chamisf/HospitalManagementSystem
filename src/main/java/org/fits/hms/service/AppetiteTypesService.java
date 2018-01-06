package org.fits.hms.service;

import org.fits.hms.domain.AppetiteTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AppetiteTypes.
 */
public interface AppetiteTypesService {

    /**
     * Save a appetiteTypes.
     *
     * @param appetiteTypes the entity to save
     * @return the persisted entity
     */
    AppetiteTypes save(AppetiteTypes appetiteTypes);

    /**
     * Get all the appetiteTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AppetiteTypes> findAll(Pageable pageable);

    /**
     * Get the "id" appetiteTypes.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AppetiteTypes findOne(Long id);

    /**
     * Delete the "id" appetiteTypes.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
