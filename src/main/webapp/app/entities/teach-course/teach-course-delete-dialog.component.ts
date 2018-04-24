import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TeachCourse } from './teach-course.model';
import { TeachCoursePopupService } from './teach-course-popup.service';
import { TeachCourseService } from './teach-course.service';

@Component({
    selector: 'jhi-teach-course-delete-dialog',
    templateUrl: './teach-course-delete-dialog.component.html'
})
export class TeachCourseDeleteDialogComponent {

    teachCourse: TeachCourse;

    constructor(
        private teachCourseService: TeachCourseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.teachCourseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'teachCourseListModification',
                content: 'Deleted an teachCourse'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-teach-course-delete-popup',
    template: ''
})
export class TeachCourseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private teachCoursePopupService: TeachCoursePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.teachCoursePopupService
                .open(TeachCourseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
