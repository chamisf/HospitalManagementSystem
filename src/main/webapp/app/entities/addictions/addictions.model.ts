import { BaseEntity } from './../../shared';

export class Addictions implements BaseEntity {
    constructor(
        public id?: number,
        public addiction?: string,
    ) {
    }
}
