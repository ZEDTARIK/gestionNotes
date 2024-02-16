import { Note } from './../interface/note';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { CustomHttpResponse } from '../interface/custom-http-response';

@Injectable({
  providedIn: 'root',
})
export class NoteService {
  private readonly server = '';

  constructor(private http: HttpClient) {}

  // get all notes from the server
  notes$ = <Observable<CustomHttpResponse>>(
    this.http
      .get<CustomHttpResponse>(`${this.server}/notes/all`)
      .pipe(tap(console.log), catchError(this.handlErrors))
  );

  // save a new note in the server
  saveNote$ = (note: Note) =>
    <Observable<CustomHttpResponse>>(
      this.http
        .post<CustomHttpResponse>(`${this.server}/notes/add`, note)
        .pipe(tap(console.log), catchError(this.handlErrors))
    );

  // update existing note in the server
  updateNote$ = (note: Note) =>
    <Observable<CustomHttpResponse>>(
      this.http
        .put<CustomHttpResponse>(`${this.server}/notes/update`, note)
        .pipe(tap(console.log), catchError(this.handlErrors))
    );

    // delete existing note in the server by noteId
  deleteNote$ = (noteId: number) =>
    <Observable<CustomHttpResponse>>(
      this.http
        .delete<CustomHttpResponse>(`${this.server}/notes/delete/${noteId}`)
        .pipe(tap(console.log), catchError(this.handlErrors))
    );

  // handle errors
  private handlErrors(handleError: any): Observable<never> {
    return throwError('Method not yet implemented');
  }
}
