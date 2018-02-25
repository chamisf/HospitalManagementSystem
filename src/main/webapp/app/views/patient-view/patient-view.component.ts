import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs/Rx";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'jhi-patient-view',
    templateUrl: './patient-view.component.html',
    styleUrls: ['patient-view.scss']
})
export class PatientViewComponent implements OnInit {

    private subscription: Subscription;
    private id: number;

    constructor(private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.id = params['id'];
        });
    }
}
