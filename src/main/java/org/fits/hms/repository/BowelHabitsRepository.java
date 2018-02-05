package org.fits.hms.repository;

import org.fits.hms.domain.BowelHabits;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BowelHabits entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BowelHabitsRepository extends JpaRepository<BowelHabits, Long> {

}
