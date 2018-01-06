import { BaseEntity } from './../../shared';

export class BowelHabits implements BaseEntity {
    constructor(
        public id?: number,
        public bowelHabit?: string,
    ) {
    }
}
