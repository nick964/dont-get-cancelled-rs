import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { Location } from '@angular/common';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Tweet } from '../Tweet';
import { CancelledtweetService } from '../cancelledtweet.service';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { trigger, state, style, animate, transition } from '@angular/animations';


@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
  encapsulation: ViewEncapsulation.None,
  animations: [trigger('fadeInOut', [
    state('void', style({
      opacity: 0
    })),
    transition('void <=> *', animate(1000)),
  ]),]
})
export class AuthComponent implements OnInit {
  closeResult: string;
  error: string;
  loading: boolean = true;
  
  private tweets: Tweet[];

  constructor(private route: ActivatedRoute,
    private location: Location,
    private http: HttpClient,
    private cancelledTweetService: CancelledtweetService,
    private modalService: NgbModal ) { }

    public oauthToken;
    public oauthVerifier;


  ngOnInit(): void {
    this.loading = true;
    console.log('Succesfully authenticated');
    
    this.route.queryParams.subscribe(params => {
      this.oauthToken = params['oauth_token'];
      this.oauthVerifier = params['oauth_verifier'];
    });

    console.log("oauth token is " + this.oauthToken);
    console.log("oauth verifier is " + this.oauthVerifier);
    
    console.log("loading is " + this.loading);
    if(this.oauthToken && this.oauthVerifier) {
     this.cancelledTweetService.getCancelledTweets(this.oauthToken, this.oauthVerifier).subscribe(
        tweets => {
          this.tweets = tweets;
          this.loading = false;
          },
        error => {
          this.error = error;
          this.loading = false;
        }
       );

     console.log(this.tweets);

    } else {
      //couldn't get auth parameters from the URL, error happened during authentication
      console.log('ERROR: Couldnt retrieve token from Url');
    }


  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      //heres the success
      this.closeResult = `Closed with: ${result}`;
      const tweet: Tweet = result as Tweet;
      let deletedTweet: Tweet = null;
      console.log(tweet);
      this.handleTweetDelete(tweet);
      
      
    }, (reason) => {
      //heres the failure
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
    console.log(this.closeResult);
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }

  private handleTweetDelete(tweet: Tweet) {
    this.cancelledTweetService.deleteTweet(tweet).subscribe();
    this.tweets = this.tweets.filter(t => t !== tweet);
  }

}
