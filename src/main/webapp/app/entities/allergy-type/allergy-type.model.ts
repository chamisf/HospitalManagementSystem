import { BaseEntity } from './../../shared';

export class AllergyType implements BaseEntity {
    constructor(
        public id?: number,
        public allergyType?: string,
    ) {
    }
}
