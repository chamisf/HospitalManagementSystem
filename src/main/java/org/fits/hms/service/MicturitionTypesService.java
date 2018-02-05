package org.fits.hms.service;

import org.fits.hms.domain.MicturitionTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MicturitionTypes.
 */
public interface MicturitionTypesService {

    /**
     * Save a micturitionTypes.
     *
     * @param micturitionTypes the entity to save
     * @return the persisted entity
     */
    MicturitionTypes save(MicturitionTypes micturitionTypes);

    /**
     * Get all the micturitionTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MicturitionTypes> findAll(Pageable pageable);

    /**
     * Get the "id" micturitionTypes.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MicturitionTypes findOne(Long id);

    /**
     * Delete the "id" micturitionTypes.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
