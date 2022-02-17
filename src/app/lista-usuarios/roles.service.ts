import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RolesService {
  private urlEndPoint: string = 'http://localhost:8080/api/Roles'
  constructor(private http: HttpClient) { }

  getRoles(): Observable<any> {
    return this.http.get(this.urlEndPoint);
  }
}



