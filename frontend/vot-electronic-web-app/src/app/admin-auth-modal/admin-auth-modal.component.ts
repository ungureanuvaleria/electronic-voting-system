import {Component, OnInit, TemplateRef} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Admin } from '../models/admin-info';
import { AuthService } from '../services/auth.service';
import { BsModalService, BsModalRef} from 'ngx-bootstrap';

@Component({
  selector: 'app-admin-auth-modal',
  templateUrl: './admin-auth-modal.component.html',
  styleUrls: ['./admin-auth-modal.component.css']
})
export class AdminAuthModalComponent implements OnInit {
  loginForm: FormGroup;
  isSubmitted  =  false;
  modalRef: BsModalRef;
  constructor(private authService: AuthService,
              private router: Router,
              private formBuilder: FormBuilder ,
              private modalService: BsModalService) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      name: ['', Validators.required],
      password: ['', Validators.required]
    });
  }
    get formControls() { return this.loginForm.controls; }
  login() {
    console.log(this.loginForm.value);
    this.isSubmitted = true;
    if (this.loginForm.invalid) {
      return;
    }
    this.authService.login(this.loginForm.value);
    this.router.navigateByUrl('/admin');
    this.modalRef.hide();
  }
  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }


}
