package com.example.mpbackend.web;

import com.example.mpbackend.service.PlaylistService;
import com.example.mpbackend.web.data.transfer.SongPayload;
import com.example.mpbackend.web.data.transfer.PlaylistAddPayload;
import com.example.mpbackend.web.data.transfer.PlaylistPayload;
import com.example.mpbackend.web.data.transfer.PlaylistUpdatePayload;
import org.hibernate.validator.constraints.Range;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    private final ModelMapper modelMapper;

    @Autowired
    public PlaylistController(PlaylistService playlistService, ModelMapper modelMapper) {
        this.playlistService = playlistService;
        this.modelMapper = modelMapper;
    }

    private static final String PLAYLIST_PROPERTIES = "id|playlist|createdAt|creatorId";

    @GetMapping
    public ResponseEntity<List<PlaylistPayload>> getAll(
            @RequestParam(defaultValue = "0") @Min(0) final int page,
            @RequestParam(defaultValue = "20") @Range(min = 0, max = 1000) final int size,
            @RequestParam(defaultValue = "id") @Pattern(regexp = PLAYLIST_PROPERTIES) final String sortBy) {
        return ResponseEntity.ok(playlistService.findAll(page, size, sortBy)
                .stream()
                .map(playlist -> modelMapper.map(playlist, PlaylistPayload.class))
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistPayload> getById(
            @PathVariable @Min(1) final long id) {
        var optionalPlaylist = playlistService.findById(id);
        return optionalPlaylist.map(playlist -> ResponseEntity.ok(modelMapper.map(playlist, PlaylistPayload.class)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}/songs")
    public ResponseEntity<List<SongPayload>> getByUserId(
            @PathVariable @Min(1) final long id) {
        var optPlaylist = playlistService.findById(id);
        return optPlaylist.map(playlist -> ResponseEntity.ok(playlist.getSongs().stream()
                        .map(song -> modelMapper.map(song, SongPayload.class))
                        .sorted(Comparator.comparing(SongPayload::getId).reversed())
                        .toList()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<PlaylistPayload> addOne(
            @RequestBody @Valid final PlaylistAddPayload payload) {
        return ResponseEntity.ok(modelMapper.map(playlistService.create(payload), PlaylistPayload.class));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlaylistPayload> delete(
            @PathVariable @Min(1) final long id) {
        playlistService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlaylistPayload> update(
            @RequestBody @Valid final PlaylistUpdatePayload payload,
            @PathVariable @Min(1) final long id) {
        return playlistService.update(payload, id) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }


}
