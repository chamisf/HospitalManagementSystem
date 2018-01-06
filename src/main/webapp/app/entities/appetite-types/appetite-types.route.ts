import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AppetiteTypesComponent } from './appetite-types.component';
import { AppetiteTypesDetailComponent } from './appetite-types-detail.component';
import { AppetiteTypesPopupComponent } from './appetite-types-dialog.component';
import { AppetiteTypesDeletePopupComponent } from './appetite-types-delete-dialog.component';

@Injectable()
export class AppetiteTypesResolvePagingParams implements Resolve<any> {

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

export const appetiteTypesRoute: Routes = [
    {
        path: 'appetite-types',
        component: AppetiteTypesComponent,
        resolve: {
            'pagingParams': AppetiteTypesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppetiteTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'appetite-types/:id',
        component: AppetiteTypesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppetiteTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appetiteTypesPopupRoute: Routes = [
    {
        path: 'appetite-types-new',
        component: AppetiteTypesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppetiteTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'appetite-types/:id/edit',
        component: AppetiteTypesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppetiteTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'appetite-types/:id/delete',
        component: AppetiteTypesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppetiteTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
