--<ScriptOptions statementTerminator=";"/>

ALTER TABLE `MySQL`.`employeedetails` DROP PRIMARY KEY;

DROP TABLE `MySQL`.`employeedetails`;

CREATE TABLE `MySQL`.`employeedetails` (
	`empid` INT DEFAULT 0 NOT NULL,
	`firstname` VARCHAR(20) NOT NULL,
	`middlename` VARCHAR(20),
	`lastname` VARCHAR(20),
	`pwd` VARCHAR(10) NOT NULL,
	`state` VARCHAR(20),
	`dob` VARCHAR(30),
	`designation` VARCHAR(50),
	`dayphone` VARCHAR(20),
	`mobile` VARCHAR(20),
	`pfvalue` VARCHAR(10) DEFAULT 8.5 NOT NULL,
	`maritalstatus` VARCHAR(10),
	`country` VARCHAR(20),
	`gender` VARCHAR(10),
	`address` VARCHAR(100),
	`email` VARCHAR(30),
	`city` VARCHAR(20),
	`zip` INT,
	`salary` VARCHAR(20) NOT NULL,
	PRIMARY KEY (`empid`)
) ENGINE=InnoDB;

