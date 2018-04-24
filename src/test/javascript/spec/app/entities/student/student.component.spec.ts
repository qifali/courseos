/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CourseosTestModule } from '../../../test.module';
import { StudentComponent } from '../../../../../../main/webapp/app/entities/student/student.component';
import { StudentService } from '../../../../../../main/webapp/app/entities/student/student.service';
import { Student } from '../../../../../../main/webapp/app/entities/student/student.model';

describe('Component Tests', () => {

    describe('Student Management Component', () => {
        let comp: StudentComponent;
        let fixture: ComponentFixture<StudentComponent>;
        let service: StudentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CourseosTestModule],
                declarations: [StudentComponent],
                providers: [
                    StudentService
                ]
            })
            .overrideTemplate(StudentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StudentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Student(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.students[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
