package org.fits.hms.service.impl;

import org.fits.hms.service.MedicationService;
import org.fits.hms.domain.Medication;
import org.fits.hms.repository.MedicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Medication.
 */
@Service
@Transactional
public class MedicationServiceImpl implements MedicationService{

    private final Logger log = LoggerFactory.getLogger(MedicationServiceImpl.class);

    private final MedicationRepository medicationRepository;

    public MedicationServiceImpl(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    /**
     * Save a medication.
     *
     * @param medication the entity to save
     * @return the persisted entity
     */
    @Override
    public Medication save(Medication medication) {
        log.debug("Request to save Medication : {}", medication);
        return medicationRepository.save(medication);
    }

    /**
     * Get all the medications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Medication> findAll(Pageable pageable) {
        log.debug("Request to get all Medications");
        return medicationRepository.findAll(pageable);
    }

    /**
     * Get one medication by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Medication findOne(Long id) {
        log.debug("Request to get Medication : {}", id);
        return medicationRepository.findOne(id);
    }

    /**
     * Delete the medication by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Medication : {}", id);
        medicationRepository.delete(id);
    }
}
