import { IDepartment, NewDepartment } from './department.model';

export const sampleWithRequiredData: IDepartment = {
  id: 32001,
};

export const sampleWithPartialData: IDepartment = {
  id: 26867,
};

export const sampleWithFullData: IDepartment = {
  id: 13276,
  departmentName: 'endow',
};

export const sampleWithNewData: NewDepartment = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
