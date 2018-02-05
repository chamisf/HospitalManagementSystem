import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FamilyHistory } from './family-history.model';
import { FamilyHistoryPopupService } from './family-history-popup.service';
import { FamilyHistoryService } from './family-history.service';
import { Patient, PatientService } from '../patient';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-family-history-dialog',
    templateUrl: './family-history-dialog.component.html'
})
export class FamilyHistoryDialogComponent implements OnInit {

    familyHistory: FamilyHistory;
    isSaving: boolean;

    patients: Patient[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private familyHistoryService: FamilyHistoryService,
        private patientService: PatientService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.patientService
            .query({filter: 'familyhistory-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.familyHistory.patient || !this.familyHistory.patient.id) {
                    this.patients = res.json;
                } else {
                    this.patientService
                        .find(this.familyHistory.patient.id)
                        .subscribe((subRes: Patient) => {
                            this.patients = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.familyHistory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.familyHistoryService.update(this.familyHistory));
        } else {
            this.subscribeToSaveResponse(
                this.familyHistoryService.create(this.familyHistory));
        }
    }

    private subscribeToSaveResponse(result: Observable<FamilyHistory>) {
        result.subscribe((res: FamilyHistory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FamilyHistory) {
        this.eventManager.broadcast({ name: 'familyHistoryListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-family-history-popup',
    template: ''
})
export class FamilyHistoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private familyHistoryPopupService: FamilyHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.familyHistoryPopupService
                    .open(FamilyHistoryDialogComponent as Component, params['id']);
            } else {
                this.familyHistoryPopupService
                    .open(FamilyHistoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
