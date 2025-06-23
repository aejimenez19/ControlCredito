import { Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { Register } from './auth/register/register';
import { AuthLayout } from './layouts/auth-layout/auth-layout';
import { MainLayout } from './layouts/main-layout/main-layout';

export const routes: Routes = [
  {
    path: '',
    component: AuthLayout,
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: Login },
      { path: 'register', component: Register },
    ],
  },
  {
    path: '',
    component: MainLayout,
    children: [
      { path: 'dashboard', redirectTo: 'dashboard/main', pathMatch: 'full' },
      {
        path: 'dashboard/main',
        loadComponent: () =>
          import('./dashboard/dashboard').then((m) => m.Dashboard),
      },
      {
        path: 'dashboard/prestador',
        loadComponent: () =>
          import('./dashboard/sections/prestamista/prestamista').then(
            (m) => m.Prestamista
          ),
      },
    ],
  },
  { path: '**', redirectTo: '' },
];
