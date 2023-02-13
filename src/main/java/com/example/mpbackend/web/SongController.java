package com.example.mpbackend.web;

import com.example.mpbackend.domain.Song;
import com.example.mpbackend.message.ResponseFile;
import com.example.mpbackend.message.ResponseMessage;
import com.example.mpbackend.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("song") MultipartFile file) {
        String message = "";
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        try {
            songService.store(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/songs")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> songs = songService.getAllSongs().map(song -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/songs/")
                    .path(song.getId())
                    .toUriString();

            return new ResponseFile(
                    song.getFilename(),
                    fileDownloadUri,
                    song.getType());
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(songs);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<String> getFile(@PathVariable String id) {
        Song song = songService.getSong(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + song.getFilename() + "\"")
                .body(song.getFilename());
    }

    @DeleteMapping("/songs/{filename}")
    public ResponseEntity<?> deleteByFilename(@PathVariable String filename){
        songService.deleteByFilename(filename);
        return ResponseEntity.ok().build();
    }
}
