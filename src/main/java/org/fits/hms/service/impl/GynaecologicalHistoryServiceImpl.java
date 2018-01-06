package org.fits.hms.service.impl;

import org.fits.hms.service.GynaecologicalHistoryService;
import org.fits.hms.domain.GynaecologicalHistory;
import org.fits.hms.repository.GynaecologicalHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing GynaecologicalHistory.
 */
@Service
@Transactional
public class GynaecologicalHistoryServiceImpl implements GynaecologicalHistoryService{

    private final Logger log = LoggerFactory.getLogger(GynaecologicalHistoryServiceImpl.class);

    private final GynaecologicalHistoryRepository gynaecologicalHistoryRepository;

    public GynaecologicalHistoryServiceImpl(GynaecologicalHistoryRepository gynaecologicalHistoryRepository) {
        this.gynaecologicalHistoryRepository = gynaecologicalHistoryRepository;
    }

    /**
     * Save a gynaecologicalHistory.
     *
     * @param gynaecologicalHistory the entity to save
     * @return the persisted entity
     */
    @Override
    public GynaecologicalHistory save(GynaecologicalHistory gynaecologicalHistory) {
        log.debug("Request to save GynaecologicalHistory : {}", gynaecologicalHistory);
        return gynaecologicalHistoryRepository.save(gynaecologicalHistory);
    }

    /**
     * Get all the gynaecologicalHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GynaecologicalHistory> findAll(Pageable pageable) {
        log.debug("Request to get all GynaecologicalHistories");
        return gynaecologicalHistoryRepository.findAll(pageable);
    }

    /**
     * Get one gynaecologicalHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GynaecologicalHistory findOne(Long id) {
        log.debug("Request to get GynaecologicalHistory : {}", id);
        return gynaecologicalHistoryRepository.findOne(id);
    }

    /**
     * Delete the gynaecologicalHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GynaecologicalHistory : {}", id);
        gynaecologicalHistoryRepository.delete(id);
    }
}
