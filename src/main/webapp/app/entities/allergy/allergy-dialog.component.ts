import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Allergy } from './allergy.model';
import { AllergyPopupService } from './allergy-popup.service';
import { AllergyService } from './allergy.service';
import { AllergyType, AllergyTypeService } from '../allergy-type';
import { Patient, PatientService } from '../patient';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-allergy-dialog',
    templateUrl: './allergy-dialog.component.html'
})
export class AllergyDialogComponent implements OnInit {

    allergy: Allergy;
    isSaving: boolean;

    allergytypes: AllergyType[];

    patients: Patient[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private allergyService: AllergyService,
        private allergyTypeService: AllergyTypeService,
        private patientService: PatientService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.allergyTypeService
            .query({filter: 'allergy-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.allergy.allergyType || !this.allergy.allergyType.id) {
                    this.allergytypes = res.json;
                } else {
                    this.allergyTypeService
                        .find(this.allergy.allergyType.id)
                        .subscribe((subRes: AllergyType) => {
                            this.allergytypes = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.patientService.query()
            .subscribe((res: ResponseWrapper) => { this.patients = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.allergy.id !== undefined) {
            this.subscribeToSaveResponse(
                this.allergyService.update(this.allergy));
        } else {
            this.subscribeToSaveResponse(
                this.allergyService.create(this.allergy));
        }
    }

    private subscribeToSaveResponse(result: Observable<Allergy>) {
        result.subscribe((res: Allergy) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Allergy) {
        this.eventManager.broadcast({ name: 'allergyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAllergyTypeById(index: number, item: AllergyType) {
        return item.id;
    }

    trackPatientById(index: number, item: Patient) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-allergy-popup',
    template: ''
})
export class AllergyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private allergyPopupService: AllergyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.allergyPopupService
                    .open(AllergyDialogComponent as Component, params['id']);
            } else {
                this.allergyPopupService
                    .open(AllergyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
