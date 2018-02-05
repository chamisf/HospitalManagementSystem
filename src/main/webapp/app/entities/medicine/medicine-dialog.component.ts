import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Medicine } from './medicine.model';
import { MedicinePopupService } from './medicine-popup.service';
import { MedicineService } from './medicine.service';

@Component({
    selector: 'jhi-medicine-dialog',
    templateUrl: './medicine-dialog.component.html'
})
export class MedicineDialogComponent implements OnInit {

    medicine: Medicine;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private medicineService: MedicineService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.medicine.id !== undefined) {
            this.subscribeToSaveResponse(
                this.medicineService.update(this.medicine));
        } else {
            this.subscribeToSaveResponse(
                this.medicineService.create(this.medicine));
        }
    }

    private subscribeToSaveResponse(result: Observable<Medicine>) {
        result.subscribe((res: Medicine) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Medicine) {
        this.eventManager.broadcast({ name: 'medicineListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-medicine-popup',
    template: ''
})
export class MedicinePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private medicinePopupService: MedicinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.medicinePopupService
                    .open(MedicineDialogComponent as Component, params['id']);
            } else {
                this.medicinePopupService
                    .open(MedicineDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
