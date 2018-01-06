package org.fits.hms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MicturitionTypes.
 */
@Entity
@Table(name = "micturition_types")
public class MicturitionTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "micturition_type")
    private String micturitionType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMicturitionType() {
        return micturitionType;
    }

    public MicturitionTypes micturitionType(String micturitionType) {
        this.micturitionType = micturitionType;
        return this;
    }

    public void setMicturitionType(String micturitionType) {
        this.micturitionType = micturitionType;
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
        MicturitionTypes micturitionTypes = (MicturitionTypes) o;
        if (micturitionTypes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), micturitionTypes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MicturitionTypes{" +
            "id=" + getId() +
            ", micturitionType='" + getMicturitionType() + "'" +
            "}";
    }
}
