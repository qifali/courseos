/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CourseosTestModule } from '../../../test.module';
import { TeacherDetailComponent } from '../../../../../../main/webapp/app/entities/teacher/teacher-detail.component';
import { TeacherService } from '../../../../../../main/webapp/app/entities/teacher/teacher.service';
import { Teacher } from '../../../../../../main/webapp/app/entities/teacher/teacher.model';

describe('Component Tests', () => {

    describe('Teacher Management Detail Component', () => {
        let comp: TeacherDetailComponent;
        let fixture: ComponentFixture<TeacherDetailComponent>;
        let service: TeacherService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CourseosTestModule],
                declarations: [TeacherDetailComponent],
                providers: [
                    TeacherService
                ]
            })
            .overrideTemplate(TeacherDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TeacherDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeacherService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Teacher(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.teacher).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
