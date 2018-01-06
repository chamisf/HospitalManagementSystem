import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Medication } from './medication.model';
import { MedicationPopupService } from './medication-popup.service';
import { MedicationService } from './medication.service';
import { DrugHistory, DrugHistoryService } from '../drug-history';
import { Medicine, MedicineService } from '../medicine';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-medication-dialog',
    templateUrl: './medication-dialog.component.html'
})
export class MedicationDialogComponent implements OnInit {

    medication: Medication;
    isSaving: boolean;

    drughistories: DrugHistory[];

    medicines: Medicine[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private medicationService: MedicationService,
        private drugHistoryService: DrugHistoryService,
        private medicineService: MedicineService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.drugHistoryService.query()
            .subscribe((res: ResponseWrapper) => { this.drughistories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.medicineService
            .query({filter: 'medication-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.medication.medicine || !this.medication.medicine.id) {
                    this.medicines = res.json;
                } else {
                    this.medicineService
                        .find(this.medication.medicine.id)
                        .subscribe((subRes: Medicine) => {
                            this.medicines = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.medication.id !== undefined) {
            this.subscribeToSaveResponse(
                this.medicationService.update(this.medication));
        } else {
            this.subscribeToSaveResponse(
                this.medicationService.create(this.medication));
        }
    }

    private subscribeToSaveResponse(result: Observable<Medication>) {
        result.subscribe((res: Medication) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Medication) {
        this.eventManager.broadcast({ name: 'medicationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDrugHistoryById(index: number, item: DrugHistory) {
        return item.id;
    }

    trackMedicineById(index: number, item: Medicine) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-medication-popup',
    template: ''
})
export class MedicationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private medicationPopupService: MedicationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.medicationPopupService
                    .open(MedicationDialogComponent as Component, params['id']);
            } else {
                this.medicationPopupService
                    .open(MedicationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
