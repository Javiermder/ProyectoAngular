package com.example.demo.modelos;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name="carreras_ciclistas")
@NamedQuery(name="CarrerasCiclista.findAll", query="SELECT c FROM CarrerasCiclista c")
public class CarrerasCiclista implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
	private int id;

	//bi-directional many-to-one association to Ciclista
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Ciclista ciclista;

	//bi-directional many-to-one association to Carrera
	@ManyToOne
	@JoinColumn(name="id_carrera")
	private Carreras carrera;

	// Constructor vacío
	public CarrerasCiclista() {
	}

	// Constructor sin ID (para crear nuevos objetos sin necesidad de ID)
	public CarrerasCiclista(Ciclista ciclista, Carreras carrera) {
		this.ciclista = ciclista;
		this.carrera = carrera;
	}

	// Constructor completo (para reconstrucción de objetos desde la BD)
	public CarrerasCiclista(int id, Ciclista ciclista, Carreras carrera) {
		this.id = id;
		this.ciclista = ciclista;
		this.carrera = carrera;
	}

	// Getters y Setters
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ciclista getCiclista() {
		return this.ciclista;
	}

	public void setCiclista(Ciclista ciclista) {
		this.ciclista = ciclista;
	}

	public Carreras getCarrera() {
		return this.carrera;
	}

	public void setCarrera(Carreras carrera) {
		this.carrera = carrera;
	}
}
