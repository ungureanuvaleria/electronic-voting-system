import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { ModalModule } from 'ngx-bootstrap';
import { HowToModalComponent } from './how-to-modal/how-to-modal.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { AdminAuthModalComponent } from './admin-auth-modal/admin-auth-modal.component';
import { VotingPageComponent } from './voting-page/voting-page.component';
import { CandidateComponent } from './candidate/candidate.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    HowToModalComponent,
    AdminPageComponent,
    AdminAuthModalComponent,
    VotingPageComponent,
    CandidateComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ModalModule.forRoot(),
    ReactiveFormsModule

  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
