package org.fits.hms.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fits.hms.domain.AllergyType;
import org.fits.hms.service.AllergyTypeService;
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
 * REST controller for managing AllergyType.
 */
@RestController
@RequestMapping("/api")
public class AllergyTypeResource {

    private final Logger log = LoggerFactory.getLogger(AllergyTypeResource.class);

    private static final String ENTITY_NAME = "allergyType";

    private final AllergyTypeService allergyTypeService;

    public AllergyTypeResource(AllergyTypeService allergyTypeService) {
        this.allergyTypeService = allergyTypeService;
    }

    /**
     * POST  /allergy-types : Create a new allergyType.
     *
     * @param allergyType the allergyType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new allergyType, or with status 400 (Bad Request) if the allergyType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/allergy-types")
    @Timed
    public ResponseEntity<AllergyType> createAllergyType(@RequestBody AllergyType allergyType) throws URISyntaxException {
        log.debug("REST request to save AllergyType : {}", allergyType);
        if (allergyType.getId() != null) {
            throw new BadRequestAlertException("A new allergyType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllergyType result = allergyTypeService.save(allergyType);
        return ResponseEntity.created(new URI("/api/allergy-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /allergy-types : Updates an existing allergyType.
     *
     * @param allergyType the allergyType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated allergyType,
     * or with status 400 (Bad Request) if the allergyType is not valid,
     * or with status 500 (Internal Server Error) if the allergyType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/allergy-types")
    @Timed
    public ResponseEntity<AllergyType> updateAllergyType(@RequestBody AllergyType allergyType) throws URISyntaxException {
        log.debug("REST request to update AllergyType : {}", allergyType);
        if (allergyType.getId() == null) {
            return createAllergyType(allergyType);
        }
        AllergyType result = allergyTypeService.save(allergyType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, allergyType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /allergy-types : get all the allergyTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of allergyTypes in body
     */
    @GetMapping("/allergy-types")
    @Timed
    public ResponseEntity<List<AllergyType>> getAllAllergyTypes(Pageable pageable) {
        log.debug("REST request to get a page of AllergyTypes");
        Page<AllergyType> page = allergyTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/allergy-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /allergy-types/:id : get the "id" allergyType.
     *
     * @param id the id of the allergyType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the allergyType, or with status 404 (Not Found)
     */
    @GetMapping("/allergy-types/{id}")
    @Timed
    public ResponseEntity<AllergyType> getAllergyType(@PathVariable Long id) {
        log.debug("REST request to get AllergyType : {}", id);
        AllergyType allergyType = allergyTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(allergyType));
    }

    /**
     * DELETE  /allergy-types/:id : delete the "id" allergyType.
     *
     * @param id the id of the allergyType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/allergy-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAllergyType(@PathVariable Long id) {
        log.debug("REST request to delete AllergyType : {}", id);
        allergyTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
