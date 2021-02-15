package com.bolsadeideas.springboot.diabeticdiary.app.models.service;

import java.util.Date;
import java.util.List;

import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.AccountId;
import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.ControlDiary;

public interface IControlDiaryService {
	
	public void save(ControlDiary control);
	
	public ControlDiary findOne(AccountId accountId);
	
	public void delete(AccountId accountId);
	
	public List<ControlDiary> findByUsernameBetweenDates(String username, Date desde, Date hasta);

}
