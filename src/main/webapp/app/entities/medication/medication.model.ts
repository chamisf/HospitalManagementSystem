import { BaseEntity } from './../../shared';

export class Medication implements BaseEntity {
    constructor(
        public id?: number,
        public dose?: string,
        public frequency?: string,
        public route?: string,
        public maneNocte?: string,
        public usingCurrently?: boolean,
        public drugHistory?: BaseEntity,
        public medicine?: BaseEntity,
    ) {
        this.usingCurrently = false;
    }
}
