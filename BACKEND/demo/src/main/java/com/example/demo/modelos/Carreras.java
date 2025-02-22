package com.example.demo.modelos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "carreras")
@NamedQuery(name = "Carreras.findAll", query = "select c from Carreras c")
public class Carreras implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private String nombre;

	private String ubicacion;

	// bi-directional many-to-one association to CarrerasCiclista
	@OneToMany(mappedBy = "carrera")
	private List<CarrerasCiclista> carrerasCiclistas;

	// Constructor vacío (Obligatorio para JPA)
	public Carreras() {
		this.carrerasCiclistas = new ArrayList<>(); // Evita NullPointerException
	}

	// Constructor con todos los atributos
	public Carreras(int id, String nombre, Date fecha, String ubicacion, List<CarrerasCiclista> carrerasCiclistas) {
		this.id = id;
		this.nombre = nombre;
		this.fecha = fecha;
		this.ubicacion = ubicacion;
		this.carrerasCiclistas = carrerasCiclistas != null ? carrerasCiclistas : new ArrayList<>();
	}

	// Constructor sin ID (Ideal para insertar nuevas carreras)
	public Carreras(String nombre, Date fecha, String ubicacion) {
		this.nombre = nombre;
		this.fecha = fecha;
		this.ubicacion = ubicacion;
		this.carrerasCiclistas = new ArrayList<>();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUbicacion() {
		return this.ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<CarrerasCiclista> getCarrerasCiclistas() {
		return this.carrerasCiclistas;
	}

	public void setCarrerasCiclistas(List<CarrerasCiclista> carrerasCiclistas) {
		this.carrerasCiclistas = carrerasCiclistas;
	}

	public CarrerasCiclista addCarrerasCiclista(CarrerasCiclista carrerasCiclista) {
		getCarrerasCiclistas().add(carrerasCiclista);
		carrerasCiclista.setCarrera(this);

		return carrerasCiclista;
	}

	public CarrerasCiclista removeCarrerasCiclista(CarrerasCiclista carrerasCiclista) {
		getCarrerasCiclistas().remove(carrerasCiclista);
		carrerasCiclista.setCarrera(null);

		return carrerasCiclista;
	}

}
