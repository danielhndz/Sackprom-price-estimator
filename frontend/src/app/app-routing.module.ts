import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './component/user/user-list/user-list.component';

import { AuthGaurdService } from './service/auth-gaurd/auth-gaurd.service';
import { SignUpComponent } from './component/user/user-session/sign-up/sign-up.component';
import { SignInComponent } from './component/user/user-session/sign-in/sign-in.component';
import { SignOutComponent } from './component/user/user-session/sign-out/sign-out.component';
import { HomeComponent } from './component/home/home.component';

const routes: Routes = [
  { path: '', redirectTo: 'sign-in', pathMatch: 'full' },
  { path: 'sign-in', component: SignInComponent },
  { path: 'home', component: HomeComponent, canActivate: [AuthGaurdService] },
  { path: 'user-list', component: UserListComponent, canActivate: [AuthGaurdService] },
  { path: 'sign-up', component: SignUpComponent },
  { path: 'sign-out', component: SignOutComponent, canActivate: [AuthGaurdService] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
