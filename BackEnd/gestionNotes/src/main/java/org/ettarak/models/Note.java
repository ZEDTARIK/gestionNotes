package org.ettarak.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.ettarak.enumaration.Level;

import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Data
public class Note implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @NotNull(message = "Title of this note cannot be null")
    @NotEmpty(message = "Title of this note cannot be empty")
    private String title;
    @NotNull(message = "Description of this note cannot be null")
    @NotEmpty(message = "Description of this note cannot be empty")
    private  String description;
    private Level level;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss", timezone = "America/NEW_YORK")
    private LocalDateTime createdAt;
}
