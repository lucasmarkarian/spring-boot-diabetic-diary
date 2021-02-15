package com.bolsadeideas.springboot.diabeticdiary.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.diabeticdiary.app.models.dao.IControlDiaryDaoCrud;
import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.AccountId;
import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.ControlDiary;

@Service
public class ControlDiaryServiceImpl implements IControlDiaryService {

	@Autowired
	IControlDiaryDaoCrud controlDiaryDaoCrud;
	
	@Override
	@Transactional
	public void save(ControlDiary control) {
		this.controlDiaryDaoCrud.save(control);
	}

	@Override
	@Transactional(readOnly = true)
	public ControlDiary findOne(AccountId accountId) {
		return this.controlDiaryDaoCrud.findById(accountId).orElse(null);
	}

	@Override
	public void delete(AccountId accountId) {
		this.controlDiaryDaoCrud.deleteById(accountId);
	}

	@Override
	public List<ControlDiary> findByUsernameBetweenDates(String username, Date desde, Date hasta) {
		return this.controlDiaryDaoCrud.findByUsernameBetweenDates(username, desde, hasta);
	}

}
