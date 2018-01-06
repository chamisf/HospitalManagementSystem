package org.fits.hms.repository;

import org.fits.hms.domain.AppetiteTypes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AppetiteTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppetiteTypesRepository extends JpaRepository<AppetiteTypes, Long> {

}
