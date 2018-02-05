import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Allergy } from './allergy.model';
import { AllergyPopupService } from './allergy-popup.service';
import { AllergyService } from './allergy.service';

@Component({
    selector: 'jhi-allergy-delete-dialog',
    templateUrl: './allergy-delete-dialog.component.html'
})
export class AllergyDeleteDialogComponent {

    allergy: Allergy;

    constructor(
        private allergyService: AllergyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.allergyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'allergyListModification',
                content: 'Deleted an allergy'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-allergy-delete-popup',
    template: ''
})
export class AllergyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private allergyPopupService: AllergyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.allergyPopupService
                .open(AllergyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
