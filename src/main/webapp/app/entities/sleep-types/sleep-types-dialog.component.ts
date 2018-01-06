import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SleepTypes } from './sleep-types.model';
import { SleepTypesPopupService } from './sleep-types-popup.service';
import { SleepTypesService } from './sleep-types.service';

@Component({
    selector: 'jhi-sleep-types-dialog',
    templateUrl: './sleep-types-dialog.component.html'
})
export class SleepTypesDialogComponent implements OnInit {

    sleepTypes: SleepTypes;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private sleepTypesService: SleepTypesService,
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
        if (this.sleepTypes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sleepTypesService.update(this.sleepTypes));
        } else {
            this.subscribeToSaveResponse(
                this.sleepTypesService.create(this.sleepTypes));
        }
    }

    private subscribeToSaveResponse(result: Observable<SleepTypes>) {
        result.subscribe((res: SleepTypes) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SleepTypes) {
        this.eventManager.broadcast({ name: 'sleepTypesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-sleep-types-popup',
    template: ''
})
export class SleepTypesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sleepTypesPopupService: SleepTypesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sleepTypesPopupService
                    .open(SleepTypesDialogComponent as Component, params['id']);
            } else {
                this.sleepTypesPopupService
                    .open(SleepTypesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
