import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login implements OnInit {
  loginFormGroup!: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService,private router :Router) { }

  ngOnInit() {
    this.loginFormGroup = this.fb.group({
      username: this.fb.control('', [Validators.required]),
      password: this.fb.control('', [Validators.required])
    });
  }

  handleLogin() {
    let username = this.loginFormGroup.value.username;
    let password = this.loginFormGroup.value.password;
    if (this.loginFormGroup.valid) {
      this.authService.login(username, password).subscribe({
        next: (data) => {
          this.authService.loadProfile(data);
          this.router.navigateByUrl("/admin")
        },
        error: (error) => {
          console.error('Login failed:', error);
        }
      });
    }
  }

}
