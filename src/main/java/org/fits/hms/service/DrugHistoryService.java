package org.fits.hms.service;

import org.fits.hms.domain.DrugHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DrugHistory.
 */
public interface DrugHistoryService {

    /**
     * Save a drugHistory.
     *
     * @param drugHistory the entity to save
     * @return the persisted entity
     */
    DrugHistory save(DrugHistory drugHistory);

    /**
     * Get all the drugHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DrugHistory> findAll(Pageable pageable);

    /**
     * Get the "id" drugHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DrugHistory findOne(Long id);

    /**
     * Delete the "id" drugHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
