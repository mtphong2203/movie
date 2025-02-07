package com.jaf.movietheater.repository;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jaf.movietheater.entities.MovieType;

public interface MovieTypeRepository extends JpaRepository<MovieType, UUID>, JpaSpecificationExecutor<MovieType>{
    MovieType findByTypeName(String typeName);

    Set<MovieType> findAllByIdIn(Set<UUID> ids);
}
