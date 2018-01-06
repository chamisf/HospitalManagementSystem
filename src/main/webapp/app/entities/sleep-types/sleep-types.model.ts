import { BaseEntity } from './../../shared';

export class SleepTypes implements BaseEntity {
    constructor(
        public id?: number,
        public sleepType?: string,
    ) {
    }
}
