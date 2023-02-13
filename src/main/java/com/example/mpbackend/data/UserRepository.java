package com.example.mpbackend.data;

import com.example.mpbackend.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByUsername(String email);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);

}
