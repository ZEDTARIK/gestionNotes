package org.ettarak.resources;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ettarak.enumaration.Level;
import org.ettarak.models.HttpResponse;
import org.ettarak.models.Note;
import org.ettarak.services.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.ettarak.utils.DateUtil.dateTimeFormatter;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    // filter note: ../note/filter?level=HIGH
    @GetMapping(path = "/filter")
    public ResponseEntity<HttpResponse<Note>> filterNotes(@RequestParam(value = "level")Level level) {
        return ResponseEntity.ok().body(noteService.filterNoteByLevel(level));
    }

    // update note: .../note/updqte
    @PutMapping(path = "/update")
    public ResponseEntity<HttpResponse<Note>> updateNote(@RequestBody @Valid Note note) {
        return ResponseEntity.ok().body(noteService.updateNote(note));
    }

    // delete note by id: .../note/delete/13
    @DeleteMapping(path = "/delete/{noteId}")
    public ResponseEntity<HttpResponse<Note>> deleteNote(@PathVariable(value = "noteId") Long id) {
        return  ResponseEntity.ok().body(noteService.deleteNote(id));
    }

    @RequestMapping(path = "/error")
    public ResponseEntity<HttpResponse<?>> handleError(HttpServletRequest request) {
        return new ResponseEntity<>(
         HttpResponse.builder()
                 .reason("There is not mapping for a " + request.getMethod() + " request for this path on server")
                 .developerMessage(request.getServletPath())
                .status(NOT_FOUND)
                .statusCode(NOT_FOUND.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build(), NOT_FOUND
        );
    }

}
