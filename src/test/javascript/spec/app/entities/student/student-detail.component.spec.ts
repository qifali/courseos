/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CourseosTestModule } from '../../../test.module';
import { StudentDetailComponent } from '../../../../../../main/webapp/app/entities/student/student-detail.component';
import { StudentService } from '../../../../../../main/webapp/app/entities/student/student.service';
import { Student } from '../../../../../../main/webapp/app/entities/student/student.model';

describe('Component Tests', () => {

    describe('Student Management Detail Component', () => {
        let comp: StudentDetailComponent;
        let fixture: ComponentFixture<StudentDetailComponent>;
        let service: StudentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CourseosTestModule],
                declarations: [StudentDetailComponent],
                providers: [
                    StudentService
                ]
            })
            .overrideTemplate(StudentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StudentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Student(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.student).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
