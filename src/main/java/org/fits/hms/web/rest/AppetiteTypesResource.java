package org.fits.hms.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fits.hms.domain.AppetiteTypes;
import org.fits.hms.service.AppetiteTypesService;
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
 * REST controller for managing AppetiteTypes.
 */
@RestController
@RequestMapping("/api")
public class AppetiteTypesResource {

    private final Logger log = LoggerFactory.getLogger(AppetiteTypesResource.class);

    private static final String ENTITY_NAME = "appetiteTypes";

    private final AppetiteTypesService appetiteTypesService;

    public AppetiteTypesResource(AppetiteTypesService appetiteTypesService) {
        this.appetiteTypesService = appetiteTypesService;
    }

    /**
     * POST  /appetite-types : Create a new appetiteTypes.
     *
     * @param appetiteTypes the appetiteTypes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appetiteTypes, or with status 400 (Bad Request) if the appetiteTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/appetite-types")
    @Timed
    public ResponseEntity<AppetiteTypes> createAppetiteTypes(@RequestBody AppetiteTypes appetiteTypes) throws URISyntaxException {
        log.debug("REST request to save AppetiteTypes : {}", appetiteTypes);
        if (appetiteTypes.getId() != null) {
            throw new BadRequestAlertException("A new appetiteTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppetiteTypes result = appetiteTypesService.save(appetiteTypes);
        return ResponseEntity.created(new URI("/api/appetite-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /appetite-types : Updates an existing appetiteTypes.
     *
     * @param appetiteTypes the appetiteTypes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appetiteTypes,
     * or with status 400 (Bad Request) if the appetiteTypes is not valid,
     * or with status 500 (Internal Server Error) if the appetiteTypes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/appetite-types")
    @Timed
    public ResponseEntity<AppetiteTypes> updateAppetiteTypes(@RequestBody AppetiteTypes appetiteTypes) throws URISyntaxException {
        log.debug("REST request to update AppetiteTypes : {}", appetiteTypes);
        if (appetiteTypes.getId() == null) {
            return createAppetiteTypes(appetiteTypes);
        }
        AppetiteTypes result = appetiteTypesService.save(appetiteTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appetiteTypes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /appetite-types : get all the appetiteTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of appetiteTypes in body
     */
    @GetMapping("/appetite-types")
    @Timed
    public ResponseEntity<List<AppetiteTypes>> getAllAppetiteTypes(Pageable pageable) {
        log.debug("REST request to get a page of AppetiteTypes");
        Page<AppetiteTypes> page = appetiteTypesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/appetite-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /appetite-types/:id : get the "id" appetiteTypes.
     *
     * @param id the id of the appetiteTypes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appetiteTypes, or with status 404 (Not Found)
     */
    @GetMapping("/appetite-types/{id}")
    @Timed
    public ResponseEntity<AppetiteTypes> getAppetiteTypes(@PathVariable Long id) {
        log.debug("REST request to get AppetiteTypes : {}", id);
        AppetiteTypes appetiteTypes = appetiteTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appetiteTypes));
    }

    /**
     * DELETE  /appetite-types/:id : delete the "id" appetiteTypes.
     *
     * @param id the id of the appetiteTypes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/appetite-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppetiteTypes(@PathVariable Long id) {
        log.debug("REST request to delete AppetiteTypes : {}", id);
        appetiteTypesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
