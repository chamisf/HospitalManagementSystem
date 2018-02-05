import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MedicineComponent } from './medicine.component';
import { MedicineDetailComponent } from './medicine-detail.component';
import { MedicinePopupComponent } from './medicine-dialog.component';
import { MedicineDeletePopupComponent } from './medicine-delete-dialog.component';

@Injectable()
export class MedicineResolvePagingParams implements Resolve<any> {

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

export const medicineRoute: Routes = [
    {
        path: 'medicine',
        component: MedicineComponent,
        resolve: {
            'pagingParams': MedicineResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medicines'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'medicine/:id',
        component: MedicineDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medicines'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const medicinePopupRoute: Routes = [
    {
        path: 'medicine-new',
        component: MedicinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medicines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'medicine/:id/edit',
        component: MedicinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medicines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'medicine/:id/delete',
        component: MedicineDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medicines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
