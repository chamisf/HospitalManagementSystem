package org.fits.hms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FamilyHistory.
 */
@Entity
@Table(name = "family_history")
public class FamilyHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fathers_diseases")
    private String fathersDiseases;

    @Column(name = "mothers_diseases")
    private String mothersDiseases;

    @Column(name = "siblings_diseases")
    private String siblingsDiseases;

    @Column(name = "other_relatives_diseases")
    private String otherRelativesDiseases;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFathersDiseases() {
        return fathersDiseases;
    }

    public FamilyHistory fathersDiseases(String fathersDiseases) {
        this.fathersDiseases = fathersDiseases;
        return this;
    }

    public void setFathersDiseases(String fathersDiseases) {
        this.fathersDiseases = fathersDiseases;
    }

    public String getMothersDiseases() {
        return mothersDiseases;
    }

    public FamilyHistory mothersDiseases(String mothersDiseases) {
        this.mothersDiseases = mothersDiseases;
        return this;
    }

    public void setMothersDiseases(String mothersDiseases) {
        this.mothersDiseases = mothersDiseases;
    }

    public String getSiblingsDiseases() {
        return siblingsDiseases;
    }

    public FamilyHistory siblingsDiseases(String siblingsDiseases) {
        this.siblingsDiseases = siblingsDiseases;
        return this;
    }

    public void setSiblingsDiseases(String siblingsDiseases) {
        this.siblingsDiseases = siblingsDiseases;
    }

    public String getOtherRelativesDiseases() {
        return otherRelativesDiseases;
    }

    public FamilyHistory otherRelativesDiseases(String otherRelativesDiseases) {
        this.otherRelativesDiseases = otherRelativesDiseases;
        return this;
    }

    public void setOtherRelativesDiseases(String otherRelativesDiseases) {
        this.otherRelativesDiseases = otherRelativesDiseases;
    }

    public Patient getPatient() {
        return patient;
    }

    public FamilyHistory patient(Patient patient) {
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
        FamilyHistory familyHistory = (FamilyHistory) o;
        if (familyHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), familyHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FamilyHistory{" +
            "id=" + getId() +
            ", fathersDiseases='" + getFathersDiseases() + "'" +
            ", mothersDiseases='" + getMothersDiseases() + "'" +
            ", siblingsDiseases='" + getSiblingsDiseases() + "'" +
            ", otherRelativesDiseases='" + getOtherRelativesDiseases() + "'" +
            "}";
    }
}
