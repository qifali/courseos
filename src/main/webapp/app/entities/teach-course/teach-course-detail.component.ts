import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TeachCourse } from './teach-course.model';
import { TeachCourseService } from './teach-course.service';

@Component({
    selector: 'jhi-teach-course-detail',
    templateUrl: './teach-course-detail.component.html'
})
export class TeachCourseDetailComponent implements OnInit, OnDestroy {

    teachCourse: TeachCourse;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private teachCourseService: TeachCourseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTeachCourses();
    }

    load(id) {
        this.teachCourseService.find(id)
            .subscribe((teachCourseResponse: HttpResponse<TeachCourse>) => {
                this.teachCourse = teachCourseResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTeachCourses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'teachCourseListModification',
            (response) => this.load(this.teachCourse.id)
        );
    }
}
