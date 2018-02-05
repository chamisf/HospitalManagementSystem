import { BaseEntity } from './../../shared';

export class Allergy implements BaseEntity {
    constructor(
        public id?: number,
        public additionalInformation?: string,
        public allergyType?: BaseEntity,
        public patient?: BaseEntity,
    ) {
    }
}
