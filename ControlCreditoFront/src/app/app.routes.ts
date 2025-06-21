import { Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { Register } from './auth/register/register';

export const routes: Routes = [
    {
        path: 'login',
        component: Login
    },
    {
        path: 'register',
        component: Register
    },
    {
        path: 'dashboard',
        loadComponent: () => import('./dashboard/dashboard').then(m => m.Dashboard),
        children: [
            {path: 'prestador', loadComponent: () => import('./dashboard/sections/prestamista/prestamista').then(m => m.Prestamista) }
        ]
    },
    { path: '', redirectTo: 'login', pathMatch: 'full' }
];
