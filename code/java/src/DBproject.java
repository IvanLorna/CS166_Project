/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

//added for date validation
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */

public class DBproject{
	//reference to physical database connection
	private Connection _connection = null;
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	//check positive number helper function
	public static boolean checkifpositive(String str) {
		if (str.trim().equals("")) {
			System.out.println("empty");
		    return false;
		} else {
		    int num;
			try {
				num = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				System.out.println(str + " is not a number");
				return false;
			}
			if (num >= 0) {
				//System.out.println(str + " is valid,  positive");
				return true;
			} else {
				System.out.println(str + " is not positive");
				return false;
			}
		}
	}
	
	//check positive double helper function
	public static boolean checkifposdouble(String str) {
		if (str.trim().equals("")) {
			System.out.println("empty");
		    return false;
		} else {
		    double num;
			try {
				num = Double.parseDouble(str);
			} catch (NumberFormatException e) {
				System.out.println(str + " is not a double");
				return false;
			}
			if (num >= 0) {
				//System.out.println(str + " is valid, positive double");
				return true;
			} else {
				System.out.println(str + " is not positive");
				return false;
			}
		}
	}

	//check number 0-500 helper function
	public static boolean checkifrange(String str) {
		if (str.trim().equals("")) {
			System.out.println("empty");
		    return false;
		} else {
		    int num;
			try {
				num = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				System.out.println(str + " is not a number");
				return false;
			}
			if ((num >= 0) && (num <= 500)) {
				//System.out.println(str + " is valid, between 0-500");
				return true;
			} else {
				System.out.println(str + " is not between 0-500");
				return false;
			}
		}
	}

	//check 5 characters helper function
	public static boolean checkif5char(String str) {
		str = str.trim();
		if (str.length() == 5) {
			//System.out.println(str + " is valid, 5 characters");
			return true;
		} else {
			System.out.println(str + " is not 5 characters");
			return false;
		}
	}

	//check 32 characters helper function
	public static boolean checkif32char(String str) {
		str = str.trim();
		if (str.length() <= 32) {
			//System.out.println(str + " is valid, less than 33 characters");
			return true;
		} else {
			System.out.println(str + " is more than 32 characters");
			return false;
		}
	}

	//check 64 characters helper function
	public static boolean checkif64char(String str) {
		str = str.trim();
		if (str.length() <= 64) {
			//System.out.println(str + " is valid, less than 65 characters");
			return true;
		} else {
			System.out.println(str + " is more than 64 characters");
			return false;
		}
	}

	//check 128 characters helper function
	public static boolean checkif128char(String str) {
		str = str.trim();
		if (str.length() <= 128) {
			//System.out.println(str + " is valid, less than 129 characters");
			return true;
		} else {
			System.out.println(str + " is more than 128 characters");
			return false;
		}
	}

	//check 24 characters helper function
	public static boolean checkif24char(String str) {
		str = str.trim();
		if (str.length() <= 24) {
			//System.out.println(str + " is valid, less than 25 characters");
			return true;
		} else {
			System.out.println(str + " is more than 24 characters");
			return false;
		}
	}

	//check customer ID helper function
	public static boolean checkifID(String str, int strnum) {
		if (str.trim().equals("")) {
			System.out.println("empty");
		    return false;
		} else {
			str = str.trim();
			int num;
			try {
				num = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				System.out.println(str + " is not a number");
				return false;
			}
			if ((num >= 0) && (num <= strnum)) {
				//System.out.println(str + " is valid, ID within bounds");
				return true;
			} else {
				System.out.println(str + " is not within bounds");
				return false;
			}
		}
	}

	//check reservation status ('R','W',or 'C') helper function
	public static boolean checkifRWC(String str) {
		str = str.trim();
		if (str.length() == 1) {
			if ((str == "R") || (str == "W") || (str == "C")) {
				//System.out.println(str + " is valid, ('R','W',or 'C')");
				return true;
			}
		} 
		System.out.println(str + " is not ('R','W',or 'C')");
		return false;
	}

	//check date format validation helper function
	public static boolean checkifdate(String str) {
		if (str.trim().equals("")) {
			System.out.println("empty");
		    return false;
		} else {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    sdf.setLenient(false);
		    try {
		        Date date = sdf.parse(str); 
		        //System.out.println(str + " is a valid date format");
		    } catch (ParseException e) {
		        System.out.println(str + " is an invalid Date format");
		        return false;
		    }
		    return true;
		}
	}
	
	public DBproject(String dbname, String dbport, String user, String passwd) throws SQLException {
		System.out.print("Connecting to database...");
		try{
			// constructs the connection URL
			String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
			System.out.println ("Connection URL: " + url + "\n");
			
			// obtain a physical connection
	        this._connection = DriverManager.getConnection(url, user, passwd);
	        System.out.println("Done");
		}catch(Exception e){
			System.err.println("Error - Unable to Connect to Database: " + e.getMessage());
	        System.out.println("Make sure you started postgres on this machine");
	        System.exit(-1);
		}
	}
	
	/**
	 * Method to execute an update SQL statement.  Update SQL instructions
	 * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
	 * 
	 * @param sql the input SQL string
	 * @throws java.sql.SQLException when update failed
	 * */
	public void executeUpdate (String sql) throws SQLException { 
		// creates a statement object
		Statement stmt = this._connection.createStatement ();

		// issues the update instruction
		stmt.executeUpdate (sql);

		// close the instruction
	    stmt.close ();
	}//end executeUpdate

	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and outputs the results to
	 * standard out.
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQueryAndPrintResult (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		/*
		 *  obtains the metadata object for the returned result set.  The metadata
		 *  contains row and column info.
		 */
		ResultSetMetaData rsmd = rs.getMetaData ();
		int numCol = rsmd.getColumnCount ();
		int rowCount = 0;
		
		//iterates through the result set and output them to standard out.
		boolean outputHeader = true;
		while (rs.next()){
			if(outputHeader){
				for(int i = 1; i <= numCol; i++){
					System.out.print(rsmd.getColumnName(i) + "\t");
			    }
			    System.out.println();
			    outputHeader = false;
			}
			for (int i=1; i<=numCol; ++i)
				System.out.print (rs.getString (i) + "\t");
			System.out.println ();
			++rowCount;
		}//end while
		stmt.close ();
		return rowCount;
	}
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the results as
	 * a list of records. Each record in turn is a list of attribute values
	 * 
	 * @param query the input query string
	 * @return the query result as a list of records
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public List<List<String>> executeQueryAndReturnResult (String query) throws SQLException { 
		//creates a statement object 
		Statement stmt = this._connection.createStatement (); 
		
		//issues the query instruction 
		ResultSet rs = stmt.executeQuery (query); 
	 
		/*
		 * obtains the metadata object for the returned result set.  The metadata 
		 * contains row and column info. 
		*/ 
		ResultSetMetaData rsmd = rs.getMetaData (); 
		int numCol = rsmd.getColumnCount (); 
		int rowCount = 0; 
	 
		//iterates through the result set and saves the data returned by the query. 
		boolean outputHeader = false;
		List<List<String>> result  = new ArrayList<List<String>>(); 
		while (rs.next()){
			List<String> record = new ArrayList<String>(); 
			for (int i=1; i<=numCol; ++i) 
				record.add(rs.getString (i)); 
			result.add(record); 
		}//end while 
		stmt.close (); 
		return result; 
	}//end executeQueryAndReturnResult
	
	/**
	 * Method to execute an input query SQL instruction (i.e. SELECT).  This
	 * method issues the query to the DBMS and returns the number of results
	 * 
	 * @param query the input query string
	 * @return the number of rows returned
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	public int executeQuery (String query) throws SQLException {
		//creates a statement object
		Statement stmt = this._connection.createStatement ();

		//issues the query instruction
		ResultSet rs = stmt.executeQuery (query);

		int rowCount = 0;

		//iterates through the result set and count nuber of results.
		if(rs.next()){
			rowCount++;
		}//end while
		stmt.close ();
		return rowCount;
	}
	
	/**
	 * Method to fetch the last value from sequence. This
	 * method issues the query to the DBMS and returns the current 
	 * value of sequence used for autogenerated keys
	 * 
	 * @param sequence name of the DB sequence
	 * @return current value of a sequence
	 * @throws java.sql.SQLException when failed to execute the query
	 */
	
	public int getCurrSeqVal(String sequence) throws SQLException {
		Statement stmt = this._connection.createStatement ();
		
		ResultSet rs = stmt.executeQuery (String.format("Select currval('%s')", sequence));
		if (rs.next()) return rs.getInt(1);
		return -1;
	}

	/**
	 * Method to close the physical connection if it is open.
	 */
	public void cleanup(){
		try{
			if (this._connection != null){
				this._connection.close ();
			}//end if
		}catch (SQLException e){
	         // ignored.
		}//end try
	}//end cleanup

	/**
	 * The main execution method
	 * 
	 * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
	 */
	public static void main (String[] args) {
		if (args.length != 3) {
			System.err.println (
				"Usage: " + "java [-classpath <classpath>] " + DBproject.class.getName () +
		            " <dbname> <port> <user>");
			return;
		}//end if
		
		DBproject esql = null;
		
		try{
			System.out.println("(1)");
			
			try {
				Class.forName("org.postgresql.Driver");
			}catch(Exception e){

				System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
				e.printStackTrace();
				return;
			}
			
			System.out.println("(2)");
			String dbname = args[0];
			String dbport = args[1];
			String user = args[2];
			
			esql = new DBproject (dbname, dbport, user, "");
			
			boolean keepon = true;
			while(keepon){
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. Add Ship");
				System.out.println("2. Add Captain");
				System.out.println("3. Add Cruise");
				System.out.println("4. Book Cruise");
				System.out.println("5. List number of available seats for a given Cruise.");
				System.out.println("6. List total number of repairs per Ship in descending order");
				System.out.println("7. Find total number of passengers with a given status");
				System.out.println("8. < EXIT");
				
				switch (readChoice()){
					case 1: AddShip(esql); break;
					case 2: AddCaptain(esql); break;
					case 3: AddCruise(esql); break;
					case 4: BookCruise(esql); break;
					case 5: ListNumberOfAvailableSeats(esql); break;
					case 6: ListsTotalNumberOfRepairsPerShip(esql); break;
					case 7: FindPassengersCountWithStatus(esql); break;
					case 8: keepon = false; break;
				}
			}
		}catch(Exception e){
			System.err.println (e.getMessage ());
		}finally{
			try{
				if(esql != null) {
					System.out.print("Disconnecting from database...");
					esql.cleanup ();
					System.out.println("Done\n\nBye !");
				}//end if				
			}catch(Exception e){
				// ignored.
			}
		}
	}

	public static int readChoice() {
		int input;
		// returns only if a correct value is given.
		do {
			System.out.print("Please make your choice: ");
			try { // read the integer, parse it and break.
				input = Integer.parseInt(in.readLine());
				break;
			}catch (Exception e) {
				System.out.println("Your input is invalid!");
				continue;
			}//end try
		}while (true);
		return input;
	}//end readChoice

	public static void AddShip(DBproject esql) {//1
		do{
			try{
       	 		//Poll user, read input
                System.out.print("Please enter new Ship's make: ");
                String input_make = in.readLine();
                while (!checkif32char(input_make)) {
                	input_make = in.readLine();
                }

				System.out.print("Please enter new Ship's model: ");
                String input_model = in.readLine();
                while (!checkif64char(input_model)) {
                	input_model = in.readLine();
                }
	
				System.out.print("Please enter new Ship's age: ");
                String input_age = in.readLine();
                while (!checkifpositive(input_age)) {
                	input_age = in.readLine();
                }
				
				System.out.print("Please enter new Ship's # of free seats (between 0 and 500): ");
                String input_seats = in.readLine();
                while (!checkifrange(input_seats)) {
                	input_seats = in.readLine();
                }
				
				//Get new id for ship, my prefered implementation over sequences
				String query_for_sid = "SELECT MAX(S.id) FROM Ship S;";
				String result = esql.executeQueryAndReturnResult(query_for_sid).get(0).get(0);
				String sid = String.valueOf(Integer.parseInt(result)+1);
				


				//write SQL query into string
				//WE want to INSERT INTO Ship to add a new ship object with the above data into the table
				String query = "INSERT INTO Ship (id, make, model, age, seats) VALUES (";
				//add user inputted data, note that some are suppose to be strings, so need to be captureed in ''
				query += sid + ", ";
				query += "'" + input_make + "', ";
				query += "'" +input_model + "', ";
				query += input_age + ", ";
				query += input_seats + ");";
				
				//execute update using given executeUpdate function, it returns void
				esql.executeUpdate(query);
				//Notify User of Successful operation
         		System.out.println ("\nSuccessfully added Ship with id " + sid + " to the database.\n");
				
				break;
      		}catch(Exception e){
         		//Output generated error for debugging
				e.printStackTrace();
				continue;
      		}
      	}while (true);
	}

	public static void AddCaptain(DBproject esql) {//2
		do{
			try{
				//Poll user, get input
				System.out.print("Please enter new Captain's full name: ");
				String input_name = in.readLine();
				while (!checkif128char(input_name)) {
                	input_name = in.readLine();
                }
				System.out.print("Please enter new Captain's nationality: ");
				String input_nationality = in.readLine();
				while (!checkif24char(input_nationality)) {
                	input_nationality = in.readLine();
                }


				//Get new cid for reservation, my prefered implementation over sequences
				String query_for_cid = "SELECT MAX(C.id) FROM Captain C";
				String result = esql.executeQueryAndReturnResult(query_for_cid).get(0).get(0);
				String cid = String.valueOf(Integer.parseInt(result)+1);
				





				//INSERT INTO captains to add new captain object to table
				String query = "INSERT INTO Captain (id, fullname, nationality) VALUES (";
				//include user generated input
				query += cid + ", ";
				query += "'" + input_name + "', ";
				query += "'" +input_nationality + "'); ";

				//execute using provided executeUpdate function
				esql.executeUpdate(query);
				//notify user of successful operation
				System.out.println ("\nSuccessfully added Captain with id " + cid + " to the database.\n");

				break;
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}while (true);
	}

	public static void AddCruise(DBproject esql) {//3
        do{
            try{
				//poll user for info (theres alot this time)
		        System.out.print("Please enter new Cruise's ticket price: ");
		        String input_cost = in.readLine();
		        while (!checkifposdouble(input_cost)) {
                	input_cost = in.readLine();
                }
		        System.out.print("Please enter new Cruise's number of tickets sold already: ");
		        String input_numtics = in.readLine();
		        while (!checkifpositive(input_numtics)) {
                	input_numtics = in.readLine();
                }
				System.out.print("Please enter new Cruise's number of stops: ");
		        String input_numstops = in.readLine();
		        while (!checkifpositive(input_numstops)) {
                	input_numstops = in.readLine();
                }
				System.out.print("Please enter new Cruise's departure date (YYYY-MM-DD): ");
		        String input_depdate = in.readLine();
		        while (!checkifdate(input_depdate)) {
                	input_depdate = in.readLine();
                }
				System.out.print("Please enter new Cruise's arrival date(YYYY-MM-DD): ");
		        String input_arrdate = in.readLine();
		        while (!checkifdate(input_arrdate)) {
                	input_arrdate = in.readLine();
                }
				System.out.print("Please enter new Cruise's departure port code (5 characters): ");
		        String input_depport = in.readLine();
		        while (!checkif5char(input_depport)) {
                	input_depport = in.readLine();
                }
				System.out.print("Please enter new Cruise's arrival port code (5 characters): ");
		        String input_arrport = in.readLine();
		        while (!checkif5char(input_arrport)) {
                	input_arrport = in.readLine();
                }

				//Get new cnum for cruise, my prefered implementation over sequences
		        String query_for_cnum = "SELECT MAX(C.cnum) FROM Cruise C;";
		        String result = esql.executeQueryAndReturnResult(query_for_cnum).get(0).get(0);
		        String cnum = String.valueOf(Integer.parseInt(result)+1);
				

				//INSERT INTO Cruise table to create new cruise object
		        String query = "INSERT INTO Cruise VALUES (";
				//include user inputted data
				//int data
		        query += cnum + ", ";
				query += input_cost + ", ";
				query += input_numtics + ", ";
				query += input_numstops + ", ";
				//string data
				query += "'" + input_depdate + "', ";
				query += "'" + input_arrdate + "', ";
		        query += "'" + input_arrport + "', ";
		        query += "'" +input_depport + "'); ";
				//perform SQL query
		        esql.executeUpdate(query);
				//notify user of successful operation
		        System.out.println ("\nSuccessfully added Cruise with id " + cnum + " to the database.\n");

		        break;
	        }catch(Exception e){
                e.printStackTrace();
                continue;
	        }
    	}while (true);
	
	}


	public static void BookCruise(DBproject esql) {//4
		// Given a customer and a Cruise that he/she wants to book, add a reservation to the DB
		do{
            try{
				//get max ID value for testing if input is existing ID
				String query_for_strnum = "SELECT MAX(C.id) FROM Customer C;";
		                String result_strnum = esql.executeQueryAndReturnResult(query_for_strnum).get(0).get(0);
		                int strnum = Integer.parseInt(result_strnum);

				
				//poll user for input
  				System.out.print("Please enter you Customer ID: ");
                String custid = in.readLine();
                while (!checkifID(custid,strnum)) {
                	custid = in.readLine();
                }
                System.out.print("Please enter Cruise number you wish to book: ");
                String crunum = in.readLine();
                while (!checkifpositive(crunum)) {
                	crunum = in.readLine();
                }
				
				//Get new rnum for reservation, my prefered implementation over sequences
				String query_for_rnum = "SELECT MAX(R.rnum) FROM Reservation R;"; 
				String result = esql.executeQueryAndReturnResult(query_for_rnum).get(0).get(0);
				String rnum = String.valueOf(Integer.parseInt(result)+1);

				//find seats available and tickets already sold
				String query_for_status = "SELECT  S.seats, C.num_sold FROM Cruise C, Ship S, CruiseInfo CI WHERE C.cnum = "+ crunum +" AND CI.ship_id = S.id;";
				List<List<String>> result_status = esql.executeQueryAndReturnResult(query_for_status);
				
				//find status based on seats available and sold tickets
				String status = "C";
				if (result_status.size() == 0) { System.out.print("That cruise number is invalid.");}
				else if (Integer.parseInt(result_status.get(0).get(0))  > Integer.parseInt(result_status.get(0).get(1))) {
					status = "R";
				} else {
					status = "W";
				}
				
				//update num_sold, there is now 1 less ticket available
				String query_for_tickets = "UPDATE Cruise SET num_sold = num_sold+1 WHERE cnum = "+crunum+";";
				esql.executeUpdate(query_for_tickets);

				//notify user of successful reservation or waitlisting
				if (status == "R") {
					System.out.println("\nSuccessfully Reserved a ticket for Cruise "+crunum+" for Customer "+custid+".\n");
				} else {
					System.out.println("\nSuccessfully Waitlisted a ticket for Cruise "+crunum+" for Customer "+custid+".\n");
 				}
					


				//INSERT new entry to Reservation
				String query = "INSERT INTO  Reservation (rnum, ccid, cid, status) VALUES (";
                query += rnum + ", ";
				query += custid + ", ";
				query += crunum + ", ";
                query += "'" + status + "');";
 
				esql.executeUpdate(query);
				//notify user of successful operation
				System.out.println ("\nSuccessfully added Reservation("+status+") with id " + rnum + " to the database.\n");
	
				

            	break;
          	}catch(Exception e){
                e.printStackTrace();
            	continue;
    		}
        }while (true);

	}

	public static void ListNumberOfAvailableSeats(DBproject esql) {//5
		// For Cruise number and date, find the number of availalbe seats (i.e. total Ship capacity minus booked seats )
		do{
            try{
                //poll user for data
				System.out.print("Please enter Cruise number: ");
                String cnum = in.readLine();
                while (!checkifpositive(cnum)) {
                	cnum = in.readLine();
                }
                System.out.print("Please enter Cruise Departure date (YYYY-MM-DD): ");
                String ddate = in.readLine();
                while (!checkifdate(ddate)) {
                	ddate = in.readLine();
                }
				
				//create query using user input
				String query_for_seats = "SELECT C.num_sold, S.seats FROM Cruise C, Ship S, CruiseInfo CI WHERE CI.cruise_id = "+cnum+" AND CI.cruise_id = C.cnum AND C.actual_departure_date = '"+ddate+"' AND CI.ship_id = S.id;";
                                
				//execute query and return a list of lists for logic below
				List<List<String>> result_seats = esql.executeQueryAndReturnResult(query_for_seats);
				
				//perform logic from results, using Integer.parseInt to get Ints from Strings
				int availableSeats = 501; //error state, there are no cruises on that date
				if (result_seats.size() > 0) {
					availableSeats = Integer.parseInt(result_seats.get(0).get(1)) -  Integer.parseInt(result_seats.get(0).get(0));
				}
				//notify user of query results
				if (availableSeats == 501) {System.out.println("\nThere are no Cruises on that date.\n");}
				else if (availableSeats <= 0) {System.out.println("\nThere are no seats available for this cruise.\n");}
				else {System.out.println("\nThere are "+ String.valueOf(availableSeats) +" seats available for this cruise.\n");}

                break;
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }while (true);
	
	}

	public static void ListsTotalNumberOfRepairsPerShip(DBproject esql) {//6
		// Count number of repairs per Ships and list them in descending order
		try{
			//this query doesnt require user input, so just perform query and print the result
			String query = "SELECT S.id as Ship_ID, COUNT(R.rid) as Repairs_Made FROM Ship S, Repairs R WHERE S.id = R.ship_id GROUP BY S.id ORDER BY COUNT(R.rid) DESC;";
			//space out lines for readibility
			System.out.print("\n");
			esql.executeQueryAndPrintResult(query);
			System.out.print("\n");
		}catch(Exception e) {
			e.printStackTrace();}

	}

	
	public static void FindPassengersCountWithStatus(DBproject esql) {//7
		// Find how many passengers there are with a status (i.e. W,C,R) and list that number.
		do{
			try{
				//apparently, need to pass in cruise and passenger status
				//poll user for cruise number and reservation status
				System.out.print("Please enter Cruise number: ");
                String cnum = in.readLine();
                while (!checkifpositive(cnum)) {
                	cnum = in.readLine();
                }
                System.out.print("Please enter Reservation status ('R','W',or 'C'): ");
                String status = in.readLine();
                /*while (!checkifRWC(status)) {
                	status = in.readLine();
                }*/
				
				//perform query using user input
				String query = "SELECT R.status, COUNT(DISTINCT R.ccid) FROM Reservation R WHERE cid = "+cnum+" AND status = '"+status+"' GROUP BY R.status;";
				
				//add space between lines for readability
				System.out.print("\n");
				esql.executeQueryAndPrintResult(query);
				System.out.print("\n");
				break;
			}catch(Exception e) {
				e.printStackTrace();
				continue;
			}
		}while(true);
	}
}
