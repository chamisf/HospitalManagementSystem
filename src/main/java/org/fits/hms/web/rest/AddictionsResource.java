package org.fits.hms.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fits.hms.domain.Addictions;
import org.fits.hms.service.AddictionsService;
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
 * REST controller for managing Addictions.
 */
@RestController
@RequestMapping("/api")
public class AddictionsResource {

    private final Logger log = LoggerFactory.getLogger(AddictionsResource.class);

    private static final String ENTITY_NAME = "addictions";

    private final AddictionsService addictionsService;

    public AddictionsResource(AddictionsService addictionsService) {
        this.addictionsService = addictionsService;
    }

    /**
     * POST  /addictions : Create a new addictions.
     *
     * @param addictions the addictions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addictions, or with status 400 (Bad Request) if the addictions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/addictions")
    @Timed
    public ResponseEntity<Addictions> createAddictions(@RequestBody Addictions addictions) throws URISyntaxException {
        log.debug("REST request to save Addictions : {}", addictions);
        if (addictions.getId() != null) {
            throw new BadRequestAlertException("A new addictions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Addictions result = addictionsService.save(addictions);
        return ResponseEntity.created(new URI("/api/addictions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /addictions : Updates an existing addictions.
     *
     * @param addictions the addictions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addictions,
     * or with status 400 (Bad Request) if the addictions is not valid,
     * or with status 500 (Internal Server Error) if the addictions couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/addictions")
    @Timed
    public ResponseEntity<Addictions> updateAddictions(@RequestBody Addictions addictions) throws URISyntaxException {
        log.debug("REST request to update Addictions : {}", addictions);
        if (addictions.getId() == null) {
            return createAddictions(addictions);
        }
        Addictions result = addictionsService.save(addictions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addictions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /addictions : get all the addictions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of addictions in body
     */
    @GetMapping("/addictions")
    @Timed
    public ResponseEntity<List<Addictions>> getAllAddictions(Pageable pageable) {
        log.debug("REST request to get a page of Addictions");
        Page<Addictions> page = addictionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/addictions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /addictions/:id : get the "id" addictions.
     *
     * @param id the id of the addictions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addictions, or with status 404 (Not Found)
     */
    @GetMapping("/addictions/{id}")
    @Timed
    public ResponseEntity<Addictions> getAddictions(@PathVariable Long id) {
        log.debug("REST request to get Addictions : {}", id);
        Addictions addictions = addictionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(addictions));
    }

    /**
     * DELETE  /addictions/:id : delete the "id" addictions.
     *
     * @param id the id of the addictions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/addictions/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddictions(@PathVariable Long id) {
        log.debug("REST request to delete Addictions : {}", id);
        addictionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
