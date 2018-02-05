package org.fits.hms.service.impl;

import org.fits.hms.service.DrugHistoryService;
import org.fits.hms.domain.DrugHistory;
import org.fits.hms.repository.DrugHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DrugHistory.
 */
@Service
@Transactional
public class DrugHistoryServiceImpl implements DrugHistoryService{

    private final Logger log = LoggerFactory.getLogger(DrugHistoryServiceImpl.class);

    private final DrugHistoryRepository drugHistoryRepository;

    public DrugHistoryServiceImpl(DrugHistoryRepository drugHistoryRepository) {
        this.drugHistoryRepository = drugHistoryRepository;
    }

    /**
     * Save a drugHistory.
     *
     * @param drugHistory the entity to save
     * @return the persisted entity
     */
    @Override
    public DrugHistory save(DrugHistory drugHistory) {
        log.debug("Request to save DrugHistory : {}", drugHistory);
        return drugHistoryRepository.save(drugHistory);
    }

    /**
     * Get all the drugHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DrugHistory> findAll(Pageable pageable) {
        log.debug("Request to get all DrugHistories");
        return drugHistoryRepository.findAll(pageable);
    }

    /**
     * Get one drugHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DrugHistory findOne(Long id) {
        log.debug("Request to get DrugHistory : {}", id);
        return drugHistoryRepository.findOne(id);
    }

    /**
     * Delete the drugHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DrugHistory : {}", id);
        drugHistoryRepository.delete(id);
    }
}
