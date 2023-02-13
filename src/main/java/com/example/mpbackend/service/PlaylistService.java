package com.example.mpbackend.service;

import com.example.mpbackend.data.PlaylistRepository;
import com.example.mpbackend.data.UserRepository;
import com.example.mpbackend.domain.Playlist;
import com.example.mpbackend.web.data.transfer.PlaylistAddPayload;
import com.example.mpbackend.web.data.transfer.PlaylistUpdatePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final UserRepository userRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Playlist create(PlaylistAddPayload payload) {
        var optCreator = userRepository.findByUsername(payload.getCreatorUsername());
        if (optCreator.isEmpty()) {
            log.warn("Creator by username {} doesn't exists", payload.getCreatorUsername());
        }
        var topic = new Playlist();
        topic.setCreator(optCreator.orElseThrow());
        topic.setTitle(payload.getTitle().strip());
        topic.setDescription(payload.getDescription().strip());
        topic.setCreator(optCreator.orElseThrow());
        return playlistRepository.save(topic);
    }

    @Transactional
    public boolean update(PlaylistUpdatePayload payload, long id) {
        var optTopic = playlistRepository.findById(id);
        if (optTopic.isPresent()) {
            var topic = optTopic.get();
            if (payload.getTitle() != null) {
                topic.setTitle(payload.getTitle().strip());
            }
            if (payload.getDescription() != null) {
                topic.setDescription(payload.getDescription().strip());
            }
            playlistRepository.save(topic);
            return true;
        }
        log.warn("Topic by id {} doesn't exists", id);
        return false;
    }

    @Transactional
    public Optional<Playlist> findById(Long id) {
        return playlistRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        if (playlistRepository.existsById(id))
            playlistRepository.deleteById(id);
    }

    @Transactional
    public Page<Playlist> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(sortBy).descending());
        return playlistRepository.findAll(pageable);
    }
}
