package org.fits.hms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Allergy.
 */
@Entity
@Table(name = "allergy")
public class Allergy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "additional_information")
    private String additionalInformation;

    @OneToOne
    @JoinColumn()
    private AllergyType allergyType;

    @ManyToOne(optional = false)
    @NotNull
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public Allergy additionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public AllergyType getAllergyType() {
        return allergyType;
    }

    public Allergy allergyType(AllergyType allergyType) {
        this.allergyType = allergyType;
        return this;
    }

    public void setAllergyType(AllergyType allergyType) {
        this.allergyType = allergyType;
    }

    public Patient getPatient() {
        return patient;
    }

    public Allergy patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Allergy allergy = (Allergy) o;
        if (allergy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), allergy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Allergy{" +
            "id=" + getId() +
            ", additionalInformation='" + getAdditionalInformation() + "'" +
            "}";
    }
}
