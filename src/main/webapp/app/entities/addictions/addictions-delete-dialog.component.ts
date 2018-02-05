import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Addictions } from './addictions.model';
import { AddictionsPopupService } from './addictions-popup.service';
import { AddictionsService } from './addictions.service';

@Component({
    selector: 'jhi-addictions-delete-dialog',
    templateUrl: './addictions-delete-dialog.component.html'
})
export class AddictionsDeleteDialogComponent {

    addictions: Addictions;

    constructor(
        private addictionsService: AddictionsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.addictionsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'addictionsListModification',
                content: 'Deleted an addictions'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-addictions-delete-popup',
    template: ''
})
export class AddictionsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private addictionsPopupService: AddictionsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.addictionsPopupService
                .open(AddictionsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
