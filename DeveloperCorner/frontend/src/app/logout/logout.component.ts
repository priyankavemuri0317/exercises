import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.logout();
  }

  logout() {
    let header = new Headers();
    let sessionId:any = sessionStorage.getItem('token');

    if(sessionId == null) {
      let msg:any = document.getElementById("message");
      msg.innerHTML = "You are not signed in!";
    }

    header.set("Authorization", sessionId);
    this.http.get<any>("/api/login").subscribe(res => {
      if (res) {
        console.log(res);
      }
    })

    localStorage.clear();
    sessionStorage.clear();
  }

}
