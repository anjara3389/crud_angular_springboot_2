import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { Observable } from 'rxjs';
import { Usuario } from './usuario';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError} from 'rxjs/operators';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {
  private urlEndPoint: string = 'http://localhost:8080/api/Usuarios'
  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(private http: HttpClient,
    private router: Router) { }

  getUsuarios(): Observable<any> {
    return this.http.get(this.urlEndPoint);
  }

  create(usuario: Usuario): Observable<any> {
    return this.http.post<Usuario>(this.urlEndPoint, usuario, { headers: this.httpHeaders }).pipe(
      catchError(e => {
        if (e.status === 400) {//BAD REQUEST
          return throwError(e);
        }
        console.error(e.error.mensaje);
        Swal.fire( e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  getUsuario(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.urlEndPoint}/${id}`).pipe(
      catchError((e) => {
        this.router.navigate(['/usuarios']);
        console.error(e.error.mensaje);
        Swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(usuario: Usuario): Observable<any> {
    return this.http.put<Usuario>(`${this.urlEndPoint}/${usuario.idUsuario}`, usuario, { headers: this.httpHeaders }).pipe(
      catchError(e => {
        if (e.status === 400) {//BAD REQUEST
          return throwError(e);
        }
        console.error(e.error.mensaje);
        Swal.fire( e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }

  delete(id:number): Observable<Usuario> {
    return this.http.delete<Usuario>(`${this.urlEndPoint}/${id}`, { headers: this.httpHeaders }).pipe(
      catchError(e => {
        console.error(e.error.mensaje);
        Swal.fire( e.error.mensaje, e.error.error, 'error');
        return throwError(e);
      })
    );
  }
}


