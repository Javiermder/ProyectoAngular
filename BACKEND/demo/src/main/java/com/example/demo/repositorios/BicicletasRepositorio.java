package com.example.demo.repositorios;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelos.Bicicletas;
import com.example.demo.modelos.Ciclista;

import jakarta.transaction.Transactional;

public interface BicicletasRepositorio extends JpaRepository<Bicicletas, Serializable> {
    @Bean
    public abstract List<Bicicletas> findAll(); 
    public abstract Bicicletas findById(int id);
     List<Bicicletas> findByCiclista(Ciclista ciclista);

    @SuppressWarnings("unchecked")
    @Transactional
    public abstract Bicicletas save(Bicicletas b);

    @Transactional
    public abstract void deleteById(int id);
    
}
