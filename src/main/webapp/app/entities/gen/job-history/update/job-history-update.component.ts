import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDepartment } from 'app/entities/gen/department/department.model';
import { DepartmentService } from 'app/entities/gen/department/service/department.service';
import { IJob } from 'app/entities/gen/job/job.model';
import { JobService } from 'app/entities/gen/job/service/job.service';
import { IEmployee } from 'app/entities/gen/employee/employee.model';
import { EmployeeService } from 'app/entities/gen/employee/service/employee.service';
import { JobHistoryService } from '../service/job-history.service';
import { IJobHistory } from '../job-history.model';
import { JobHistoryFormGroup, JobHistoryFormService } from './job-history-form.service';

@Component({
  selector: 'jhi-job-history-update',
  templateUrl: './job-history-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class JobHistoryUpdateComponent implements OnInit {
  isSaving = false;
  jobHistory: IJobHistory | null = null;

  departmentsCollection: IDepartment[] = [];
  jobsCollection: IJob[] = [];
  employeesCollection: IEmployee[] = [];

  protected jobHistoryService = inject(JobHistoryService);
  protected jobHistoryFormService = inject(JobHistoryFormService);
  protected departmentService = inject(DepartmentService);
  protected jobService = inject(JobService);
  protected employeeService = inject(EmployeeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: JobHistoryFormGroup = this.jobHistoryFormService.createJobHistoryFormGroup();

  compareDepartment = (o1: IDepartment | null, o2: IDepartment | null): boolean => this.departmentService.compareDepartment(o1, o2);

  compareJob = (o1: IJob | null, o2: IJob | null): boolean => this.jobService.compareJob(o1, o2);

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobHistory }) => {
      this.jobHistory = jobHistory;
      if (jobHistory) {
        this.updateForm(jobHistory);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobHistory = this.jobHistoryFormService.getJobHistory(this.editForm);
    if (jobHistory.id !== null) {
      this.subscribeToSaveResponse(this.jobHistoryService.update(jobHistory));
    } else {
      this.subscribeToSaveResponse(this.jobHistoryService.create(jobHistory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobHistory>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(jobHistory: IJobHistory): void {
    this.jobHistory = jobHistory;
    this.jobHistoryFormService.resetForm(this.editForm, jobHistory);

    this.departmentsCollection = this.departmentService.addDepartmentToCollectionIfMissing<IDepartment>(
      this.departmentsCollection,
      jobHistory.department,
    );
    this.jobsCollection = this.jobService.addJobToCollectionIfMissing<IJob>(this.jobsCollection, jobHistory.job);
    this.employeesCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesCollection,
      jobHistory.employee,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.departmentService
      .query({ filter: 'jobhistory-is-null' })
      .pipe(map((res: HttpResponse<IDepartment[]>) => res.body ?? []))
      .pipe(
        map((departments: IDepartment[]) =>
          this.departmentService.addDepartmentToCollectionIfMissing<IDepartment>(departments, this.jobHistory?.department),
        ),
      )
      .subscribe((departments: IDepartment[]) => (this.departmentsCollection = departments));

    this.jobService
      .query({ filter: 'jobhistory-is-null' })
      .pipe(map((res: HttpResponse<IJob[]>) => res.body ?? []))
      .pipe(map((jobs: IJob[]) => this.jobService.addJobToCollectionIfMissing<IJob>(jobs, this.jobHistory?.job)))
      .subscribe((jobs: IJob[]) => (this.jobsCollection = jobs));

    this.employeeService
      .query({ filter: 'jobhistory-is-null' })
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.jobHistory?.employee),
        ),
      )
      .subscribe((employees: IEmployee[]) => (this.employeesCollection = employees));
  }
}
