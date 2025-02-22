package com.example.demo.controladores;

import com.example.demo.modelos.Bicicletas;
import com.example.demo.modelos.Ciclista;
import com.example.demo.repositorios.BicicletasRepositorio;
import com.example.demo.repositorios.CiclistaRepositorio;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/bicicletas")
public class BicicletasController {

    @Autowired
    BicicletasRepositorio bicicletasRepositorio;
    @Autowired
    CiclistaRepositorio ciclistaRepositorio;

    // 🔹 Obtener todas las bicicletas
    @GetMapping("/obtenerTodas")
    public List<DTO> getTodasBicicletas() {
        List<DTO> listaBicicletasDTO = new ArrayList<>();
        List<Bicicletas> bicicletas = bicicletasRepositorio.findAll();

        for (Bicicletas b : bicicletas) {
            DTO dtoBici = new DTO();
            dtoBici.put("id", b.getId());
            dtoBici.put("marca", b.getMarca());
            dtoBici.put("modelo", b.getModelo());
            if (b.getCiclista() != null) {
                dtoBici.put("ciclista_id", b.getCiclista().getId());
            } else {
                dtoBici.put("ciclista_id", null);
            }
            listaBicicletasDTO.add(dtoBici);
        }

        return listaBicicletasDTO;
    }

    // 🔹 Obtener bicicleta por ID
    @GetMapping("/obtenerPorID")
    public DTO getBicicletaPorID(@RequestBody DTO soloid, HttpServletRequest request) {
        DTO dtoBici = new DTO();
        Bicicletas b = bicicletasRepositorio.findById(Integer.parseInt(soloid.get("id").toString()));

        if (b != null) {
            dtoBici.put("id", b.getId());
            dtoBici.put("marca", b.getMarca());
            dtoBici.put("modelo", b.getModelo());
            if (b.getCiclista() != null) {
                dtoBici.put("ciclista_id", b.getCiclista().getId());
            } else {
                dtoBici.put("ciclista_id", null);
            }
        } else {
            dtoBici.put("mensaje", "Bicicleta no encontrada");
        }

        return dtoBici;
    }

    // 🔹 Obtener bicicletas de un ciclista
    @PostMapping("/obtenerPorCiclista")
    public List<DTO> getBicicletasPorCiclista(@RequestBody DTO datos, HttpServletRequest request) {
        List<DTO> listaBicicletasDTO = new ArrayList<>();
        int ciclista_id = Integer.parseInt(datos.get("ciclista_id").toString());
        Ciclista ciclista = ciclistaRepositorio.findById(ciclista_id);

        if (ciclista != null) {
            List<Bicicletas> bicicletas = bicicletasRepositorio.findByCiclista(ciclista);

            for (Bicicletas b : bicicletas) {
                DTO dtoBici = new DTO();
                dtoBici.put("id", b.getId());
                dtoBici.put("marca", b.getMarca());
                dtoBici.put("modelo", b.getModelo());
                dtoBici.put("ciclista_id", ciclista_id);
                listaBicicletasDTO.add(dtoBici);
            }
        }

        return listaBicicletasDTO;
    }

    // 🔹 Crear bicicleta
    @PostMapping("/anadirBicicleta")
    public DTO anadirBicicleta(@RequestBody DatosAltaBicicleta b) {
        Ciclista ciclistaOpt = ciclistaRepositorio.findById(b.ciclistaId);
        DTO dto = new DTO();

        if (ciclistaOpt != null) { // Verificamos que el ciclista existe
            Bicicletas bicicleta = new Bicicletas();
            bicicleta.setMarca(b.marca);
            bicicleta.setModelo(b.modelo);
            bicicleta.setCiclista(ciclistaOpt);
    
            bicicletasRepositorio.save(bicicleta);
            dto.put("result", "ok");
            dto.put("mensaje", "Bicicleta insertada correctamente");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ciclista no encontrado");
        }
        return dto;
    }
    


public static class DatosAltaBicicleta {
    public String marca;
    public String modelo;
    public int ciclistaId; // Solo el ID del ciclista
}



    // 🔹 Actualizar bicicleta
    @PutMapping("/actualizar")
    public DTO actualizarBicicleta(@RequestBody DTO datos) {
        DTO dto = new DTO();
        int bicicletaId = Integer.parseInt(datos.get("id").toString());
        Bicicletas bicicleta = bicicletasRepositorio.findById(bicicletaId);

        if (bicicleta != null) {
            bicicleta.setMarca(datos.get("marca").toString());
            bicicleta.setModelo(datos.get("modelo").toString());
            bicicletasRepositorio.save(bicicleta);

            dto.put("result", "ok");
            dto.put("mensaje", "Bicicleta actualizada correctamente");
        } else {
            dto.put("result", "fail");
            dto.put("mensaje", "Bicicleta no encontrada");
        }

        return dto;
    }

    // 🔹 Borrar bicicleta por ID
    @DeleteMapping("/borrar")
    public DTO borrarBicicleta(@RequestBody DTO soloid, HttpServletRequest request) {
        DTO dtoBici = new DTO();
        int id = Integer.parseInt(soloid.get("id").toString());

        if (bicicletasRepositorio.existsById(id)) {
            bicicletasRepositorio.deleteById(id);
            dtoBici.put("result", "ok");

            dtoBici.put("mensaje", "Bicicleta eliminada correctamente");
        } else {
            dtoBici.put("result", "fail");

            dtoBici.put("mensaje", "Bicicleta no encontrada");
        }

        return dtoBici;
    }
}
