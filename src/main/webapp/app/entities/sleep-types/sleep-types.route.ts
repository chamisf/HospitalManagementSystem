import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SleepTypesComponent } from './sleep-types.component';
import { SleepTypesDetailComponent } from './sleep-types-detail.component';
import { SleepTypesPopupComponent } from './sleep-types-dialog.component';
import { SleepTypesDeletePopupComponent } from './sleep-types-delete-dialog.component';

@Injectable()
export class SleepTypesResolvePagingParams implements Resolve<any> {

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

export const sleepTypesRoute: Routes = [
    {
        path: 'sleep-types',
        component: SleepTypesComponent,
        resolve: {
            'pagingParams': SleepTypesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SleepTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sleep-types/:id',
        component: SleepTypesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SleepTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sleepTypesPopupRoute: Routes = [
    {
        path: 'sleep-types-new',
        component: SleepTypesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SleepTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sleep-types/:id/edit',
        component: SleepTypesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SleepTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sleep-types/:id/delete',
        component: SleepTypesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SleepTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
