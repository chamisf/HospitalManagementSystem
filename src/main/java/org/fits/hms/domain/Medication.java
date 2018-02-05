package org.fits.hms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Medication.
 */
@Entity
@Table(name = "medication")
public class Medication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dose")
    private String dose;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "route")
    private String route;

    @Column(name = "mane_nocte")
    private String maneNocte;

    @Column(name = "using_currently")
    private Boolean usingCurrently;

    @ManyToOne
    private DrugHistory drugHistory;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Medicine medicine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDose() {
        return dose;
    }

    public Medication dose(String dose) {
        this.dose = dose;
        return this;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getFrequency() {
        return frequency;
    }

    public Medication frequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getRoute() {
        return route;
    }

    public Medication route(String route) {
        this.route = route;
        return this;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getManeNocte() {
        return maneNocte;
    }

    public Medication maneNocte(String maneNocte) {
        this.maneNocte = maneNocte;
        return this;
    }

    public void setManeNocte(String maneNocte) {
        this.maneNocte = maneNocte;
    }

    public Boolean isUsingCurrently() {
        return usingCurrently;
    }

    public Medication usingCurrently(Boolean usingCurrently) {
        this.usingCurrently = usingCurrently;
        return this;
    }

    public void setUsingCurrently(Boolean usingCurrently) {
        this.usingCurrently = usingCurrently;
    }

    public DrugHistory getDrugHistory() {
        return drugHistory;
    }

    public Medication drugHistory(DrugHistory drugHistory) {
        this.drugHistory = drugHistory;
        return this;
    }

    public void setDrugHistory(DrugHistory drugHistory) {
        this.drugHistory = drugHistory;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public Medication medicine(Medicine medicine) {
        this.medicine = medicine;
        return this;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
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
        Medication medication = (Medication) o;
        if (medication.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medication.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medication{" +
            "id=" + getId() +
            ", dose='" + getDose() + "'" +
            ", frequency='" + getFrequency() + "'" +
            ", route='" + getRoute() + "'" +
            ", maneNocte='" + getManeNocte() + "'" +
            ", usingCurrently='" + isUsingCurrently() + "'" +
            "}";
    }
}
