package org.fits.hms.repository;

import org.fits.hms.domain.Addictions;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Addictions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddictionsRepository extends JpaRepository<Addictions, Long> {

}
