 package com.example.demo.repositorios;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelos.Ciclista;

import jakarta.transaction.Transactional;

public interface CiclistaRepositorio extends JpaRepository<Ciclista,Serializable>{
    Ciclista findByEmailAndPassword(String email, String password);

    @Bean
    @Transactional
    public abstract List<Ciclista> findAll();
    public abstract Ciclista findById(int id);
    public abstract Ciclista findByNombre(String nombre);

    @SuppressWarnings("unchecked")
    @Transactional
    public abstract Ciclista save(Ciclista c);

    @Transactional
    public abstract void deleteById(int id);
}