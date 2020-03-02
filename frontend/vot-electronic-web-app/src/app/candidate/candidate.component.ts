import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {Candidate} from "../models/Candidate";

@Component({
  selector: 'app-candidate',
  templateUrl: './candidate.component.html',
  styleUrls: ['./candidate.component.css']
})
export class CandidateComponent implements OnInit {

  @Input() name: string;
  @Input() imgUrl: string;
  @Input() surname: string;
  @Input() party: string;
  @Input() candidate: Candidate;
  @Output() candidateSelected = new EventEmitter();
  constructor() {}

  ngOnInit(): void {
  }

  public selectCandidate() {
    this.candidateSelected.emit(this.candidate);
  }
}
