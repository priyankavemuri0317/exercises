import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  model: any = {};
  model2: any = {
    id: 0,
    rolename: 'LEARNER'
  };
  model3: any = {
    timeAvailable: 'morning'
  }

  constructor(
    private router: Router,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
  }

  register() {
    if(this.model2.rolename == 'LEARNER') {
      this.model2.id = 3;
    }else{
      this.model2.id = 2;
    }

    let url = 'api/register';
    this.http.post<any>(url, {
      username: this.model.username,
      password: this.model.password,
      role: this.model2,
      timeAvailable: this.model3.timeAvailable,
      enabled: true,
      email: this.model.email,
      credentialsNonExpired: true,
      accountNonExpired: true,
      accountNonLocked: true
    }).subscribe(res => {
        if (res) {
          console.log(res);
          alert("Account Created.");
          this.router.navigate(['']);
        } else {
          alert("Authentication failed.")
        }
    });
  }

}
