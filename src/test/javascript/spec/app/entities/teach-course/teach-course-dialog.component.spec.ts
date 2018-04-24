/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CourseosTestModule } from '../../../test.module';
import { TeachCourseDialogComponent } from '../../../../../../main/webapp/app/entities/teach-course/teach-course-dialog.component';
import { TeachCourseService } from '../../../../../../main/webapp/app/entities/teach-course/teach-course.service';
import { TeachCourse } from '../../../../../../main/webapp/app/entities/teach-course/teach-course.model';
import { TeacherService } from '../../../../../../main/webapp/app/entities/teacher';
import { CourseService } from '../../../../../../main/webapp/app/entities/course';

describe('Component Tests', () => {

    describe('TeachCourse Management Dialog Component', () => {
        let comp: TeachCourseDialogComponent;
        let fixture: ComponentFixture<TeachCourseDialogComponent>;
        let service: TeachCourseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CourseosTestModule],
                declarations: [TeachCourseDialogComponent],
                providers: [
                    TeacherService,
                    CourseService,
                    TeachCourseService
                ]
            })
            .overrideTemplate(TeachCourseDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TeachCourseDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeachCourseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TeachCourse(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.teachCourse = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'teachCourseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TeachCourse();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.teachCourse = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'teachCourseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
