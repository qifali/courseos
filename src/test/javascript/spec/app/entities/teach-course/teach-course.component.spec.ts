/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CourseosTestModule } from '../../../test.module';
import { TeachCourseComponent } from '../../../../../../main/webapp/app/entities/teach-course/teach-course.component';
import { TeachCourseService } from '../../../../../../main/webapp/app/entities/teach-course/teach-course.service';
import { TeachCourse } from '../../../../../../main/webapp/app/entities/teach-course/teach-course.model';

import { TeacherService } from '../../../../../../main/webapp/app/entities/teacher/teacher.service';
import { CourseService } from '../../../../../../main/webapp/app/entities/course/course.service';

describe('Component Tests', () => {

    describe('TeachCourse Management Component', () => {
        let comp: TeachCourseComponent;
        let fixture: ComponentFixture<TeachCourseComponent>;
        let service: TeachCourseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CourseosTestModule],
                declarations: [TeachCourseComponent],
                providers: [
                    TeachCourseService,
                    TeacherService,
                    CourseService
                ]
            })
            .overrideTemplate(TeachCourseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TeachCourseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeachCourseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TeachCourse(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.teachCourses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
