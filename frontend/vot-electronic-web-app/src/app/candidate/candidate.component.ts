import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-candidate',
  templateUrl: './candidate.component.html',
  styleUrls: ['./candidate.component.css']
})
export class CandidateComponent implements OnInit {

  @Input() name: string;
  @Input() imgUrl: string;
  constructor() {}

  ngOnInit(): void {
  }

}
