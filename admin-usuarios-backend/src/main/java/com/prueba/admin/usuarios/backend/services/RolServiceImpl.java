package com.prueba.admin.usuarios.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.admin.usuarios.backend.dao.IRolDao;
import com.prueba.admin.usuarios.backend.entity.Rol;

@Service
public class RolServiceImpl implements IRolService {

	@Autowired
	IRolDao rolDao;

	@Override
	@Transactional(readOnly = true)
	public List<Rol> findAll() {
		return (List<Rol>) rolDao.findAll();
	}
}
