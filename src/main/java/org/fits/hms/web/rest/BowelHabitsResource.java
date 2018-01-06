package org.fits.hms.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fits.hms.domain.BowelHabits;
import org.fits.hms.service.BowelHabitsService;
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
 * REST controller for managing BowelHabits.
 */
@RestController
@RequestMapping("/api")
public class BowelHabitsResource {

    private final Logger log = LoggerFactory.getLogger(BowelHabitsResource.class);

    private static final String ENTITY_NAME = "bowelHabits";

    private final BowelHabitsService bowelHabitsService;

    public BowelHabitsResource(BowelHabitsService bowelHabitsService) {
        this.bowelHabitsService = bowelHabitsService;
    }

    /**
     * POST  /bowel-habits : Create a new bowelHabits.
     *
     * @param bowelHabits the bowelHabits to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bowelHabits, or with status 400 (Bad Request) if the bowelHabits has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bowel-habits")
    @Timed
    public ResponseEntity<BowelHabits> createBowelHabits(@RequestBody BowelHabits bowelHabits) throws URISyntaxException {
        log.debug("REST request to save BowelHabits : {}", bowelHabits);
        if (bowelHabits.getId() != null) {
            throw new BadRequestAlertException("A new bowelHabits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BowelHabits result = bowelHabitsService.save(bowelHabits);
        return ResponseEntity.created(new URI("/api/bowel-habits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bowel-habits : Updates an existing bowelHabits.
     *
     * @param bowelHabits the bowelHabits to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bowelHabits,
     * or with status 400 (Bad Request) if the bowelHabits is not valid,
     * or with status 500 (Internal Server Error) if the bowelHabits couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bowel-habits")
    @Timed
    public ResponseEntity<BowelHabits> updateBowelHabits(@RequestBody BowelHabits bowelHabits) throws URISyntaxException {
        log.debug("REST request to update BowelHabits : {}", bowelHabits);
        if (bowelHabits.getId() == null) {
            return createBowelHabits(bowelHabits);
        }
        BowelHabits result = bowelHabitsService.save(bowelHabits);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bowelHabits.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bowel-habits : get all the bowelHabits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bowelHabits in body
     */
    @GetMapping("/bowel-habits")
    @Timed
    public ResponseEntity<List<BowelHabits>> getAllBowelHabits(Pageable pageable) {
        log.debug("REST request to get a page of BowelHabits");
        Page<BowelHabits> page = bowelHabitsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bowel-habits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bowel-habits/:id : get the "id" bowelHabits.
     *
     * @param id the id of the bowelHabits to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bowelHabits, or with status 404 (Not Found)
     */
    @GetMapping("/bowel-habits/{id}")
    @Timed
    public ResponseEntity<BowelHabits> getBowelHabits(@PathVariable Long id) {
        log.debug("REST request to get BowelHabits : {}", id);
        BowelHabits bowelHabits = bowelHabitsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bowelHabits));
    }

    /**
     * DELETE  /bowel-habits/:id : delete the "id" bowelHabits.
     *
     * @param id the id of the bowelHabits to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bowel-habits/{id}")
    @Timed
    public ResponseEntity<Void> deleteBowelHabits(@PathVariable Long id) {
        log.debug("REST request to delete BowelHabits : {}", id);
        bowelHabitsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
