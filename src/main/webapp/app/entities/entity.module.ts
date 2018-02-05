import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HospitalManagementSystemPatientModule } from './patient/patient.module';
import { HospitalManagementSystemPersonalSocialDetailsModule } from './personal-social-details/personal-social-details.module';
import { HospitalManagementSystemSleepTypesModule } from './sleep-types/sleep-types.module';
import { HospitalManagementSystemAppetiteTypesModule } from './appetite-types/appetite-types.module';
import { HospitalManagementSystemMicturitionTypesModule } from './micturition-types/micturition-types.module';
import { HospitalManagementSystemBowelHabitsModule } from './bowel-habits/bowel-habits.module';
import { HospitalManagementSystemAddictionsModule } from './addictions/addictions.module';
import { HospitalManagementSystemGynaecologicalHistoryModule } from './gynaecological-history/gynaecological-history.module';
import { HospitalManagementSystemAllergyModule } from './allergy/allergy.module';
import { HospitalManagementSystemAllergyTypeModule } from './allergy-type/allergy-type.module';
import { HospitalManagementSystemFamilyHistoryModule } from './family-history/family-history.module';
import { HospitalManagementSystemDrugHistoryModule } from './drug-history/drug-history.module';
import { HospitalManagementSystemMedicationModule } from './medication/medication.module';
import { HospitalManagementSystemMedicineModule } from './medicine/medicine.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        HospitalManagementSystemPatientModule,
        HospitalManagementSystemPersonalSocialDetailsModule,
        HospitalManagementSystemSleepTypesModule,
        HospitalManagementSystemAppetiteTypesModule,
        HospitalManagementSystemMicturitionTypesModule,
        HospitalManagementSystemBowelHabitsModule,
        HospitalManagementSystemAddictionsModule,
        HospitalManagementSystemGynaecologicalHistoryModule,
        HospitalManagementSystemAllergyModule,
        HospitalManagementSystemAllergyTypeModule,
        HospitalManagementSystemFamilyHistoryModule,
        HospitalManagementSystemDrugHistoryModule,
        HospitalManagementSystemMedicationModule,
        HospitalManagementSystemMedicineModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HospitalManagementSystemEntityModule {}
