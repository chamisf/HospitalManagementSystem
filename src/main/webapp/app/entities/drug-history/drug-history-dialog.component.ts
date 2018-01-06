import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DrugHistory } from './drug-history.model';
import { DrugHistoryPopupService } from './drug-history-popup.service';
import { DrugHistoryService } from './drug-history.service';
import { Patient, PatientService } from '../patient';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-drug-history-dialog',
    templateUrl: './drug-history-dialog.component.html'
})
export class DrugHistoryDialogComponent implements OnInit {

    drugHistory: DrugHistory;
    isSaving: boolean;

    patients: Patient[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private drugHistoryService: DrugHistoryService,
        private patientService: PatientService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.patientService
            .query({filter: 'drughistory-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.drugHistory.patient || !this.drugHistory.patient.id) {
                    this.patients = res.json;
                } else {
                    this.patientService
                        .find(this.drugHistory.patient.id)
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
        if (this.drugHistory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.drugHistoryService.update(this.drugHistory));
        } else {
            this.subscribeToSaveResponse(
                this.drugHistoryService.create(this.drugHistory));
        }
    }

    private subscribeToSaveResponse(result: Observable<DrugHistory>) {
        result.subscribe((res: DrugHistory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DrugHistory) {
        this.eventManager.broadcast({ name: 'drugHistoryListModification', content: 'OK'});
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
    selector: 'jhi-drug-history-popup',
    template: ''
})
export class DrugHistoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private drugHistoryPopupService: DrugHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.drugHistoryPopupService
                    .open(DrugHistoryDialogComponent as Component, params['id']);
            } else {
                this.drugHistoryPopupService
                    .open(DrugHistoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
