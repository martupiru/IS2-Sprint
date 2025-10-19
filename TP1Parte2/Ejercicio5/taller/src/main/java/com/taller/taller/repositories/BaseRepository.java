package com.taller.taller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.taller.taller.entities.BaseEntity;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<ID>, ID> extends JpaRepository<T, ID> {
    List<T> findAllByEliminadoFalse();

    Optional<T> findByIdAndEliminadoFalse(ID id);
}