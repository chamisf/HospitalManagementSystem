package org.fits.hms.service;

import org.fits.hms.domain.Medication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Medication.
 */
public interface MedicationService {

    /**
     * Save a medication.
     *
     * @param medication the entity to save
     * @return the persisted entity
     */
    Medication save(Medication medication);

    /**
     * Get all the medications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Medication> findAll(Pageable pageable);

    /**
     * Get the "id" medication.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Medication findOne(Long id);

    /**
     * Delete the "id" medication.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
