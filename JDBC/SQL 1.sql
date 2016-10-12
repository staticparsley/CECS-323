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
    VALUES('DanielGroup','Daniel','2016','Horror');

SELECT * FROM WritingGroup;

INSERT INTO WritingGroup
    VALUES('RubenGroup','Ruben','2015','Mystery');

SELECT * FROM WritingGroup;

INSERT INTO Publishers
    VALUES('DanielPub','123 Sunshine Dr','8675309','DanielPub@yahoo.com');

INSERT INTO Publishers
    VALUES('RubenPub','123 Fake st','5555555','rubenPub@sbcglobal.net');