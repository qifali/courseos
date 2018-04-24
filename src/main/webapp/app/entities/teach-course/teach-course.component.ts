import { Component, EventEmitter, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { TeachCourse } from './teach-course.model';
import { TeachCourseService } from './teach-course.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';

import { Teacher } from '../teacher/teacher.model';
import { TeacherService } from '../teacher/teacher.service';
import {Course} from "../course/course.model";
import {CourseService} from "../course/course.service";

@Component({
    selector: 'jhi-teach-course',
    templateUrl: './teach-course.component.html'
})
export class TeachCourseComponent implements OnInit, OnDestroy {

currentAccount: any;
    teachCourses: TeachCourse[];
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
    query: TeachCourse;

    teachers: Teacher[];
    teacherQuery: Teacher;
    searchTeacher = new EventEmitter<string>();

    courses: Course[];
    courseQuery: Course;
    searchCourse = new EventEmitter<string>();


    constructor(
        private teachCourseService: TeachCourseService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private teacherService: TeacherService,
        private courseService: CourseService,
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
            this.query = new TeachCourse();
            this.teacherQuery = new Teacher();
            this.courseQuery = new Course();
        });
    }

    searchTeacherFilter(name){
        this.teacherQuery['name'] = name;
        this.teacherService.query({
            page: 0,
            size: 20,
            teacherQuery: this.teacherQuery,
            sort: this.sort()}).subscribe(
            (res: HttpResponse<Teacher[]>) =>  {
                this.teachers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    searchCourseFilter(name){
        this.courseQuery['name'] = name;
        this.courseService.query({
            page: 0,
            size: 20,
            courseQuery: this.courseQuery,
            sort: this.sort()}).subscribe(
            (res: HttpResponse<Course[]>) =>  {
                this.courses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    loadAll() {
        this.teachCourseService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            query: this.query,
            sort: this.sort()}).subscribe(
                (res: HttpResponse<TeachCourse[]>) => this.onSuccess(res.body, res.headers),
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
        this.router.navigate(['/teach-course'], {queryParams:
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
        this.router.navigate(['/teach-course', {
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
        this.registerChangeInTeachCourses();

        this.searchTeacher.subscribe(name => this.searchTeacherFilter(name));
        this.searchCourse.subscribe(name => this.searchCourseFilter(name));
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TeachCourse) {
        return item.id;
    }
    registerChangeInTeachCourses() {
        this.eventSubscriber = this.eventManager.subscribe('teachCourseListModification', (response) => this.loadAll());
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
        this.teachCourses = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
