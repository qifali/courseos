import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CourseosStudentModule } from './student/student.module';
import { CourseosTeacherModule } from './teacher/teacher.module';
import { CourseosCourseModule } from './course/course.module';
import { CourseosTeachCourseModule } from './teach-course/teach-course.module';
import { CourseosStuCourseModule } from './stu-course/stu-course.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CourseosStudentModule,
        CourseosTeacherModule,
        CourseosCourseModule,
        CourseosTeachCourseModule,
        CourseosStuCourseModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CourseosEntityModule {}
