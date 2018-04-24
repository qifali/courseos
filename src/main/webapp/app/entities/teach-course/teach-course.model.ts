import { BaseEntity } from './../../shared';

export const enum TeachCourseType {
    'ONE2MANY',
    'ONE2ONE'
}

export class TeachCourse implements BaseEntity {
    constructor(
        public id?: number,
        public teachCourseCode?: string,
        public day?: string,
        public time?: string,
        public teachCourseType?: TeachCourseType,
        public fee?: string,
        public desc?: string,
        public count?: string,
        public courseDue?: string,
        public teacher?: BaseEntity,
        public course?: BaseEntity,
    ) {
    }
}
