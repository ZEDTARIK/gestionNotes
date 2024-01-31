package org.ettarak.resources;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ettarak.models.HttpResponse;
import org.ettarak.models.Note;
import org.ettarak.services.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/note")
@RequiredArgsConstructor
public class NoteResources {
    private  final NoteService noteService;

    // get all notes  : .../note/all
    @GetMapping(path = "/all")
    public ResponseEntity<HttpResponse<Note>> getNotes() {
        return ResponseEntity.ok().body(noteService.getNotes());
    }

    // save new note: .../note/save
    @PostMapping(path = "/save")
    public ResponseEntity<HttpResponse<Note>> saveNote(@RequestBody @Valid Note note){
        return  ResponseEntity.created(
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/all/note").toUriString())
        ).body(noteService.saveNote(note));
    }

}
