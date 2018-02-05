import { BaseEntity } from './../../shared';

export class DrugHistory implements BaseEntity {
    constructor(
        public id?: number,
        public isCompliantPatient?: boolean,
        public additionalMedicationInformation?: string,
        public patient?: BaseEntity,
        public medications?: BaseEntity[],
    ) {
        this.isCompliantPatient = false;
    }
}
