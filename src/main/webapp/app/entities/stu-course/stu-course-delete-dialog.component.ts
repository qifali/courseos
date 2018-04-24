import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { StuCourse } from './stu-course.model';
import { StuCoursePopupService } from './stu-course-popup.service';
import { StuCourseService } from './stu-course.service';

@Component({
    selector: 'jhi-stu-course-delete-dialog',
    templateUrl: './stu-course-delete-dialog.component.html'
})
export class StuCourseDeleteDialogComponent {

    stuCourse: StuCourse;

    constructor(
        private stuCourseService: StuCourseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stuCourseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'stuCourseListModification',
                content: 'Deleted an stuCourse'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stu-course-delete-popup',
    template: ''
})
export class StuCourseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stuCoursePopupService: StuCoursePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.stuCoursePopupService
                .open(StuCourseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
