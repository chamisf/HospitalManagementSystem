package org.fits.hms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BowelHabits.
 */
@Entity
@Table(name = "bowel_habits")
public class BowelHabits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bowel_habit")
    private String bowelHabit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBowelHabit() {
        return bowelHabit;
    }

    public BowelHabits bowelHabit(String bowelHabit) {
        this.bowelHabit = bowelHabit;
        return this;
    }

    public void setBowelHabit(String bowelHabit) {
        this.bowelHabit = bowelHabit;
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
        BowelHabits bowelHabits = (BowelHabits) o;
        if (bowelHabits.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bowelHabits.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BowelHabits{" +
            "id=" + getId() +
            ", bowelHabit='" + getBowelHabit() + "'" +
            "}";
    }
}
