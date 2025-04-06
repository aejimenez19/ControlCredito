import { Routes } from '@angular/router';

export const PAYMENTS_ROUTES: Routes = [
  { path: '', loadComponent: () => import('./list/list.component').then(m => m.ListComponent) },
  { path: 'create', loadComponent: () => import('./create/create.component').then(m => m.CreateComponent) },
];