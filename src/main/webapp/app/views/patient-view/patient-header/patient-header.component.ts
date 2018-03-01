import {Component, Input, OnInit} from '@angular/core';
import {Patient, PatientService} from "../../../entities/patient";

@Component({
    selector: 'jhi-patient-header',
    templateUrl: './patient-header.component.html',
    styles: []
})
export class PatientHeaderComponent implements OnInit {

    @Input()
    private patientId: number;
    private currentPatient: Patient;

    constructor(private patientService: PatientService) {
    }

    ngOnInit() {
        this.load(this.patientId);
    }

    load(id) {
        this.patientService.find(id).subscribe((patient) => {
            this.currentPatient = patient;
        });
    }
}
