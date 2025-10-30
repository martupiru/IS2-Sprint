package com.sprint.tinder.repositories;

import com.sprint.tinder.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String> {

    @Query("SELECT c FROM Usuario c WHERE c.mail = :mail")
    public Usuario buscarPorMail(@Param("mail") String mail);

    @Query("SELECT c FROM Usuario c WHERE c.mail = :mail AND c.clave = :clave")
    public Usuario buscarUsuarioPorEmailYClave(@Param("mail")String mail, @Param("clave")String clave);

    Optional<Usuario> findByMail(String mail);
}
