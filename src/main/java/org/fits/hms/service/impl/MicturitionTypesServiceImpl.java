package org.fits.hms.service.impl;

import org.fits.hms.service.MicturitionTypesService;
import org.fits.hms.domain.MicturitionTypes;
import org.fits.hms.repository.MicturitionTypesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MicturitionTypes.
 */
@Service
@Transactional
public class MicturitionTypesServiceImpl implements MicturitionTypesService{

    private final Logger log = LoggerFactory.getLogger(MicturitionTypesServiceImpl.class);

    private final MicturitionTypesRepository micturitionTypesRepository;

    public MicturitionTypesServiceImpl(MicturitionTypesRepository micturitionTypesRepository) {
        this.micturitionTypesRepository = micturitionTypesRepository;
    }

    /**
     * Save a micturitionTypes.
     *
     * @param micturitionTypes the entity to save
     * @return the persisted entity
     */
    @Override
    public MicturitionTypes save(MicturitionTypes micturitionTypes) {
        log.debug("Request to save MicturitionTypes : {}", micturitionTypes);
        return micturitionTypesRepository.save(micturitionTypes);
    }

    /**
     * Get all the micturitionTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MicturitionTypes> findAll(Pageable pageable) {
        log.debug("Request to get all MicturitionTypes");
        return micturitionTypesRepository.findAll(pageable);
    }

    /**
     * Get one micturitionTypes by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MicturitionTypes findOne(Long id) {
        log.debug("Request to get MicturitionTypes : {}", id);
        return micturitionTypesRepository.findOne(id);
    }

    /**
     * Delete the micturitionTypes by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MicturitionTypes : {}", id);
        micturitionTypesRepository.delete(id);
    }
}
