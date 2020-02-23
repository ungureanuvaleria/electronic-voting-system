import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BallotPaperRegistration} from '../models/BallotPaperRegistration';

@Injectable({
  providedIn: 'root'
})
export class BallotPaperRegistrationService {
  rootUrl: string;

  constructor(private httpClient: HttpClient) {
    this.rootUrl = 'http://localhost:8080/api/ballotPaperRegistration';
  }

  public registerBallotPaper(ballotPaperRegistration: BallotPaperRegistration) {
    return this.httpClient.post(this.rootUrl, ballotPaperRegistration);
  }
}
