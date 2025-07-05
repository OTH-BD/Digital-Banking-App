import { Component } from '@angular/core';
import { Navbar } from "../navbar/navbar";
import { RouterModule, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-template',
  imports: [CommonModule, RouterModule, Navbar, RouterOutlet],
  templateUrl: './admin-template.html',
  styleUrl: './admin-template.css'
})
export class AdminTemplateComponent {

}
