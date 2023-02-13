package com.example.mpbackend.web.data.transfer;

import com.example.mpbackend.domain.Playlist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Playlist} entity
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistPayload implements Serializable {
    Long id;
    String title;
    String description;
    String creatorUsername;
    LocalDateTime createdAt;
}