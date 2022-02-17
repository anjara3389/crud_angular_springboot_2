package com.prueba.admin.usuarios.backend.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.admin.usuarios.backend.entity.Rol;
import com.prueba.admin.usuarios.backend.entity.Usuario;
import com.prueba.admin.usuarios.backend.services.IRolService;
import com.prueba.admin.usuarios.backend.services.IUsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class UsuariosRestController {
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IRolService rolService;
	
	private final Logger log=org.slf4j.LoggerFactory.getLogger(UsuariosRestController.class);

	@GetMapping("/Usuarios")
	public List<Usuario> index() {
		return usuarioService.findAll();
	}
	
	@GetMapping("/Roles")
	public List<Rol> getRoles() {
		return rolService.findAll();
	}
	
	@GetMapping("/Usuarios/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Usuario Usuario = usuarioService.findById(id);
			if (Usuario == null) {
				response.put("mensaje",
						"El Usuario ID: ".concat(id.toString().concat(" no exíste en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Usuario>(Usuario, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al consultar el Usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/Usuarios")
	public ResponseEntity<?> create(@RequestBody Usuario Usuario, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()))
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			Usuario UsuarioNew = usuarioService.save(Usuario);
			response.put("mensaje", "El Usuario ha sido creado con éxito");
			response.put("Usuario", UsuarioNew);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al crear el Usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/Usuarios/{id}")
	public ResponseEntity<?> update(@RequestBody Usuario Usuario, BindingResult result, @PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '".concat(err.getField()).concat("' ").concat(err.getDefaultMessage()))
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			Usuario UsuarioActual = usuarioService.findById(id);
			if (UsuarioActual == null) {
				response.put("mensaje",
						"Error, no se pudo editar. El Usuario ID: ".concat(id.toString().concat(" no exíste en la base de datos")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			UsuarioActual.setNombre(Usuario.getNombre());
			UsuarioActual.setIdRol(Usuario.getIdRol());
			UsuarioActual.setActivo(Usuario.getActivo());
			Usuario UsuarioUpdated = usuarioService.save(UsuarioActual);
			response.put("mensaje", "El Usuario ha sido actualizado con éxito");
			response.put("Usuario", UsuarioUpdated);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el Usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/Usuarios/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			usuarioService.delete(id);
			response.put("mensaje", "El Usuario ha sido eliminado con éxito");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el Usuario de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
