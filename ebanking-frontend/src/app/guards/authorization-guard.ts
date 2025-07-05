import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth';
import { inject } from '@angular/core';

export const authorizationGuard: CanActivateFn = (route, state) => {

   const router = inject(Router);
  const authService = inject(AuthService);
  if(authService.roles.includes('ADMIN')) {
    return true;
  }else{
    router.navigate(['/admin/notAuthorized']);
    return false;

  }

 
};
