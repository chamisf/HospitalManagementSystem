import { BaseEntity } from './../../shared';

export class FamilyHistory implements BaseEntity {
    constructor(
        public id?: number,
        public fathersDiseases?: string,
        public mothersDiseases?: string,
        public siblingsDiseases?: string,
        public otherRelativesDiseases?: string,
        public patient?: BaseEntity,
    ) {
    }
}
