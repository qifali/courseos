import { BaseEntity } from './../../shared';

export class StuCourse implements BaseEntity {
    constructor(
        public id?: number,
        public desc?: string,
        public phase?: string,
        public student?: BaseEntity,
        public teachCourse?: BaseEntity,
    ) {
    }
}
