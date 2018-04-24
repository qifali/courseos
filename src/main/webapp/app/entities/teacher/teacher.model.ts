import { BaseEntity } from './../../shared';

export const enum IsOnJobStatus {
    'YES',
    'NO'
}

export class Teacher implements BaseEntity {
    constructor(
        public id?: number,
        public teacherCode?: string,
        public name?: string,
        public subject?: string,
        public college?: string,
        public desc?: string,
        public isOnJob?: IsOnJobStatus,
    ) {
    }
}
