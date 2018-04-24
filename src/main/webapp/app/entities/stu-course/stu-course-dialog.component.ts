import { Component, OnInit, OnDestroy, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { StuCourse } from './stu-course.model';
import { StuCoursePopupService } from './stu-course-popup.service';
import { StuCourseService } from './stu-course.service';
import { Student, StudentService } from '../student';
import { TeachCourse, TeachCourseService } from '../teach-course';

@Component({
    selector: 'jhi-stu-course-dialog',
    templateUrl: './stu-course-dialog.component.html'
})
export class StuCourseDialogComponent implements OnInit {

    stuCourse: StuCourse;
    isSaving: boolean;

    students: Student[];

    teachcourses: TeachCourse[];


    studentQuery: Student;
    searchStudent = new EventEmitter<string>();

    teachCourseQuery: TeachCourse;
    searchTeachCourse = new EventEmitter<string>();

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private stuCourseService: StuCourseService,
        private studentService: StudentService,
        private teachCourseService: TeachCourseService,
        private eventManager: JhiEventManager
    ) {
        this.studentQuery = new Student();
        this.teachCourseQuery = new TeachCourse();
    }

    ngOnInit() {
        this.isSaving = false;
        if(this.stuCourse.student) {
            this.studentQuery['id'] = this.stuCourse.student.id;
            this.studentService.query({
                    studentQuery: this.studentQuery
                })
                .subscribe((res:HttpResponse<Student[]>) => {
                    this.students = res.body;
                }, (res:HttpErrorResponse) => this.onError(res.message));
        }

        if(this.stuCourse.teachCourse) {
            this.teachCourseQuery['id'] = this.stuCourse.teachCourse.id;
            this.teachCourseService.query({
                    query: this.teachCourseQuery
                })
                .subscribe((res:HttpResponse<TeachCourse[]>) => {
                    this.teachcourses = res.body;
                }, (res:HttpErrorResponse) => this.onError(res.message));
        }
        
        this.searchStudent.subscribe(name => this.searchStudentFilter(name));
        this.searchTeachCourse.subscribe(name => this.searchteachCourseFilter(name));
    }


    searchStudentFilter(name){
        this.studentQuery = new Student();
        this.studentQuery['chnName'] = name;
        this.studentService.query({
            page: 0,
            size: 20,
            studentQuery: this.studentQuery}).subscribe(
            (res: HttpResponse<Student[]>) =>  {
                this.students = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    searchteachCourseFilter(name){
        this.teachCourseQuery = new TeachCourse();
        this.teachCourseQuery['teachCourseCode'] = name;
        this.teachCourseService.query({
            page: 0,
            size: 20,
            query: this.teachCourseQuery}).subscribe(
            (res: HttpResponse<TeachCourse[]>) =>  {
                this.teachcourses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.stuCourse.id !== undefined) {
            this.subscribeToSaveResponse(
                this.stuCourseService.update(this.stuCourse));
        } else {
            this.subscribeToSaveResponse(
                this.stuCourseService.create(this.stuCourse));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<StuCourse>>) {
        result.subscribe((res: HttpResponse<StuCourse>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: StuCourse) {
        this.eventManager.broadcast({ name: 'stuCourseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackStudentById(index: number, item: Student) {
        return item.id;
    }

    trackTeachCourseById(index: number, item: TeachCourse) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-stu-course-popup',
    template: ''
})
export class StuCoursePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stuCoursePopupService: StuCoursePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.stuCoursePopupService
                    .open(StuCourseDialogComponent as Component, params['id']);
            } else {
                this.stuCoursePopupService
                    .open(StuCourseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
