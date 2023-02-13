package com.example.mpbackend.service;

import com.example.mpbackend.data.SongRepository;
import com.example.mpbackend.domain.Song;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Slf4j
public class SongService {


    private final StorageService storageService;
    private final SongRepository songRepository;

    public SongService(StorageService storageService, SongRepository songRepository) {
        this.storageService = storageService;
        this.songRepository = songRepository;
    }

    public void store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Song song = new Song(fileName.replaceAll(" ",""), file.getContentType());
        songRepository.save(song);
        storageService.songUpload(file);

    }
    @Transactional
    public Song getSong(String id) {
        return songRepository.findById(id).get();
    }

    @Transactional
    public Stream<Song> getAllSongs() {
        System.out.println(songRepository.findAll());
        return songRepository.findAll().stream();
    }

    @Transactional
    public void deleteByFilename(String filename) {
        songRepository.deleteByFilename(filename);
    }
}

