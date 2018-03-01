package org.fits.hms.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fits.hms.domain.FamilyHistory;
import org.fits.hms.service.FamilyHistoryService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FamilyHistory.
 */
@RestController
@RequestMapping("/api")
public class FamilyHistoryResource {

    private final Logger log = LoggerFactory.getLogger(FamilyHistoryResource.class);

    private static final String ENTITY_NAME = "familyHistory";

    private final FamilyHistoryService familyHistoryService;

    public FamilyHistoryResource(FamilyHistoryService familyHistoryService) {
        this.familyHistoryService = familyHistoryService;
    }

    /**
     * POST  /family-histories : Create a new familyHistory.
     *
     * @param familyHistory the familyHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new familyHistory, or with status 400 (Bad Request) if the familyHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/family-histories")
    @Timed
    public ResponseEntity<FamilyHistory> createFamilyHistory(@Valid @RequestBody FamilyHistory familyHistory) throws URISyntaxException {
        log.debug("REST request to save FamilyHistory : {}", familyHistory);
        if (familyHistory.getId() != null) {
            throw new BadRequestAlertException("A new familyHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FamilyHistory result = familyHistoryService.save(familyHistory);
        return ResponseEntity.created(new URI("/api/family-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /family-histories : Updates an existing familyHistory.
     *
     * @param familyHistory the familyHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated familyHistory,
     * or with status 400 (Bad Request) if the familyHistory is not valid,
     * or with status 500 (Internal Server Error) if the familyHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/family-histories")
    @Timed
    public ResponseEntity<FamilyHistory> updateFamilyHistory(@Valid @RequestBody FamilyHistory familyHistory) throws URISyntaxException {
        log.debug("REST request to update FamilyHistory : {}", familyHistory);
        if (familyHistory.getId() == null) {
            return createFamilyHistory(familyHistory);
        }
        FamilyHistory result = familyHistoryService.save(familyHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, familyHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /family-histories : get all the familyHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of familyHistories in body
     */
    @GetMapping("/family-histories")
    @Timed
    public ResponseEntity<List<FamilyHistory>> getAllFamilyHistories(Pageable pageable) {
        log.debug("REST request to get a page of FamilyHistories");
        Page<FamilyHistory> page = familyHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/family-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /family-histories/:id : get the "id" familyHistory.
     *
     * @param id the id of the familyHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the familyHistory, or with status 404 (Not Found)
     */
    @GetMapping("/family-histories/{id}")
    @Timed
    public ResponseEntity<FamilyHistory> getFamilyHistory(@PathVariable Long id) {
        log.debug("REST request to get FamilyHistory : {}", id);
        FamilyHistory familyHistory = familyHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(familyHistory));
    }

    /**
     * GET  /family-histories/by-patient/:id : get the familyHistory of given :id patient.
     *
     * @param id the id of the patient of familyHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the familyHistory, or with status 404 (Not Found)
     */
    @GetMapping("/family-histories/by-patient/{id}")
    @Timed
    public ResponseEntity<FamilyHistory> getFirstFamilyHistoryByPatientId(@PathVariable Long id) {
        log.debug("REST request to get Family History by Patient Id : {}", id);
        FamilyHistory familyHistory = familyHistoryService.findFirstByPatientId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(familyHistory));
    }

    /**
     * DELETE  /family-histories/:id : delete the "id" familyHistory.
     *
     * @param id the id of the familyHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/family-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteFamilyHistory(@PathVariable Long id) {
        log.debug("REST request to delete FamilyHistory : {}", id);
        familyHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
