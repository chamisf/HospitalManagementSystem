import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FamilyHistory } from './family-history.model';
import { FamilyHistoryService } from './family-history.service';

@Component({
    selector: 'jhi-family-history-detail',
    templateUrl: './family-history-detail.component.html'
})
export class FamilyHistoryDetailComponent implements OnInit, OnDestroy {

    familyHistory: FamilyHistory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private familyHistoryService: FamilyHistoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFamilyHistories();
    }

    load(id) {
        this.familyHistoryService.find(id).subscribe((familyHistory) => {
            this.familyHistory = familyHistory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFamilyHistories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'familyHistoryListModification',
            (response) => this.load(this.familyHistory.id)
        );
    }
}
