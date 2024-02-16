import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { CustomHttpResponse } from '../interface/custom-http-response';

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  private readonly server = '';

  constructor(private http: HttpClient) { }

  // get all notes from the server
  notes$ = <Observable<CustomHttpResponse>> this.http.get<CustomHttpResponse>
            (`${this.server}/notes/all`)
            .pipe(
              tap(console.log),
              catchError(this.handlErrors)
            );

  // handle errors
  private handlErrors(handleError: any): Observable<never> {
    return throwError('Method not yet implemented');
  }

}
