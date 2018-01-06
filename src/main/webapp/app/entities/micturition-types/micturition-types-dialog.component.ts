import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MicturitionTypes } from './micturition-types.model';
import { MicturitionTypesPopupService } from './micturition-types-popup.service';
import { MicturitionTypesService } from './micturition-types.service';

@Component({
    selector: 'jhi-micturition-types-dialog',
    templateUrl: './micturition-types-dialog.component.html'
})
export class MicturitionTypesDialogComponent implements OnInit {

    micturitionTypes: MicturitionTypes;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private micturitionTypesService: MicturitionTypesService,
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
        if (this.micturitionTypes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.micturitionTypesService.update(this.micturitionTypes));
        } else {
            this.subscribeToSaveResponse(
                this.micturitionTypesService.create(this.micturitionTypes));
        }
    }

    private subscribeToSaveResponse(result: Observable<MicturitionTypes>) {
        result.subscribe((res: MicturitionTypes) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MicturitionTypes) {
        this.eventManager.broadcast({ name: 'micturitionTypesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-micturition-types-popup',
    template: ''
})
export class MicturitionTypesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private micturitionTypesPopupService: MicturitionTypesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.micturitionTypesPopupService
                    .open(MicturitionTypesDialogComponent as Component, params['id']);
            } else {
                this.micturitionTypesPopupService
                    .open(MicturitionTypesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
