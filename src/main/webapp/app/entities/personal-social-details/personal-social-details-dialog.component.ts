import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PersonalSocialDetails } from './personal-social-details.model';
import { PersonalSocialDetailsPopupService } from './personal-social-details-popup.service';
import { PersonalSocialDetailsService } from './personal-social-details.service';
import { Patient, PatientService } from '../patient';
import { SleepTypes, SleepTypesService } from '../sleep-types';
import { AppetiteTypes, AppetiteTypesService } from '../appetite-types';
import { MicturitionTypes, MicturitionTypesService } from '../micturition-types';
import { BowelHabits, BowelHabitsService } from '../bowel-habits';
import { Addictions, AddictionsService } from '../addictions';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-personal-social-details-dialog',
    templateUrl: './personal-social-details-dialog.component.html'
})
export class PersonalSocialDetailsDialogComponent implements OnInit {

    personalSocialDetails: PersonalSocialDetails;
    isSaving: boolean;

    patients: Patient[];

    sleeptypes: SleepTypes[];

    appetitetypes: AppetiteTypes[];

    micturitiontypes: MicturitionTypes[];

    bowelhabits: BowelHabits[];

    addictions: Addictions[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private personalSocialDetailsService: PersonalSocialDetailsService,
        private patientService: PatientService,
        private sleepTypesService: SleepTypesService,
        private appetiteTypesService: AppetiteTypesService,
        private micturitionTypesService: MicturitionTypesService,
        private bowelHabitsService: BowelHabitsService,
        private addictionsService: AddictionsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.patientService
            .query({filter: 'personalsocialdetails-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.personalSocialDetails.patient || !this.personalSocialDetails.patient.id) {
                    this.patients = res.json;
                } else {
                    this.patientService
                        .find(this.personalSocialDetails.patient.id)
                        .subscribe((subRes: Patient) => {
                            this.patients = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.sleepTypesService
            .query({filter: 'personalsocialdetails-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.personalSocialDetails.sleepType || !this.personalSocialDetails.sleepType.id) {
                    this.sleeptypes = res.json;
                } else {
                    this.sleepTypesService
                        .find(this.personalSocialDetails.sleepType.id)
                        .subscribe((subRes: SleepTypes) => {
                            this.sleeptypes = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.appetiteTypesService
            .query({filter: 'personalsocialdetails-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.personalSocialDetails.appetiteType || !this.personalSocialDetails.appetiteType.id) {
                    this.appetitetypes = res.json;
                } else {
                    this.appetiteTypesService
                        .find(this.personalSocialDetails.appetiteType.id)
                        .subscribe((subRes: AppetiteTypes) => {
                            this.appetitetypes = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.micturitionTypesService
            .query({filter: 'personalsocialdetails-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.personalSocialDetails.micturitionType || !this.personalSocialDetails.micturitionType.id) {
                    this.micturitiontypes = res.json;
                } else {
                    this.micturitionTypesService
                        .find(this.personalSocialDetails.micturitionType.id)
                        .subscribe((subRes: MicturitionTypes) => {
                            this.micturitiontypes = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.bowelHabitsService
            .query({filter: 'personalsocialdetails-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.personalSocialDetails.bowelHabit || !this.personalSocialDetails.bowelHabit.id) {
                    this.bowelhabits = res.json;
                } else {
                    this.bowelHabitsService
                        .find(this.personalSocialDetails.bowelHabit.id)
                        .subscribe((subRes: BowelHabits) => {
                            this.bowelhabits = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.addictionsService
            .query({filter: 'personalsocialdetails-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.personalSocialDetails.addiction || !this.personalSocialDetails.addiction.id) {
                    this.addictions = res.json;
                } else {
                    this.addictionsService
                        .find(this.personalSocialDetails.addiction.id)
                        .subscribe((subRes: Addictions) => {
                            this.addictions = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personalSocialDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personalSocialDetailsService.update(this.personalSocialDetails));
        } else {
            this.subscribeToSaveResponse(
                this.personalSocialDetailsService.create(this.personalSocialDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<PersonalSocialDetails>) {
        result.subscribe((res: PersonalSocialDetails) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PersonalSocialDetails) {
        this.eventManager.broadcast({ name: 'personalSocialDetailsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPatientById(index: number, item: Patient) {
        return item.id;
    }

    trackSleepTypesById(index: number, item: SleepTypes) {
        return item.id;
    }

    trackAppetiteTypesById(index: number, item: AppetiteTypes) {
        return item.id;
    }

    trackMicturitionTypesById(index: number, item: MicturitionTypes) {
        return item.id;
    }

    trackBowelHabitsById(index: number, item: BowelHabits) {
        return item.id;
    }

    trackAddictionsById(index: number, item: Addictions) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-personal-social-details-popup',
    template: ''
})
export class PersonalSocialDetailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personalSocialDetailsPopupService: PersonalSocialDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.personalSocialDetailsPopupService
                    .open(PersonalSocialDetailsDialogComponent as Component, params['id']);
            } else {
                this.personalSocialDetailsPopupService
                    .open(PersonalSocialDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
