import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AddictionsComponent } from './addictions.component';
import { AddictionsDetailComponent } from './addictions-detail.component';
import { AddictionsPopupComponent } from './addictions-dialog.component';
import { AddictionsDeletePopupComponent } from './addictions-delete-dialog.component';

@Injectable()
export class AddictionsResolvePagingParams implements Resolve<any> {

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

export const addictionsRoute: Routes = [
    {
        path: 'addictions',
        component: AddictionsComponent,
        resolve: {
            'pagingParams': AddictionsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Addictions'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'addictions/:id',
        component: AddictionsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Addictions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const addictionsPopupRoute: Routes = [
    {
        path: 'addictions-new',
        component: AddictionsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Addictions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'addictions/:id/edit',
        component: AddictionsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Addictions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'addictions/:id/delete',
        component: AddictionsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Addictions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
