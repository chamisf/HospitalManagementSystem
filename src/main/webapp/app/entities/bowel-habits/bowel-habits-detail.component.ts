import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BowelHabits } from './bowel-habits.model';
import { BowelHabitsService } from './bowel-habits.service';

@Component({
    selector: 'jhi-bowel-habits-detail',
    templateUrl: './bowel-habits-detail.component.html'
})
export class BowelHabitsDetailComponent implements OnInit, OnDestroy {

    bowelHabits: BowelHabits;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bowelHabitsService: BowelHabitsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBowelHabits();
    }

    load(id) {
        this.bowelHabitsService.find(id).subscribe((bowelHabits) => {
            this.bowelHabits = bowelHabits;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBowelHabits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bowelHabitsListModification',
            (response) => this.load(this.bowelHabits.id)
        );
    }
}
