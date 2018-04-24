import { Component, OnInit, OnDestroy, EventEmitter} from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { StuCourse } from './stu-course.model';
import { StuCourseService } from './stu-course.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import {Student} from "../student/student.model";
import {StudentService} from "../student/student.service";
import {TeachCourse} from "../teach-course/teach-course.model";
import {TeachCourseService} from "../teach-course/teach-course.service";

@Component({
    selector: 'jhi-stu-course',
    templateUrl: './stu-course.component.html'
})
export class StuCourseComponent implements OnInit, OnDestroy {

currentAccount: any;
    stuCourses: StuCourse[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    query: StuCourse;

    students: Student[];
    studentQuery: Student;
    searchStudent = new EventEmitter<string>();

    teachCourses: TeachCourse[];
    teachCourseQuery: TeachCourse;
    searchTeachCourse = new EventEmitter<string>();


    constructor(
        private stuCourseService: StuCourseService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private studentService: StudentService,
        private teachCourseService: TeachCourseService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;

            this.query = new StuCourse();
            this.studentQuery = new Student();
            this.teachCourseQuery = new TeachCourse();
        });
    }

    searchStudentFilter(name){
        this.studentQuery['chnName'] = name;
        this.studentService.query({
            page: 0,
            size: 20,
            studentQuery: this.studentQuery,
            sort: this.sort()}).subscribe(
            (res: HttpResponse<Student[]>) =>  {
                this.students = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    searchteachCourseFilter(name){
        this.teachCourseQuery['teachCourseCode'] = name;
        this.teachCourseService.query({
            page: 0,
            size: 20,
            query: this.teachCourseQuery,
            sort: this.sort()}).subscribe(
            (res: HttpResponse<TeachCourse[]>) =>  {
                this.teachCourses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    loadAll() {
        this.stuCourseService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            query: this.query,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<StuCourse[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/stu-course'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                query: this.query,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/stu-course', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInStuCourses();

        this.searchStudent.subscribe(name => this.searchStudentFilter(name));
        this.searchTeachCourse.subscribe(name => this.searchteachCourseFilter(name));
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: StuCourse) {
        return item.id;
    }
    registerChangeInStuCourses() {
        this.eventSubscriber = this.eventManager.subscribe('stuCourseListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.stuCourses = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
