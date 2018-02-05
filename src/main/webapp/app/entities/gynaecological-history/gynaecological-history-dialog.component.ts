import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GynaecologicalHistory } from './gynaecological-history.model';
import { GynaecologicalHistoryPopupService } from './gynaecological-history-popup.service';
import { GynaecologicalHistoryService } from './gynaecological-history.service';
import { Patient, PatientService } from '../patient';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-gynaecological-history-dialog',
    templateUrl: './gynaecological-history-dialog.component.html'
})
export class GynaecologicalHistoryDialogComponent implements OnInit {

    gynaecologicalHistory: GynaecologicalHistory;
    isSaving: boolean;

    patients: Patient[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private gynaecologicalHistoryService: GynaecologicalHistoryService,
        private patientService: PatientService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.patientService
            .query({filter: 'gynaecologicalhistory-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.gynaecologicalHistory.patient || !this.gynaecologicalHistory.patient.id) {
                    this.patients = res.json;
                } else {
                    this.patientService
                        .find(this.gynaecologicalHistory.patient.id)
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
        if (this.gynaecologicalHistory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.gynaecologicalHistoryService.update(this.gynaecologicalHistory));
        } else {
            this.subscribeToSaveResponse(
                this.gynaecologicalHistoryService.create(this.gynaecologicalHistory));
        }
    }

    private subscribeToSaveResponse(result: Observable<GynaecologicalHistory>) {
        result.subscribe((res: GynaecologicalHistory) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: GynaecologicalHistory) {
        this.eventManager.broadcast({ name: 'gynaecologicalHistoryListModification', content: 'OK'});
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
    selector: 'jhi-gynaecological-history-popup',
    template: ''
})
export class GynaecologicalHistoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gynaecologicalHistoryPopupService: GynaecologicalHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.gynaecologicalHistoryPopupService
                    .open(GynaecologicalHistoryDialogComponent as Component, params['id']);
            } else {
                this.gynaecologicalHistoryPopupService
                    .open(GynaecologicalHistoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
