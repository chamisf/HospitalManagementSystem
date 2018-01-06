package org.fits.hms.repository;

import org.fits.hms.domain.GynaecologicalHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GynaecologicalHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GynaecologicalHistoryRepository extends JpaRepository<GynaecologicalHistory, Long> {

}
