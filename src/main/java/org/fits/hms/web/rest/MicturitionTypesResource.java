package org.fits.hms.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fits.hms.domain.MicturitionTypes;
import org.fits.hms.service.MicturitionTypesService;
import org.fits.hms.web.rest.errors.BadRequestAlertException;
import org.fits.hms.web.rest.util.HeaderUtil;
import org.fits.hms.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MicturitionTypes.
 */
@RestController
@RequestMapping("/api")
public class MicturitionTypesResource {

    private final Logger log = LoggerFactory.getLogger(MicturitionTypesResource.class);

    private static final String ENTITY_NAME = "micturitionTypes";

    private final MicturitionTypesService micturitionTypesService;

    public MicturitionTypesResource(MicturitionTypesService micturitionTypesService) {
        this.micturitionTypesService = micturitionTypesService;
    }

    /**
     * POST  /micturition-types : Create a new micturitionTypes.
     *
     * @param micturitionTypes the micturitionTypes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new micturitionTypes, or with status 400 (Bad Request) if the micturitionTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/micturition-types")
    @Timed
    public ResponseEntity<MicturitionTypes> createMicturitionTypes(@RequestBody MicturitionTypes micturitionTypes) throws URISyntaxException {
        log.debug("REST request to save MicturitionTypes : {}", micturitionTypes);
        if (micturitionTypes.getId() != null) {
            throw new BadRequestAlertException("A new micturitionTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MicturitionTypes result = micturitionTypesService.save(micturitionTypes);
        return ResponseEntity.created(new URI("/api/micturition-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /micturition-types : Updates an existing micturitionTypes.
     *
     * @param micturitionTypes the micturitionTypes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated micturitionTypes,
     * or with status 400 (Bad Request) if the micturitionTypes is not valid,
     * or with status 500 (Internal Server Error) if the micturitionTypes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/micturition-types")
    @Timed
    public ResponseEntity<MicturitionTypes> updateMicturitionTypes(@RequestBody MicturitionTypes micturitionTypes) throws URISyntaxException {
        log.debug("REST request to update MicturitionTypes : {}", micturitionTypes);
        if (micturitionTypes.getId() == null) {
            return createMicturitionTypes(micturitionTypes);
        }
        MicturitionTypes result = micturitionTypesService.save(micturitionTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, micturitionTypes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /micturition-types : get all the micturitionTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of micturitionTypes in body
     */
    @GetMapping("/micturition-types")
    @Timed
    public ResponseEntity<List<MicturitionTypes>> getAllMicturitionTypes(Pageable pageable) {
        log.debug("REST request to get a page of MicturitionTypes");
        Page<MicturitionTypes> page = micturitionTypesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/micturition-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /micturition-types/:id : get the "id" micturitionTypes.
     *
     * @param id the id of the micturitionTypes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the micturitionTypes, or with status 404 (Not Found)
     */
    @GetMapping("/micturition-types/{id}")
    @Timed
    public ResponseEntity<MicturitionTypes> getMicturitionTypes(@PathVariable Long id) {
        log.debug("REST request to get MicturitionTypes : {}", id);
        MicturitionTypes micturitionTypes = micturitionTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(micturitionTypes));
    }

    /**
     * DELETE  /micturition-types/:id : delete the "id" micturitionTypes.
     *
     * @param id the id of the micturitionTypes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/micturition-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteMicturitionTypes(@PathVariable Long id) {
        log.debug("REST request to delete MicturitionTypes : {}", id);
        micturitionTypesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
