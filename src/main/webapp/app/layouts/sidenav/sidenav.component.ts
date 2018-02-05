import {Component, OnInit} from '@angular/core';
import {Principal} from "../../shared";

@Component({
    selector: 'hms-sidenav',
    templateUrl: './sidenav.component.html',
    styleUrls: ['sidenav.scss']
})
export class SidenavComponent implements OnInit {

    constructor(private principal: Principal) {
    }

    ngOnInit() {
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
}
