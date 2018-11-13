package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.Emp;

// 인터페이스의 구현체 역할을 수행할 수 있는 객체(일종의 프록시)를 
// 마이바티스가 빈 컨테이너에 등록합니다.
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


@Repository
public class EmpDaoImpl implements EmpDao {

	@Autowired
	public JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert jdbcInsert;

	@Autowired
	private NamedParameterJdbcTemplate nTemplate;

	// Row(테이블 행) 정보를 <Emp> 객체에 Mapper(매핑) 하기 위한 로직
	// 어는 칼럼의 데이터를 어는 멤버변수에 넣어야 하는지 알려주는 로직
	private RowMapper<Emp> rowMapper = new RowMapper<Emp>() {
		@Override
		public Emp mapRow(ResultSet rs, int rowNum) throws SQLException {
			Emp e = new Emp();
			e.setEmpno(rs.getInt("empno"));
			e.setEname(rs.getString("ename"));
			e.setJob(rs.getString("job"));
			e.setSal(rs.getDouble("sal"));
			return e;
		}
	};

	@PostConstruct
	public void init() {
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("EMP").usingGeneratedKeyColumns("empno");
		// SimpleJdbcInsert를 사용하면 디비가 자동으로 증가시키는 키 값을 얻을 수 있다.
	}

// @Autowired
// public void setDataSource(DataSource dataSource) {
// this.jdbcTemplate = new JdbcTemplate(dataSource);
// }

	@Override
	public int insert(Emp emp) {
		if (emp.getEmpno() == 0) {
			// 도메인 클래스의 멤버변수 이름과 테이블의 칼럼명이 일치하는 경우
			// 멤버변수를 칼럼명으로 사용하여 자동으로 SQL insert 쿼리문을 작성합니다.
			// jdbcInsert 객체를 사용할 때 따로 SQL 쿼리문을 알려줄 필요가 없습니다.
			SqlParameterSource param = new BeanPropertySqlParameterSource(emp);
			// 자바에서 숫자를 취급하는 Number객체 안에 디비가 제너레이트한 키 값이
			// 존재합니다.
			Number number = jdbcInsert.executeAndReturnKey(param);
			// 디비가 제너레이트한 키 값을 insert() 메소드가 받은 파라미터 객체에
			// 추가하면 객체는 참조이기 때문에 이 메소드를 호출한 측에서 바로 객체에서
			// 키 값을 꺼내서 사용할 수 있습니다.
			emp.setEmpno(number.intValue());
			return 1;
		} else {
			// PK인 empno 값을 개발자가 직접 지정해서 저장하는 방식입니다.
			// 디비가 PK 값을 제너레이트 하는 경우라면 이 방식을 사용하지 않습니다.
			String sql = "insert into EMP(empno, ename, job, sal) values(?, ?, ?, ?)";
			return jdbcTemplate.update(sql, emp.getEmpno(), emp.getEname(), emp.getJob(), emp.getSal());
		}
	}

	@Override
	public int update(Emp emp) {
		// String sql = "update EMP set ename=?, job=?, sal=? where empno=?";
		// return jdbcTemplate.update(sql,
		// emp.getEname(), emp.getJob(), emp.getSal(), emp.getEmpno());
		// 순서 기반 위치보유자 ? 대신,
		// 이름 기반 위치보유자 : 를 사용하면
		// 파라미터로 받은 값을 SQL 문자열과 결합할 때,
		// 순서가 헷갈려서 벌어지는 실수를 예방할 수 있습니다.
		String sql = "update EMP set ename=:ename, job=:job, sal=:sal " + "where empno=:key";
		Map<String, Object> map = new HashMap<>();
		map.put("key", emp.getEmpno());
		map.put("ename", emp.getEname());
		map.put("job", emp.getJob());
		map.put("sal", emp.getSal());
		return nTemplate.update(sql, map);
	}

	@Override
	public int delete(int empno) {
		String sql = "delete EMP where empno=?";
		return jdbcTemplate.update(sql, empno);
	}

	@Override
	public List<Emp> findAll() {
		String sql = "select empno, ename, job, sal from EMP order by empno asc";
		// RowMapper를 멤버변수나 별도의 클래스로 만들어서 사용하면 재 사용 시 편리하다.
		return jdbcTemplate.query(sql, rowMapper);
	}

	public List<Emp> findAll2() {
		String sql = "select * from EMP order by empno asc";
		// 칼럼명과 멤버변수명이 일치할 때 BeanPropertyRowMapper를 사용하면 편리하다.
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Emp>(Emp.class));
	}

	public List<Emp> findAll3() {
		String sql = "select * from EMP order by empno asc";
		// DTO를 따로 만들지 않고 사용할 때 유리하다.
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		List<Emp> emps = new ArrayList<Emp>();
		for (Map<String, Object> row : rows) {
			Emp e = new Emp();
			e.setEmpno((int) row.get("empno"));
			e.setEname((String) row.get("ename"));
			e.setJob((String) row.get("job"));
			e.setSal((double) row.get("sal"));
			emps.add(e);
		}
		return emps;
	}

	@Override
	public int count() {
		String sql = "select count(*) from EMP";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public Emp findOne(int empno) {
		String sql = "select empno, ename, job, sal from EMP where empno=?";
		return jdbcTemplate.queryForObject(sql, rowMapper, empno);
	}

	
	// save 메소드는 insert/update를 모두 지원하는 upsert 메소드입니다.
	public int save(Emp emp) {
		// 키 값이 없다면(0 값인 상태) 새 로우를 추가하는 insert 쿼리를 수행한다.
		if (emp.getEmpno() == 0) {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("ename", emp.getEname());
			paramMap.put("job", emp.getJob());
			paramMap.put("sal", emp.getSal());
			Number number = jdbcInsert.executeAndReturnKey(paramMap);
			emp.setEmpno(number.intValue());
			return 1;
		} else {
			// 키 값이 있다면 기존 로우를 수정하는 update 쿼리를 수행한다.
			String sql = "update EMP set ename=:ename, job=:job, sal=:sal where empno=:key";
			// 멤버변수와 칼럼명이 다르다면 BeanPropertySqlParameterSource를
			// 사용할 수 없다. 이 때, MapSqlParameterSource를 사용한다.
			SqlParameterSource paramSource = new MapSqlParameterSource().addValue("ename", emp.getEname())
					.addValue("job", emp.getJob()).addValue("sal", emp.getSal()).addValue("key", emp.getEmpno());
			return nTemplate.update(sql, paramSource);
		}
	}
	

}
