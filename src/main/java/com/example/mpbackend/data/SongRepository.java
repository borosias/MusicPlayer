package com.example.mpbackend.data;

import com.example.mpbackend.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, String> {

    boolean existsSongsByFilenameEquals(String filename);

    void deleteByFilename(String filename);

}

