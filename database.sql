use vmeet;
DROP TABLE IF EXISTS Assets;
DROP TABLE IF EXISTS Users;
CREATE TABLE Users (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20),
    password VARCHAR(20),
    phone VARCHAR(11),
    CONSTRAINT UNIQUE(name),
	CONSTRAINT UNIQUE(phone)
);
CREATE TABLE Assets (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20),
    uid INTEGER,
    type VARCHAR(20),
    privacy VARCHAR(10) DEFAULT "private",
    created_time DATETIME DEFAULT NOW(),
    last_modified_time DATETIME DEFAULT NOW(),
	CONSTRAINT FOREIGN KEY (uid) REFERENCES Users(id)
);
select * from Assets;
insert into Assets (name, uid, type) values ("trial", 1, "Scene");
update Assets SET privacy = "public" where id = 1;