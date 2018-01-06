import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FamilyHistoryComponent } from './family-history.component';
import { FamilyHistoryDetailComponent } from './family-history-detail.component';
import { FamilyHistoryPopupComponent } from './family-history-dialog.component';
import { FamilyHistoryDeletePopupComponent } from './family-history-delete-dialog.component';

@Injectable()
export class FamilyHistoryResolvePagingParams implements Resolve<any> {

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

export const familyHistoryRoute: Routes = [
    {
        path: 'family-history',
        component: FamilyHistoryComponent,
        resolve: {
            'pagingParams': FamilyHistoryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FamilyHistories'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'family-history/:id',
        component: FamilyHistoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FamilyHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const familyHistoryPopupRoute: Routes = [
    {
        path: 'family-history-new',
        component: FamilyHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FamilyHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'family-history/:id/edit',
        component: FamilyHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FamilyHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'family-history/:id/delete',
        component: FamilyHistoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FamilyHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
