import { Note } from './../interface/note';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { CustomHttpResponse } from '../interface/custom-http-response';

@Injectable({
  providedIn: 'root',
})
export class NoteService {
  private readonly server = 'http://localhost:8085';

  constructor(private http: HttpClient) {}

  // get all notes from the server
  notes$ = <Observable<CustomHttpResponse>>(
    this.http
      .get<CustomHttpResponse>(`${this.server}/note/all`)
      .pipe(tap(console.log), catchError(this.handlErrors))
  );

  // save a new note in the server
  saveNote$ = (note: Note) =>
    <Observable<CustomHttpResponse>>(
      this.http
        .post<CustomHttpResponse>(`${this.server}/note/add`, note)
        .pipe(tap(console.log), catchError(this.handlErrors))
    );

  // update existing note in the server
  updateNote$ = (note: Note) =>
    <Observable<CustomHttpResponse>>(
      this.http
        .put<CustomHttpResponse>(`${this.server}/note/update`, note)
        .pipe(tap(console.log), catchError(this.handlErrors))
    );

  // delete existing note in the server by noteId
  deleteNote$ = (noteId: number) =>
    <Observable<CustomHttpResponse>>(
      this.http
        .delete<CustomHttpResponse>(`${this.server}/note/delete/${noteId}`)
        .pipe(tap(console.log), catchError(this.handlErrors))
    );

  // handle errors
  private handlErrors(httpError: HttpErrorResponse): Observable<never> {
    console.error(httpError);
    let errorMessage: string;

    if (httpError.error instanceof ErrorEvent) {
      errorMessage = `A client error occurred: ${httpError.error.message}`;
    } else {
      if (httpError.error) {
        errorMessage = `${httpError.error.reason} - Error code ${httpError.status}`;
      } else {
        errorMessage = `An error occurred - Error code ${httpError.status}`;
      }
    }
    return throwError(errorMessage);
  }
}
