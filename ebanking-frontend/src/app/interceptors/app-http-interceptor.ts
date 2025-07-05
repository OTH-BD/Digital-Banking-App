import { inject } from '@angular/core';
import { AuthService } from './../services/auth';
import { HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const appHttpInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  // Ne pas ajouter le token pour /auth/login
  if (req.url.includes('/auth/login')) {
    return next(req);
  }

  const token = authService.accessToken;
  if (token) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(authReq).pipe(
      catchError((error) => {
        if (error.status === 401) {
          // Rediriger vers la page de connexion ou gérer l'erreur
          authService.logout();
       }
        return throwError(() => error);
      })
    );
  }

  return next(req);
};