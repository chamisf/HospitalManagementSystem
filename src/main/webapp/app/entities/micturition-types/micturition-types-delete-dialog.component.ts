import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MicturitionTypes } from './micturition-types.model';
import { MicturitionTypesPopupService } from './micturition-types-popup.service';
import { MicturitionTypesService } from './micturition-types.service';

@Component({
    selector: 'jhi-micturition-types-delete-dialog',
    templateUrl: './micturition-types-delete-dialog.component.html'
})
export class MicturitionTypesDeleteDialogComponent {

    micturitionTypes: MicturitionTypes;

    constructor(
        private micturitionTypesService: MicturitionTypesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.micturitionTypesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'micturitionTypesListModification',
                content: 'Deleted an micturitionTypes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-micturition-types-delete-popup',
    template: ''
})
export class MicturitionTypesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private micturitionTypesPopupService: MicturitionTypesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.micturitionTypesPopupService
                .open(MicturitionTypesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
