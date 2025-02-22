package com.example.demo.modelos;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "bicicletas")
@NamedQuery(name = "Bicicletas.findAll", query = "SELECT b FROM Bicicletas b")
public class Bicicletas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String marca;

	private String modelo;
	

	// bi-directional many-to-one association to Ciclista
	@ManyToOne
	private Ciclista ciclista;

	// Constructor vacío (Obligatorio para JPA)
	public Bicicletas() {
	}

	// Constructor con todos los atributos
	public Bicicletas(int id, String marca, String modelo, Ciclista ciclista) {
		this.id = id;
		this.marca = marca;
		this.modelo = modelo;
		this.ciclista = ciclista;
	}

	// Constructor sin ID (La base de datos lo generará automáticamente)
	public Bicicletas(String marca, String modelo, Ciclista ciclista) {
		this.marca = marca;
		this.modelo = modelo;
		this.ciclista = ciclista;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Ciclista getCiclista() {
		return this.ciclista;
	}

	public void setCiclista(Ciclista ciclista) {
		this.ciclista = ciclista;
	}
}
