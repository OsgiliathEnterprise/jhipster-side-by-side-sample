import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IJobHistory, NewJobHistory } from '../job-history.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJobHistory for edit and NewJobHistoryFormGroupInput for create.
 */
type JobHistoryFormGroupInput = IJobHistory | PartialWithRequiredKeyOf<NewJobHistory>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IJobHistory | NewJobHistory> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

type JobHistoryFormRawValue = FormValueOf<IJobHistory>;

type NewJobHistoryFormRawValue = FormValueOf<NewJobHistory>;

type JobHistoryFormDefaults = Pick<NewJobHistory, 'id' | 'startDate' | 'endDate'>;

type JobHistoryFormGroupContent = {
  id: FormControl<JobHistoryFormRawValue['id'] | NewJobHistory['id']>;
  startDate: FormControl<JobHistoryFormRawValue['startDate']>;
  endDate: FormControl<JobHistoryFormRawValue['endDate']>;
  department: FormControl<JobHistoryFormRawValue['department']>;
  job: FormControl<JobHistoryFormRawValue['job']>;
  employee: FormControl<JobHistoryFormRawValue['employee']>;
};

export type JobHistoryFormGroup = FormGroup<JobHistoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JobHistoryFormService {
  createJobHistoryFormGroup(jobHistory: JobHistoryFormGroupInput = { id: null }): JobHistoryFormGroup {
    const jobHistoryRawValue = this.convertJobHistoryToJobHistoryRawValue({
      ...this.getFormDefaults(),
      ...jobHistory,
    });
    return new FormGroup<JobHistoryFormGroupContent>({
      id: new FormControl(
        { value: jobHistoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      startDate: new FormControl(jobHistoryRawValue.startDate),
      endDate: new FormControl(jobHistoryRawValue.endDate),
      department: new FormControl(jobHistoryRawValue.department, {
        validators: [Validators.required],
      }),
      job: new FormControl(jobHistoryRawValue.job, {
        validators: [Validators.required],
      }),
      employee: new FormControl(jobHistoryRawValue.employee, {
        validators: [Validators.required],
      }),
    });
  }

  getJobHistory(form: JobHistoryFormGroup): IJobHistory | NewJobHistory {
    return this.convertJobHistoryRawValueToJobHistory(form.getRawValue() as JobHistoryFormRawValue | NewJobHistoryFormRawValue);
  }

  resetForm(form: JobHistoryFormGroup, jobHistory: JobHistoryFormGroupInput): void {
    const jobHistoryRawValue = this.convertJobHistoryToJobHistoryRawValue({ ...this.getFormDefaults(), ...jobHistory });
    form.reset(
      {
        ...jobHistoryRawValue,
        id: { value: jobHistoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): JobHistoryFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      startDate: currentTime,
      endDate: currentTime,
    };
  }

  private convertJobHistoryRawValueToJobHistory(
    rawJobHistory: JobHistoryFormRawValue | NewJobHistoryFormRawValue,
  ): IJobHistory | NewJobHistory {
    return {
      ...rawJobHistory,
      startDate: dayjs(rawJobHistory.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawJobHistory.endDate, DATE_TIME_FORMAT),
    };
  }

  private convertJobHistoryToJobHistoryRawValue(
    jobHistory: IJobHistory | (Partial<NewJobHistory> & JobHistoryFormDefaults),
  ): JobHistoryFormRawValue | PartialWithRequiredKeyOf<NewJobHistoryFormRawValue> {
    return {
      ...jobHistory,
      startDate: jobHistory.startDate ? jobHistory.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: jobHistory.endDate ? jobHistory.endDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
