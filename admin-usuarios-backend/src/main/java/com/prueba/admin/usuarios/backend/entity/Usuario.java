package com.prueba.admin.usuarios.backend.entity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idUsuario;
	
	@Column(nullable = false)
	private Long idRol;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private char activo;
	
	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public char getActivo() {
		return activo;
	}

	public void setActivo(char activo) {
		this.activo = activo;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
