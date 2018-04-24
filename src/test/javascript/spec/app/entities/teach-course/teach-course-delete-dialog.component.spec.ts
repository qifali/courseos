/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CourseosTestModule } from '../../../test.module';
import { TeachCourseDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/teach-course/teach-course-delete-dialog.component';
import { TeachCourseService } from '../../../../../../main/webapp/app/entities/teach-course/teach-course.service';

describe('Component Tests', () => {

    describe('TeachCourse Management Delete Component', () => {
        let comp: TeachCourseDeleteDialogComponent;
        let fixture: ComponentFixture<TeachCourseDeleteDialogComponent>;
        let service: TeachCourseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CourseosTestModule],
                declarations: [TeachCourseDeleteDialogComponent],
                providers: [
                    TeachCourseService
                ]
            })
            .overrideTemplate(TeachCourseDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TeachCourseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TeachCourseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
