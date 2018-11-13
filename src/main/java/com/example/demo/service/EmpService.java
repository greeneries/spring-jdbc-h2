package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.Emp;

public interface EmpService {


	public int insert(Emp emp);
	public int update(Emp emp);
	public int delete(int empno);
	public List<Emp> findAll();
	public int count();
	public Emp findOne(int empno);


}
