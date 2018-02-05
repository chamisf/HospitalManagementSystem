import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BowelHabits } from './bowel-habits.model';
import { BowelHabitsPopupService } from './bowel-habits-popup.service';
import { BowelHabitsService } from './bowel-habits.service';

@Component({
    selector: 'jhi-bowel-habits-delete-dialog',
    templateUrl: './bowel-habits-delete-dialog.component.html'
})
export class BowelHabitsDeleteDialogComponent {

    bowelHabits: BowelHabits;

    constructor(
        private bowelHabitsService: BowelHabitsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bowelHabitsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bowelHabitsListModification',
                content: 'Deleted an bowelHabits'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bowel-habits-delete-popup',
    template: ''
})
export class BowelHabitsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bowelHabitsPopupService: BowelHabitsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bowelHabitsPopupService
                .open(BowelHabitsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
