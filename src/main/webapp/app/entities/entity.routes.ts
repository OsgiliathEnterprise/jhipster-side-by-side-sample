import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'country',
    data: { pageTitle: 'Countries' },
    loadChildren: () => import('./gen/country/country.routes'),
  },
  {
    path: 'department',
    data: { pageTitle: 'Departments' },
    loadChildren: () => import('./gen/department/department.routes'),
  },
  {
    path: 'employee',
    data: { pageTitle: 'Employees' },
    loadChildren: () => import('./gen/employee/employee.routes'),
  },
  {
    path: 'job',
    data: { pageTitle: 'Jobs' },
    loadChildren: () => import('./gen/job/job.routes'),
  },
  {
    path: 'job-history',
    data: { pageTitle: 'JobHistories' },
    loadChildren: () => import('./gen/job-history/job-history.routes'),
  },
  {
    path: 'location',
    data: { pageTitle: 'Locations' },
    loadChildren: () => import('./gen/location/location.routes'),
  },
  {
    path: 'region',
    data: { pageTitle: 'Regions' },
    loadChildren: () => import('./gen/region/region.routes'),
  },
  {
    path: 'task',
    data: { pageTitle: 'Tasks' },
    loadChildren: () => import('./gen/task/task.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
