import { ILocation } from 'app/entities/gen/location/location.model';

export interface IDepartment {
  id: number;
  departmentName?: string | null;
  location?: ILocation | null;
}

export type NewDepartment = Omit<IDepartment, 'id'> & { id: null };
