import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FamilyHistory } from './family-history.model';
import { FamilyHistoryPopupService } from './family-history-popup.service';
import { FamilyHistoryService } from './family-history.service';

@Component({
    selector: 'jhi-family-history-delete-dialog',
    templateUrl: './family-history-delete-dialog.component.html'
})
export class FamilyHistoryDeleteDialogComponent {

    familyHistory: FamilyHistory;

    constructor(
        private familyHistoryService: FamilyHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.familyHistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'familyHistoryListModification',
                content: 'Deleted an familyHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-family-history-delete-popup',
    template: ''
})
export class FamilyHistoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private familyHistoryPopupService: FamilyHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.familyHistoryPopupService
                .open(FamilyHistoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
