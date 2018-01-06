package org.fits.hms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A GynaecologicalHistory.
 */
@Entity
@Table(name = "gynaecological_history")
public class GynaecologicalHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "age_of_menarche")
    private String ageOfMenarche;

    @Column(name = "last_menstrual_period")
    private String lastMenstrualPeriod;

    @Column(name = "regularity_of_cycle")
    private String regularityOfCycle;

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

    public String getAgeOfMenarche() {
        return ageOfMenarche;
    }

    public GynaecologicalHistory ageOfMenarche(String ageOfMenarche) {
        this.ageOfMenarche = ageOfMenarche;
        return this;
    }

    public void setAgeOfMenarche(String ageOfMenarche) {
        this.ageOfMenarche = ageOfMenarche;
    }

    public String getLastMenstrualPeriod() {
        return lastMenstrualPeriod;
    }

    public GynaecologicalHistory lastMenstrualPeriod(String lastMenstrualPeriod) {
        this.lastMenstrualPeriod = lastMenstrualPeriod;
        return this;
    }

    public void setLastMenstrualPeriod(String lastMenstrualPeriod) {
        this.lastMenstrualPeriod = lastMenstrualPeriod;
    }

    public String getRegularityOfCycle() {
        return regularityOfCycle;
    }

    public GynaecologicalHistory regularityOfCycle(String regularityOfCycle) {
        this.regularityOfCycle = regularityOfCycle;
        return this;
    }

    public void setRegularityOfCycle(String regularityOfCycle) {
        this.regularityOfCycle = regularityOfCycle;
    }

    public Patient getPatient() {
        return patient;
    }

    public GynaecologicalHistory patient(Patient patient) {
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
        GynaecologicalHistory gynaecologicalHistory = (GynaecologicalHistory) o;
        if (gynaecologicalHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gynaecologicalHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GynaecologicalHistory{" +
            "id=" + getId() +
            ", ageOfMenarche='" + getAgeOfMenarche() + "'" +
            ", lastMenstrualPeriod='" + getLastMenstrualPeriod() + "'" +
            ", regularityOfCycle='" + getRegularityOfCycle() + "'" +
            "}";
    }
}
