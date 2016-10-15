CREATE TABLE WritingGroup (
GroupName varchar(30) NOT NULL,
HeadWriter varchar(30),
YearFormed varchar(5),
Subject varchar(20),
CONSTRAINT group_pk primary key(GroupName)
);

CREATE TABLE Publishers (
PubName varchar(20) NOT NULL,
PubAddr varchar (30),
PubPhone varchar (10),
pubEmail varchar (40),
CONSTRAINT pub_pk primary key(PubName)
);

CREATE TABLE Book (
BookTitle varchar(50),
YearPublished varchar(4),
NumberPages varchar (4),
group_name varchar(30),
pub_name varchar(20),
CONSTRAINT book_fk1 foreign key (group_name) references WritingGroup(GroupName),
CONSTRAINT book_fk2 foreign key (pub_name) references Publishers(pubName),
CONSTRAINT book_pk primary key (group_name,BookTitle)
);

INSERT INTO WritingGroup
    VALUES ('DanielGroup', 'Daniel', '2016', 'Horror'),
           ('RubenGroup', 'Ruben', '2015', 'Mystery'),
           ('MimiGroup', 'Mimi', '2004', 'Political Thriller'),
           ('HillaryGroup', 'Hillary', '2007', 'Young Adult'),
           ('TrumpGroup', 'Trump', '2010', 'Self-Help');

INSERT INTO Publishers
    VALUES ('DanielPub', '123 Sunshine Dr', '867-5309', 'DanielPub@yahoo.com'),
           ('RubenPub', '123 Fake St', '555-5555', 'RubenPub@sbcglobal.com'),
           ('PaddysPub', '123 Fascination St', '123-4567', 'PaddysPub@gmail.com'),
           ('AnyonePub', '123 Peachtree Rd', '555-9999', 'AnyonePub@gmail.com');

INSERT INTO Book
    Values ('When Sandwiches Attack', '2016', '1452', 'DanielGroup', 'DanielPub'),
           ('Low Battery', '2016', '1976', 'DanielGroup', 'AnyonePub'),
           ('In the Lion''s Den', '2012', '789', 'RubenGroup', 'RubenPub'),
           ('Stacy''s Dad', '2003', '546', 'RubenGroup', 'RubenPub'),
           ('Clump for President', '2016', '612', 'MimiGroup', 'PaddysPub'),
           ('Election Day at Bernie''s', '2016', '854', 'MimiGroup', 'PaddysPub'),
           ('See? I Was Right', '2017', '348', 'HillaryGroup', 'AnyonePub'),
           ('No I was Right', '2020', '28', 'TrumpGroup', 'AnyonePub');

SELECT * FROM WritingGroup;
SELECT * FROM Publishers;
SELECT * FROM Book;