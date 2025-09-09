import { ApplicationConfig, inject, provideAppInitializer, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideHttpClient, HTTP_INTERCEPTORS, withInterceptorsFromDi, HttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { JwtInterceptor } from './filters/jwt.interceptor';
import { routes } from './app.routes';
import { AuthService } from './services/auth.service';
import { firstValueFrom } from 'rxjs';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withInterceptorsFromDi()),
    { provide: HTTP_INTERCEPTORS, useClass:JwtInterceptor, multi:true },
    provideAppInitializer(async () => {
      const authService = inject(AuthService);
      await firstValueFrom(authService.initAppAuth());
    }),
  ]
};
