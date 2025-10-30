package com.taller.taller.services;

import com.taller.taller.entities.Rol;
import com.taller.taller.entities.Usuario;
import com.taller.taller.repositories.MecanicoRepository;
import com.taller.taller.repositories.BaseRepository;
import com.taller.taller.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService extends BaseService<Usuario, String> implements UserDetailsService {

    public UsuarioService(BaseRepository<Usuario, String> repository) {
        super(repository);
    }

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected Usuario createEmpty() {
        Usuario u = new Usuario();
        u.setEliminado(false);
        return u;
    }

    @Override
    protected void validar(Usuario entidad) throws ErrorServiceException {
        if (entidad == null) throw new ErrorServiceException("Usuario nulo");
        if (entidad.getNombre() == null || entidad.getNombre().isBlank())
            throw new ErrorServiceException("Nombre de usuario es obligatorio");
        if (entidad.getClave() == null || entidad.getClave().isBlank())
            throw new ErrorServiceException("Clave es obligatoria");
        if (entidad.getClave().length() < 6) {
            throw new ErrorServiceException("La clave debe tener al menos 6 caracteres");
        }
    }

    @Transactional
    public void registrar(String clave, String nombre, String email, String clave2) throws ErrorServiceException {
        if (clave == null || clave.isBlank() || clave2 == null || clave2.isBlank()) {
            throw new ErrorServiceException("Las claves no pueden estar vacías");
        }
        if (!clave.equals(clave2)) {
            throw new ErrorServiceException("Las claves deben coincidir");
        }
        if (usuarioRepository.existsUsuarioPorNombre(nombre)) {
            throw new ErrorServiceException("El nombre de usuario ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setClave(passwordEncoder.encode(clave));
        usuario.setEmail(email);
        usuario.setRol(Rol.USER);
        usuario.setEliminado(false);

        validar(usuario);

        usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.buscarUsuarioPorNombre(username);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getNombre(), usuario.getClave(), permisos);
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    }
}

