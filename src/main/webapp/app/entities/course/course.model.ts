import { BaseEntity } from './../../shared';

export class Course implements BaseEntity {
    constructor(
        public id?: number,
        public courseCode?: string,
        public name?: string,
        public desc?: string,
        public grade?: string,
    ) {
    }
}
