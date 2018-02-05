package org.fits.hms.repository;

import org.fits.hms.domain.DrugHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DrugHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrugHistoryRepository extends JpaRepository<DrugHistory, Long> {

}
