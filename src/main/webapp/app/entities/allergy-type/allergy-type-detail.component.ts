import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AllergyType } from './allergy-type.model';
import { AllergyTypeService } from './allergy-type.service';

@Component({
    selector: 'jhi-allergy-type-detail',
    templateUrl: './allergy-type-detail.component.html'
})
export class AllergyTypeDetailComponent implements OnInit, OnDestroy {

    allergyType: AllergyType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private allergyTypeService: AllergyTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAllergyTypes();
    }

    load(id) {
        this.allergyTypeService.find(id).subscribe((allergyType) => {
            this.allergyType = allergyType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAllergyTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'allergyTypeListModification',
            (response) => this.load(this.allergyType.id)
        );
    }
}
