package org.fits.hms.repository;

import org.fits.hms.domain.FamilyHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FamilyHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilyHistoryRepository extends JpaRepository<FamilyHistory, Long> {

    /**
     * Find first match of Family history of patient matched to given id.
     *
     * @param id patient id
     * @return Family history of given patient
     */
    FamilyHistory findFirstByPatientId(Long id);
}
