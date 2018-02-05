import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PersonalSocialDetails } from './personal-social-details.model';
import { PersonalSocialDetailsPopupService } from './personal-social-details-popup.service';
import { PersonalSocialDetailsService } from './personal-social-details.service';

@Component({
    selector: 'jhi-personal-social-details-delete-dialog',
    templateUrl: './personal-social-details-delete-dialog.component.html'
})
export class PersonalSocialDetailsDeleteDialogComponent {

    personalSocialDetails: PersonalSocialDetails;

    constructor(
        private personalSocialDetailsService: PersonalSocialDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personalSocialDetailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'personalSocialDetailsListModification',
                content: 'Deleted an personalSocialDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-personal-social-details-delete-popup',
    template: ''
})
export class PersonalSocialDetailsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personalSocialDetailsPopupService: PersonalSocialDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.personalSocialDetailsPopupService
                .open(PersonalSocialDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
