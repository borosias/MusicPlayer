package com.example.mpbackend.web.data.transfer;

import com.example.mpbackend.domain.Song;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * A DTO for the {@link Song} entity
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SongPayload implements Serializable {
    Long id;
    String filename;
    Long topicId;

}