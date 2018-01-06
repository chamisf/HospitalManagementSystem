import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BowelHabitsComponent } from './bowel-habits.component';
import { BowelHabitsDetailComponent } from './bowel-habits-detail.component';
import { BowelHabitsPopupComponent } from './bowel-habits-dialog.component';
import { BowelHabitsDeletePopupComponent } from './bowel-habits-delete-dialog.component';

@Injectable()
export class BowelHabitsResolvePagingParams implements Resolve<any> {

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

export const bowelHabitsRoute: Routes = [
    {
        path: 'bowel-habits',
        component: BowelHabitsComponent,
        resolve: {
            'pagingParams': BowelHabitsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BowelHabits'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bowel-habits/:id',
        component: BowelHabitsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BowelHabits'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bowelHabitsPopupRoute: Routes = [
    {
        path: 'bowel-habits-new',
        component: BowelHabitsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BowelHabits'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bowel-habits/:id/edit',
        component: BowelHabitsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BowelHabits'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bowel-habits/:id/delete',
        component: BowelHabitsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BowelHabits'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
