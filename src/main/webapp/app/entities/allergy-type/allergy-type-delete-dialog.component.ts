import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AllergyType } from './allergy-type.model';
import { AllergyTypePopupService } from './allergy-type-popup.service';
import { AllergyTypeService } from './allergy-type.service';

@Component({
    selector: 'jhi-allergy-type-delete-dialog',
    templateUrl: './allergy-type-delete-dialog.component.html'
})
export class AllergyTypeDeleteDialogComponent {

    allergyType: AllergyType;

    constructor(
        private allergyTypeService: AllergyTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.allergyTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'allergyTypeListModification',
                content: 'Deleted an allergyType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-allergy-type-delete-popup',
    template: ''
})
export class AllergyTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private allergyTypePopupService: AllergyTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.allergyTypePopupService
                .open(AllergyTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
