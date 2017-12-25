import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { HospitalManagemetSystemSharedModule, UserRouteAccessService } from './shared';
import { HospitalManagemetSystemAppRoutingModule} from './app-routing.module';
import { HospitalManagemetSystemHomeModule } from './home/home.module';
import { HospitalManagemetSystemAdminModule } from './admin/admin.module';
import { HospitalManagemetSystemAccountModule } from './account/account.module';
import { HospitalManagemetSystemEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        HospitalManagemetSystemAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        HospitalManagemetSystemSharedModule,
        HospitalManagemetSystemHomeModule,
        HospitalManagemetSystemAdminModule,
        HospitalManagemetSystemAccountModule,
        HospitalManagemetSystemEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        FooterComponent
    ],
    providers: [
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class HospitalManagemetSystemAppModule {}
