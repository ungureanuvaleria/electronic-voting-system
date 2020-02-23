
import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Candidate} from '../models/Candidate';

@Injectable({
  providedIn: 'root'
})
export class CandidateService {
  rootUrl: string;

  constructor(private httpClient: HttpClient) {
    this.rootUrl = 'http://localhost:8080/api/candidate';
  }

  public getAllCandidates(): Observable<Candidate[]> {
    return this.httpClient.get<Candidate[]>(this.rootUrl);
  }

}
