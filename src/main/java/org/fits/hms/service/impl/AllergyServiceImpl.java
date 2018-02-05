package org.fits.hms.service.impl;

import org.fits.hms.service.AllergyService;
import org.fits.hms.domain.Allergy;
import org.fits.hms.repository.AllergyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Allergy.
 */
@Service
@Transactional
public class AllergyServiceImpl implements AllergyService{

    private final Logger log = LoggerFactory.getLogger(AllergyServiceImpl.class);

    private final AllergyRepository allergyRepository;

    public AllergyServiceImpl(AllergyRepository allergyRepository) {
        this.allergyRepository = allergyRepository;
    }

    /**
     * Save a allergy.
     *
     * @param allergy the entity to save
     * @return the persisted entity
     */
    @Override
    public Allergy save(Allergy allergy) {
        log.debug("Request to save Allergy : {}", allergy);
        return allergyRepository.save(allergy);
    }

    /**
     * Get all the allergies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Allergy> findAll(Pageable pageable) {
        log.debug("Request to get all Allergies");
        return allergyRepository.findAll(pageable);
    }

    /**
     * Get one allergy by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Allergy findOne(Long id) {
        log.debug("Request to get Allergy : {}", id);
        return allergyRepository.findOne(id);
    }

    /**
     * Delete the allergy by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Allergy : {}", id);
        allergyRepository.delete(id);
    }
}
