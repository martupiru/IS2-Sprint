package sprint.tinder.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
/*
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
*/

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sprint.tinder.entities.Foto;
import sprint.tinder.entities.Usuario;
import sprint.tinder.entities.Zona;
import sprint.tinder.errors.ErrorServicio;
import sprint.tinder.repositories.UsuarioRepositorio;
import sprint.tinder.repositories.ZonaRepositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UsuarioServicio {

    @Autowired //Le indica que lo tiene que inicializar
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private FotoServicio fotoServicio;
    @Autowired
    private NotificacionServicio notificacionServicio;
    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @Transactional
    public void registrar(String nombre, String apellido, String mail, String clave, String clave2, MultipartFile archivo, String idZona) throws ErrorServicio { // hay que indicar que puede largar errores de ese tipo cpm throws ErrorServicio
        Optional<Zona> zonaOpt = zonaRepositorio.findById(idZona); //getOne() del video estaba deprecado
        if (!zonaOpt.isPresent()) {
            throw new ErrorServicio("No se encontró la zona solicitada");
        }
        Zona zona = zonaOpt.get();

        validar(nombre, apellido, mail, clave, clave2, zona);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        //String encriptada = new BCryptPasswordEncoder().encode(clave);
        //usuario.setClave(encriptada); // cuando persistimos el usuario, la contraseña está encriptada
        usuario.setClave(clave);
        usuario.setZona(zona);
        usuario.setAlta(new Date());

        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);

        usuarioRepositorio.save(usuario);
        //Nosotros recibimos los datos por un formulario web, se transforman en tipo usuario y cuando se transforman le decimos al repositorio que lo almacene en la base de datos

        /* // Mensaje de bienvenida al mail, por ahora lo comento
        try {
            notificacionServicio.enviar("¡Bienvenido al Tinder de mascotas!", "Tinder de Mascotas", usuario.getMail());
        } catch (Exception ex) {
            // loguear pero no relanzar para no afectar la transacción ya completada
            Logger.getLogger(UsuarioServicio.class.getName()).log(Level.SEVERE, "Error enviando notificación: " + ex.getMessage(), ex);
        }
       */
    }

    @Transactional
    public void modificar(String id, String nombre, String apellido, String mail, String clave,String clave2, MultipartFile archivo, String idZona) throws ErrorServicio {
        Optional<Zona> zonaOpt = zonaRepositorio.findById(idZona); //getOne() del video estaba deprecado
        if (!zonaOpt.isPresent()) {
            throw new ErrorServicio("No se encontró la zona solicitada");
        }
        Zona zona = zonaOpt.get();

        validar(nombre, apellido, mail, clave, clave2, zona);
        Optional<Usuario> respuesta = this.usuarioRepositorio.findById(id); // Con optional puedo ver si lo que devolvio la base de datos está presente (o sea ese usuario como respuesta a ese id)
        if(respuesta.isPresent()){ //Si me devuelve la base de datos un usuario con ese id
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setMail(mail);
            //String encriptada = new BCryptPasswordEncoder().encode(clave);
            //usuario.setClave(encriptada);
            usuario.setClave(clave);
            usuario.setZona(zona);

            String idFoto = null;
            if(usuario.getFoto() != null){ //Me fijo si tenia una foto antes
                idFoto = usuario.getFoto().getId();
            }
            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            usuario.setFoto(foto);
            usuarioRepositorio.save(usuario);
        } else{
            throw new ErrorServicio("No se encontró el usuario solicitado");
        }
    }

    @Transactional
    public void deshabilitar(String id) throws ErrorServicio {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id); // Con optional puedo ver si lo que devolvio la base de datos está presente (o sea ese usuario como respuesta a ese id)
        if(respuesta.isPresent()){ //Si me devuelve la base de datos un usuario con ese id
            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());
            usuarioRepositorio.save(usuario);
        } else{
            throw new ErrorServicio("No se encontró el usuario solicitado");
        }
    }

    @Transactional
    public void habilitar(String id) throws ErrorServicio {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id); // Con optional puedo ver si lo que devolvio la base de datos está presente (o sea ese usuario como respuesta a ese id)
        if(respuesta.isPresent()){ //Si me devuelve la base de datos un usuario con ese id
            Usuario usuario = respuesta.get();
            usuario.setBaja(null);
            usuarioRepositorio.save(usuario);
        } else{
            throw new ErrorServicio("No se encontró el usuario solicitado");
        }
    }

    public void validar(String nombre,  String apellido, String mail, String clave, String clave2, Zona zona) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {// Antes de persistir, tenemos que validar
            throw new ErrorServicio("El nombre es obligatorio");
        }
        if (apellido == null || apellido.isEmpty()) {// Antes de persistir, tenemos que validar
            throw new ErrorServicio("El apellido es obligatorio");
        }
        if (mail == null || mail.isEmpty()) {// Antes de persistir, tenemos que validar
            throw new ErrorServicio("El mail es obligatorio");
        }
        if (clave == null || clave.isEmpty() || clave.length() <= 5) {
            throw new ErrorServicio("La clave es obligatoria y debe tener más de 5 caracteres");
        }
        if (clave2==null || clave2.isEmpty() || clave2.length() <= 5) {
            throw new ErrorServicio("Debe verificar la clave");
        }
        if (!clave.equals(clave2)) {
            throw new ErrorServicio("Las claves deben coincidir");
        }
        if (zona == null){
            throw new ErrorServicio("No se encontró la zona solicitada");
        }
    }
    /*
    // Para implementar esto hay que poner arriba public class UsuarioServici implements UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException { //Buscamos por mail
        // Este metodo recibe el mail, con ese mail buscamos en la base de datos el usuario de nuestro dominio y lo transformemos en un usuario de dominio de spring security
        Usuario usuario = this.usuarioRepositorio.buscarPorMail(mail);
        if(usuario != null){
            List<GrantedAuthority> permisos = new ArrayList<>(); // Permisos que va a tener el usuario dueño de la mascota
            GrantedAuthority permiso1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(permiso1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession"), usuario);

            // Los permisos son los accesos a esos 3 modulos
            User user  = new User(usuario.getMail(), usuario.getClave(), permisos); // Le pasa a Spring Security los datos mail y clave, y los permisos que tiene ese usuario
            return user;
        } else {
            return null;
        }
    }
    */



}
