import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
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
    private patientId: number;

    constructor(private eventManager: JhiEventManager,
                private gynaecologicalHistoryService: GynaecologicalHistoryService,
                private route: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit() {
        this.subscription = this.route.parent.params.subscribe((params) => {
            this.patientId = params['id'];
            this.loadByPatientId(this.patientId);
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
            (response) => {
                if (this.gynaecologicalHistory.id) {
                    this.load(this.gynaecologicalHistory.id)
                } else {
                    this.loadByPatientId(this.patientId);
                }
            }
        );
    }

    navigateTo() {
        let popUpRoute: string;
        if (this.gynaecologicalHistory.id) {
            popUpRoute = 'gynaecological-history/' + this.gynaecologicalHistory.id + '/edit';

        }
        else {
            popUpRoute = 'gynaecological-history-new';
        }

        let navigationExtras: any = {
            outlets: {
                popup: popUpRoute
            }
        };
        this.router.navigate([navigationExtras]);
    }
}
