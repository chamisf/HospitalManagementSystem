package org.fits.hms.service;

import org.fits.hms.domain.GynaecologicalHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing GynaecologicalHistory.
 */
public interface GynaecologicalHistoryService {

    /**
     * Save a gynaecologicalHistory.
     *
     * @param gynaecologicalHistory the entity to save
     * @return the persisted entity
     */
    GynaecologicalHistory save(GynaecologicalHistory gynaecologicalHistory);

    /**
     * Get all the gynaecologicalHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GynaecologicalHistory> findAll(Pageable pageable);

    /**
     * Get the "id" gynaecologicalHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GynaecologicalHistory findOne(Long id);

    /**
     * Delete the "id" gynaecologicalHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Find first match of gynaecological history of patient matched to given id.
     *
     * @param id patient id
     * @return gynaecologincal history of given patient
     */
    GynaecologicalHistory findFirstByPatientId(Long id);
}
