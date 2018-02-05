import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DrugHistory } from './drug-history.model';
import { DrugHistoryPopupService } from './drug-history-popup.service';
import { DrugHistoryService } from './drug-history.service';

@Component({
    selector: 'jhi-drug-history-delete-dialog',
    templateUrl: './drug-history-delete-dialog.component.html'
})
export class DrugHistoryDeleteDialogComponent {

    drugHistory: DrugHistory;

    constructor(
        private drugHistoryService: DrugHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.drugHistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'drugHistoryListModification',
                content: 'Deleted an drugHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-drug-history-delete-popup',
    template: ''
})
export class DrugHistoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private drugHistoryPopupService: DrugHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.drugHistoryPopupService
                .open(DrugHistoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
