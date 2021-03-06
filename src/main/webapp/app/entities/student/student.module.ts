import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CourseosSharedModule } from '../../shared';
import {
    StudentService,
    StudentPopupService,
    StudentComponent,
    StudentDetailComponent,
    StudentDialogComponent,
    StudentPopupComponent,
    StudentDeletePopupComponent,
    StudentDeleteDialogComponent,
    studentRoute,
    studentPopupRoute,
    StudentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...studentRoute,
    ...studentPopupRoute,
];

@NgModule({
    imports: [
        CourseosSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StudentComponent,
        StudentDetailComponent,
        StudentDialogComponent,
        StudentDeleteDialogComponent,
        StudentPopupComponent,
        StudentDeletePopupComponent,
    ],
    entryComponents: [
        StudentComponent,
        StudentDialogComponent,
        StudentPopupComponent,
        StudentDeleteDialogComponent,
        StudentDeletePopupComponent,
    ],
    providers: [
        StudentService,
        StudentPopupService,
        StudentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CourseosStudentModule {}
