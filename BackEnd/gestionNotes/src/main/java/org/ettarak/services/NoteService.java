package org.ettarak.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ettarak.enumaration.Level;
import org.ettarak.exception.NoteNotFoundException;
import org.ettarak.models.HttpResponse;
import org.ettarak.models.Note;
import org.ettarak.repositories.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.ettarak.utils.DateUtil.dateTimeFormatter;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class NoteService {
    private  final NoteRepository noteRepository;

    // Fetching All Notes from database
    public HttpResponse<Note> getNotes() {
        log.info("Fetching the notes from database");
        return HttpResponse.<Note>builder()
                .notes(noteRepository.findAll())
                .message(noteRepository.count() > 0 ? noteRepository.count() + " notes retrieved": "No Notes to display")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }
    // Fetching notes by level params
    public HttpResponse<Note> filterNoteByLevel(Level level) {
        log.info("Fetching the notes filter by level {}", level);
        List<Note> notes = noteRepository.findByLevel(level);
        return HttpResponse.<Note>builder()
                .notes(notes)
                .message(notes.size() + " notes are of " + level + " importance")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }

    // Saving new note
    public  HttpResponse<Note> saveNote(Note note) {
        log.info("Saving a new note into the database");
        note.setCreatedAt(LocalDateTime.now());
        return HttpResponse.<Note>builder()
                .notes(Collections.singleton(noteRepository.save(note)))
                .message("Note created successfully")
                .status(CREATED)
                .statusCode(CREATED.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }

    // update note
    public HttpResponse<Note> updateNote(Note note) throws NoteNotFoundException {
        log.info("Updating note with id {}", note.getId());

        // using optional and Exception
        Optional<Note> optionalNote = Optional.of(noteRepository.findById(note.getId()))
                .orElseThrow(() -> new NoteNotFoundException("The note was not found on the database"));

        // update existing note
        Note updateNote = optionalNote.orElseThrow();

        updateNote.setId(note.getId());
        updateNote.setTitle(note.getTitle());
        updateNote.setDescription(note.getDescription());
        updateNote.setLevel(note.getLevel());
        noteRepository.save(updateNote);

        return HttpResponse.<Note>builder()
                .notes(Collections.singleton(updateNote))
                .message("Note updated successfully")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }

    // delete existing note by id
    public HttpResponse<Note> deleteNote(Long id) throws NoteNotFoundException {
        log.info("Deleting note from the database by id {}", id);
        // searching note inside server
        Optional<Note> optionalNote = Optional.ofNullable(noteRepository.findById(id))
                .orElseThrow(() -> new NoteNotFoundException("The note was not found on the database"));
        // deleting from the database
        optionalNote.ifPresent(noteRepository::delete);

        return HttpResponse.<Note>builder()
                .notes(Collections.singleton(optionalNote.get()))
                .message("Note deleting successfully")
                .status(OK)
                .statusCode(OK.value())
                .timeStamp(LocalDateTime.now().format(dateTimeFormatter()))
                .build();
    }
}
