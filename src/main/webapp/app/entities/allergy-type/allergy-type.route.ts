import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AllergyTypeComponent } from './allergy-type.component';
import { AllergyTypeDetailComponent } from './allergy-type-detail.component';
import { AllergyTypePopupComponent } from './allergy-type-dialog.component';
import { AllergyTypeDeletePopupComponent } from './allergy-type-delete-dialog.component';

@Injectable()
export class AllergyTypeResolvePagingParams implements Resolve<any> {

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

export const allergyTypeRoute: Routes = [
    {
        path: 'allergy-type',
        component: AllergyTypeComponent,
        resolve: {
            'pagingParams': AllergyTypeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AllergyTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'allergy-type/:id',
        component: AllergyTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AllergyTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const allergyTypePopupRoute: Routes = [
    {
        path: 'allergy-type-new',
        component: AllergyTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AllergyTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'allergy-type/:id/edit',
        component: AllergyTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AllergyTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'allergy-type/:id/delete',
        component: AllergyTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AllergyTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
