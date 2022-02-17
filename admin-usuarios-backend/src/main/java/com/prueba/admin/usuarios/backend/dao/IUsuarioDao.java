package com.prueba.admin.usuarios.backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.prueba.admin.usuarios.backend.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

}

