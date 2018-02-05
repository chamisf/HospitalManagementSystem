package org.fits.hms.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fits.hms.domain.DrugHistory;
import org.fits.hms.service.DrugHistoryService;
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
 * REST controller for managing DrugHistory.
 */
@RestController
@RequestMapping("/api")
public class DrugHistoryResource {

    private final Logger log = LoggerFactory.getLogger(DrugHistoryResource.class);

    private static final String ENTITY_NAME = "drugHistory";

    private final DrugHistoryService drugHistoryService;

    public DrugHistoryResource(DrugHistoryService drugHistoryService) {
        this.drugHistoryService = drugHistoryService;
    }

    /**
     * POST  /drug-histories : Create a new drugHistory.
     *
     * @param drugHistory the drugHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drugHistory, or with status 400 (Bad Request) if the drugHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/drug-histories")
    @Timed
    public ResponseEntity<DrugHistory> createDrugHistory(@Valid @RequestBody DrugHistory drugHistory) throws URISyntaxException {
        log.debug("REST request to save DrugHistory : {}", drugHistory);
        if (drugHistory.getId() != null) {
            throw new BadRequestAlertException("A new drugHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrugHistory result = drugHistoryService.save(drugHistory);
        return ResponseEntity.created(new URI("/api/drug-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drug-histories : Updates an existing drugHistory.
     *
     * @param drugHistory the drugHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drugHistory,
     * or with status 400 (Bad Request) if the drugHistory is not valid,
     * or with status 500 (Internal Server Error) if the drugHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/drug-histories")
    @Timed
    public ResponseEntity<DrugHistory> updateDrugHistory(@Valid @RequestBody DrugHistory drugHistory) throws URISyntaxException {
        log.debug("REST request to update DrugHistory : {}", drugHistory);
        if (drugHistory.getId() == null) {
            return createDrugHistory(drugHistory);
        }
        DrugHistory result = drugHistoryService.save(drugHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, drugHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drug-histories : get all the drugHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of drugHistories in body
     */
    @GetMapping("/drug-histories")
    @Timed
    public ResponseEntity<List<DrugHistory>> getAllDrugHistories(Pageable pageable) {
        log.debug("REST request to get a page of DrugHistories");
        Page<DrugHistory> page = drugHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/drug-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /drug-histories/:id : get the "id" drugHistory.
     *
     * @param id the id of the drugHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drugHistory, or with status 404 (Not Found)
     */
    @GetMapping("/drug-histories/{id}")
    @Timed
    public ResponseEntity<DrugHistory> getDrugHistory(@PathVariable Long id) {
        log.debug("REST request to get DrugHistory : {}", id);
        DrugHistory drugHistory = drugHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(drugHistory));
    }

    /**
     * DELETE  /drug-histories/:id : delete the "id" drugHistory.
     *
     * @param id the id of the drugHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/drug-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteDrugHistory(@PathVariable Long id) {
        log.debug("REST request to delete DrugHistory : {}", id);
        drugHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
