package com.sprint.tinder.repositories;

import com.sprint.tinder.entities.Mascota;
import com.sprint.tinder.enumerations.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota,String> {
    @Query("SELECT c FROM Mascota c WHERE c.usuario.id = :id AND c.baja IS NULL")
    public List<Mascota> listarMascotasPorUsuario(@Param("id")String id);

    @Query("SELECT c FROM Mascota c WHERE c.usuario.id = :id AND c.nombre = :nombre AND c.baja IS NULL")
    public Mascota buscarMascota(@Param("id")String id, @Param("nombre")String nombre);

    @Query("SELECT c FROM Mascota c WHERE c.usuario.id = :id AND c.baja IS NOT NULL")
    public List<Mascota> listarMascotasDeBaja(@Param("id")String id);

    @Query("SELECT m FROM Mascota m WHERE m.usuario.id != :idUsuario AND m.baja IS NULL AND m.eliminado = false")
    Collection<Mascota> listarMascotasMenosUsuario(@Param("idUsuario") String idUsuario);

    @Query("SELECT m FROM Mascota m WHERE m.usuario.id != :idUsuario AND m.baja IS NULL AND m.eliminado = false AND m.tipo = :tipo")
    Collection<Mascota> listarMascotasMenosUsuarioPorTipo(@Param("idUsuario") String idUsuario, @Param("tipo") Tipo tipo);
}
