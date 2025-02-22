package com.example.demo.controladores;

import com.example.demo.modelos.CarrerasCiclista;
import com.example.demo.modelos.Ciclista;
import com.example.demo.modelos.Carreras;
import com.example.demo.repositorios.CarrerasCiclistaRepositorio;
import com.example.demo.repositorios.CiclistaRepositorio;
import com.example.demo.repositorios.CarrerasRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@CrossOrigin
@RestController
@RequestMapping("/carreras-ciclistas")
public class CarrerasCiclistaController {

    @Autowired
    CarrerasCiclistaRepositorio carrerasCiclistaRepositorio;

    @Autowired
    CiclistaRepositorio ciclistaRepositorio;

    @Autowired
    CarrerasRepositorio carrerasRepositorio;

    // Obtener todas las relaciones carrera-ciclista en formato DTO
    @GetMapping("/obtener")
    @Transactional
    public List<DTO> getCarrerasCiclistas() {
        List<DTO> listaCarrerasCiclistasDTO = new ArrayList<>();
        List<CarrerasCiclista> relaciones = carrerasCiclistaRepositorio.findAll();

        for (CarrerasCiclista cc : relaciones) {
            DTO dtoRelacion = new DTO();
            dtoRelacion.put("id", cc.getId());
            dtoRelacion.put("ciclista_id", cc.getCiclista().getId());
            dtoRelacion.put("ciclista_nombre", cc.getCiclista().getNombre());
            dtoRelacion.put("carrera_id", cc.getCarrera().getId());
            dtoRelacion.put("carrera_nombre", cc.getCarrera().getNombre());
            listaCarrerasCiclistasDTO.add(dtoRelacion);
        }

        return listaCarrerasCiclistasDTO;
    }

    // Obtener todas las carreras de un ciclista específico
    @PostMapping(path = "/obtenerPorCiclista", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<DTO> obtenerPorCiclista(@RequestBody DTO soloid) {
        List<DTO> listaCarrerasDTO = new ArrayList<>();
        List<CarrerasCiclista> carreras = carrerasCiclistaRepositorio
                .findByCiclistaId(Integer.parseInt(soloid.get("id").toString()));

        for (CarrerasCiclista cc : carreras) {
            DTO dtoCarrera = new DTO();
            dtoCarrera.put("id", cc.getCarrera().getId());
            dtoCarrera.put("nombre", cc.getCarrera().getNombre());
            dtoCarrera.put("fecha", cc.getCarrera().getFecha());
            dtoCarrera.put("ubicacion", cc.getCarrera().getUbicacion());
            listaCarrerasDTO.add(dtoCarrera);
        }

        return listaCarrerasDTO;
    }

    // Obtener todos los ciclistas de una carrera específica
    @PostMapping(path = "/obtenerPorCarrera", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<DTO> obtenerPorCarrera(@RequestBody DTO soloid) {
        List<DTO> listaCiclistasDTO = new ArrayList<>();
        List<CarrerasCiclista> ciclistas = carrerasCiclistaRepositorio
                .findByCarreraId(Integer.parseInt(soloid.get("id").toString()));

        for (CarrerasCiclista cc : ciclistas) {
            DTO dtoCiclista = new DTO();
            dtoCiclista.put("id", cc.getCiclista().getId());
            dtoCiclista.put("nombre", cc.getCiclista().getNombre());
            dtoCiclista.put("email", cc.getCiclista().getEmail());
            dtoCiclista.put("sexo", cc.getCiclista().getSexo());
            dtoCiclista.put("pais", cc.getCiclista().getPais());
            dtoCiclista.put("aficiones", cc.getCiclista().getAficiones());
            dtoCiclista.put("rol", cc.getCiclista().getRol());
            listaCiclistasDTO.add(dtoCiclista);
        }

        return listaCiclistasDTO;
    }

    // Asignar un ciclista a una carrera
    @PostMapping(path = "/asignar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO asignarCiclistaACarrera(@RequestBody DatosAsignacion datos) {
        DTO dto = new DTO();
        dto.put("result", "fail");

        // Buscamos al ciclista directamente, no usando Optional
        Ciclista ciclistaOpt = ciclistaRepositorio.findById(datos.idCiclista);

        // Buscamos la carrera directamente, no usando Optional
        Carreras carreraOpt = carrerasRepositorio.findById(datos.idCarrera).orElse(null);

        // Verificamos que tanto el ciclista como la carrera existan
        if (ciclistaOpt != null && carreraOpt != null) {
            CarrerasCiclista nuevaRelacion = new CarrerasCiclista(ciclistaOpt, carreraOpt);
            carrerasCiclistaRepositorio.save(nuevaRelacion);
            dto.put("result", "ok");
        } else {
            dto.put("mensaje", "Ciclista o carrera no encontrados");
        }

        return dto;
    }

    // Eliminar una relación entre ciclista y carrera
    @DeleteMapping(path = "/eliminar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO eliminarRelacion(@RequestBody DTO soloid) {
        DTO dto = new DTO();
        Optional<CarrerasCiclista> relacionOpt = carrerasCiclistaRepositorio
                .findById(Integer.parseInt(soloid.get("id").toString()));

        if (relacionOpt.isPresent()) {
            carrerasCiclistaRepositorio.delete(relacionOpt.get());
            dto.put("borrado", "ok");
        } else {
            dto.put("borrado", "fail");
        }

        return dto;
    }

    // Clase interna para manejar asignación de ciclistas a carreras
    static class DatosAsignacion {
        int idCiclista;
        int idCarrera;

        public DatosAsignacion(int idCiclista, int idCarrera) {
            this.idCiclista = idCiclista;
            this.idCarrera = idCarrera;
        }
    }
}
