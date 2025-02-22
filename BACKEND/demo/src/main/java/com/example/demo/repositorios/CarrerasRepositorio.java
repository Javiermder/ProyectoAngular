package com.example.demo.repositorios;

import com.example.demo.modelos.Carreras;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrerasRepositorio extends JpaRepository<Carreras, Serializable> {
}
