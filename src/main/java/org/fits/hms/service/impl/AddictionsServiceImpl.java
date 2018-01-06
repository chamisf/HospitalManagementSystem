package org.fits.hms.service.impl;

import org.fits.hms.service.AddictionsService;
import org.fits.hms.domain.Addictions;
import org.fits.hms.repository.AddictionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Addictions.
 */
@Service
@Transactional
public class AddictionsServiceImpl implements AddictionsService{

    private final Logger log = LoggerFactory.getLogger(AddictionsServiceImpl.class);

    private final AddictionsRepository addictionsRepository;

    public AddictionsServiceImpl(AddictionsRepository addictionsRepository) {
        this.addictionsRepository = addictionsRepository;
    }

    /**
     * Save a addictions.
     *
     * @param addictions the entity to save
     * @return the persisted entity
     */
    @Override
    public Addictions save(Addictions addictions) {
        log.debug("Request to save Addictions : {}", addictions);
        return addictionsRepository.save(addictions);
    }

    /**
     * Get all the addictions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Addictions> findAll(Pageable pageable) {
        log.debug("Request to get all Addictions");
        return addictionsRepository.findAll(pageable);
    }

    /**
     * Get one addictions by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Addictions findOne(Long id) {
        log.debug("Request to get Addictions : {}", id);
        return addictionsRepository.findOne(id);
    }

    /**
     * Delete the addictions by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Addictions : {}", id);
        addictionsRepository.delete(id);
    }
}
