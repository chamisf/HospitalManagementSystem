import { BaseEntity } from './../../shared';

export class PersonalSocialDetails implements BaseEntity {
    constructor(
        public id?: number,
        public education?: string,
        public employment?: string,
        public assistanceAtHome?: string,
        public patient?: BaseEntity,
        public sleepType?: BaseEntity,
        public appetiteType?: BaseEntity,
        public micturitionType?: BaseEntity,
        public bowelHabit?: BaseEntity,
        public addiction?: BaseEntity,
    ) {
    }
}
