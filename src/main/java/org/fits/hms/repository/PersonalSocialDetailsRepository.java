package org.fits.hms.repository;

import org.fits.hms.domain.PersonalSocialDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PersonalSocialDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalSocialDetailsRepository extends JpaRepository<PersonalSocialDetails, Long> {

    /**
     * Find first match of personal and social details of patient matched to given id.
     *
     * @param id patient id
     * @return personal and social details of given patient
     */
    PersonalSocialDetails findFirstByPatientId(Long id);
}
