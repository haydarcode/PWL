package com.portalpolinema.backend.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    // Sengaja dibuat default method: query turunan "existsByEmail" gagal di Spring Data terbaru
    // karena primary key entity bernama "id_user" (bukan "id").
    default boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }
}
