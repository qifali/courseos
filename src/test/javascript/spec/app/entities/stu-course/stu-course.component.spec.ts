/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CourseosTestModule } from '../../../test.module';
import { StuCourseComponent } from '../../../../../../main/webapp/app/entities/stu-course/stu-course.component';
import { StuCourseService } from '../../../../../../main/webapp/app/entities/stu-course/stu-course.service';
import { StuCourse } from '../../../../../../main/webapp/app/entities/stu-course/stu-course.model';

import {StudentService} from "../../../../../../main/webapp/app/entities/student/student.service";
import {TeachCourseService} from "../../../../../../main/webapp/app/entities/teach-course/teach-course.service";

describe('Component Tests', () => {

    describe('StuCourse Management Component', () => {
        let comp: StuCourseComponent;
        let fixture: ComponentFixture<StuCourseComponent>;
        let service: StuCourseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CourseosTestModule],
                declarations: [StuCourseComponent],
                providers: [
                    StuCourseService,
                    StudentService,
                    TeachCourseService
                ]
            })
            .overrideTemplate(StuCourseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StuCourseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StuCourseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new StuCourse(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.stuCourses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
