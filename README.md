CS166_Project 

Summary:
Develop a user interface for a Cruise Management system using Java Database Connector for SQL. The application will allow the user to execute specific queries to the system. This includes reading to, writing from, and manipulating tables in the database.

Instructions:
Access bolt server
run the following commands
ssh wch132-##	
git clone https://github.com/IvanLorna/CS166_Project.git
cd CS166_Project/code
source ./postgresql/startPostgreSQL.sh
source ./postgresql/createPostgreDB.sh
source ./java/compile.sh
source ./java/run.sh
the application should now be running
before exiting:
source ./postgresql/stopPostgreDB.sh

Assumptions:
for new objects, their unique IDs are the current highest ID value +1
we assume price input will always be in the correct monetary format
user will follow the syntax for dates correctly (YYYY-MM-DD)
tickets_sold can be greater than the number of seats, there are [tickets_sold - seats] people on the waitlist for that cruise.
Cruises date only tracks year, month, day and not time
Customer ID is 10 digits long
Ship has 500 seats

Queries:
Find current highest value ID in table=
    SELECT MAX(T.id) FROM Table T;
    
1) AddShip=
    INSERT INTO Ship (id, make, model, age, seats) 
    VALUES (new_id, uin_make, uin_model, uin_age, uin_seats);
        
2) AddCaptain=
    INSERT INTO Captain (id, fullname, nationality) 
    VALUES (new_id, uin_name, uin_nationality);
        
3) AddCruise=
    INSERT INTO Cruise 
    VALUES (new_cnum, uin_cost, uin_tickets, uin_stops, uin_depdate, uin_arrdate, uin_arrport, uin_depport);
        
Find tickets_sold and num_seats to determine the status of new Reservation=
    SELECT  S.seats, C.num_sold FROM Cruise C, Ship S, CruiseInfo CI 
    WHERE CI.cruise_id = new_cnum AND CI.ship_id = S.id;

Update tickets_sold after making Reservation=
    UPDATE Cruise SET num_sold = num_sold+1 WHERE cnum = crnum
    
4) BookCruise=
    INSERT INTO Reservation (rnum, ccid, cid, status) 
    VALUES (new_rnum, uin_custid, uin_cnum, status);
    
5) ListNumberOfAvailableSeats
    SELECT C.num_sold, S.seats FROM Cruise C, Ship S, CruiseInfo CI 
    WHERE CI.cruise_id = cnum AND CI.cruise_id = C.num AND C.actual_departure_date = ddate AND CI.ship_id = S.id;
    
6) ListsTotalNumberOfRepairsPerShip
    SELECT S.id as Ship_ID, COUNT(R.rid) as Repairs_Made FROM Ship S, Repairs R 
    WHERE S.id = R.ship_id GROUP BY S.id ORDER BY COUNT(R.rid) DESC;
    
7) FindPassengersCountWithStatus
    SELECT R.status, COUNT(DISTINCT R.ccid) FROM Reservation R 
    WHERE cid = uin_cnum AND status = uin_status GROUP BY R.status;
