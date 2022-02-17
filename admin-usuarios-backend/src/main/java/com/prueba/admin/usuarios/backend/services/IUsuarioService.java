package com.prueba.admin.usuarios.backend.services;

import java.util.List;

import com.prueba.admin.usuarios.backend.entity.Usuario;

public interface IUsuarioService {
	public List<Usuario> findAll();

	public Usuario findById(Long id);

	public Usuario save(Usuario usuario);

	public void delete(Long id);
}
