import {Routes, RouterModule} from '@angular/router';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {DashboardListComponent} from './dashboard/dashboard-list/dashboard-list.component';
import {DashboardDetailComponent} from './dashboard/dashboard-detail/dashboard-detail.component';


export const appRoutes: Routes = [
  {path: '', component: DashboardListComponent},
  {
    path: 'dashboards', children: [
    {path: '', component: DashboardListComponent},
    {path: ':dashboardId', component: DashboardDetailComponent},
  ]
  },
  {path: '**', component: PageNotFoundComponent}
];
export const routing = RouterModule.forRoot(appRoutes);

