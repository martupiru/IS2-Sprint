package com.taller.taller.repositories;

import com.taller.taller.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends BaseRepository<com.taller.taller.entities.Usuario, String> {

    @Query("SELECT u "
            + "  FROM Usuario u "
            + " WHERE u.email = :email "
            + "   AND u.eliminado = FALSE")
    public Usuario buscarUsuarioPorEmail (@Param("email") String email);

    @Query ("SELECT u "
            + "  FROM Usuario u "
            + " WHERE u.nombre = :nombre"
            + "   AND u.eliminado = FALSE")
    public Usuario buscarUsuarioPorNombre (@Param ("nombre") String nombre);

    @Query ("SELECT u "
            + "  FROM Usuario u "
            + " WHERE u.eliminado = FALSE")
    public List<Usuario> listarUsuarioActivo ();

    @Query("SELECT u "
            + "  FROM Usuario u "
            + " WHERE u.email = :email "
            + "   AND u.clave = :clave"
            + "   AND u.eliminado = FALSE")
    public Usuario buscarUsuarioPorEmailYClave(@Param("email")String email, @Param("clave")String clave);

   @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END "
        + "  FROM Usuario u "
        + " WHERE u.nombre = :nombre "
        + "   AND u.eliminado = FALSE")
   public boolean existsUsuarioPorNombre(@Param("nombre") String nombre);



}
