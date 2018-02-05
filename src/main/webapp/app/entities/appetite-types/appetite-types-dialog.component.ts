import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AppetiteTypes } from './appetite-types.model';
import { AppetiteTypesPopupService } from './appetite-types-popup.service';
import { AppetiteTypesService } from './appetite-types.service';

@Component({
    selector: 'jhi-appetite-types-dialog',
    templateUrl: './appetite-types-dialog.component.html'
})
export class AppetiteTypesDialogComponent implements OnInit {

    appetiteTypes: AppetiteTypes;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private appetiteTypesService: AppetiteTypesService,
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
        if (this.appetiteTypes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.appetiteTypesService.update(this.appetiteTypes));
        } else {
            this.subscribeToSaveResponse(
                this.appetiteTypesService.create(this.appetiteTypes));
        }
    }

    private subscribeToSaveResponse(result: Observable<AppetiteTypes>) {
        result.subscribe((res: AppetiteTypes) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AppetiteTypes) {
        this.eventManager.broadcast({ name: 'appetiteTypesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-appetite-types-popup',
    template: ''
})
export class AppetiteTypesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appetiteTypesPopupService: AppetiteTypesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.appetiteTypesPopupService
                    .open(AppetiteTypesDialogComponent as Component, params['id']);
            } else {
                this.appetiteTypesPopupService
                    .open(AppetiteTypesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
