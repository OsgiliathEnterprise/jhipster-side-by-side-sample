import { IRegion } from 'app/entities/gen/region/region.model';

export interface ICountry {
  id: number;
  countryName?: string | null;
  region?: Pick<IRegion, 'id'> | null;
}

export type NewCountry = Omit<ICountry, 'id'> & { id: null };
