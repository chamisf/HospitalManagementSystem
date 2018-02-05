package org.fits.hms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AllergyType.
 */
@Entity
@Table(name = "allergy_type")
public class AllergyType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "allergy_type")
    private String allergyType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAllergyType() {
        return allergyType;
    }

    public AllergyType allergyType(String allergyType) {
        this.allergyType = allergyType;
        return this;
    }

    public void setAllergyType(String allergyType) {
        this.allergyType = allergyType;
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
        AllergyType allergyType = (AllergyType) o;
        if (allergyType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), allergyType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AllergyType{" +
            "id=" + getId() +
            ", allergyType='" + getAllergyType() + "'" +
            "}";
    }
}
