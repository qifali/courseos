/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CourseosTestModule } from '../../../test.module';
import { StuCourseDetailComponent } from '../../../../../../main/webapp/app/entities/stu-course/stu-course-detail.component';
import { StuCourseService } from '../../../../../../main/webapp/app/entities/stu-course/stu-course.service';
import { StuCourse } from '../../../../../../main/webapp/app/entities/stu-course/stu-course.model';

describe('Component Tests', () => {

    describe('StuCourse Management Detail Component', () => {
        let comp: StuCourseDetailComponent;
        let fixture: ComponentFixture<StuCourseDetailComponent>;
        let service: StuCourseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CourseosTestModule],
                declarations: [StuCourseDetailComponent],
                providers: [
                    StuCourseService
                ]
            })
            .overrideTemplate(StuCourseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StuCourseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StuCourseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new StuCourse(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.stuCourse).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
