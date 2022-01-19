create schema olympics;

create table athletes(
Name varchar(255) NOT NULL, Country varchar(255) NOT NULL,SportsType int NOT NULL,
AID int UNIQUE NOT NULL, PRIMARY KEY (AID));

create table country(
Name varchar(255) NOT NULL,SoloMedals int NOT NULL,TeamMedals int NOT NULL, 
CID int UNIQUE NOT NULL, PRIMARY KEY (CID));

create table Referee(
Name varchar(255) NOT NULL,Type int NOT NULL, Country varchar(255) NOT NULL,RID int UNIQUE NOT NULL, PRIMARY KEY (RID));

create table Stadium(
Name varchar(255) NOT NULL,Seats int NOT NULL, Location varchar(255) NOT NULL,SID int UNIQUE NOT NULL, PRIMARY KEY (SID));

create table competition(
CID int NOT NULL, RID int NOT NULL,SID int NOT NULL,type int NOT NULL, foreign key(SID) references stadium(SID),foreign key(RID) references referee(RID), primary key(CID));

create table JumpTeam(AID int NOT NULL, Name varchar(255) NOT NULL, CID int NOT NULL, foreign key(CID) references country(CID));

create table RunTeam(AID int NOT NULL, Name varchar(255) NOT NULL, CID int NOT NULL, foreign key(CID) references country(CID));