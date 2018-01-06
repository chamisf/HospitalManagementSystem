import { BaseEntity } from './../../shared';

export class MicturitionTypes implements BaseEntity {
    constructor(
        public id?: number,
        public micturitionType?: string,
    ) {
    }
}
