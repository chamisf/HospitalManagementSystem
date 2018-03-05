import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, NavigationExtras, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager} from 'ng-jhipster';

import {FamilyHistory} from './family-history.model';
import {FamilyHistoryService} from './family-history.service';

@Component({
    selector: 'jhi-family-history-detail',
    templateUrl: './family-history-detail.component.html'
})
export class FamilyHistoryDetailComponent implements OnInit, OnDestroy {

    familyHistory: FamilyHistory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    private patientId: number;

    constructor(private eventManager: JhiEventManager,
                private familyHistoryService: FamilyHistoryService,
                private route: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit() {
        this.subscription = this.route.parent.params.subscribe((params) => {
            this.patientId = +params['id'];
            this.loadByPatientId(this.patientId);
        });
        this.registerChangeInFamilyHistories();
    }

    load(id) {
        this.familyHistoryService.find(id).subscribe((familyHistory) => {
            this.familyHistory = familyHistory;
        });
    }

    loadByPatientId(id) {
        this.familyHistoryService.findByPatientId(id).subscribe((familyHistory) => {
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
            (response) => {
                if (this.familyHistory.id) {
                    this.load(this.familyHistory.id)
                } else {
                    this.loadByPatientId(this.patientId);
                }
            }
        );
    }

    navigateTo() {
        let popUpRoute: string;
        if (this.familyHistory.id) {
            popUpRoute = 'family-history/' + this.familyHistory.id + '/edit';
        }
        else {
            popUpRoute = 'family-history-new';
        }

        let navigationExtras: any = {
            outlets: {
                popup: popUpRoute
            }
        };
        this.router.navigate([navigationExtras]);
    }
}
