import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { GynaecologicalHistoryComponent } from './gynaecological-history.component';
import { GynaecologicalHistoryDetailComponent } from './gynaecological-history-detail.component';
import { GynaecologicalHistoryPopupComponent } from './gynaecological-history-dialog.component';
import { GynaecologicalHistoryDeletePopupComponent } from './gynaecological-history-delete-dialog.component';

@Injectable()
export class GynaecologicalHistoryResolvePagingParams implements Resolve<any> {

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

export const gynaecologicalHistoryRoute: Routes = [
    {
        path: 'gynaecological-history',
        component: GynaecologicalHistoryComponent,
        resolve: {
            'pagingParams': GynaecologicalHistoryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GynaecologicalHistories'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'gynaecological-history/:id',
        component: GynaecologicalHistoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GynaecologicalHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gynaecologicalHistoryPopupRoute: Routes = [
    {
        path: 'gynaecological-history-new',
        component: GynaecologicalHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GynaecologicalHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gynaecological-history/:id/edit',
        component: GynaecologicalHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GynaecologicalHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gynaecological-history/:id/delete',
        component: GynaecologicalHistoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GynaecologicalHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
