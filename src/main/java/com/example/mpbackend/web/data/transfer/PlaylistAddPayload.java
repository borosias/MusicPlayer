package com.example.mpbackend.web.data.transfer;

import com.example.mpbackend.domain.Playlist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link Playlist} entity
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistAddPayload implements Serializable {
    @Size(min = 3, max = 255) String title;
    @Size(min = 1, max = 255) String description;
    @Size(min = 5, max = 255) @Pattern(regexp = "^\\S+$") String creatorUsername;
}
