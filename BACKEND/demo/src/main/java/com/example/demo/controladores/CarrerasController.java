package com.example.demo.controladores;

import com.example.demo.modelos.Carreras;
import com.example.demo.repositorios.CarrerasRepositorio;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/carreras")
public class CarrerasController {

    @Autowired
    CarrerasRepositorio carrerasRepositorio;

    // 🔹 Obtener todas las carreras
    @GetMapping("/obtenerTodas")
    public List<DTO> getTodasCarreras() {
        List<DTO> listaCarrerasDTO = new ArrayList<>();
        List<Carreras> carreras = carrerasRepositorio.findAll();

        for (Carreras c : carreras) {
            DTO dtoCarrera = new DTO();
            dtoCarrera.put("id", c.getId());
            dtoCarrera.put("nombre", c.getNombre());
            dtoCarrera.put("fecha", c.getFecha());
            dtoCarrera.put("ubicacion", c.getUbicacion());
            listaCarrerasDTO.add(dtoCarrera);
        }
        return listaCarrerasDTO;
    }

    // 🔹 Obtener carrera por ID
    @PostMapping("/obtenerPorID")
    public DTO getCarreraPorID(@RequestBody DTO soloid, HttpServletRequest request) {
        DTO dtoCarrera = new DTO();
        Optional<Carreras> carrera = carrerasRepositorio.findById(Integer.parseInt(soloid.get("id").toString()));

        if (carrera.isPresent()) {
            Carreras c = carrera.get();
            dtoCarrera.put("id", c.getId());
            dtoCarrera.put("nombre", c.getNombre());
            dtoCarrera.put("fecha", c.getFecha());
            dtoCarrera.put("ubicacion", c.getUbicacion());
        } else {
            dtoCarrera.put("mensaje", "Carrera no encontrada");
        }

        return dtoCarrera;
    }

    // 🔹 Crear una nueva carrera
    @PostMapping("/anadirnueva")
    public void anadirCarrera(@RequestBody DatosCarrera datos) {
        carrerasRepositorio.save(
                new Carreras(datos.nombre, Date.valueOf(datos.fecha), datos.ubicacion));
    }

    // 🔹 Actualizar una carrera existente
    @PutMapping(path = "/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO actualizarCarrera(@RequestBody DatosCarrera datos) {
        DTO dto = new DTO();
        Optional<Carreras> carreraExistente = carrerasRepositorio.findById(datos.id);

        if (carreraExistente.isPresent()) {
            Carreras carrera = carreraExistente.get();
            carrera.setNombre(datos.nombre);
            carrera.setFecha(Date.valueOf(datos.fecha));
            carrera.setUbicacion(datos.ubicacion);
            carrerasRepositorio.save(carrera);
            dto.put("result", "ok");
        } else {
            dto.put("result", "fail");
        }

        return dto;
    }

    // 🔹 Borrar carrera por ID
    @DeleteMapping("/borrar")
    public DTO borrarCarrera(@RequestBody DTO soloid, HttpServletRequest request) {
        DTO respuesta = new DTO();
        int id = Integer.parseInt(soloid.get("id").toString());

        if (carrerasRepositorio.existsById(id)) {
            carrerasRepositorio.deleteById(id);
            respuesta.put("mensaje", "Carrera eliminada correctamente");
        } else {
            respuesta.put("mensaje", "Carrera no encontrada");
        }

        return respuesta;
    }

    static class DatosCarrera {
        public int id;
        public String nombre;
        public String fecha;
        public String ubicacion;
    }

}
