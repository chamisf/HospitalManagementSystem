import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AppetiteTypes } from './appetite-types.model';
import { AppetiteTypesService } from './appetite-types.service';

@Component({
    selector: 'jhi-appetite-types-detail',
    templateUrl: './appetite-types-detail.component.html'
})
export class AppetiteTypesDetailComponent implements OnInit, OnDestroy {

    appetiteTypes: AppetiteTypes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private appetiteTypesService: AppetiteTypesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAppetiteTypes();
    }

    load(id) {
        this.appetiteTypesService.find(id).subscribe((appetiteTypes) => {
            this.appetiteTypes = appetiteTypes;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAppetiteTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'appetiteTypesListModification',
            (response) => this.load(this.appetiteTypes.id)
        );
    }
}
