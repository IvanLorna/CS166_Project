## CS166 Project Phase 3

### Goal:
Develop a user interface for a Cruise Management system using Java Database Connector for SQL. The application will allow the user to execute specific queries to the system. This includes reading to, writing from, and manipulating tables in the database.

### Setup:
1. Access bolt server
2. run the following commands 
```bash ssh wch132-##	
git clone https://github.com/IvanLorna/CS166_Project.git
cd CS166_Project/code
source ./postgresql/startPostgreSQL.sh
source ./postgresql/createPostgreDB.sh
source ./java/compile.sh
source ./java/run.sh
```
	The application should now be running.
3. before exiting:
```bash 
source ./postgresql/stopPostgreDB.sh
```
### Assumptions:
- For new objects, their unique IDs are the current highest ID value +1
- We assume price input will always be in the correct monetary format (only up to 2 decnimal places).
- Tickets_sold can be greater than the number of seats. There are [tickets_sold - seats] people on the waitlist for that cruise.
- Cruises date only tracks year, month, day and not time

### Queries:
Find current highest value ID in table:
    ```SELECT MAX(T.id) FROM Table T;```
AddShip:
```
    INSERT INTO Ship (id, make, model, age, seats) 
    VALUES (new_id, uin_make, uin_model, uin_age, uin_seats);
```
AddCaptain:
```
    INSERT INTO Captain (id, fullname, nationality) 
    VALUES (new_id, uin_name, uin_nationality);
```      
AddCruise:
 ```
	 INSERT INTO Cruise VALUES (new_cnum, uin_cost, uin_tickets,
	  uin_stops, uin_depdate, uin_arrdate, uin_arrport, uin_depport); 
 ```
        
Find tickets_sold and num_seats to determine the status of new Reservation:
```
    SELECT  S.seats, C.num_sold FROM Cruise C, Ship S, CruiseInfo CI 
    WHERE CI.cruise_id = new_cnum AND CI.ship_id = S.id;
```
Update tickets_sold after making Reservation:
```
    UPDATE Cruise SET num_sold = num_sold+1 WHERE cnum = crnum
```   
BookCruise:
 ```
	INSERT INTO Reservation (rnum, ccid, cid, status) 
    VALUES (new_rnum, uin_custid, uin_cnum, status);
```
ListNumberOfAvailableSeats:
```
    SELECT C.num_sold, S.seats FROM Cruise C, Ship S, CruiseInfo CI 
    WHERE CI.cruise_id = cnum AND CI.cruise_id = C.num 
    AND C.actual_departure_date = ddate AND CI.ship_id = S.id;
```   
ListsTotalNumberOfRepairsPerShip:
```
    SELECT S.id as Ship_ID, COUNT(R.rid) as Repairs_Made FROM Ship S, Repairs R 
    WHERE S.id = R.ship_id GROUP BY S.id ORDER BY COUNT(R.rid) DESC;
```    
FindPassengersCountWithStatus:
```
    SELECT R.status, COUNT(DISTINCT R.ccid) FROM Reservation R 
    WHERE cid = uin_cnum AND status = uin_status GROUP BY R.status;
```
