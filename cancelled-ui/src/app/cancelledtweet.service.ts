import { Injectable } from '@angular/core';
import { Tweet } from './Tweet';
import { HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment }  from 'src/environments/environment';




@Injectable({
  providedIn: 'root'
})
export class CancelledtweetService {
  private apiUrl = environment.apiUrl;
  private apiUrlTweets = this.apiUrl + 'success';
  private apiUrlDelete = this.apiUrl + 'delete';

  constructor( private http: HttpClient) { }

  getCancelledTweets(oAuthToken:string,  oAuthVerifier:string): Observable<Tweet[]> {

    let params = new HttpParams().set("oauth_token", oAuthToken).set('oauth_verifier', oAuthVerifier);
    return this.http.get<Tweet[]>(this.apiUrlTweets, {params}).pipe(
      catchError(this.handleError('getCancelledTweets', []))
    );
  }

  deleteTweet(tweet:Tweet): Observable<{}> {
    console.log("here is the passed in thing: " + tweet.id);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return this.http.post<Tweet>(this.apiUrlDelete, tweet, httpOptions);
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a HeroService message with the MessageService */
  private log(message: string) {
    console.log(message);
  }

}
