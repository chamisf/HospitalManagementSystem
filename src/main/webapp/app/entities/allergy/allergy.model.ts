import { BaseEntity } from './../../shared';
import {AllergyType} from "../allergy-type";

export class Allergy implements BaseEntity {
    constructor(
        public id?: number,
        public additionalInformation?: string,
        public allergyType?: AllergyType,
        public patient?: BaseEntity,
    ) {
    }
}
