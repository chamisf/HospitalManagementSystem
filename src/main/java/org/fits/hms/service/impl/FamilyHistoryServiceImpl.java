package org.fits.hms.service.impl;

import org.fits.hms.service.FamilyHistoryService;
import org.fits.hms.domain.FamilyHistory;
import org.fits.hms.repository.FamilyHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FamilyHistory.
 */
@Service
@Transactional
public class FamilyHistoryServiceImpl implements FamilyHistoryService{

    private final Logger log = LoggerFactory.getLogger(FamilyHistoryServiceImpl.class);

    private final FamilyHistoryRepository familyHistoryRepository;

    public FamilyHistoryServiceImpl(FamilyHistoryRepository familyHistoryRepository) {
        this.familyHistoryRepository = familyHistoryRepository;
    }

    /**
     * Save a familyHistory.
     *
     * @param familyHistory the entity to save
     * @return the persisted entity
     */
    @Override
    public FamilyHistory save(FamilyHistory familyHistory) {
        log.debug("Request to save FamilyHistory : {}", familyHistory);
        return familyHistoryRepository.save(familyHistory);
    }

    /**
     * Get all the familyHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FamilyHistory> findAll(Pageable pageable) {
        log.debug("Request to get all FamilyHistories");
        return familyHistoryRepository.findAll(pageable);
    }

    /**
     * Get one familyHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FamilyHistory findOne(Long id) {
        log.debug("Request to get FamilyHistory : {}", id);
        return familyHistoryRepository.findOne(id);
    }

    /**
     * Delete the familyHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FamilyHistory : {}", id);
        familyHistoryRepository.delete(id);
    }
}
