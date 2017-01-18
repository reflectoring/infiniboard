import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {AppComponent} from './app.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {routing} from './app.routing';
import {DashboardService} from './dashboard/shared/dashboard.service';
import {PageNotImplementedComponent} from './page-not-implemented/page-not-implemented.component';
import {DashboardListComponent} from './dashboard/dashboard-list/dashboard-list.component';
import {DashboardDetailComponent} from './dashboard/dashboard-detail/dashboard-detail.component';
import {DashboardSidebarLinksComponent} from './dashboard/dashboard-sidebar-links/dashboard-sidebar-links.component';
import {WidgetService} from './dashboard/shared/widget.service';
import {PlatformStatusWidgetComponent} from './dashboard/widget/platform-status-widget/platform-status-widget.component';
import {StatusColorPipe} from './dashboard/widget/platform-status-widget/status-color.pipe';
import {StatusIconPipe} from './dashboard/widget/platform-status-widget/status-icon.pipe';
import {HashLocationStrategy, LocationStrategy} from '@angular/common';
import {InfoService} from './shared/info.service';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    PageNotImplementedComponent,
    DashboardListComponent,
    DashboardDetailComponent,
    DashboardSidebarLinksComponent,
    PlatformStatusWidgetComponent,
    StatusColorPipe,
    StatusIconPipe
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routing
  ],
  providers: [
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    DashboardService,
    WidgetService,
    InfoService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
