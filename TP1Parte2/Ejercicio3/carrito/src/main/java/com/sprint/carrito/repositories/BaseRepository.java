package com.sprint.carrito.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sprint.carrito.entities.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<ID>, ID> extends JpaRepository<T, ID> {
}
