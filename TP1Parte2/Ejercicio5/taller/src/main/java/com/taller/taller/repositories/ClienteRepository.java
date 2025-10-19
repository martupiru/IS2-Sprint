package com.taller.taller.repositories;

import com.taller.taller.entities.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends BaseRepository<Cliente, String> {
}

