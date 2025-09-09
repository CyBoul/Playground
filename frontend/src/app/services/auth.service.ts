import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/api/auth';
  private token: string | null = null;

  constructor(private http: HttpClient) {}

  initAppAuth(): Observable<void> {
    return this.http
      .post<{ token: string }>(
        '/api/auth/login', 
        {
          username: 'frontend-app',
          password: 'frontend-secret'
        }
      )
      .pipe(
        tap(({ token }) => {
          this.token = token;
          this.saveToken(token) // optional, for persistence
        }),
        map(() => void 0), // convert to Observable<void>
        catchError(err => {
          console.error('Auto-login failed', err);
          return of(void 0); // app still starts even if login fails
        })
      );
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { username, password });
  }

  saveToken(token: string) {
    localStorage.setItem('jwt', token);
  }

  getToken() {
    return this.token || localStorage.getItem('jwt');
  }

  logout() {
    this.token = null;
    localStorage.removeItem('jwt');
  }
}
