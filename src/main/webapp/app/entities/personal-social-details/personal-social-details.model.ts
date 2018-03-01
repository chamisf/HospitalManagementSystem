import { BaseEntity } from './../../shared';
import {SleepTypes} from "../sleep-types";
import {AppetiteTypes} from "../appetite-types";
import {MicturitionTypes} from "../micturition-types";
import {BowelHabits} from "../bowel-habits";
import {Addictions} from "../addictions";

export class PersonalSocialDetails implements BaseEntity {
    constructor(
        public id?: number,
        public education?: string,
        public employment?: string,
        public assistanceAtHome?: string,
        public patient?: BaseEntity,
        public sleepType?: SleepTypes,
        public appetiteType?: AppetiteTypes,
        public micturitionType?: MicturitionTypes,
        public bowelHabit?: BowelHabits,
        public addiction?: Addictions,
    ) {
    }
}
