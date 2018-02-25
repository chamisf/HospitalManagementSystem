import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {GynaecologicalHistory} from './gynaecological-history.model';
import {GynaecologicalHistoryService} from './gynaecological-history.service';

@Component({
    selector: 'jhi-gynaecological-history-detail',
    templateUrl: './gynaecological-history-detail.component.html'
})
export class GynaecologicalHistoryDetailComponent implements OnInit, OnDestroy {

    gynaecologicalHistory: GynaecologicalHistory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager,
                private gynaecologicalHistoryService: GynaecologicalHistoryService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this.route.parent.params.subscribe((params) => {
            this.loadByPatientId(params['id']);
        });
        this.registerChangeInGynaecologicalHistories();
    }

    private loadByPatientId(id) {
        this.gynaecologicalHistoryService.findByPatientId(id).subscribe((gynaecologicalHistory) => {
            this.gynaecologicalHistory = gynaecologicalHistory;
        });

    }

    load(id) {
        this.gynaecologicalHistoryService.find(id).subscribe((gynaecologicalHistory) => {
            this.gynaecologicalHistory = gynaecologicalHistory;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGynaecologicalHistories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'gynaecologicalHistoryListModification',
            (response) => this.load(this.gynaecologicalHistory.id)
        );
    }
}
