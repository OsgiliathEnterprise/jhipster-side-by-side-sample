import dayjs from 'dayjs/esm';

import { IJobHistory, NewJobHistory } from './job-history.model';

export const sampleWithRequiredData: IJobHistory = {
  id: 16459,
};

export const sampleWithPartialData: IJobHistory = {
  id: 11960,
};

export const sampleWithFullData: IJobHistory = {
  id: 26932,
  startDate: dayjs('2025-07-05T02:54'),
  endDate: dayjs('2025-07-05T07:23'),
};

export const sampleWithNewData: NewJobHistory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
