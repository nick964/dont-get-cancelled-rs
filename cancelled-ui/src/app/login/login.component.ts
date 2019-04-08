import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login/login.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { OverviewListComponent} from '../overview-list/overview-list.component';
import { CancelledtweetService } from '../cancelledtweet.service';
import { FireauthService } from '../fireauth.service';




@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user = null;
  


  constructor(private http: HttpClient, private loginService: LoginService, private fireAuthService: FireauthService,
    private cancelledTweetService: CancelledtweetService, ) { }

  ngOnInit() {
  }

  signInWithTwitter() {
    this.fireAuthService.signInWithTwitter().then((res) => { 
      var credential = res.credential;
      console.log(credential);
 
      
    })
  .catch((err) => console.log(err));
}
  

  callToken() {
    console.log('called function');

    this.loginService.getToken().subscribe(data => {

      if(data.isLoggedIn === "TRUE") {
        console.log('user is logged in');
      } else {
      if(data.authenticationURL.length > 0) {
        console.log(data);
        console.log(data.authenticationURL);
        window.location.href = data.authenticationURL;
      }
    }
    })
  }

}
