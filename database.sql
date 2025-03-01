use vmeet;
DROP TRIGGER IF EXISTS AfterUserInsert;
DROP PROCEDURE IF EXISTS addToken;
DROP TABLE IF EXISTS Chat;
DROP TABLE IF EXISTS ChatSession;
DROP TABLE IF EXISTS Friendship;
DROP TABLE IF EXISTS AssetOwnership;
DROP TABLE IF EXISTS UserTokens;
DROP TABLE IF EXISTS UserProfiles;
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
CREATE TABLE UserProfiles
(
    uid       INTEGER PRIMARY KEY,
    credits   FLOAT DEFAULT 0,
    signature TEXT,
    CONSTRAINT FOREIGN KEY (uid) REFERENCES Users(id)
);

DELIMITER //

CREATE TRIGGER AfterUserInsert
AFTER INSERT ON Users
FOR EACH ROW
BEGIN
    INSERT INTO UserProfiles (uid)
    VALUES (NEW.id);
END;

//
DELIMITER ;

CREATE TABLE Assets (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20),
    uid INTEGER,
    type VARCHAR(20),
    privacy VARCHAR(10) DEFAULT "private",
    created_time DATETIME DEFAULT NOW(),
    last_modified_time DATETIME DEFAULT NOW(),
    price FLOAT DEFAULT 0,
    thumbimages TEXT,
    description TEXT,
    num_views INTEGER DEFAULT 0,
    num_buys INTEGER DEFAULT 0,
	CONSTRAINT FOREIGN KEY (uid) REFERENCES Users(id)
);
CREATE TABLE UserTokens (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    uid INTEGER,
    token VARCHAR(100),
    CONSTRAINT FOREIGN KEY (uid) REFERENCES Users(id)
);

DELIMITER //
CREATE PROCEDURE addToken(IN uid INT, IN token VARCHAR(100))
BEGIN
    IF EXISTS (SELECT * FROM UserTokens WHERE uid = uid) THEN
        UPDATE UserTokens SET token = token WHERE uid = uid;
    ELSE
        INSERT INTO UserTokens (uid, token) VALUES (uid, token);
    END IF;
END //
DELIMITER ;

CREATE TABLE AssetOwnership (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    uid INTEGER,
    aid INTEGER,
    CONSTRAINT FOREIGN KEY (uid) REFERENCES Users(id),
    CONSTRAINT FOREIGN KEY (aid) REFERENCES Assets(id)
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

CREATE TABLE Chat (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    from_uid INTEGER,
    to_uid INTEGER,
    content TEXT,
    visibility BOOLEAN DEFAULT 1,
    created_time DATETIME DEFAULT NOW(),
    CONSTRAINT FOREIGN KEY (from_uid) REFERENCES Users(id),
    CONSTRAINT FOREIGN KEY (to_uid) REFERENCES Users(id)
);

CREATE TABLE ChatSession (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    uid1 INTEGER,
    uid2 INTEGER,
    CONSTRAINT FOREIGN KEY (uid1) REFERENCES Users(id),
    CONSTRAINT FOREIGN KEY (uid2) REFERENCES Users(id)
);

insert into users (name,password,phone) values ("徐亦昶","xyl3331996","13233131808");