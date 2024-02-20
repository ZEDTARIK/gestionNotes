import { Component, OnInit } from '@angular/core';
import { NoteService } from './services/note.service';
import { AppState } from './interface/app-state';
import { CustomHttpResponse } from './interface/custom-http-response';
import { DataState } from './enum/data-state';
import { Observable, of } from 'rxjs';
import { catchError, map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  appState$: Observable<AppState<CustomHttpResponse>> | undefined;

  constructor(private noteService: NoteService) {}

  ngOnInit(): void {
    this.appState$ = this.noteService.notes$
      .pipe(
        map(response => {
            return { dataState: DataState.LOADED_STATE, data: response}
        }),
        startWith({ dataState: DataState.LOADING_STATE}),
        catchError((error: string) => {
          return of({ dataState: DataState.ERROR_STATE, error: error})
        })
      );
  }
}
