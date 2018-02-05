import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PersonalSocialDetails } from './personal-social-details.model';
import { PersonalSocialDetailsService } from './personal-social-details.service';

@Component({
    selector: 'jhi-personal-social-details-detail',
    templateUrl: './personal-social-details-detail.component.html'
})
export class PersonalSocialDetailsDetailComponent implements OnInit, OnDestroy {

    personalSocialDetails: PersonalSocialDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private personalSocialDetailsService: PersonalSocialDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonalSocialDetails();
    }

    load(id) {
        this.personalSocialDetailsService.find(id).subscribe((personalSocialDetails) => {
            this.personalSocialDetails = personalSocialDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonalSocialDetails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personalSocialDetailsListModification',
            (response) => this.load(this.personalSocialDetails.id)
        );
    }
}
