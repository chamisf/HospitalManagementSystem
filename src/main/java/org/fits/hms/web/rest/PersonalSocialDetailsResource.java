package org.fits.hms.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fits.hms.domain.PersonalSocialDetails;
import org.fits.hms.service.PersonalSocialDetailsService;
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
 * REST controller for managing PersonalSocialDetails.
 */
@RestController
@RequestMapping("/api")
public class PersonalSocialDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PersonalSocialDetailsResource.class);

    private static final String ENTITY_NAME = "personalSocialDetails";

    private final PersonalSocialDetailsService personalSocialDetailsService;

    public PersonalSocialDetailsResource(PersonalSocialDetailsService personalSocialDetailsService) {
        this.personalSocialDetailsService = personalSocialDetailsService;
    }

    /**
     * POST  /personal-social-details : Create a new personalSocialDetails.
     *
     * @param personalSocialDetails the personalSocialDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personalSocialDetails, or with status 400 (Bad Request) if the personalSocialDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personal-social-details")
    @Timed
    public ResponseEntity<PersonalSocialDetails> createPersonalSocialDetails(@Valid @RequestBody PersonalSocialDetails personalSocialDetails) throws URISyntaxException {
        log.debug("REST request to save PersonalSocialDetails : {}", personalSocialDetails);
        if (personalSocialDetails.getId() != null) {
            throw new BadRequestAlertException("A new personalSocialDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonalSocialDetails result = personalSocialDetailsService.save(personalSocialDetails);
        return ResponseEntity.created(new URI("/api/personal-social-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personal-social-details : Updates an existing personalSocialDetails.
     *
     * @param personalSocialDetails the personalSocialDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personalSocialDetails,
     * or with status 400 (Bad Request) if the personalSocialDetails is not valid,
     * or with status 500 (Internal Server Error) if the personalSocialDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personal-social-details")
    @Timed
    public ResponseEntity<PersonalSocialDetails> updatePersonalSocialDetails(@Valid @RequestBody PersonalSocialDetails personalSocialDetails) throws URISyntaxException {
        log.debug("REST request to update PersonalSocialDetails : {}", personalSocialDetails);
        if (personalSocialDetails.getId() == null) {
            return createPersonalSocialDetails(personalSocialDetails);
        }
        PersonalSocialDetails result = personalSocialDetailsService.save(personalSocialDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personalSocialDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personal-social-details : get all the personalSocialDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personalSocialDetails in body
     */
    @GetMapping("/personal-social-details")
    @Timed
    public ResponseEntity<List<PersonalSocialDetails>> getAllPersonalSocialDetails(Pageable pageable) {
        log.debug("REST request to get a page of PersonalSocialDetails");
        Page<PersonalSocialDetails> page = personalSocialDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/personal-social-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /personal-social-details/:id : get the "id" personalSocialDetails.
     *
     * @param id the id of the personalSocialDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personalSocialDetails, or with status 404 (Not Found)
     */
    @GetMapping("/personal-social-details/{id}")
    @Timed
    public ResponseEntity<PersonalSocialDetails> getPersonalSocialDetails(@PathVariable Long id) {
        log.debug("REST request to get PersonalSocialDetails : {}", id);
        PersonalSocialDetails personalSocialDetails = personalSocialDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personalSocialDetails));
    }

    /**
     * GET  /personal-social-details/:id : get the "id" personalSocialDetails.
     *
     * @param id the id of the personalSocialDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personalSocialDetails, or with status 404 (Not Found)
     */
    @GetMapping("/personal-social-details//by-patient/{id}")
    @Timed
    public ResponseEntity<PersonalSocialDetails> getPersonalSocialDetailsByPatientId(@PathVariable Long id) {
        log.debug("REST request to get PersonalSocialDetails by Patient Id : {}", id);
        PersonalSocialDetails personalSocialDetails = personalSocialDetailsService.findFirstByPatientId(id);
        if (personalSocialDetails == null) {
            personalSocialDetails = new PersonalSocialDetails();
        }
        return ResponseEntity.ok(personalSocialDetails);
    }

    /**
     * DELETE  /personal-social-details/:id : delete the "id" personalSocialDetails.
     *
     * @param id the id of the personalSocialDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personal-social-details/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonalSocialDetails(@PathVariable Long id) {
        log.debug("REST request to delete PersonalSocialDetails : {}", id);
        personalSocialDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
