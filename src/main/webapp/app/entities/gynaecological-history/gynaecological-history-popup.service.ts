import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { GynaecologicalHistory } from './gynaecological-history.model';
import { GynaecologicalHistoryService } from './gynaecological-history.service';

@Injectable()
export class GynaecologicalHistoryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private gynaecologicalHistoryService: GynaecologicalHistoryService

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
                this.gynaecologicalHistoryService.find(id).subscribe((gynaecologicalHistory) => {
                    this.ngbModalRef = this.gynaecologicalHistoryModalRef(component, gynaecologicalHistory);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.gynaecologicalHistoryModalRef(component, new GynaecologicalHistory());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    gynaecologicalHistoryModalRef(component: Component, gynaecologicalHistory: GynaecologicalHistory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.gynaecologicalHistory = gynaecologicalHistory;
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
