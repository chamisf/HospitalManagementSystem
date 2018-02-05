package org.fits.hms.service.impl;

import org.fits.hms.service.PersonalSocialDetailsService;
import org.fits.hms.domain.PersonalSocialDetails;
import org.fits.hms.repository.PersonalSocialDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PersonalSocialDetails.
 */
@Service
@Transactional
public class PersonalSocialDetailsServiceImpl implements PersonalSocialDetailsService{

    private final Logger log = LoggerFactory.getLogger(PersonalSocialDetailsServiceImpl.class);

    private final PersonalSocialDetailsRepository personalSocialDetailsRepository;

    public PersonalSocialDetailsServiceImpl(PersonalSocialDetailsRepository personalSocialDetailsRepository) {
        this.personalSocialDetailsRepository = personalSocialDetailsRepository;
    }

    /**
     * Save a personalSocialDetails.
     *
     * @param personalSocialDetails the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonalSocialDetails save(PersonalSocialDetails personalSocialDetails) {
        log.debug("Request to save PersonalSocialDetails : {}", personalSocialDetails);
        return personalSocialDetailsRepository.save(personalSocialDetails);
    }

    /**
     * Get all the personalSocialDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonalSocialDetails> findAll(Pageable pageable) {
        log.debug("Request to get all PersonalSocialDetails");
        return personalSocialDetailsRepository.findAll(pageable);
    }

    /**
     * Get one personalSocialDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PersonalSocialDetails findOne(Long id) {
        log.debug("Request to get PersonalSocialDetails : {}", id);
        return personalSocialDetailsRepository.findOne(id);
    }

    /**
     * Delete the personalSocialDetails by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonalSocialDetails : {}", id);
        personalSocialDetailsRepository.delete(id);
    }
}
