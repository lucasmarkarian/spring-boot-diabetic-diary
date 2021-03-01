package com.bolsadeideas.springboot.diabeticdiary.app.models.service;

import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.Usuario;

public interface IUsuarioService {
	
	public Usuario findByUsername(String username);
	
	public void save(Usuario usuario);
	
	public Long getAccountsNumber();

}
