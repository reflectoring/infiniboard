import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {AppComponent} from './app.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {routing} from './app.routing';
import {LinksComponent} from './dashboard/links/links.component';
import {DashboardService} from './dashboard/shared/dashboard.service';
import {PageNotImplementedComponent} from './page-not-implemented/page-not-implemented.component';
import {DashboardListComponent} from './dashboard/dashboard-list/dashboard-list.component';
import {DashboardDetailComponent} from './dashboard/dashboard-detail/dashboard-detail.component';
import {DashboardSidebarLinksComponent} from './dashboard/dashboard-sidebar-links/dashboard-sidebar-links.component';
import {WidgetService} from './widget/shared/widget.service';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    LinksComponent,
    PageNotImplementedComponent,
    DashboardListComponent,
    DashboardDetailComponent,
    DashboardSidebarLinksComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routing
  ],
  providers: [
    DashboardService,
    WidgetService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
