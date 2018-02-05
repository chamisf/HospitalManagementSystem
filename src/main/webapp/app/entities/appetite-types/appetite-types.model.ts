import { BaseEntity } from './../../shared';

export class AppetiteTypes implements BaseEntity {
    constructor(
        public id?: number,
        public appetiteType?: string,
    ) {
    }
}
