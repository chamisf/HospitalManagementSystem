import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { HospitalManagementSystemSharedModule, UserRouteAccessService } from './shared';
import { HospitalManagementSystemAppRoutingModule} from './app-routing.module';
import { HospitalManagementSystemHomeModule } from './home/home.module';
import { HospitalManagementSystemAdminModule } from './admin/admin.module';
import { HospitalManagementSystemAccountModule } from './account/account.module';
import { HospitalManagementSystemEntityModule } from './entities/entity.module';
import {HospitalManagementSystemViewsModule} from "./views/views.module";
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ErrorComponent,
    SidenavComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        HospitalManagementSystemAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        HospitalManagementSystemSharedModule,
        HospitalManagementSystemHomeModule,
        HospitalManagementSystemAdminModule,
        HospitalManagementSystemAccountModule,
        HospitalManagementSystemEntityModule,
        HospitalManagementSystemViewsModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        FooterComponent,
        SidenavComponent
    ],
    providers: [
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class HospitalManagementSystemAppModule {}
