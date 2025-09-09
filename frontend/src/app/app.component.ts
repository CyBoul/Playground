import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Login } from "./login.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  /*template: `<h1>Welcome to My Angular 20.2 App!</h1>
    <p>{{ message }}</p>`,*/
  styles: [`
    h1 { color: #1976d2;  font-family: Arial, sans-serif; }
     p { font-size: 16px; }
  `],
  imports: [Login]
})
export class AppComponent {
  // message = 'This is a working Angular component!';
  title = 'Gugu Demo';
  data = {}  as any;
  constructor(private http: HttpClient) {
    // http.get('/api/gugu')
    //     .subscribe(data => this.data = data);
    this.http.get('/api/gugu').subscribe({
      next: data => {
        console.log('Response:', data);
        this.data = data;
      },
      error: err => console.error('Error:', err)
    });
  }
}
