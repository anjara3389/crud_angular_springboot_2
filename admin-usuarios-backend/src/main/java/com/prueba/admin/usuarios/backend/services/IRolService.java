package com.prueba.admin.usuarios.backend.services;

import java.util.List;

import com.prueba.admin.usuarios.backend.entity.Rol;

public interface IRolService {
	public List<Rol> findAll();
}
