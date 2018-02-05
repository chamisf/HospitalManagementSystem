package org.fits.hms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonalSocialDetails.
 */
@Entity
@Table(name = "personal_social_details")
public class PersonalSocialDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "education")
    private String education;

    @Column(name = "employment")
    private String employment;

    @Column(name = "assistance_at_home")
    private String assistanceAtHome;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Patient patient;

    @OneToOne
    @JoinColumn(unique = true)
    private SleepTypes sleepType;

    @OneToOne
    @JoinColumn(unique = true)
    private AppetiteTypes appetiteType;

    @OneToOne
    @JoinColumn(unique = true)
    private MicturitionTypes micturitionType;

    @OneToOne
    @JoinColumn(unique = true)
    private BowelHabits bowelHabit;

    @OneToOne
    @JoinColumn(unique = true)
    private Addictions addiction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEducation() {
        return education;
    }

    public PersonalSocialDetails education(String education) {
        this.education = education;
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEmployment() {
        return employment;
    }

    public PersonalSocialDetails employment(String employment) {
        this.employment = employment;
        return this;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getAssistanceAtHome() {
        return assistanceAtHome;
    }

    public PersonalSocialDetails assistanceAtHome(String assistanceAtHome) {
        this.assistanceAtHome = assistanceAtHome;
        return this;
    }

    public void setAssistanceAtHome(String assistanceAtHome) {
        this.assistanceAtHome = assistanceAtHome;
    }

    public Patient getPatient() {
        return patient;
    }

    public PersonalSocialDetails patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public SleepTypes getSleepType() {
        return sleepType;
    }

    public PersonalSocialDetails sleepType(SleepTypes sleepTypes) {
        this.sleepType = sleepTypes;
        return this;
    }

    public void setSleepType(SleepTypes sleepTypes) {
        this.sleepType = sleepTypes;
    }

    public AppetiteTypes getAppetiteType() {
        return appetiteType;
    }

    public PersonalSocialDetails appetiteType(AppetiteTypes appetiteTypes) {
        this.appetiteType = appetiteTypes;
        return this;
    }

    public void setAppetiteType(AppetiteTypes appetiteTypes) {
        this.appetiteType = appetiteTypes;
    }

    public MicturitionTypes getMicturitionType() {
        return micturitionType;
    }

    public PersonalSocialDetails micturitionType(MicturitionTypes micturitionTypes) {
        this.micturitionType = micturitionTypes;
        return this;
    }

    public void setMicturitionType(MicturitionTypes micturitionTypes) {
        this.micturitionType = micturitionTypes;
    }

    public BowelHabits getBowelHabit() {
        return bowelHabit;
    }

    public PersonalSocialDetails bowelHabit(BowelHabits bowelHabits) {
        this.bowelHabit = bowelHabits;
        return this;
    }

    public void setBowelHabit(BowelHabits bowelHabits) {
        this.bowelHabit = bowelHabits;
    }

    public Addictions getAddiction() {
        return addiction;
    }

    public PersonalSocialDetails addiction(Addictions addictions) {
        this.addiction = addictions;
        return this;
    }

    public void setAddiction(Addictions addictions) {
        this.addiction = addictions;
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
        PersonalSocialDetails personalSocialDetails = (PersonalSocialDetails) o;
        if (personalSocialDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personalSocialDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonalSocialDetails{" +
            "id=" + getId() +
            ", education='" + getEducation() + "'" +
            ", employment='" + getEmployment() + "'" +
            ", assistanceAtHome='" + getAssistanceAtHome() + "'" +
            "}";
    }
}
