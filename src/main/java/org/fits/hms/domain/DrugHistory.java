package org.fits.hms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DrugHistory.
 */
@Entity
@Table(name = "drug_history")
public class DrugHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_compliant_patient")
    private Boolean isCompliantPatient;

    @Column(name = "additional_medication_information")
    private String additionalMedicationInformation;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Patient patient;

    @OneToMany(mappedBy = "drugHistory")
    @JsonIgnore
    private Set<Medication> medications = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsCompliantPatient() {
        return isCompliantPatient;
    }

    public DrugHistory isCompliantPatient(Boolean isCompliantPatient) {
        this.isCompliantPatient = isCompliantPatient;
        return this;
    }

    public void setIsCompliantPatient(Boolean isCompliantPatient) {
        this.isCompliantPatient = isCompliantPatient;
    }

    public String getAdditionalMedicationInformation() {
        return additionalMedicationInformation;
    }

    public DrugHistory additionalMedicationInformation(String additionalMedicationInformation) {
        this.additionalMedicationInformation = additionalMedicationInformation;
        return this;
    }

    public void setAdditionalMedicationInformation(String additionalMedicationInformation) {
        this.additionalMedicationInformation = additionalMedicationInformation;
    }

    public Patient getPatient() {
        return patient;
    }

    public DrugHistory patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Set<Medication> getMedications() {
        return medications;
    }

    public DrugHistory medications(Set<Medication> medications) {
        this.medications = medications;
        return this;
    }

    public DrugHistory addMedications(Medication medication) {
        this.medications.add(medication);
        medication.setDrugHistory(this);
        return this;
    }

    public DrugHistory removeMedications(Medication medication) {
        this.medications.remove(medication);
        medication.setDrugHistory(null);
        return this;
    }

    public void setMedications(Set<Medication> medications) {
        this.medications = medications;
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
        DrugHistory drugHistory = (DrugHistory) o;
        if (drugHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), drugHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DrugHistory{" +
            "id=" + getId() +
            ", isCompliantPatient='" + isIsCompliantPatient() + "'" +
            ", additionalMedicationInformation='" + getAdditionalMedicationInformation() + "'" +
            "}";
    }
}
