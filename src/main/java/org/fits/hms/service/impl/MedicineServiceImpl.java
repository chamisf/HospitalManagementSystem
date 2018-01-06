package org.fits.hms.service.impl;

import org.fits.hms.service.MedicineService;
import org.fits.hms.domain.Medicine;
import org.fits.hms.repository.MedicineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Medicine.
 */
@Service
@Transactional
public class MedicineServiceImpl implements MedicineService{

    private final Logger log = LoggerFactory.getLogger(MedicineServiceImpl.class);

    private final MedicineRepository medicineRepository;

    public MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    /**
     * Save a medicine.
     *
     * @param medicine the entity to save
     * @return the persisted entity
     */
    @Override
    public Medicine save(Medicine medicine) {
        log.debug("Request to save Medicine : {}", medicine);
        return medicineRepository.save(medicine);
    }

    /**
     * Get all the medicines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Medicine> findAll(Pageable pageable) {
        log.debug("Request to get all Medicines");
        return medicineRepository.findAll(pageable);
    }

    /**
     * Get one medicine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Medicine findOne(Long id) {
        log.debug("Request to get Medicine : {}", id);
        return medicineRepository.findOne(id);
    }

    /**
     * Delete the medicine by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Medicine : {}", id);
        medicineRepository.delete(id);
    }
}
