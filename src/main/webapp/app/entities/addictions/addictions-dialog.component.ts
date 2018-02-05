import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Addictions } from './addictions.model';
import { AddictionsPopupService } from './addictions-popup.service';
import { AddictionsService } from './addictions.service';

@Component({
    selector: 'jhi-addictions-dialog',
    templateUrl: './addictions-dialog.component.html'
})
export class AddictionsDialogComponent implements OnInit {

    addictions: Addictions;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private addictionsService: AddictionsService,
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
        if (this.addictions.id !== undefined) {
            this.subscribeToSaveResponse(
                this.addictionsService.update(this.addictions));
        } else {
            this.subscribeToSaveResponse(
                this.addictionsService.create(this.addictions));
        }
    }

    private subscribeToSaveResponse(result: Observable<Addictions>) {
        result.subscribe((res: Addictions) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Addictions) {
        this.eventManager.broadcast({ name: 'addictionsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-addictions-popup',
    template: ''
})
export class AddictionsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private addictionsPopupService: AddictionsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.addictionsPopupService
                    .open(AddictionsDialogComponent as Component, params['id']);
            } else {
                this.addictionsPopupService
                    .open(AddictionsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
