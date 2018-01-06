import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MicturitionTypes } from './micturition-types.model';
import { MicturitionTypesService } from './micturition-types.service';

@Component({
    selector: 'jhi-micturition-types-detail',
    templateUrl: './micturition-types-detail.component.html'
})
export class MicturitionTypesDetailComponent implements OnInit, OnDestroy {

    micturitionTypes: MicturitionTypes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private micturitionTypesService: MicturitionTypesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMicturitionTypes();
    }

    load(id) {
        this.micturitionTypesService.find(id).subscribe((micturitionTypes) => {
            this.micturitionTypes = micturitionTypes;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMicturitionTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'micturitionTypesListModification',
            (response) => this.load(this.micturitionTypes.id)
        );
    }
}
