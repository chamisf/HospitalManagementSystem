package org.fits.hms.repository;

import org.fits.hms.domain.MicturitionTypes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MicturitionTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MicturitionTypesRepository extends JpaRepository<MicturitionTypes, Long> {

}
