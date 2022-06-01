import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddblogComponent } from './addblog/addblog.component';
import { AuthenticationGuard } from './authentication.guard';
import { BlogComponent } from './blog/blog.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { MessagesComponent } from './messages/messages.component';
import { NavbarComponent } from './navbar/navbar.component';
import { QuestionsComponent } from './questions/questions.component';
import { RegisterComponent } from './register/register.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';

const routes: Routes = [
  {path: '', canActivate:[AuthenticationGuard], children: [
    {path: '', component: HomeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'logout', component: LogoutComponent},
    {path: 'register', component: RegisterComponent},

    {path: 'devcorn', component: NavbarComponent,
      children: [
        {path: 'userdashboard', component: UserDashboardComponent},
        {path: 'questions', component: QuestionsComponent},
        {path: 'messages', component: MessagesComponent},
        {path: 'blog', component: BlogComponent},
        {path: 'addblog', component: AddblogComponent},
        
      ]
    },

 
    {path: '**', redirectTo: ''}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
