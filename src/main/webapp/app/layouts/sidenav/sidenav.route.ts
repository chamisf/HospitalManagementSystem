import {Route} from '@angular/router';

import {SidenavComponent} from './sidenav.component';

export const sideNavbarRoute: Route = {
    path: '',
    component: SidenavComponent,
    outlet: 'navbar'
};
