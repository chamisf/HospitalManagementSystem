import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SleepTypes } from './sleep-types.model';
import { SleepTypesPopupService } from './sleep-types-popup.service';
import { SleepTypesService } from './sleep-types.service';

@Component({
    selector: 'jhi-sleep-types-delete-dialog',
    templateUrl: './sleep-types-delete-dialog.component.html'
})
export class SleepTypesDeleteDialogComponent {

    sleepTypes: SleepTypes;

    constructor(
        private sleepTypesService: SleepTypesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sleepTypesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sleepTypesListModification',
                content: 'Deleted an sleepTypes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sleep-types-delete-popup',
    template: ''
})
export class SleepTypesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sleepTypesPopupService: SleepTypesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sleepTypesPopupService
                .open(SleepTypesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
