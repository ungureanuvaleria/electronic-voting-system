import { Component, OnInit } from '@angular/core';
import {CandidateComponent} from '../candidate/candidate.component';


@Component({
  selector: 'app-voting-page',
  templateUrl: './voting-page.component.html',
  styleUrls: ['./voting-page.component.css']
})
export class VotingPageComponent implements OnInit {
  candidates: { name: string, imgUrl: string }[] = [
    {name: 'Candidat1', imgUrl: '../../assets/images/handshake.png'},
    {name: 'candidat2', imgUrl: '../../assets/images/stema.PNG'}];
  constructor() {}

  ngOnInit(): void {
  }


}
