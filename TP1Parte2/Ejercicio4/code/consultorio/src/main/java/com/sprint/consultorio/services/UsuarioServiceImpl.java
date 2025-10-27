package com.sprint.consultorio.services;

import com.sprint.consultorio.entities.Paciente;
import com.sprint.consultorio.entities.Usuario;
import com.sprint.consultorio.repositories.UsuarioRepository;
import com.sprint.consultorio.utils.HashForLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // HASH CLAVES
    @Override
    protected void beforeSave(Usuario usuario) throws Exception {
        String claveHasheada = HashForLogin.hashClave(usuario.getClave());
        usuario.setClave(claveHasheada);
    }

    @Override
    protected void beforeUpdate(Usuario usuario) throws Exception {
        String claveHasheada = HashForLogin.hashClave(usuario.getClave());
        usuario.setClave(claveHasheada);
    }

    // --- Métodos específicos de Usuario ---

    @Override
    public List<Usuario> findAll() throws Exception {
        try {
            return usuarioRepository.findAllActivos();
        } catch (Exception e) {
            throw new Exception("Error al listar usuarios activos: " + e.getMessage());
        }
    }

    @Override
    public Optional<Usuario> findByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    @Override
    public Optional<Usuario> login(String nombreUsuario, String clave) {
        Optional<Usuario> usuarioOptional = this.findByNombreUsuario(nombreUsuario);

        if (usuarioOptional.isEmpty()) {
            return Optional.empty();
        }

        Usuario usuario = usuarioOptional.get();

        String claveHasheada = HashForLogin.hashClave(clave);

        if (claveHasheada.equals(usuario.getClave())) {
            return Optional.of(usuario);
        } else {
            return Optional.empty();
        }
    }
}
