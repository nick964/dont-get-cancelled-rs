import { Injectable } from '@angular/core';
import { Router } from "@angular/router";
import { AngularFireAuth } from 'angularfire2/auth';
import * as firebase from 'firebase/app';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class FireauthService {
  private user: Observable<firebase.User>;

  constructor(private _firebaseAuth: AngularFireAuth, private router: Router) { 
    this.user = _firebaseAuth.authState;
}

signInWithTwitter() {
  return this._firebaseAuth.auth.signInWithPopup(
    new firebase.auth.TwitterAuthProvider()
  )
}

}
