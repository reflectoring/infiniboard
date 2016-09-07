import {Routes, RouterModule} from '@angular/router';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';

export const appRoutes: Routes = [
  {
    path: '**', component: PageNotFoundComponent
  }
];
export const routing = RouterModule.forRoot(appRoutes);

