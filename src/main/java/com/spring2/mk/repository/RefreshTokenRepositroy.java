package com.spring2.mk.repository;

import com.spring2.mk.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepositroy extends JpaRepository<RefreshToken, String> {
}
