    package com.gimnasio.gimnasio.controllers.admin;

    import com.gimnasio.gimnasio.entities.CuotaMensual;
    import com.gimnasio.gimnasio.entities.Socio;
    import com.gimnasio.gimnasio.entities.ValorCuota;
    import com.gimnasio.gimnasio.enumerations.EstadoCuotaMensual;
    import com.gimnasio.gimnasio.enumerations.Meses;
    import com.gimnasio.gimnasio.repositories.SocioRepository;
    import com.gimnasio.gimnasio.services.CuotaMensualService;
    import com.gimnasio.gimnasio.services.ValorCuotaService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;

    import java.util.Calendar;
    import java.util.Date;
    import java.util.List;

    @Controller
    @RequestMapping("/admin/cuotas")
    public class CuotaAdminController {

        @Autowired
        private CuotaMensualService cuotaMensualService;

        @Autowired
        private SocioRepository socioRepository;

        @Autowired
        private ValorCuotaService valorCuotaService;

        @GetMapping("/menu")
        public String menuCuotas() {
            return "views/admin/gestion-cuotas";
        }

        @GetMapping("/ver")
        public String historialCuotas(Model model) {
            List<ValorCuota> historial = valorCuotaService.listarTodos();
            model.addAttribute("historialCuotas", historial);
            return "views/admin/cuotas";
        }

        @GetMapping("/cambiar")
        public String mostrarFormularioCambio(Model model) {
            model.addAttribute("nuevoValor", new ValorCuota());
            return "views/admin/cuotas-cambiar";
        }

        @PostMapping("/cambiar")
        public String cambiarValorCuota(@RequestParam("valorCuota") double valor, RedirectAttributes redirect) {
            try {
                valorCuotaService.cambiarValorCuota(valor);
                redirect.addFlashAttribute("success", "Valor de cuota actualizado correctamente.");
            } catch (Exception e) {
                redirect.addFlashAttribute("error", "Error al actualizar valor de cuota: " + e.getMessage());
            }
            return "redirect:/admin/cuotas/menu";
        }

        @GetMapping("/crear")
        public String mostrarFormularioCrearCuota(Model model) {
            model.addAttribute("meses", Meses.values());
            model.addAttribute("anioActual", Calendar.getInstance().get(Calendar.YEAR));
            return "views/admin/cuotas-crear";
        }

        @PostMapping("/crear")
        public String crearCuotas(@RequestParam("mes") Meses mes,
                                  @RequestParam("anio") int anio,
                                  RedirectAttributes redirect) {
            try {
                Calendar hoy = Calendar.getInstance();
                int mesActual = hoy.get(Calendar.MONTH);
                int anioActual = hoy.get(Calendar.YEAR);

                if (anio < anioActual || (anio == anioActual && mes.ordinal() < mesActual)) {
                    redirect.addFlashAttribute("error", "No se pueden crear cuotas de meses o años anteriores.");
                    return "redirect:/admin/cuotas/crear";
                }

                // validar si ya existe alguna cuota para ese mes y año
                if (cuotaMensualService.existeCuotaPorMesAnio(mes, anio)) {
                    redirect.addFlashAttribute("error", "Ya existe una cuota con ese mes y año correspondientes.");
                    return "redirect:/admin/cuotas/crear";
                }

                ValorCuota valorActivo = valorCuotaService.obtenerEntidadActiva();
                List<Socio> sociosActivos = socioRepository.findAllByEliminadoFalse();

                int creadas = 0;
                for (Socio socio : sociosActivos) {
                    Date fechaVencimiento = calcularFechaVencimiento(anio, mes);
                    cuotaMensualService.crearCuotaMensual(
                            socio.getId(),
                            mes,
                            anio,
                            EstadoCuotaMensual.ADEUDADA,
                            fechaVencimiento,
                            valorActivo
                    );
                    creadas++;
                }

                redirect.addFlashAttribute("success", "Se crearon " + creadas + " cuotas para " + mes + " " + anio + ".");

            } catch (Exception e) {
                redirect.addFlashAttribute("error", "Error al crear cuotas: " + e.getMessage());
            }

            return "redirect:/admin/cuotas/menu";
        }


        private Date calcularFechaVencimiento(int anio, Meses mes) {
            Calendar cal = Calendar.getInstance();
            cal.set(anio, mes.ordinal(), 10, 0, 0, 0);
            return cal.getTime();
        }
    }