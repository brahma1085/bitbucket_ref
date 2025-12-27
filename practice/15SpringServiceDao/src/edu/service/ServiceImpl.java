package edu.service;

import edu.dao.Dao;

public class ServiceImpl implements Service {
	private Dao dao;

	public void serviceMethod() {
		System.out.println("SERVICE-IMPL-METHOD-start");
		dao.daoMethod();
		System.out.println("SERVICE-IMPL-METHOD-end");
	}

	public ServiceImpl(Dao dao) {
		this.dao = dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

}
