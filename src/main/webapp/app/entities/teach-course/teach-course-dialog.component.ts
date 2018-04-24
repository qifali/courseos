import { Component, OnInit, OnDestroy, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TeachCourse } from './teach-course.model';
import { TeachCoursePopupService } from './teach-course-popup.service';
import { TeachCourseService } from './teach-course.service';
import { Teacher, TeacherService } from '../teacher';
import { Course, CourseService } from '../course';

@Component({
    selector: 'jhi-teach-course-dialog',
    templateUrl: './teach-course-dialog.component.html'
})
export class TeachCourseDialogComponent implements OnInit {

    teachCourse: TeachCourse;
    isSaving: boolean;

    teachers: Teacher[];

    courses: Course[];


    teacherQuery: Teacher;
    searchTeacher = new EventEmitter<string>();

    courseQuery: Course;
    searchCourse = new EventEmitter<string>();


    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private teachCourseService: TeachCourseService,
        private teacherService: TeacherService,
        private courseService: CourseService,
        private eventManager: JhiEventManager
    ) {
        this.teacherQuery = new Teacher();
        this.courseQuery = new Course();
    }

    ngOnInit() {
        this.isSaving = false;
        if(this.teachCourse.teacher){
            this.teacherQuery['id'] = this.teachCourse.teacher.id;
            this.teacherService.query({
                    teacherQuery: this.teacherQuery
                })
                .subscribe((res:HttpResponse<Teacher[]>) => {
                    this.teachers = res.body;
                }, (res:HttpErrorResponse) => this.onError(res.message));
        }
        if(this.teachCourse.course) {
            this.courseQuery['id'] = this.teachCourse.course.id;
            this.courseService.query({
                    courseQuery: this.courseQuery
                })
                .subscribe((res:HttpResponse<Course[]>) => {
                    this.courses = res.body;
                }, (res:HttpErrorResponse) => this.onError(res.message));
        }
        this.searchTeacher.subscribe(name => this.searchTeacherFilter(name));
        this.searchCourse.subscribe(name => this.searchCourseFilter(name));
    }

    searchTeacherFilter(name){
        this.teacherQuery = new Teacher();
        this.teacherQuery['name'] = name;
        this.teacherService.query({
            page: 0,
            size: 20,
            teacherQuery: this.teacherQuery}).subscribe(
            (res: HttpResponse<Teacher[]>) =>  {
                this.teachers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    searchCourseFilter(name){
        this.courseQuery = new Course();
        this.courseQuery['name'] = name;
        this.courseService.query({
            page: 0,
            size: 20,
            courseQuery: this.courseQuery}).subscribe(
            (res: HttpResponse<Course[]>) =>  {
                this.courses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }


    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.teachCourse.id !== undefined) {
            this.subscribeToSaveResponse(
                this.teachCourseService.update(this.teachCourse));
        } else {
            this.subscribeToSaveResponse(
                this.teachCourseService.create(this.teachCourse));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TeachCourse>>) {
        result.subscribe((res: HttpResponse<TeachCourse>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TeachCourse) {
        this.eventManager.broadcast({ name: 'teachCourseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTeacherById(index: number, item: Teacher) {
        return item.id;
    }

    trackCourseById(index: number, item: Course) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-teach-course-popup',
    template: ''
})
export class TeachCoursePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private teachCoursePopupService: TeachCoursePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.teachCoursePopupService
                    .open(TeachCourseDialogComponent as Component, params['id']);
            } else {
                this.teachCoursePopupService
                    .open(TeachCourseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
