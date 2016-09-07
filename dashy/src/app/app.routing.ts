import {Routes, RouterModule} from '@angular/router';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {PageNotImplementedComponent} from './page-not-implemented/page-not-implemented.component';

export const appRoutes: Routes = [
  {
    path: 'dashboards', children: [
    {path: '', component: PageNotImplementedComponent},
    {path: ':dashboardId', component: PageNotImplementedComponent},
  ]
  },
  {path: '**', component: PageNotFoundComponent}
];
export const routing = RouterModule.forRoot(appRoutes);

