import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AllergyType } from './allergy-type.model';
import { AllergyTypePopupService } from './allergy-type-popup.service';
import { AllergyTypeService } from './allergy-type.service';

@Component({
    selector: 'jhi-allergy-type-dialog',
    templateUrl: './allergy-type-dialog.component.html'
})
export class AllergyTypeDialogComponent implements OnInit {

    allergyType: AllergyType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private allergyTypeService: AllergyTypeService,
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
        if (this.allergyType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.allergyTypeService.update(this.allergyType));
        } else {
            this.subscribeToSaveResponse(
                this.allergyTypeService.create(this.allergyType));
        }
    }

    private subscribeToSaveResponse(result: Observable<AllergyType>) {
        result.subscribe((res: AllergyType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AllergyType) {
        this.eventManager.broadcast({ name: 'allergyTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-allergy-type-popup',
    template: ''
})
export class AllergyTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private allergyTypePopupService: AllergyTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.allergyTypePopupService
                    .open(AllergyTypeDialogComponent as Component, params['id']);
            } else {
                this.allergyTypePopupService
                    .open(AllergyTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
