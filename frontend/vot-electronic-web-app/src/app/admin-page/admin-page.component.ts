import {Component, OnDestroy, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../services/user.service';
import {User} from "../models/User";

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit, OnDestroy {

  websocketEndpoint = 'http://localhost:8080/votingSystemApp';
  fingerprintScansBroker = '/user/fingerprints';
  stompClient: any;
  showHideAddingForm: boolean;
  showHideDeletingForm: boolean;
  addUserForm: FormGroup;
  deleteUserForm: FormGroup;
  message = '';
  users: User[];

  constructor(private authService: AuthService,
              private router: Router,
              private formBuilder: FormBuilder,
              private userService: UserService) { }

  ngOnInit() {
    this.updateMessage = this.updateMessage.bind(this);
    this.connect();
    this.addUserForm = this.formBuilder.group({
      firstName: new FormControl(null, Validators.required),
      lastName: new FormControl(null, Validators.required)
    });
    this.deleteUserForm = this.formBuilder.group({
      user: new FormControl(null, Validators.required)
    });
    this.userService.getAll()
      .toPromise()
      .then(users => {
        this.users = users;
      });
  }

  ngOnDestroy() {
    this.disconnect();
  }

  logout() {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }

  addNewFingerprint() {
    this.message = 'Scan your finger!';
    this.stompClient.send('/app/workstationMessages', {}, 'INSERT');
    this.showHideAddingForm = true;
  }

  deleteFingerprint() {
    this.message = 'Select user to delete';
    this.showHideDeletingForm = true;
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
        const responseArray: string[] = message.body.split(',');
        if (responseArray[0].includes('SCAN_AGAIN')) {
          this.updateMessage('Scan your finger again!');
        } else if (responseArray[0].includes('INSERTED')) {
          this.updateMessage('Please, fill the form to finish adding a new user!');
          localStorage.setItem('new_finger_id', responseArray[1]);
        } else if (responseArray[0].includes('EXISTS')) {
          alert('This fingerprint already exists!');
          this.showHideAddingForm = false;
          this.stompClient.send('/app/workstationMessages', {}, 'READ');
        } else if (responseArray[0].includes('DELETED')) {
          this.showHideDeletingForm = false;
          this.stompClient.send('/app/workstationMessages', {}, 'READ');
          console.log('DELETING ' + this.deleteUserForm.controls.user.value);
          this.userService.deleteUser(this.deleteUserForm.controls.user.value);
          alert('Fingerprint successfully deleted!');
        } else if (responseArray[0].includes('NON_EXISTENT')) {
          alert('Fingerprint for this user was not found!');
          this.showHideDeletingForm = false;
          this.stompClient.send('/app/workstationMessages', {}, 'READ');
        } else {
          console.log('SHITTY POOPY PANTS');
        }
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

  addUser(userForm) {
    this.stompClient.send('/app/workstationMessages', {}, 'READ');
    const user = {
      name: userForm.firstName,
      surname: userForm.lastName,
      fingerId: localStorage.getItem('new_finger_id')
    };

    this.userService.addUser(user)
      .toPromise()
      .then(responseMessage => {
        alert('Successfully added new user!');
      });

    this.message = '';
    this.showHideAddingForm = false;
  }

  deleteUser(userForm) {
    this.stompClient.send('/app/workstationMessages', {}, 'DELETE,' + userForm.user.fingerId);
  }

  updateMessage(message) {
    this.message = message;
  }
}
