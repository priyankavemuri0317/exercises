import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model: any = {};
  sessionId: any = "";

  constructor(
    private router: Router,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
  }

  login() {
    let url = 'api/login';
    this.http.post<any>(url, {
      username: this.model.username,
      password: this.model.password
    }).subscribe(res => {
        if (res) {
          let session = res;
          this.sessionId = session.session_id;
          console.log(this.sessionId);

          sessionStorage.setItem(
            'token',
            this.sessionId
          );

          sessionStorage.setItem(
            'username',
            this.model.username
          );
          this.router.navigate(['']);
        } else {
          alert("Authentication failed.")
        }
    });
  }

  loginHtml() {
    this.router.navigate(['/devcorn/userdashboard']);
  }
  
}
