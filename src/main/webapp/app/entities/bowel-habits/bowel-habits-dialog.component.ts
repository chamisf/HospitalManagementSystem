import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BowelHabits } from './bowel-habits.model';
import { BowelHabitsPopupService } from './bowel-habits-popup.service';
import { BowelHabitsService } from './bowel-habits.service';

@Component({
    selector: 'jhi-bowel-habits-dialog',
    templateUrl: './bowel-habits-dialog.component.html'
})
export class BowelHabitsDialogComponent implements OnInit {

    bowelHabits: BowelHabits;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private bowelHabitsService: BowelHabitsService,
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
        if (this.bowelHabits.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bowelHabitsService.update(this.bowelHabits));
        } else {
            this.subscribeToSaveResponse(
                this.bowelHabitsService.create(this.bowelHabits));
        }
    }

    private subscribeToSaveResponse(result: Observable<BowelHabits>) {
        result.subscribe((res: BowelHabits) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: BowelHabits) {
        this.eventManager.broadcast({ name: 'bowelHabitsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-bowel-habits-popup',
    template: ''
})
export class BowelHabitsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bowelHabitsPopupService: BowelHabitsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bowelHabitsPopupService
                    .open(BowelHabitsDialogComponent as Component, params['id']);
            } else {
                this.bowelHabitsPopupService
                    .open(BowelHabitsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
