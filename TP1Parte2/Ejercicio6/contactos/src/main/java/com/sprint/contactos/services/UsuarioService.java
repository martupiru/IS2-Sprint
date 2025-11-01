package com.sprint.contactos.services;

import com.sprint.contactos.entities.Usuario;
import com.sprint.contactos.repositories.BaseRepository;
import com.sprint.contactos.repositories.UsuarioRepository;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioService(BaseRepository<Usuario, String> repository) {
        super(repository);
    }

    @Override
    protected Usuario createEmpty() {
        Usuario u = new Usuario();
        u.setEliminado(false);
        return u;
    }

    @Override
    protected void validar(Usuario entidad) throws ErrorServiceException {
        if (entidad == null) throw new ErrorServiceException("Usuario nulo");
        if (entidad.getCuenta() == null || entidad.getCuenta().isBlank())
            throw new ErrorServiceException("El nombre de usuario es obligatorio");
        if (entidad.getClave() == null || entidad.getClave().isBlank())
            throw new ErrorServiceException("La clave es obligatoria");
        if (entidad.getClave().length() < 6)
            throw new ErrorServiceException("La clave debe tener al menos 6 caracteres");
    }

    @Transactional
    public void registrar(String cuenta, String clave) throws ErrorServiceException {
        if (clave == null || clave.isBlank()) {
            throw new ErrorServiceException("La clave no puede estar vacía");
        }
        if (usuarioRepository.existsByCuenta(cuenta)) {
            throw new ErrorServiceException("El nombre de usuario ya existe");
        }

        Usuario usuario = new Usuario();
        usuario.setCuenta(cuenta);
        usuario.setClave(passwordEncoder.encode(clave));
        usuario.setEliminado(false);
        validar(usuario);
        usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCuenta(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        List<GrantedAuthority> permisos = new ArrayList<>();
        permisos.add(new SimpleGrantedAuthority("ROLE_USER"));

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("usuariosession", usuario);

        return new User(usuario.getCuenta(), usuario.getClave(), permisos);
    }
}
