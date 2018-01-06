import { BaseEntity } from './../../shared';

export class Medicine implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
