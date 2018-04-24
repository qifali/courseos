import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { StuCourse } from './stu-course.model';
import { StuCourseService } from './stu-course.service';

@Component({
    selector: 'jhi-stu-course-detail',
    templateUrl: './stu-course-detail.component.html'
})
export class StuCourseDetailComponent implements OnInit, OnDestroy {

    stuCourse: StuCourse;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private stuCourseService: StuCourseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStuCourses();
    }

    load(id) {
        this.stuCourseService.find(id)
            .subscribe((stuCourseResponse: HttpResponse<StuCourse>) => {
                this.stuCourse = stuCourseResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStuCourses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'stuCourseListModification',
            (response) => this.load(this.stuCourse.id)
        );
    }
}
