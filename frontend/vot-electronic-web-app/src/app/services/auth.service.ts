import { Injectable } from '@angular/core';
import { Admin } from '../models/admin-info';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() {
  }

  public login(adminInfo: Admin) {
    localStorage.setItem('ACCESS_TOKEN', 'access_token');
  }

  public isLoggedIn() {
    return localStorage.getItem('ACCESS_TOKEN') !== null;
  }

  public logout() {
    localStorage.removeItem('ACCESS_TOKEN');
  }
}
