package org.fits.hms.repository;

import org.fits.hms.domain.AllergyType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AllergyType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllergyTypeRepository extends JpaRepository<AllergyType, Long> {

}
