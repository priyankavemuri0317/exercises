import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { elementAt } from 'rxjs';
import { User } from '../entity/user';

@Component({
  selector: 'app-userdetails',
  templateUrl: './userdetails.component.html',
  styleUrls: ['./userdetails.component.css']
})
export class UserdetailsComponent implements OnInit {

  user: User = {
    id: 0,
    username: '',
    password: '',
    role: '',
    email: ''
  }

  // public currentUsername = sessionStorage.getItem('username');

  public showDetails = false;

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.getUserDetails();
  }

  getUserDetails() {
    let currentUsername = sessionStorage.getItem('username');
    let address = "/api/session/" + currentUsername;
    let header = new Headers();
    let sessionId:any = sessionStorage.getItem('token');
    header.set("Authorization", sessionId);
    this.http.get<any>(address).subscribe(res => {
      if (res) {
        console.log(res);
        let user = res;
        let role = user.role;
        localStorage.setItem('userId', user.userId);
        localStorage.setItem('user-username', user.username);
        localStorage.setItem('user-password', user.password);
        localStorage.setItem('user-role', role.role_name);
        localStorage.setItem('user-email', user.email);
      }
    })
  }

  displayUserDetails() {
    let btnDetails = document.getElementById("userDetailsButton");

    if(!this.showDetails) {
      let newId:any = localStorage.getItem('userId');
      let newUsername:any = localStorage.getItem('user-username');
      let newPassword:any = localStorage.getItem('user-password');
      let newRole:any = localStorage.getItem('user-role');
      let newEmail:any = localStorage.getItem('user-email');

      this.user.id = newId;
      this.user.username = newUsername;
      this.user.password = newPassword;
      this.user.role = newRole;
      this.user.email = newEmail;

      btnDetails!.innerText = 'Click Me to Hide User Details!';
      this.showDetails = true;
    }else {
      this.user.id = 0;
      this.user.username = '';
      this.user.password = '';
      this.user.role = '';
      this.user.email = '';

      btnDetails!.innerText = 'Click Me to Show User Details!';
      this.showDetails = false;
    }
  }

}
