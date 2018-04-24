import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CourseosSharedModule } from '../../shared';
import { NgSelectModule } from '@ng-select/ng-select';

import {
    TeachCourseService,
    TeachCoursePopupService,
    TeachCourseComponent,
    TeachCourseDetailComponent,
    TeachCourseDialogComponent,
    TeachCoursePopupComponent,
    TeachCourseDeletePopupComponent,
    TeachCourseDeleteDialogComponent,
    teachCourseRoute,
    teachCoursePopupRoute,
    TeachCourseResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...teachCourseRoute,
    ...teachCoursePopupRoute,
];

@NgModule({
    imports: [
        CourseosSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        NgSelectModule
    ],
    declarations: [
        TeachCourseComponent,
        TeachCourseDetailComponent,
        TeachCourseDialogComponent,
        TeachCourseDeleteDialogComponent,
        TeachCoursePopupComponent,
        TeachCourseDeletePopupComponent,
    ],
    entryComponents: [
        TeachCourseComponent,
        TeachCourseDialogComponent,
        TeachCoursePopupComponent,
        TeachCourseDeleteDialogComponent,
        TeachCourseDeletePopupComponent,
    ],
    providers: [
        TeachCourseService,
        TeachCoursePopupService,
        TeachCourseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CourseosTeachCourseModule {}
