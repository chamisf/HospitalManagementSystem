import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DrugHistory } from './drug-history.model';
import { DrugHistoryService } from './drug-history.service';

@Component({
    selector: 'jhi-drug-history-detail',
    templateUrl: './drug-history-detail.component.html'
})
export class DrugHistoryDetailComponent implements OnInit, OnDestroy {

    drugHistory: DrugHistory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private drugHistoryService: DrugHistoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDrugHistories();
    }

    load(id) {
        this.drugHistoryService.find(id).subscribe((drugHistory) => {
            this.drugHistory = drugHistory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDrugHistories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'drugHistoryListModification',
            (response) => this.load(this.drugHistory.id)
        );
    }
}
