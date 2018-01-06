import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DrugHistoryComponent } from './drug-history.component';
import { DrugHistoryDetailComponent } from './drug-history-detail.component';
import { DrugHistoryPopupComponent } from './drug-history-dialog.component';
import { DrugHistoryDeletePopupComponent } from './drug-history-delete-dialog.component';

@Injectable()
export class DrugHistoryResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const drugHistoryRoute: Routes = [
    {
        path: 'drug-history',
        component: DrugHistoryComponent,
        resolve: {
            'pagingParams': DrugHistoryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DrugHistories'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'drug-history/:id',
        component: DrugHistoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DrugHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const drugHistoryPopupRoute: Routes = [
    {
        path: 'drug-history-new',
        component: DrugHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DrugHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'drug-history/:id/edit',
        component: DrugHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DrugHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'drug-history/:id/delete',
        component: DrugHistoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DrugHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
