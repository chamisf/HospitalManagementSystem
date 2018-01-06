import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SleepTypes } from './sleep-types.model';
import { SleepTypesService } from './sleep-types.service';

@Component({
    selector: 'jhi-sleep-types-detail',
    templateUrl: './sleep-types-detail.component.html'
})
export class SleepTypesDetailComponent implements OnInit, OnDestroy {

    sleepTypes: SleepTypes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sleepTypesService: SleepTypesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSleepTypes();
    }

    load(id) {
        this.sleepTypesService.find(id).subscribe((sleepTypes) => {
            this.sleepTypes = sleepTypes;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSleepTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sleepTypesListModification',
            (response) => this.load(this.sleepTypes.id)
        );
    }
}
