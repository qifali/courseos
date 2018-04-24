/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CourseosTestModule } from '../../../test.module';
import { StuCourseDialogComponent } from '../../../../../../main/webapp/app/entities/stu-course/stu-course-dialog.component';
import { StuCourseService } from '../../../../../../main/webapp/app/entities/stu-course/stu-course.service';
import { StuCourse } from '../../../../../../main/webapp/app/entities/stu-course/stu-course.model';
import { StudentService } from '../../../../../../main/webapp/app/entities/student';
import { TeachCourseService } from '../../../../../../main/webapp/app/entities/teach-course';

describe('Component Tests', () => {

    describe('StuCourse Management Dialog Component', () => {
        let comp: StuCourseDialogComponent;
        let fixture: ComponentFixture<StuCourseDialogComponent>;
        let service: StuCourseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CourseosTestModule],
                declarations: [StuCourseDialogComponent],
                providers: [
                    StudentService,
                    TeachCourseService,
                    StuCourseService
                ]
            })
            .overrideTemplate(StuCourseDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StuCourseDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StuCourseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new StuCourse(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.stuCourse = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'stuCourseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new StuCourse();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.stuCourse = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'stuCourseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
