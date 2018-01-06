package org.fits.hms.repository;

import org.fits.hms.domain.Medication;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Medication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

}
