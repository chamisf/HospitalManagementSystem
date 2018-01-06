import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Allergy } from './allergy.model';
import { AllergyService } from './allergy.service';

@Component({
    selector: 'jhi-allergy-detail',
    templateUrl: './allergy-detail.component.html'
})
export class AllergyDetailComponent implements OnInit, OnDestroy {

    allergy: Allergy;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private allergyService: AllergyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAllergies();
    }

    load(id) {
        this.allergyService.find(id).subscribe((allergy) => {
            this.allergy = allergy;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAllergies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'allergyListModification',
            (response) => this.load(this.allergy.id)
        );
    }
}
