package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dao.EmpDao;
import com.example.demo.dao.EmpDaoImpl;
import com.example.demo.domain.Emp;

@Service
public class EmpServiceImpl implements EmpService {
	@Autowired
	private EmpDaoImpl empDao;

	@Override
	public int insert(Emp emp) {
		// 로직을 추가해야 할 때 서비스 클래스의 메소드 공간이 필요합니다.
		return empDao.insert(emp);
	}

	@Override
	public int update(Emp emp) {
		return empDao.update(emp);
	}

	@Override
	public int delete(int empno) {
		return empDao.delete(empno);
	}

	@Override
	public List<Emp> findAll() {
		return empDao.findAll();
	}

	@Override
	public int count() {
		return empDao.count();
	}

	@Override
	public Emp findOne(int empno) {
		return empDao.findOne(empno);
	}
}
