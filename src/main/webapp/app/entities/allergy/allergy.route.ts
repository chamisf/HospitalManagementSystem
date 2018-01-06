import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AllergyComponent } from './allergy.component';
import { AllergyDetailComponent } from './allergy-detail.component';
import { AllergyPopupComponent } from './allergy-dialog.component';
import { AllergyDeletePopupComponent } from './allergy-delete-dialog.component';

@Injectable()
export class AllergyResolvePagingParams implements Resolve<any> {

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

export const allergyRoute: Routes = [
    {
        path: 'allergy',
        component: AllergyComponent,
        resolve: {
            'pagingParams': AllergyResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Allergies'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'allergy/:id',
        component: AllergyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Allergies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const allergyPopupRoute: Routes = [
    {
        path: 'allergy-new',
        component: AllergyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Allergies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'allergy/:id/edit',
        component: AllergyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Allergies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'allergy/:id/delete',
        component: AllergyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Allergies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
