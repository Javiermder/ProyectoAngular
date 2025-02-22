package com.example.demo.modelos;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "ciclistas")
@NamedQuery(name = "Ciclista.findAll", query = "SELECT u FROM Ciclista u")
public class Ciclista implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String aficiones;

	private String email;

	private String nombre;

	private String pais;

	private String password;

	private String rol;

	private String sexo;

	// bi-directional many-to-one association to Bicicleta
	@OneToMany(mappedBy = "ciclista")
	private List<Bicicletas> bicicletas;

	// bi-directional many-to-one association to CarrerasCiclista
	@OneToMany(mappedBy = "ciclista")
	private List<CarrerasCiclista> carrerasCiclistas;

	// Constructor vacío (obligatorio para JPA)
	public Ciclista() {
	}

	// Constructor con todos los atributos
	public Ciclista(int id, String nombre, String email, String password, String sexo, String pais, String aficiones,
			String rol) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.sexo = sexo;
		this.pais = pais;
		this.aficiones = aficiones;
		this.rol = rol;
	}

	// Constructor sin ID (Spring Boot generalmente maneja el ID automáticamente)
	public Ciclista(String nombre, String email, String password, String sexo, String pais, String aficiones,
			String rol) {
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.sexo = sexo;
		this.pais = pais;
		this.aficiones = aficiones;
		this.rol = rol;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAficiones() {
		return this.aficiones;
	}

	public void setAficiones(String aficiones) {
		this.aficiones = aficiones;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return this.rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public List<Bicicletas> getBicicletas() {
		return this.bicicletas;
	}

	public void setBicicletas(List<Bicicletas> bicicletas) {
		this.bicicletas = bicicletas;
	}

	public Bicicletas addBicicleta(Bicicletas bicicleta) {
		getBicicletas().add(bicicleta);
		bicicleta.setCiclista(this);

		return bicicleta;
	}

	public Bicicletas removeBicicleta(Bicicletas bicicleta) {
		getBicicletas().remove(bicicleta);
		bicicleta.setCiclista(null);

		return bicicleta;
	}

	public List<CarrerasCiclista> getCarrerasCiclistas() {
		return this.carrerasCiclistas;
	}

	public void setCarrerasCiclistas(List<CarrerasCiclista> carrerasCiclistas) {
		this.carrerasCiclistas = carrerasCiclistas;
	}

	public CarrerasCiclista addCarrerasCiclista(CarrerasCiclista carrerasCiclista) {
		getCarrerasCiclistas().add(carrerasCiclista);
		carrerasCiclista.setCiclista(this);

		return carrerasCiclista;
	}

	public CarrerasCiclista removeCarrerasCiclista(CarrerasCiclista carrerasCiclista) {
		getCarrerasCiclistas().remove(carrerasCiclista);
		carrerasCiclista.setCiclista(null);

		return carrerasCiclista;
	}
}
