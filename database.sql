use vmeet;
DROP TABLE IF EXISTS Friendship;
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
CREATE TABLE Friendship (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    uid1 INTEGER,
    uid2 INTEGER,
    status VARCHAR(10) DEFAULT "pending",
    created_time DATETIME DEFAULT NOW(),
    last_modified_time DATETIME DEFAULT NOW(),
    CONSTRAINT FOREIGN KEY (uid1) REFERENCES Users(id),
    CONSTRAINT FOREIGN KEY (uid2) REFERENCES Users(id),
    CONSTRAINT UNIQUE(uid1, uid2)
);
insert into users (name,password,phone) values ("xyc","xyl3331996","13233131808");
insert into users (name,password,phone) values ("lhm","xyl3331996","15658682212");
select * from users;
select * from Friendship;