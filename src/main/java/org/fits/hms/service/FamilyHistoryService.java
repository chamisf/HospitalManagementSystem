package org.fits.hms.service;

import org.fits.hms.domain.FamilyHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FamilyHistory.
 */
public interface FamilyHistoryService {

    /**
     * Save a familyHistory.
     *
     * @param familyHistory the entity to save
     * @return the persisted entity
     */
    FamilyHistory save(FamilyHistory familyHistory);

    /**
     * Get all the familyHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FamilyHistory> findAll(Pageable pageable);

    /**
     * Get the "id" familyHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FamilyHistory findOne(Long id);

    /**
     * Delete the "id" familyHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
