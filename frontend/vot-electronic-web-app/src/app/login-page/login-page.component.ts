import {Component, OnDestroy, OnInit} from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {Router} from "@angular/router";


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit, OnDestroy {

  websocketEndpoint = 'http://localhost:8080/votingSystemApp';
  fingerprintScansBroker = '/user/fingerprints';
  stompClient: any;

  constructor(private router: Router) { }

  ngOnInit() {
    this.connect();
  }

  ngOnDestroy() {
    this.disconnect();
  }

  private connect() {
    console.log('Connecting to server via websockets to observe new scans...');
    const ws = new SockJS(this.websocketEndpoint);
    this.stompClient = Stomp.over(ws);
    const headers = {
      workstation_id: localStorage.getItem('workstationId')
    };
    this.stompClient.connect(headers, () => {
      this.stompClient.subscribe(this.fingerprintScansBroker, message => {
        console.log(message.body);
        localStorage.setItem('scanId', message.body);
        this.router.navigateByUrl('votingPage');
      });
    }, this.errorCallback.bind(this));
  }

  private errorCallback(error) {
    console.error(error);
    setTimeout(() => {
      this.connect();
    }, 1000);
  }

  private disconnect() {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
  }
}
