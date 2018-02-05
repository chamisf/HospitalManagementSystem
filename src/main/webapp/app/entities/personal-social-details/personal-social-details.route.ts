import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PersonalSocialDetailsComponent } from './personal-social-details.component';
import { PersonalSocialDetailsDetailComponent } from './personal-social-details-detail.component';
import { PersonalSocialDetailsPopupComponent } from './personal-social-details-dialog.component';
import { PersonalSocialDetailsDeletePopupComponent } from './personal-social-details-delete-dialog.component';

@Injectable()
export class PersonalSocialDetailsResolvePagingParams implements Resolve<any> {

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

export const personalSocialDetailsRoute: Routes = [
    {
        path: 'personal-social-details',
        component: PersonalSocialDetailsComponent,
        resolve: {
            'pagingParams': PersonalSocialDetailsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonalSocialDetails'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'personal-social-details/:id',
        component: PersonalSocialDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonalSocialDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personalSocialDetailsPopupRoute: Routes = [
    {
        path: 'personal-social-details-new',
        component: PersonalSocialDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonalSocialDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'personal-social-details/:id/edit',
        component: PersonalSocialDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonalSocialDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'personal-social-details/:id/delete',
        component: PersonalSocialDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonalSocialDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
