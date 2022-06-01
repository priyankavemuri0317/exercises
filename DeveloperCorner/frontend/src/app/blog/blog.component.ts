import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css']
})
export class BlogComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  viewBlog() {
    this.router.navigate(['/devcorn/addblog']);
  }

  addBlog() {
    this.router.navigate(['/devcorn/addblog']);
  }

}
