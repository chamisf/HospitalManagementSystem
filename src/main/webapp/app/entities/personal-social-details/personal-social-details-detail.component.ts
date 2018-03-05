import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {PersonalSocialDetails} from './personal-social-details.model';
import {PersonalSocialDetailsService} from './personal-social-details.service';

@Component({
    selector: 'jhi-personal-social-details-detail',
    templateUrl: './personal-social-details-detail.component.html'
})
export class PersonalSocialDetailsDetailComponent implements OnInit, OnDestroy {

    personalSocialDetails: PersonalSocialDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    private patientId: number;

    constructor(private eventManager: JhiEventManager,
                private personalSocialDetailsService: PersonalSocialDetailsService,
                private route: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit() {
        this.subscription = this.route.parent.params.subscribe((params) => {
            this.patientId = +params['id'];
            this.loadByPatientId(this.patientId);
        });
        this.registerChangeInPersonalSocialDetails();
    }

    load(id) {
        this.personalSocialDetailsService.find(id).subscribe((personalSocialDetails) => {
            this.personalSocialDetails = personalSocialDetails;
        });
    }

    private loadByPatientId(id) {
        this.personalSocialDetailsService.loadByPatientId(id).subscribe((personalSocialDetails) => {
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
            (response) => {
                if (this.personalSocialDetails.id) {
                    this.load(this.personalSocialDetails.id)
                } else {
                    this.loadByPatientId(this.patientId);
                }
            }
        );
    }

    navigateTo() {
        let popUpRoute: string;
        if (this.personalSocialDetails.id) {
            popUpRoute = 'personal-social-details/' + this.personalSocialDetails.id + '/edit';
        }
        else {
            popUpRoute = 'personal-social-details-new';
        }

        let navigationExtras: any = {
            outlets: {
                popup: popUpRoute
            }
        };
        this.router.navigate([navigationExtras]);
    }
}
