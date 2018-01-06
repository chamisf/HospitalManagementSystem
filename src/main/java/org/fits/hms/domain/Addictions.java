package org.fits.hms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Addictions.
 */
@Entity
@Table(name = "addictions")
public class Addictions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "addiction")
    private String addiction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddiction() {
        return addiction;
    }

    public Addictions addiction(String addiction) {
        this.addiction = addiction;
        return this;
    }

    public void setAddiction(String addiction) {
        this.addiction = addiction;
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
        Addictions addictions = (Addictions) o;
        if (addictions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), addictions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Addictions{" +
            "id=" + getId() +
            ", addiction='" + getAddiction() + "'" +
            "}";
    }
}
