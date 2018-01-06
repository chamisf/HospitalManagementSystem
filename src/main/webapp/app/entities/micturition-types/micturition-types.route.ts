import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MicturitionTypesComponent } from './micturition-types.component';
import { MicturitionTypesDetailComponent } from './micturition-types-detail.component';
import { MicturitionTypesPopupComponent } from './micturition-types-dialog.component';
import { MicturitionTypesDeletePopupComponent } from './micturition-types-delete-dialog.component';

@Injectable()
export class MicturitionTypesResolvePagingParams implements Resolve<any> {

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

export const micturitionTypesRoute: Routes = [
    {
        path: 'micturition-types',
        component: MicturitionTypesComponent,
        resolve: {
            'pagingParams': MicturitionTypesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MicturitionTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'micturition-types/:id',
        component: MicturitionTypesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MicturitionTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const micturitionTypesPopupRoute: Routes = [
    {
        path: 'micturition-types-new',
        component: MicturitionTypesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MicturitionTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'micturition-types/:id/edit',
        component: MicturitionTypesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MicturitionTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'micturition-types/:id/delete',
        component: MicturitionTypesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MicturitionTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
