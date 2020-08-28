import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserListComponent } from './component/user/user-list/user-list.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { SignUpComponent } from './component/user/user-session/sign-up/sign-up.component';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from './component/header/header.component';
import { FooterComponent } from './component/footer/footer.component';
import { SignInComponent } from './component/user/user-session/sign-in/sign-in.component';
import { SignOutComponent } from './component/user/user-session/sign-out/sign-out.component';
import { HttpInterceptorService } from './service/http-interceptor/http-interceptor.service';
import { UserDetailsComponent } from './component/user/user-details/user-details.component';
import { HomeComponent } from './component/home/home.component';
import { CalcComponent } from './component/calc/calc.component';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    SignUpComponent,
    HeaderComponent,
    FooterComponent,
    SignInComponent,
    SignOutComponent,
    UserDetailsComponent,
    HomeComponent,
    CalcComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    {
      provide:HTTP_INTERCEPTORS, useClass:HttpInterceptorService, multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
