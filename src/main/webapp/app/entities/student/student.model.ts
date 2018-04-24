import { BaseEntity } from './../../shared';

export const enum IsAfterSchoolStatus {
    'YES',
    'NO',
    ''
}

export class Student implements BaseEntity {
    constructor(
        public id?: number,
        public stuCode?: string,
        public chnName?: string,
        public engName?: string,
        public grade?: string,
        public school?: string,
        public desc?: string,
        public isAfterSchool?: IsAfterSchoolStatus,
    ) {
    }
}
