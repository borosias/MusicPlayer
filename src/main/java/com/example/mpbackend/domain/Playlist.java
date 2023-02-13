package com.example.mpbackend.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "PLAYLIST")
@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "creator_id")
    com.example.mpbackend.domain.User creator;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    @Setter(AccessLevel.PRIVATE)
    @ToString.Exclude
    Set<com.example.mpbackend.domain.Song> songs = new LinkedHashSet<>();

    public void addSong(com.example.mpbackend.domain.Song song) {
        Objects.requireNonNull(song, "Impossible to add null values to relations");
        songs.add(song);
        song.setPlaylist(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Playlist playlist = (Playlist) o;
        return id != null && Objects.equals(id, playlist.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
