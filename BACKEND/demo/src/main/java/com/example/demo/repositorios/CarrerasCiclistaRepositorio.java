package com.example.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.modelos.CarrerasCiclista;
import java.util.List;

@Repository
public interface CarrerasCiclistaRepositorio extends JpaRepository<CarrerasCiclista, Integer> {
    
    List<CarrerasCiclista> findByCiclistaId(int idUsuario);

    List<CarrerasCiclista> findByCarreraId(int idCarrera);
}
