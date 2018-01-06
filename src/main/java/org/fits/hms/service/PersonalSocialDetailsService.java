package org.fits.hms.service;

import org.fits.hms.domain.PersonalSocialDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PersonalSocialDetails.
 */
public interface PersonalSocialDetailsService {

    /**
     * Save a personalSocialDetails.
     *
     * @param personalSocialDetails the entity to save
     * @return the persisted entity
     */
    PersonalSocialDetails save(PersonalSocialDetails personalSocialDetails);

    /**
     * Get all the personalSocialDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PersonalSocialDetails> findAll(Pageable pageable);

    /**
     * Get the "id" personalSocialDetails.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PersonalSocialDetails findOne(Long id);

    /**
     * Delete the "id" personalSocialDetails.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
