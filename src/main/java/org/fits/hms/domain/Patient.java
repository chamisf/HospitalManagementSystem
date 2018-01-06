package org.fits.hms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.fits.hms.domain.enumeration.Gender;

import org.fits.hms.domain.enumeration.MaritalStatus;

import org.fits.hms.domain.enumeration.BloodGroup;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @NotNull
    @Column(name = "hospital_number", nullable = false)
    private String hospitalNumber;

    @NotNull
    @Column(name = "registered_date", nullable = false)
    private LocalDate registeredDate;

    @Column(name = "age")
    private Integer age;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "patient_height")
    private String patientHeight;

    @Column(name = "patient_wight")
    private Integer patientWight;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Pattern(regexp = "[0-9]{9}[vVxX]")
    @Column(name = "nic_number")
    private String nicNumber;

    @Pattern(regexp = "(0|\\+94)[0-9]{9}")
    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Pattern(regexp = "(0|\\+94)[0-9]{9}")
    @Column(name = "emergency_contact_number")
    private String emergencyContactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "occupation")
    private String occupation;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroup bloodGroup;

    @Column(name = "guardian_name")
    private String guardianName;

    @Column(name = "guardian_address")
    private String guardianAddress;

    @Column(name = "referring_physician")
    private String referringPhysician;

    @Column(name = "referring_hospital")
    private String referringHospital;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public Patient patientName(String patientName) {
        this.patientName = patientName;
        return this;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getHospitalNumber() {
        return hospitalNumber;
    }

    public Patient hospitalNumber(String hospitalNumber) {
        this.hospitalNumber = hospitalNumber;
        return this;
    }

    public void setHospitalNumber(String hospitalNumber) {
        this.hospitalNumber = hospitalNumber;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public Patient registeredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
        return this;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Integer getAge() {
        return age;
    }

    public Patient age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Patient birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public Patient gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPatientHeight() {
        return patientHeight;
    }

    public Patient patientHeight(String patientHeight) {
        this.patientHeight = patientHeight;
        return this;
    }

    public void setPatientHeight(String patientHeight) {
        this.patientHeight = patientHeight;
    }

    public Integer getPatientWight() {
        return patientWight;
    }

    public Patient patientWight(Integer patientWight) {
        this.patientWight = patientWight;
        return this;
    }

    public void setPatientWight(Integer patientWight) {
        this.patientWight = patientWight;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public Patient maritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNicNumber() {
        return nicNumber;
    }

    public Patient nicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
        return this;
    }

    public void setNicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public Patient telephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public Patient emergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
        return this;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getEmail() {
        return email;
    }

    public Patient email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public Patient address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public Patient occupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public Patient bloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
        return this;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public Patient guardianName(String guardianName) {
        this.guardianName = guardianName;
        return this;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getGuardianAddress() {
        return guardianAddress;
    }

    public Patient guardianAddress(String guardianAddress) {
        this.guardianAddress = guardianAddress;
        return this;
    }

    public void setGuardianAddress(String guardianAddress) {
        this.guardianAddress = guardianAddress;
    }

    public String getReferringPhysician() {
        return referringPhysician;
    }

    public Patient referringPhysician(String referringPhysician) {
        this.referringPhysician = referringPhysician;
        return this;
    }

    public void setReferringPhysician(String referringPhysician) {
        this.referringPhysician = referringPhysician;
    }

    public String getReferringHospital() {
        return referringHospital;
    }

    public Patient referringHospital(String referringHospital) {
        this.referringHospital = referringHospital;
        return this;
    }

    public void setReferringHospital(String referringHospital) {
        this.referringHospital = referringHospital;
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
        Patient patient = (Patient) o;
        if (patient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", patientName='" + getPatientName() + "'" +
            ", hospitalNumber='" + getHospitalNumber() + "'" +
            ", registeredDate='" + getRegisteredDate() + "'" +
            ", age=" + getAge() +
            ", birthDate='" + getBirthDate() + "'" +
            ", gender='" + getGender() + "'" +
            ", patientHeight='" + getPatientHeight() + "'" +
            ", patientWight=" + getPatientWight() +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", nicNumber='" + getNicNumber() + "'" +
            ", telephoneNumber='" + getTelephoneNumber() + "'" +
            ", emergencyContactNumber='" + getEmergencyContactNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", bloodGroup='" + getBloodGroup() + "'" +
            ", guardianName='" + getGuardianName() + "'" +
            ", guardianAddress='" + getGuardianAddress() + "'" +
            ", referringPhysician='" + getReferringPhysician() + "'" +
            ", referringHospital='" + getReferringHospital() + "'" +
            "}";
    }
}
