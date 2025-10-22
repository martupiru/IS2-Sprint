package com.sprint.part2ej1;

import com.sprint.part2ej1.entities.Departamento;
import com.sprint.part2ej1.entities.Localidad;
import com.sprint.part2ej1.entities.Pais;
import com.sprint.part2ej1.entities.Provincia;
import com.sprint.part2ej1.repositories.DepartamentoRepository;
import com.sprint.part2ej1.repositories.LocalidadRepository;
import com.sprint.part2ej1.repositories.PaisRepository;
import com.sprint.part2ej1.repositories.ProvinciaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Part2ej1Application {

	public static void main(String[] args) {
		SpringApplication.run(Part2ej1Application.class, args);
	}

    //guardar direccion de prueba

    @Bean
    public CommandLineRunner cargarDatosIniciales(PaisRepository paisRepo,
                                                  ProvinciaRepository provinciaRepo,
                                                  DepartamentoRepository departamentoRepo,
                                                  LocalidadRepository localidadRepo) {
        return args -> {
            Pais pais = paisRepo.findByNombreAndEliminadoFalse("Argentina").orElseGet(() -> {
                Pais p = new Pais();
                p.setNombre("Argentina");
                p.setEliminado(false);
                return paisRepo.save(p);
            });

            Provincia provincia = provinciaRepo.findByNombreAndEliminadoFalse("Mendoza").orElseGet(() -> {
                Provincia pr = new Provincia();
                pr.setNombre("Mendoza");
                pr.setEliminado(false);
                pr.setPais(pais);
                return provinciaRepo.save(pr);
            });

            Departamento departamento = departamentoRepo.findByNombreAndEliminadoFalse("Capital").orElseGet(() -> {
                Departamento d = new Departamento();
                d.setNombre("Capital");
                d.setEliminado(false);
                d.setProvincia(provincia);
                return departamentoRepo.save(d);
            });

            Localidad localidad = localidadRepo.findByNombreAndEliminadoFalse("Ciudad").orElseGet(() -> {
                Localidad l = new Localidad();
                l.setNombre("Ciudad");
                l.setCodigoPostal("5500");
                l.setEliminado(false);
                l.setDepartamento(departamento);
                return localidadRepo.save(l);
            });

            System.out.println("Datos iniciales: pais=" + pais.getNombre() + ", provincia=" + provincia.getNombre() + ", departamento=" + departamento.getNombre() + ", localidad=" + localidad.getNombre());
        };
    }


}
