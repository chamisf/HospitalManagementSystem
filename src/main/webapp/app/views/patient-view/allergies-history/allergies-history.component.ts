import {Component, OnDestroy, OnInit} from '@angular/core';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from "ng-jhipster";
import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from "../../../shared";
import {Subscription} from "rxjs/Rx";
import {ActivatedRoute, Router} from "@angular/router";
import {Allergy} from "../../../entities/allergy/allergy.model";
import {AllergyService} from "../../../entities/allergy";

@Component({
    selector: 'jhi-allergies-history',
    templateUrl: './allergies-history.component.html',
    styles: []
})
export class AllergiesHistoryComponent implements OnInit, OnDestroy {

    selectedAllergy: Allergy;
    currentAccount: any;
    allergies: Allergy[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(private allergyService: AllergyService,
                private parseLinks: JhiParseLinks,
                private jhiAlertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        this.allergyService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/allergy'], {
            queryParams:
                {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/allergy', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAllergies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Allergy) {
        return item.id;
    }

    registerChangeInAllergies() {
        this.eventSubscriber = this.eventManager.subscribe('allergyListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.allergies = data;
        if (this.allergies[0] != null)
            this.selectedAllergy = this.allergies[0];
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    viewSelectedAllergy(allergy: Allergy) {
        this.selectedAllergy = allergy;
    }

}
