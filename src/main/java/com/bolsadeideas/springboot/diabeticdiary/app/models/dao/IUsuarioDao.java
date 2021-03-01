package com.bolsadeideas.springboot.diabeticdiary.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
	
	public Usuario findByUsername(String username);
	
	@Query("select COUNT(*) from Usuario")
	public Integer getAccountsNumber();

}
