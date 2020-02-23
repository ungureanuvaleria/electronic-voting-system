import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {WorkstationAuthService} from "../services/workstation-auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-workstation-login',
  templateUrl: './workstation-login.component.html',
  styleUrls: ['./workstation-login.component.css']
})
export class WorkstationLoginComponent implements OnInit {

  workstationLoginForm: FormGroup;
  constructor(private formBuilder: FormBuilder,
              private workstationAuthService: WorkstationAuthService,
              private router: Router) { }

  ngOnInit(): void {
    this.workstationLoginForm = this.formBuilder.group({
      workstationId: new FormControl(null, Validators.required),
      password: new FormControl(null, Validators.required)
    });
  }

  loginWorkstation(form) {
    if (form.workstationId && form.password) {
      this.workstationAuthService.authenticateWorkstation(form.workstationId, form.password)
        .toPromise()
        .then(workstationAuthResponse => {
          localStorage.setItem('workstationId', workstationAuthResponse.workstationId);
          this.router.navigateByUrl('/login');
        })
        .catch(error => {
          console.error(error);
          this.workstationLoginForm.reset();
        });
    }
  }


}
