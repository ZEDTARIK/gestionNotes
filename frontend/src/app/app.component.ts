import { Component, OnInit } from '@angular/core';
import { NoteService } from './services/note.service';
import { Observable } from 'rxjs';
import { AppState } from './interface/app-state';
import { CustomHttpResponse } from './interface/custom-http-response';
import { map } from 'rxjs/operators';
import { DataState } from './enum/data-state';

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
        })
      );
  }
}
