package org.ettarak.repositories;

import org.ettarak.enumaration.Level;
import org.ettarak.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByLevel(Level level);
    void deleteById(Long id);

}
