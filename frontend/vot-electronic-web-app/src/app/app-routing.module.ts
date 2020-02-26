import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { AuthGuard } from './auth.guard';
import {WorkstationLoginComponent} from './workstation-login/workstation-login.component';
import {VotingPageComponent} from './voting-page/voting-page.component';
import {ThankYouPageComponent} from "./thank-you-page/thank-you-page.component";


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'workstationLogin' },
  { path: 'login', component: LoginPageComponent },
  { path: 'admin', component: AdminPageComponent, canActivate: [AuthGuard] },
  { path: 'workstationLogin', component: WorkstationLoginComponent },
  { path: 'votingPage', component: VotingPageComponent},
  { path: 'thankYouPage', component: ThankYouPageComponent},
  { path: '**', component: WorkstationLoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
