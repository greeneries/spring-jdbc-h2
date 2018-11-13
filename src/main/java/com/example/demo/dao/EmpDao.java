package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.Emp;

// 인터페이스의 구현체 역할을 수행할 수 있는 객체(일종의 프록시)를 
// 마이바티스가 빈 컨테이너에 등록합니다.
@Mapper
public interface EmpDao {
	// 리턴자료형 int: 작업결과로 영향받은 row의 개수를 의미합니다.
	public int insert(Emp emp);
	public int update(Emp emp);
	public int delete(int empno);
	public List<Emp> findAll();
	public int count();
	public Emp findOne(int empno);
}