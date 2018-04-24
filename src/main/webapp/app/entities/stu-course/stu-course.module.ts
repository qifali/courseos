import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';

import { CourseosSharedModule } from '../../shared';
import {
    StuCourseService,
    StuCoursePopupService,
    StuCourseComponent,
    StuCourseDetailComponent,
    StuCourseDialogComponent,
    StuCoursePopupComponent,
    StuCourseDeletePopupComponent,
    StuCourseDeleteDialogComponent,
    stuCourseRoute,
    stuCoursePopupRoute,
    StuCourseResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...stuCourseRoute,
    ...stuCoursePopupRoute,
];

@NgModule({
    imports: [
        CourseosSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        NgSelectModule
    ],
    declarations: [
        StuCourseComponent,
        StuCourseDetailComponent,
        StuCourseDialogComponent,
        StuCourseDeleteDialogComponent,
        StuCoursePopupComponent,
        StuCourseDeletePopupComponent,
    ],
    entryComponents: [
        StuCourseComponent,
        StuCourseDialogComponent,
        StuCoursePopupComponent,
        StuCourseDeleteDialogComponent,
        StuCourseDeletePopupComponent,
    ],
    providers: [
        StuCourseService,
        StuCoursePopupService,
        StuCourseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CourseosStuCourseModule {}
