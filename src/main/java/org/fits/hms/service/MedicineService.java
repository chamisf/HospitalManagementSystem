package org.fits.hms.service;

import org.fits.hms.domain.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Medicine.
 */
public interface MedicineService {

    /**
     * Save a medicine.
     *
     * @param medicine the entity to save
     * @return the persisted entity
     */
    Medicine save(Medicine medicine);

    /**
     * Get all the medicines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Medicine> findAll(Pageable pageable);

    /**
     * Get the "id" medicine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Medicine findOne(Long id);

    /**
     * Delete the "id" medicine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
