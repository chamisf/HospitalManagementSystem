package org.fits.hms.repository;

import org.fits.hms.domain.SleepTypes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SleepTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SleepTypesRepository extends JpaRepository<SleepTypes, Long> {

}
