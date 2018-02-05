package org.fits.hms.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fits.hms.domain.SleepTypes;
import org.fits.hms.service.SleepTypesService;
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
 * REST controller for managing SleepTypes.
 */
@RestController
@RequestMapping("/api")
public class SleepTypesResource {

    private final Logger log = LoggerFactory.getLogger(SleepTypesResource.class);

    private static final String ENTITY_NAME = "sleepTypes";

    private final SleepTypesService sleepTypesService;

    public SleepTypesResource(SleepTypesService sleepTypesService) {
        this.sleepTypesService = sleepTypesService;
    }

    /**
     * POST  /sleep-types : Create a new sleepTypes.
     *
     * @param sleepTypes the sleepTypes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sleepTypes, or with status 400 (Bad Request) if the sleepTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sleep-types")
    @Timed
    public ResponseEntity<SleepTypes> createSleepTypes(@RequestBody SleepTypes sleepTypes) throws URISyntaxException {
        log.debug("REST request to save SleepTypes : {}", sleepTypes);
        if (sleepTypes.getId() != null) {
            throw new BadRequestAlertException("A new sleepTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SleepTypes result = sleepTypesService.save(sleepTypes);
        return ResponseEntity.created(new URI("/api/sleep-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sleep-types : Updates an existing sleepTypes.
     *
     * @param sleepTypes the sleepTypes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sleepTypes,
     * or with status 400 (Bad Request) if the sleepTypes is not valid,
     * or with status 500 (Internal Server Error) if the sleepTypes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sleep-types")
    @Timed
    public ResponseEntity<SleepTypes> updateSleepTypes(@RequestBody SleepTypes sleepTypes) throws URISyntaxException {
        log.debug("REST request to update SleepTypes : {}", sleepTypes);
        if (sleepTypes.getId() == null) {
            return createSleepTypes(sleepTypes);
        }
        SleepTypes result = sleepTypesService.save(sleepTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sleepTypes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sleep-types : get all the sleepTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sleepTypes in body
     */
    @GetMapping("/sleep-types")
    @Timed
    public ResponseEntity<List<SleepTypes>> getAllSleepTypes(Pageable pageable) {
        log.debug("REST request to get a page of SleepTypes");
        Page<SleepTypes> page = sleepTypesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sleep-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sleep-types/:id : get the "id" sleepTypes.
     *
     * @param id the id of the sleepTypes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sleepTypes, or with status 404 (Not Found)
     */
    @GetMapping("/sleep-types/{id}")
    @Timed
    public ResponseEntity<SleepTypes> getSleepTypes(@PathVariable Long id) {
        log.debug("REST request to get SleepTypes : {}", id);
        SleepTypes sleepTypes = sleepTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sleepTypes));
    }

    /**
     * DELETE  /sleep-types/:id : delete the "id" sleepTypes.
     *
     * @param id the id of the sleepTypes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sleep-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteSleepTypes(@PathVariable Long id) {
        log.debug("REST request to delete SleepTypes : {}", id);
        sleepTypesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
