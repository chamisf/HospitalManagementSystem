package org.fits.hms.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fits.hms.domain.GynaecologicalHistory;
import org.fits.hms.service.GynaecologicalHistoryService;
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
 * REST controller for managing GynaecologicalHistory.
 */
@RestController
@RequestMapping("/api")
public class GynaecologicalHistoryResource {

    private final Logger log = LoggerFactory.getLogger(GynaecologicalHistoryResource.class);

    private static final String ENTITY_NAME = "gynaecologicalHistory";

    private final GynaecologicalHistoryService gynaecologicalHistoryService;

    public GynaecologicalHistoryResource(GynaecologicalHistoryService gynaecologicalHistoryService) {
        this.gynaecologicalHistoryService = gynaecologicalHistoryService;
    }

    /**
     * POST  /gynaecological-histories : Create a new gynaecologicalHistory.
     *
     * @param gynaecologicalHistory the gynaecologicalHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gynaecologicalHistory, or with status 400 (Bad Request) if the gynaecologicalHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gynaecological-histories")
    @Timed
    public ResponseEntity<GynaecologicalHistory> createGynaecologicalHistory(@Valid @RequestBody GynaecologicalHistory gynaecologicalHistory) throws URISyntaxException {
        log.debug("REST request to save GynaecologicalHistory : {}", gynaecologicalHistory);
        if (gynaecologicalHistory.getId() != null) {
            throw new BadRequestAlertException("A new gynaecologicalHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GynaecologicalHistory result = gynaecologicalHistoryService.save(gynaecologicalHistory);
        return ResponseEntity.created(new URI("/api/gynaecological-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gynaecological-histories : Updates an existing gynaecologicalHistory.
     *
     * @param gynaecologicalHistory the gynaecologicalHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gynaecologicalHistory,
     * or with status 400 (Bad Request) if the gynaecologicalHistory is not valid,
     * or with status 500 (Internal Server Error) if the gynaecologicalHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gynaecological-histories")
    @Timed
    public ResponseEntity<GynaecologicalHistory> updateGynaecologicalHistory(@Valid @RequestBody GynaecologicalHistory gynaecologicalHistory) throws URISyntaxException {
        log.debug("REST request to update GynaecologicalHistory : {}", gynaecologicalHistory);
        if (gynaecologicalHistory.getId() == null) {
            return createGynaecologicalHistory(gynaecologicalHistory);
        }
        GynaecologicalHistory result = gynaecologicalHistoryService.save(gynaecologicalHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gynaecologicalHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gynaecological-histories : get all the gynaecologicalHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gynaecologicalHistories in body
     */
    @GetMapping("/gynaecological-histories")
    @Timed
    public ResponseEntity<List<GynaecologicalHistory>> getAllGynaecologicalHistories(Pageable pageable) {
        log.debug("REST request to get a page of GynaecologicalHistories");
        Page<GynaecologicalHistory> page = gynaecologicalHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gynaecological-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /gynaecological-histories/:id : get the "id" gynaecologicalHistory.
     *
     * @param id the id of the gynaecologicalHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gynaecologicalHistory, or with status 404 (Not Found)
     */
    @GetMapping("/gynaecological-histories/{id}")
    @Timed
    public ResponseEntity<GynaecologicalHistory> getGynaecologicalHistory(@PathVariable Long id) {
        log.debug("REST request to get GynaecologicalHistory : {}", id);
        GynaecologicalHistory gynaecologicalHistory = gynaecologicalHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gynaecologicalHistory));
    }

    /**
     * GET  /gynaecological-histories/by-patient/:id : get the gynaecologicalHistory of given :id patient.
     *
     * @param id the id of the patient of gynaecologicalHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gynaecologicalHistory, or with status 404 (Not Found)
     */
    @GetMapping("/gynaecological-histories/by-patient/{id}")
    @Timed
    public ResponseEntity<GynaecologicalHistory> getFirstGynaecologicalHistoryByPatientId(@PathVariable Long id) {
        log.debug("REST request to get GynaecologicalHistory by Patient Id : {}", id);
        GynaecologicalHistory gynaecologicalHistory = gynaecologicalHistoryService.findFirstByPatientId(id);
        if (gynaecologicalHistory == null) {
            gynaecologicalHistory = new GynaecologicalHistory();
        }
        return ResponseEntity.ok(gynaecologicalHistory);
    }

    /**
     * DELETE  /gynaecological-histories/:id : delete the "id" gynaecologicalHistory.
     *
     * @param id the id of the gynaecologicalHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gynaecological-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteGynaecologicalHistory(@PathVariable Long id) {
        log.debug("REST request to delete GynaecologicalHistory : {}", id);
        gynaecologicalHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
