import { BaseEntity } from './../../shared';

export const enum Gender {
    'MALE',
    'FEMALE'
}

export const enum MaritalStatus {
    'MARRIED',
    'SINGLE',
    'DIVORCED',
    'WIDOWED'
}

export const enum BloodGroup {
    'A_PLUS',
    'A_MINUS',
    'B_PLUS',
    'B_MINUS',
    'AB_PLUS',
    'AB_MINUS',
    'O_PLUS',
    'O_MINUS'
}

export class Patient implements BaseEntity {
    constructor(
        public id?: number,
        public patientName?: string,
        public hospitalNumber?: string,
        public registeredDate?: any,
        public age?: number,
        public birthDate?: any,
        public gender?: Gender,
        public patientHeight?: string,
        public patientWight?: number,
        public maritalStatus?: MaritalStatus,
        public nicNumber?: string,
        public telephoneNumber?: string,
        public emergencyContactNumber?: string,
        public email?: string,
        public address?: string,
        public occupation?: string,
        public bloodGroup?: BloodGroup,
        public guardianName?: string,
        public guardianAddress?: string,
        public referringPhysician?: string,
        public referringHospital?: string,
    ) {
    }
}
