import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SimpleResponse} from '../models/SimpleResponse';
import {User} from '../models/User';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  rootUrl: string;

  constructor(private httpService: HttpClient) {
    this.rootUrl = 'http://localhost:8080/api/users';
  }

  public addUser(user): Observable<SimpleResponse> {
    return this.httpService.post<SimpleResponse>(this.rootUrl, user);
  }

  public getAll(): Observable<User[]> {
    return this.httpService.get<User[]>(this.rootUrl);
  }

  public deleteUser(user) {
    this.httpService.delete(this.rootUrl, user);
  }
}
