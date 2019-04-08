import { Component, ViewEncapsulation } from '@angular/core';
import { environment }  from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  private apiUrl = environment.apiUrl;
  title = 'Don\'t get cancelled.';

  constructor(
    
  ) { }


}
