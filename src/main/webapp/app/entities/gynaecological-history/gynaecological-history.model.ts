import { BaseEntity } from './../../shared';

export class GynaecologicalHistory implements BaseEntity {
    constructor(
        public id?: number,
        public ageOfMenarche?: string,
        public lastMenstrualPeriod?: string,
        public regularityOfCycle?: string,
        public patient?: BaseEntity,
    ) {
    }
}
