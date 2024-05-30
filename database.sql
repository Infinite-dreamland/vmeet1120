use vmeet;
DROP TABLE IF EXISTS Users;
CREATE TABLE Users (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20),
    password VARCHAR(20),
    phone VARCHAR(11),
    CONSTRAINT UNIQUE(name),
	CONSTRAINT UNIQUE(phone)
);
select * from Users;