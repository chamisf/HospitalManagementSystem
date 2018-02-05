package org.fits.hms.service.impl;

import org.fits.hms.service.AllergyTypeService;
import org.fits.hms.domain.AllergyType;
import org.fits.hms.repository.AllergyTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AllergyType.
 */
@Service
@Transactional
public class AllergyTypeServiceImpl implements AllergyTypeService{

    private final Logger log = LoggerFactory.getLogger(AllergyTypeServiceImpl.class);

    private final AllergyTypeRepository allergyTypeRepository;

    public AllergyTypeServiceImpl(AllergyTypeRepository allergyTypeRepository) {
        this.allergyTypeRepository = allergyTypeRepository;
    }

    /**
     * Save a allergyType.
     *
     * @param allergyType the entity to save
     * @return the persisted entity
     */
    @Override
    public AllergyType save(AllergyType allergyType) {
        log.debug("Request to save AllergyType : {}", allergyType);
        return allergyTypeRepository.save(allergyType);
    }

    /**
     * Get all the allergyTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AllergyType> findAll(Pageable pageable) {
        log.debug("Request to get all AllergyTypes");
        return allergyTypeRepository.findAll(pageable);
    }

    /**
     * Get one allergyType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AllergyType findOne(Long id) {
        log.debug("Request to get AllergyType : {}", id);
        return allergyTypeRepository.findOne(id);
    }

    /**
     * Delete the allergyType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AllergyType : {}", id);
        allergyTypeRepository.delete(id);
    }
}
