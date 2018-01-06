import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Medicine } from './medicine.model';
import { MedicineService } from './medicine.service';

@Component({
    selector: 'jhi-medicine-detail',
    templateUrl: './medicine-detail.component.html'
})
export class MedicineDetailComponent implements OnInit, OnDestroy {

    medicine: Medicine;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private medicineService: MedicineService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMedicines();
    }

    load(id) {
        this.medicineService.find(id).subscribe((medicine) => {
            this.medicine = medicine;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMedicines() {
        this.eventSubscriber = this.eventManager.subscribe(
            'medicineListModification',
            (response) => this.load(this.medicine.id)
        );
    }
}
