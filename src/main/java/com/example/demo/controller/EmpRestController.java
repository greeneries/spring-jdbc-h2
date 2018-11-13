package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Emp;
import com.example.demo.service.EmpService;
import com.example.demo.service.EmpServiceImpl;

@RestController
public class EmpRestController {
	
	@Autowired
	private EmpService empService;
	
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public Object get() {
		return "Hello";
	}
		
	
	// Empno 목록  http://localhost:8080/emps
	@RequestMapping(value="/emps", method=RequestMethod.GET)
	public Object getAll() {
		List<Emp> emp = empService.findAll();
		return emp;
	}
	
	
	// Empno 검색 
	@RequestMapping(value="/emps/{id}", method=RequestMethod.GET)
	public Object getOne(@PathVariable("id") int id) {
		Emp emp = empService.findOne(id);
		return emp;
	}
	
	// Empno 저장 
	@RequestMapping(value="/emps", method=RequestMethod.POST)
	public Object postOne(@RequestBody Map<String, Object> map) {
		Emp emp = new Emp();
		emp.setEname((String)map.get("ename"));
		emp.setSal((Double)(map.get("sal")));
		emp.setJob((String)map.get("job"));

		int affected = empService.insert(emp);
		return emp;
	}
	
	
	// Empno 수정 
	@RequestMapping(value="/emps/{id}", method=RequestMethod.PUT)
	public Object putOne(@PathVariable("id") int id, @RequestBody Map<String, Object> map) {
		Emp emp = new Emp();
		emp.setEmpno(id);
		emp.setEname((String)map.get("ename"));
		emp.setSal((Double)(map.get("sal")));
		emp.setJob((String)map.get("job"));
		int affected = empService.update(emp);
		
		return emp;
	}
	
	// Empno 삭제 
	@RequestMapping(value="/emps/{id}", method=RequestMethod.DELETE)
	public Object deleteOne(@PathVariable("id") int id) {
		int affected = empService.delete(id);
		
		// 삭제된 데이터는 존재하지 않으므로 OK 의미만 전달 
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
