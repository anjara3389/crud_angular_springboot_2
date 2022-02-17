package com.prueba.admin.usuarios.backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.prueba.admin.usuarios.backend.entity.Rol;

public interface IRolDao extends CrudRepository<Rol, Long> {

}

