import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Medicine } from './medicine.model';
import { MedicinePopupService } from './medicine-popup.service';
import { MedicineService } from './medicine.service';

@Component({
    selector: 'jhi-medicine-delete-dialog',
    templateUrl: './medicine-delete-dialog.component.html'
})
export class MedicineDeleteDialogComponent {

    medicine: Medicine;

    constructor(
        private medicineService: MedicineService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.medicineService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'medicineListModification',
                content: 'Deleted an medicine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-medicine-delete-popup',
    template: ''
})
export class MedicineDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private medicinePopupService: MedicinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.medicinePopupService
                .open(MedicineDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
