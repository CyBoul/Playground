import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = '/api/auth';
  private token: string | null = null;

  constructor(private http: HttpClient) {}

  initAppAuth(): Observable<void> {  // ToRmv  // just for testing purposes
    return this.http
      .post<{ token: string }>(
        '/api/auth/login', 
        {
          email: 'frontend-app',
          password: 'frontend-secret'
        }
      )
      .pipe(
        tap(({ token }) => {
          this.token = token;
          //this.saveToken(token) // optional, for persistence 
        }),
        map(() => void 0), // convert to Observable<void>
        catchError(err => {
          console.error('Auto-login failed', err);
          return of(void 0); // app still starts even if login fails
        })
      );
  }

  login(email: string, password: string): Observable<any> {
    return this.http
        .post<{ token: string }>(`${this.apiUrl}/login`, { email, password })
          .pipe(
            tap(({ token }) => {
              this.token = token;
            }),
            map(() => void 0), // convert to Observable<void>
            catchError(err => {
              console.error('Auto-login failed', err);
              return of(void 0); // app still starts even if login fails
            })
          );
  }

  //saveToken(token: string) { localStorage.setItem('jwt', token); }

  getToken() {
    return this.token;
    //|| localStorage.getItem('jwt'); // ToRmv
  }

  logout() {
    this.token = null;
    localStorage.removeItem('jwt'); // ToRmv
  }
}
