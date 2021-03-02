package com.bolsadeideas.springboot.diabeticdiary.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.diabeticdiary.app.models.dao.IUsuarioDao;
import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.Role;
import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.Usuario;

@Service
public class IUsuarioServiceImpl implements IUsuarioService {
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Usuario findByUsername(String username) {
		return this.usuarioDao.findByUsername(username);
	}

	@Override
	public void save(Usuario usuario) {
		String encryptedPassword = this.passwordEncoder.encode(usuario.getPassword());
		usuario.setPassword(encryptedPassword);
		usuario.setEnabled(true);
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_USER"));
		usuario.setRoles(roles);
		
		this.usuarioDao.save(usuario);
	}

	@Override
	public Long getAccountsNumber() {
		return this.usuarioDao.count();
	}

	@Override
	@Transactional
	public void update(String name, String lastName, Long id) {
		this.usuarioDao.update(name, lastName, id);
	}

}
