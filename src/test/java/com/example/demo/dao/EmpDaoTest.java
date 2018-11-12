package com.example.demo.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.domain.Emp;


/*
 * jUnit 테스트 코드 클래스 생성하려면
 *  패키지 마우스 오른쪽 클릭 > New > jUnit Test Case 선택 >
 *  > Name : EmpDaoImpleTest , class under test : com.example.demo.dao.EmpDao 입력 후 next >
 *  > EmpDao 모두 선택 > finish
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmpDaoTest {

	
//	@Autowired
	@Resource(name="empDao")
	private EmpDao dao;

	@Test
	public void testFindAll() {
		List<Emp> emps = dao.findAll();
		System.out.println(emps.size());
		
		for (Emp emp : emps) {
			System.out.println(emp);
		}
	}
  
	@Test
	public void testCount() {
		int count = dao.count();
		System.out.println(count);
	}
	
	
	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}



	@Test
	public void testFindOne() {
		fail("Not yet implemented");
	}

}
