package org.fits.hms.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fits.hms.domain.Allergy;
import org.fits.hms.service.AllergyService;
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
 * REST controller for managing Allergy.
 */
@RestController
@RequestMapping("/api")
public class AllergyResource {

    private final Logger log = LoggerFactory.getLogger(AllergyResource.class);

    private static final String ENTITY_NAME = "allergy";

    private final AllergyService allergyService;

    public AllergyResource(AllergyService allergyService) {
        this.allergyService = allergyService;
    }

    /**
     * POST  /allergies : Create a new allergy.
     *
     * @param allergy the allergy to create
     * @return the ResponseEntity with status 201 (Created) and with body the new allergy, or with status 400 (Bad Request) if the allergy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/allergies")
    @Timed
    public ResponseEntity<Allergy> createAllergy(@Valid @RequestBody Allergy allergy) throws URISyntaxException {
        log.debug("REST request to save Allergy : {}", allergy);
        if (allergy.getId() != null) {
            throw new BadRequestAlertException("A new allergy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Allergy result = allergyService.save(allergy);
        return ResponseEntity.created(new URI("/api/allergies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /allergies : Updates an existing allergy.
     *
     * @param allergy the allergy to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated allergy,
     * or with status 400 (Bad Request) if the allergy is not valid,
     * or with status 500 (Internal Server Error) if the allergy couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/allergies")
    @Timed
    public ResponseEntity<Allergy> updateAllergy(@Valid @RequestBody Allergy allergy) throws URISyntaxException {
        log.debug("REST request to update Allergy : {}", allergy);
        if (allergy.getId() == null) {
            return createAllergy(allergy);
        }
        Allergy result = allergyService.save(allergy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, allergy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /allergies : get all the allergies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of allergies in body
     */
    @GetMapping("/allergies")
    @Timed
    public ResponseEntity<List<Allergy>> getAllAllergies(Pageable pageable) {
        log.debug("REST request to get a page of Allergies");
        Page<Allergy> page = allergyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/allergies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /allergies/:id : get the "id" allergy.
     *
     * @param id the id of the allergy to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the allergy, or with status 404 (Not Found)
     */
    @GetMapping("/allergies/{id}")
    @Timed
    public ResponseEntity<Allergy> getAllergy(@PathVariable Long id) {
        log.debug("REST request to get Allergy : {}", id);
        Allergy allergy = allergyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(allergy));
    }

    /**
     * DELETE  /allergies/:id : delete the "id" allergy.
     *
     * @param id the id of the allergy to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/allergies/{id}")
    @Timed
    public ResponseEntity<Void> deleteAllergy(@PathVariable Long id) {
        log.debug("REST request to delete Allergy : {}", id);
        allergyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
