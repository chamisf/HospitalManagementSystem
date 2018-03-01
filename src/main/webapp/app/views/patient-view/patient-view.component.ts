import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs/Rx";
import {ActivatedRoute} from "@angular/router";
import {Patient, PatientService} from "../../entities/patient";

@Component({
    selector: 'jhi-patient-view',
    templateUrl: './patient-view.component.html',
    styleUrls: ['patient-view.scss']
})
export class PatientViewComponent implements OnInit {

    private subscription: Subscription;
    private id: number;
    private currentPatient: Patient;

    constructor(private route: ActivatedRoute,
                private patientService: PatientService) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.id = params['id'];
            // this.load(this.id);
        });
    }

    load(id) {
        this.patientService.find(id).subscribe((patient) => {
            this.currentPatient = patient;
        });
    }
}
