package com.example.demo.controladores;

import com.example.demo.modelos.Carreras;
import com.example.demo.modelos.Ciclista;
import com.example.demo.repositorios.CiclistaRepositorio;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.jwSecurity.AutenticadorJWT;
import org.springframework.transaction.annotation.Transactional;

@CrossOrigin
@RestController
@RequestMapping("/ciclista")
public class CiclistaController {

    @Autowired
    CiclistaRepositorio cRepositorio;

    @GetMapping("/obtener")
    @Transactional
    public List<DTO> getCiclistas() {
        List<DTO> listaCiclistasDTO = new ArrayList<>();
        List<Ciclista> ciclistas = cRepositorio.findAll();

        for (Ciclista c : ciclistas) {
            DTO dtoCiclista = new DTO();
            dtoCiclista.put("id", c.getId());
            dtoCiclista.put("nombre", c.getNombre());
            dtoCiclista.put("email", c.getEmail());
            dtoCiclista.put("sexo", c.getSexo());
            dtoCiclista.put("pais", c.getPais());
            dtoCiclista.put("aficiones", c.getAficiones());
            dtoCiclista.put("rol", c.getRol());
            listaCiclistasDTO.add(dtoCiclista);
        }

        return listaCiclistasDTO;
    }

    @PostMapping(path = "/obtener1", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getCiclistaPorID(@RequestBody DTO soloid) {
        DTO dtoCiclista = new DTO();
        Ciclista c = cRepositorio.findById(Integer.parseInt(soloid.get("id").toString()));

        if (c != null) {
            dtoCiclista.put("id", c.getId());
            dtoCiclista.put("nombre", c.getNombre());
            dtoCiclista.put("email", c.getEmail());
            dtoCiclista.put("sexo", c.getSexo());
            dtoCiclista.put("pais", c.getPais());
            dtoCiclista.put("aficiones", c.getAficiones());
            dtoCiclista.put("rol", c.getRol());
        } else {
            dtoCiclista.put("result", "fail");
        }
        return dtoCiclista;
    }

    @DeleteMapping(path = "/borrar1", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO borrarCiclista(@RequestBody DTO soloid) {
        DTO dtoCiclista = new DTO();
        Ciclista c = cRepositorio.findById(Integer.parseInt(soloid.get("id").toString()));

        if (c != null) {
            cRepositorio.delete(c);
            dtoCiclista.put("borrado", "ok");
        } else {
            dtoCiclista.put("borrado", "fail");
        }

        return dtoCiclista;
    }

    @PostMapping("/anadirnuevo")
    public void anadirCiclista(@RequestBody DatosAltaCiclista c) {
        cRepositorio.save(
                new Ciclista(c.id, c.nombre, c.email, c.password, c.sexo, c.pais, c.aficiones, "user"));
    }

    @PostMapping(path = "/autentica", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO autenticaCiclista(@RequestBody DatosAutenticaCiclista datos, HttpServletResponse response) {
        DTO dto = new DTO();
        dto.put("result", "fail");
        Ciclista ciclistaAutenticado = cRepositorio.findByEmailAndPassword(datos.email, datos.password);

        if (ciclistaAutenticado != null) {
            dto.put("result", "ok");
            dto.put("jwt", AutenticadorJWT.codificaJWT(ciclistaAutenticado));
            Cookie cook = new Cookie("jwt", AutenticadorJWT.codificaJWT(ciclistaAutenticado));
            cook.setMaxAge(-1);
            response.addCookie(cook);
        }

        return dto;
    }

    @GetMapping(path = "/quieneres")
    public DTO getAutenticado(@RequestHeader("Authorization") String token) {
        DTO dtoCiclista = new DTO();
        int idCiclistaAutenticado = -1;

        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7); // Eliminar "Bearer "
            idCiclistaAutenticado = AutenticadorJWT.getIdUsuarioDesdeJWT(jwt);
        }

        Ciclista c = cRepositorio.findById(idCiclistaAutenticado);
        if (c != null) {
            dtoCiclista.put("id", c.getId());
            dtoCiclista.put("nombre", c.getNombre());
            dtoCiclista.put("email", c.getEmail());
            dtoCiclista.put("password", c.getPassword());
            dtoCiclista.put("sexo", c.getSexo());
            dtoCiclista.put("pais", c.getPais());
            dtoCiclista.put("aficiones", c.getAficiones());
            dtoCiclista.put("rol", c.getRol());
        } else {
            dtoCiclista.put("error", "Usuario no autenticado");
        }

        return dtoCiclista;
    }

    @PutMapping(path = "/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO actualizarCiclista(@RequestBody DatosAltaCiclista datos) {
        DTO dto = new DTO();
        Ciclista ciclista = cRepositorio.findById(datos.id);

        if (ciclista != null) {
            ciclista.setNombre(datos.nombre);
            ciclista.setEmail(datos.email);
            ciclista.setPassword(datos.password);
            ciclista.setSexo(datos.sexo);
            ciclista.setPais(datos.pais);
            ciclista.setAficiones(datos.aficiones);
            cRepositorio.save(ciclista);
            dto.put("result", "ok");
        } else {
            dto.put("result", "fail");
        }

        return dto;
    }

    static class DatosAutenticaCiclista {
        String email;
        String password;

        public DatosAutenticaCiclista(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    static class DatosAltaCiclista {
        int id;
        String nombre;
        String email;
        String password;
        String sexo;
        String pais;
        String aficiones;
        List<Carreras> carreras;

        public DatosAltaCiclista(int id, String nombre, String email, String password, String sexo, String pais,
                String aficiones, List<Carreras> carreras) {
            this.id = id;
            this.nombre = nombre;
            this.email = email;
            this.password = password;
            this.sexo = sexo;
            this.pais = pais;
            this.aficiones = aficiones;
            this.carreras = carreras;
        }
    }
}
