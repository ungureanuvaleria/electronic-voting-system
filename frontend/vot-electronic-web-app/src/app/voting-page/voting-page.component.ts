import { Component, OnInit } from '@angular/core';
import {CandidateComponent} from '../candidate/candidate.component';
import {Candidate} from '../models/Candidate';
import {CandidateService} from '../services/candidate.service';
import {BallotPaperRegistration} from '../models/BallotPaperRegistration';
import {BallotPaperRegistrationService} from '../services/ballot-paper-registration.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-voting-page',
  templateUrl: './voting-page.component.html',
  styleUrls: ['./voting-page.component.css']
})
export class VotingPageComponent implements OnInit {
  candidateList: Candidate[];

 /* candidates: { name: string, imgUrl: string }[] = [
    {name: 'Candidat1', imgUrl: '../../assets/images/handshake.png'},
    {name: 'candidat2', imgUrl: '../../assets/images/stema.PNG'}];
*/
  constructor(private candidateService: CandidateService,
              private ballotPaperRegistrationService: BallotPaperRegistrationService,
              private router: Router) {}

  ngOnInit(): void {
    this.candidateService.getAllCandidates()
      .toPromise()
      .then(candidates => {
        this.candidateList = candidates;
      })
      .catch(error => {
        console.error(error);
      });
  }

  public vote(candidate: Candidate) {
    console.log(JSON.stringify(candidate));
    const ballotPaperRegistration: BallotPaperRegistration = {
      scanId: +localStorage.getItem('scanId'),
      candidate
    };
    this.ballotPaperRegistrationService.registerBallotPaper(ballotPaperRegistration)
      .toPromise()
      .then(() => {
        localStorage.removeItem('scanId');
        this.router.navigateByUrl('thankYouPage');
      })
      .catch(error => {
        console.error(error);
        console.log('Failed to register ballot-paper!');
      });
  }
}
