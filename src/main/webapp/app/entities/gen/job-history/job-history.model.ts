import dayjs from 'dayjs/esm';
import { IDepartment } from 'app/entities/gen/department/department.model';
import { IJob } from 'app/entities/gen/job/job.model';
import { IEmployee } from 'app/entities/gen/employee/employee.model';

export interface IJobHistory {
  id: number;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  department?: Pick<IDepartment, 'id'> | null;
  job?: Pick<IJob, 'id'> | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewJobHistory = Omit<IJobHistory, 'id'> & { id: null };
