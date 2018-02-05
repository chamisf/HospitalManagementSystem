import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Addictions } from './addictions.model';
import { AddictionsService } from './addictions.service';

@Component({
    selector: 'jhi-addictions-detail',
    templateUrl: './addictions-detail.component.html'
})
export class AddictionsDetailComponent implements OnInit, OnDestroy {

    addictions: Addictions;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private addictionsService: AddictionsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAddictions();
    }

    load(id) {
        this.addictionsService.find(id).subscribe((addictions) => {
            this.addictions = addictions;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAddictions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'addictionsListModification',
            (response) => this.load(this.addictions.id)
        );
    }
}
