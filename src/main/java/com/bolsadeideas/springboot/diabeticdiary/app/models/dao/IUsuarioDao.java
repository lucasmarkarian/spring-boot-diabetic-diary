package com.bolsadeideas.springboot.diabeticdiary.app.models.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
	
	public Usuario findByUsername(String username);
	
	@Modifying
	@Query("update Usuario set nombre=?1, apellido=?2 where id=?3")
	public void update(String name, String lastName, Long id);

}
