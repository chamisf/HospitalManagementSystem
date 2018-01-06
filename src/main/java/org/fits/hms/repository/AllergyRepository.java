package org.fits.hms.repository;

import org.fits.hms.domain.Allergy;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Allergy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {

}
