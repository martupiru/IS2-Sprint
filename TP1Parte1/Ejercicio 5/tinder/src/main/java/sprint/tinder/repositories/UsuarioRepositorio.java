package sprint.tinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sprint.tinder.entities.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {
    @Query("SELECT c FROM Usuario c WHERE c.mail = :mail")
    public Usuario buscarPorMail(@Param("mail") String mail);

    @Query("SELECT c FROM Usuario c WHERE c.mail = :mail AND c.clave = :clave")
    public Usuario buscarUsuarioPorEmailYClave(@Param("mail")String mail, @Param("clave")String clave);
}
