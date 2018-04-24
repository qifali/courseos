/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CourseosTestModule } from '../../../test.module';
import { StuCourseDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/stu-course/stu-course-delete-dialog.component';
import { StuCourseService } from '../../../../../../main/webapp/app/entities/stu-course/stu-course.service';

describe('Component Tests', () => {

    describe('StuCourse Management Delete Component', () => {
        let comp: StuCourseDeleteDialogComponent;
        let fixture: ComponentFixture<StuCourseDeleteDialogComponent>;
        let service: StuCourseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CourseosTestModule],
                declarations: [StuCourseDeleteDialogComponent],
                providers: [
                    StuCourseService
                ]
            })
            .overrideTemplate(StuCourseDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StuCourseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StuCourseService);
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
