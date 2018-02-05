package org.fits.hms.service.impl;

import org.fits.hms.service.AppetiteTypesService;
import org.fits.hms.domain.AppetiteTypes;
import org.fits.hms.repository.AppetiteTypesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AppetiteTypes.
 */
@Service
@Transactional
public class AppetiteTypesServiceImpl implements AppetiteTypesService{

    private final Logger log = LoggerFactory.getLogger(AppetiteTypesServiceImpl.class);

    private final AppetiteTypesRepository appetiteTypesRepository;

    public AppetiteTypesServiceImpl(AppetiteTypesRepository appetiteTypesRepository) {
        this.appetiteTypesRepository = appetiteTypesRepository;
    }

    /**
     * Save a appetiteTypes.
     *
     * @param appetiteTypes the entity to save
     * @return the persisted entity
     */
    @Override
    public AppetiteTypes save(AppetiteTypes appetiteTypes) {
        log.debug("Request to save AppetiteTypes : {}", appetiteTypes);
        return appetiteTypesRepository.save(appetiteTypes);
    }

    /**
     * Get all the appetiteTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AppetiteTypes> findAll(Pageable pageable) {
        log.debug("Request to get all AppetiteTypes");
        return appetiteTypesRepository.findAll(pageable);
    }

    /**
     * Get one appetiteTypes by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AppetiteTypes findOne(Long id) {
        log.debug("Request to get AppetiteTypes : {}", id);
        return appetiteTypesRepository.findOne(id);
    }

    /**
     * Delete the appetiteTypes by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppetiteTypes : {}", id);
        appetiteTypesRepository.delete(id);
    }
}
