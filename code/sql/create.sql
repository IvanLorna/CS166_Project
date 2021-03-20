DROP TABLE IF EXISTS Customer CASCADE;--OK
DROP TABLE IF EXISTS Cruise CASCADE;--OK
DROP TABLE IF EXISTS Captain CASCADE;--OK
DROP TABLE IF EXISTS Ship CASCADE;--OK
DROP TABLE IF EXISTS Technician CASCADE;--OK

DROP TABLE IF EXISTS Reservation CASCADE;--OK
DROP TABLE IF EXISTS CruiseInfo CASCADE;--OK
DROP TABLE IF EXISTS Repairs CASCADE;--OK
DROP TABLE IF EXISTS Schedule CASCADE;--OK

-------------
---DOMAINS---
-------------
CREATE DOMAIN us_postal_code AS TEXT CHECK(VALUE ~ '^\d{5}$' OR VALUE ~ '^\d{5}-\d{4}$');
CREATE DOMAIN _STATUS CHAR(1) CHECK (value IN ( 'W' , 'C', 'R' ) );
CREATE DOMAIN _GENDER CHAR(1) CHECK (value IN ( 'F' , 'M' ) );
CREATE DOMAIN _CODE CHAR(2) CHECK (value IN ( 'MJ' , 'MN', 'SV' ) ); --Major, Minimum, Service
CREATE DOMAIN _PINTEGER AS int4 CHECK(VALUE > 0);
CREATE DOMAIN _PZEROINTEGER AS int4 CHECK(VALUE >= 0);
CREATE DOMAIN _YEAR_1970 AS int4 CHECK(VALUE >= 0);
CREATE DOMAIN _SEATS AS int4 CHECK(VALUE > 0 AND VALUE < 500);--Ship Seats

------------
---TABLES---
------------
CREATE TABLE Customer
(
	id INTEGER NOT NULL,
	fname CHAR(24) NOT NULL,
	lname CHAR(24) NOT NULL,
	gtype _GENDER NOT NULL,
	dob DATE NOT NULL,
	address CHAR(256),
	phone CHAR(10),
	zipcode char(10),
	PRIMARY KEY (id)
);

CREATE TABLE Captain
(
	id INTEGER NOT NULL,
	fullname CHAR(128),
	nationality CHAR(24),
	PRIMARY KEY (id)
);

CREATE TABLE Cruise
(
	cnum INTEGER NOT NULL,
	cost _PINTEGER NOT NULL,
	num_sold _PZEROINTEGER NOT NULL,
	num_stops _PZEROINTEGER NOT NULL,
	actual_departure_date DATE NOT NULL,
	actual_arrival_date DATE NOT NULL,
	arrival_port CHAR(5) NOT NULL,-- PORT CODE --
	departure_port CHAR(5) NOT NULL,-- PORT CODE --
	PRIMARY KEY (cnum)
);

CREATE TABLE Ship
(
	id INTEGER NOT NULL,
	make CHAR(32) NOT NULL,
	model CHAR(64) NOT NULL,
	age _YEAR_1970 NOT NULL,
	seats _SEATS NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE Technician
(
	id INTEGER NOT NULL,
	full_name CHAR(128) NOT NULL,
	PRIMARY KEY (id)
);

---------------
---RELATIONS---
---------------

CREATE TABLE Reservation
(
	rnum INTEGER NOT NULL,
	ccid INTEGER NOT NULL,
	cid INTEGER NOT NULL,
	status _STATUS,
	PRIMARY KEY (rnum),
	FOREIGN KEY (ccid) REFERENCES Customer(id),
	FOREIGN KEY (cid) REFERENCES Cruise(cnum)
);

CREATE TABLE CruiseInfo
(
	ciid INTEGER NOT NULL,
	cruise_id INTEGER NOT NULL,
	captain_id INTEGER NOT NULL,
	ship_id INTEGER NOT NULL,
	PRIMARY KEY (ciid),
	FOREIGN KEY (cruise_id) REFERENCES Cruise(cnum),
	FOREIGN KEY (captain_id) REFERENCES Captain(id),
	FOREIGN KEY (ship_id) REFERENCES Ship(id)
);

CREATE TABLE Repairs
(
	rid INTEGER NOT NULL,
	repair_date DATE NOT NULL,
	repair_code _CODE,
	captain_id INTEGER NOT NULL,
	ship_id INTEGER NOT NULL,
	technician_id INTEGER NOT NULL,
	PRIMARY KEY (rid),
	FOREIGN KEY (captain_id) REFERENCES Captain(id),
	FOREIGN KEY (ship_id) REFERENCES Ship(id),
	FOREIGN KEY (technician_id) REFERENCES Technician(id)
);

CREATE TABLE Schedule
(
	id INTEGER NOT NULL,
	cruiseNum INTEGER NOT NULL,
	departure_time DATE NOT NULL,
	arrival_time DATE NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (cruiseNum) REFERENCES Cruise(cnum)
);

----------------------------
-- INSERT DATA STATEMENTS --
----------------------------

COPY Customer (
	id,
	fname,
	lname,
	gtype,
	dob,
	address,
	phone,
	zipcode
)
FROM 'customer.csv'
WITH DELIMITER ',';

COPY Captain (
	id,
	fullname,
	nationality
)
FROM 'Captains.csv'
WITH DELIMITER ',';

COPY Ship (
	id,
	make,
	model,
	age,
	seats
)
FROM 'Ships.csv'
WITH DELIMITER ',';

COPY Technician (
	id,
	full_name
)
FROM 'technician.csv'
WITH DELIMITER ',';

COPY Cruise (
	cnum,
	cost,
	num_sold,
	num_stops,
	actual_departure_date,
	actual_arrival_date,
	arrival_port,
	departure_port
)
FROM 'Cruises.csv'
WITH DELIMITER ',';

COPY Reservation (
	rnum,
	ccid,
	cid,
	status
)
FROM 'reservation.csv'
WITH DELIMITER ',';

COPY CruiseInfo (
	ciid,
	cruise_id,
	captain_id,
	ship_id
)
FROM 'Cruiseinfo.csv'
WITH DELIMITER ',';

COPY Repairs (
	rid,
	repair_date,
	repair_code,
	captain_id,
	ship_id,
	technician_id
)
FROM 'repairs.csv'
WITH DELIMITER ',';

COPY Schedule (
	id,
	cruiseNum,
	departure_time,
	arrival_time
)
FROM 'schedule.csv'
WITH DELIMITER ',';
