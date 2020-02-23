import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {WorkstationAuthResponse} from "../models/WorkstationAuthResponse";

@Injectable({
  providedIn: 'root'
})
export class WorkstationAuthService {
  rootUrl: string;

  constructor(private httpService: HttpClient) {
    this.rootUrl = 'http://localhost:8080/api/auth/workstation';
  }

  public authenticateWorkstation(workstationId: string, workstationPassword: string): Observable<WorkstationAuthResponse> {
    const body = {
      workstation_id: workstationId,
      workstation_password: workstationPassword
    };
    return this.httpService.post<WorkstationAuthResponse>(this.rootUrl, body);
  }
}
