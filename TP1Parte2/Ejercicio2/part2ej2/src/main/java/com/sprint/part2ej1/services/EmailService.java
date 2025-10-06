package com.sprint.part2ej1.services;
import com.sprint.part2ej1.entities.Usuario;
import com.sprint.part2ej1.entities.Proveedor;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ProveedorService proveedorService;

    public EmailService(final JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void enviarMensaje(String destinatario, String titulo, String cuerpo, String imagePath) throws Exception{
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // El mime deja insertar cosas mas complejas que un SimpleEmail como imagenes, html, etc
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("emaildepruebasprint@gmail.com");
            helper.setTo(destinatario);
            helper.setSubject(titulo);

            // HTML que va en el mail
            String htmlTemplate = """
                <html>
                    <body>
                        <div style="background-color:#f0f0f0">
                            <h2>%s</h2>
                            <img src="cid:logoImage" alt="Logo" style="width:150px;"/><br><br>
                            <p>%s</p>
                            <p>Hacé clic en el botón <b>[[AHORA]]</b>:</p>
                            <a href="https://www.uncuyo.edu.ar/" target="_blank"
                               style="display:inline-block;
                                      padding:10px 20px;
                                      font-size:16px;
                                      color:#fff;
                                      background-color:#007BFF;
                                      text-decoration:none;
                                      border-radius:5px;">
                                Ir a la página
                            </a>
                        </div>
                    </body>
                </html>
                """;
            String htmlContent = String.format(htmlTemplate, titulo, cuerpo); // Insertar parametros en el html
            helper.setText(htmlContent, true);

            FileSystemResource image = new FileSystemResource(new File(imagePath));
            helper.addInline("logoImage", image);

            emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mailAnioNuevo() throws Exception{
        List<Proveedor> proveedores = proveedorService.listarProveedoresActivos();

        for (Proveedor proveedor : proveedores) {
            try {
                enviarMensaje(proveedor.getCorreoElectronico(), "¡FELIZ AÑO NUEVO! 😁🥳🥳🔥", "Desde Sprint te deseamos un maravilloso año nuevo 💯💯🤑🤑", "src/main/resources/static/images/logo.png");
            } catch (Exception e) {
                System.err.println("Error al enviar mensaje de año nuevo: " + e.getMessage());
            }
        }
    }

    public void mailPublicidad(String titulo, String cuerpo) throws Exception{
        List<Usuario> usuarios = usuarioService.listarUsuariosActivos();

        for (Usuario usuario : usuarios) {
            try {
                enviarMensaje(usuario.getCorreoElectronico(), titulo, cuerpo, "src/main/resources/static/images/logo.png");
            } catch (Exception e) {
                System.err.println("Error al enviar mensaje de publicidad: " + e.getMessage());
            }
        }
    }

}


