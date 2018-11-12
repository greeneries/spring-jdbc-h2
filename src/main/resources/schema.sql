drop table if exists emp;

create table emp (
	empno bigint identity not null primary key,
	ename varchar(100),
	job varchar(100),
	sal bigint
);