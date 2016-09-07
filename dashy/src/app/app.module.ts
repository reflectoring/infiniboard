import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import {routing} from './app.routing';
import { LinksComponent } from './dashboard/links/links.component';
import {DashboardService} from './dashboard/shared/dashboard.service';
import { PageNotImplementedComponent } from './page-not-implemented/page-not-implemented.component';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    LinksComponent,
    PageNotImplementedComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routing
  ],
  providers: [DashboardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
