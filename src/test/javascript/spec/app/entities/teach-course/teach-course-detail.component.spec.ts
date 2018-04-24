/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CourseosTestModule } from '../../../test.module';
import { TeachCourseDetailComponent } from '../../../../../../main/webapp/app/entities/teach-course/teach-course-detail.component';
import { TeachCourseService } from '../../../../../../main/webapp/app/entities/teach-course/teach-course.service';
import { TeachCourse } from '../../../../../../main/webapp/app/entities/teach-course/teach-course.model';

describe('Component Tests', () => {

    describe('TeachCourse Management Detail Component', () => {
        let comp: TeachCourseDetailComponent;
        let fixture: ComponentFixture<TeachCourseDetailComponent>;
        let service: TeachCourseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CourseosTestModule],
                declarations: [TeachCourseDetailComponent],
                providers: [
                    TeachCourseService
                ]
            })
            .overrideTemplate(TeachCourseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TeachCourseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeachCourseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TeachCourse(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.teachCourse).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
