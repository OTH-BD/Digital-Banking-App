import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth';
import { inject } from '@angular/core';

export const authenticationGuard: CanActivateFn = (route, state) => {
    const router = inject(Router);
    const authService = inject(AuthService);
    if(authService.isAuthenticated== true){
      return true;
    }else {
      router.navigate(['/login']);
      return false;
    }
  
};
