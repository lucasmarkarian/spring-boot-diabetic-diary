package com.bolsadeideas.springboot.diabeticdiary.app.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.AccountId;
import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.ControlDiary;

public interface IControlDiaryDaoCrud extends CrudRepository<ControlDiary, AccountId> {

	@Query("select c from ControlDiary c where c.username_match = ?1 and c.date between ?2 and ?3")
	public List<ControlDiary> findByUsernameBetweenDates(String username, Date desde, Date hasta);
	
}
