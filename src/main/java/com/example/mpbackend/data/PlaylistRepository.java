package com.example.mpbackend.data;

import com.example.mpbackend.domain.Playlist;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends PagingAndSortingRepository<Playlist, Long> {
}
