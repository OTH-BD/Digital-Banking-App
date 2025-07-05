import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from "./navbar/navbar";
import { AuthService } from './services/auth';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected title = 'ebanking-frontend';
  constructor(private authService: AuthService) { }
  ngOnInit(): void {
   
    this.authService.loadJwtTokenFromLocalStorage();
  }
}
