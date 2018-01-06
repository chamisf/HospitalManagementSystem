import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PersonalSocialDetails } from './personal-social-details.model';
import { PersonalSocialDetailsService } from './personal-social-details.service';

@Injectable()
export class PersonalSocialDetailsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private personalSocialDetailsService: PersonalSocialDetailsService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.personalSocialDetailsService.find(id).subscribe((personalSocialDetails) => {
                    this.ngbModalRef = this.personalSocialDetailsModalRef(component, personalSocialDetails);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.personalSocialDetailsModalRef(component, new PersonalSocialDetails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    personalSocialDetailsModalRef(component: Component, personalSocialDetails: PersonalSocialDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.personalSocialDetails = personalSocialDetails;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
