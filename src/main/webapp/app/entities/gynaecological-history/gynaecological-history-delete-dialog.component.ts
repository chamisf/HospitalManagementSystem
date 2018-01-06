import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GynaecologicalHistory } from './gynaecological-history.model';
import { GynaecologicalHistoryPopupService } from './gynaecological-history-popup.service';
import { GynaecologicalHistoryService } from './gynaecological-history.service';

@Component({
    selector: 'jhi-gynaecological-history-delete-dialog',
    templateUrl: './gynaecological-history-delete-dialog.component.html'
})
export class GynaecologicalHistoryDeleteDialogComponent {

    gynaecologicalHistory: GynaecologicalHistory;

    constructor(
        private gynaecologicalHistoryService: GynaecologicalHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gynaecologicalHistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'gynaecologicalHistoryListModification',
                content: 'Deleted an gynaecologicalHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-gynaecological-history-delete-popup',
    template: ''
})
export class GynaecologicalHistoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gynaecologicalHistoryPopupService: GynaecologicalHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.gynaecologicalHistoryPopupService
                .open(GynaecologicalHistoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
