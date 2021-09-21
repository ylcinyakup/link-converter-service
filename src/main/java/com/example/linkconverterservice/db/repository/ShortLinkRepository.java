package com.example.linkconverterservice.db.repository;

import com.example.linkconverterservice.db.entity.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortLinkRepository extends JpaRepository<ShortLink, Long> {

    Optional<ShortLink> findByLink(String url);
}
