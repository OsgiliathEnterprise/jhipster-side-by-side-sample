import { IEmployee } from 'app/entities/gen/employee/employee.model';
import { ITask } from 'app/entities/gen/task/task.model';

export interface IJob {
  id: number;
  jobTitle?: string | null;
  minSalary?: number | null;
  maxSalary?: number | null;
  employee?: Pick<IEmployee, 'id'> | null;
  tasks?: Pick<ITask, 'id'>[] | null;
}

export type NewJob = Omit<IJob, 'id'> & { id: null };
