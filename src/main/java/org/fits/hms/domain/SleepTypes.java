package org.fits.hms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SleepTypes.
 */
@Entity
@Table(name = "sleep_types")
public class SleepTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sleep_type")
    private String sleepType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSleepType() {
        return sleepType;
    }

    public SleepTypes sleepType(String sleepType) {
        this.sleepType = sleepType;
        return this;
    }

    public void setSleepType(String sleepType) {
        this.sleepType = sleepType;
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
        SleepTypes sleepTypes = (SleepTypes) o;
        if (sleepTypes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sleepTypes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SleepTypes{" +
            "id=" + getId() +
            ", sleepType='" + getSleepType() + "'" +
            "}";
    }
}
