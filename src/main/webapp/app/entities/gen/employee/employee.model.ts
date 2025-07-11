import dayjs from 'dayjs/esm';
import { IDepartment } from 'app/entities/gen/department/department.model';

export interface IEmployee {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  hireDate?: dayjs.Dayjs | null;
  salary?: number | null;
  commissionPct?: number | null;
  department?: Pick<IDepartment, 'id'> | null;
  manager?: Pick<IEmployee, 'id'> | null;
}

export type NewEmployee = Omit<IEmployee, 'id'> & { id: null };
