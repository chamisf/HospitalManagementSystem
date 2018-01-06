import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AppetiteTypes } from './appetite-types.model';
import { AppetiteTypesPopupService } from './appetite-types-popup.service';
import { AppetiteTypesService } from './appetite-types.service';

@Component({
    selector: 'jhi-appetite-types-delete-dialog',
    templateUrl: './appetite-types-delete-dialog.component.html'
})
export class AppetiteTypesDeleteDialogComponent {

    appetiteTypes: AppetiteTypes;

    constructor(
        private appetiteTypesService: AppetiteTypesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.appetiteTypesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'appetiteTypesListModification',
                content: 'Deleted an appetiteTypes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-appetite-types-delete-popup',
    template: ''
})
export class AppetiteTypesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private appetiteTypesPopupService: AppetiteTypesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.appetiteTypesPopupService
                .open(AppetiteTypesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
