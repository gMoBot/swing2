drop table if exists persons;


create table persons (
	person_id INTEGER PRIMARY KEY AUTOINCREMENT,
	name char(40),
	occupation char(20),
	age_cat int (10),
	emp_cat int (10),
	tax_id char(20),
  isus_citizen char(20),
  gender char(10)
);
