package org.fits.hms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AppetiteTypes.
 */
@Entity
@Table(name = "appetite_types")
public class AppetiteTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appetite_type")
    private String appetiteType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppetiteType() {
        return appetiteType;
    }

    public AppetiteTypes appetiteType(String appetiteType) {
        this.appetiteType = appetiteType;
        return this;
    }

    public void setAppetiteType(String appetiteType) {
        this.appetiteType = appetiteType;
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
        AppetiteTypes appetiteTypes = (AppetiteTypes) o;
        if (appetiteTypes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appetiteTypes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppetiteTypes{" +
            "id=" + getId() +
            ", appetiteType='" + getAppetiteType() + "'" +
            "}";
    }
}
