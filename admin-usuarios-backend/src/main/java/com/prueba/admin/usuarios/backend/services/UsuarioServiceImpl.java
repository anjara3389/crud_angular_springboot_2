package com.prueba.admin.usuarios.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.admin.usuarios.backend.dao.IUsuarioDao;
import com.prueba.admin.usuarios.backend.entity.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	IUsuarioDao usuarioDao;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDao.findAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public Usuario findById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public Usuario save(Usuario cliente) {
		return usuarioDao.save(cliente);
	}

	@Override
	public void delete(Long id) {
		usuarioDao.deleteById(id);
	}
}
