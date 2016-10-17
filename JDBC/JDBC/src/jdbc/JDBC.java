/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 *  further tweaked by Daniel Jacobo, and Ruben Marin
 */
public class JDBC{
    //  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are
    //strings, but that won't always be the case.
    static final String displayWritingGroupFormat="%-30s%-30s%-15s%-20s\n";
    static final String displayPublishersFormat="%-20s%-30s%-10s%-40s\n";
    static final String displayBookFormat= "%-5s%-15s%-15s%-15s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
//            + "testdb;user=";
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }

    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        System.out.print("Database user name: ");
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            
            //STEP 3.5: Menu options
            boolean run = true;
            int menuChoice = 0;
            stmt = conn.createStatement();
            String sql;
            int num;
            ResultSet rs = null;
            
            while(run) {
                DisplayMenu();
                
                try {
                    menuChoice = in.nextInt();
                    if(menuChoice < 1 || menuChoice > 10) {
                        System.out.println("Invalid Input, Please choose one of the menu options");
                        in.nextLine();
                        continue;
                    }
                    if(menuChoice == 10) {
                        System.out.println("Goodbye!");
                        run = false;
                        continue;
                    }

                    //STEP 4: Generate query
                    switch(menuChoice) {
                        case 1 :    //view all Writing Groups
                        {
                            sql = "SELECT * FROM writinggroup";
                            rs = stmt.executeQuery(sql);
                            DisplayGroup(rs);
                            break;
                        }
                        case 2 :    //View specific Writing Group
                        {
                            PreparedStatement listWritingGroup = conn.prepareStatement("SELECT * FROM WritingGroup WHERE groupname = ?");
                            System.out.println("What Writing Group would you like to view?");
                            sql = in.nextLine();
                            listWritingGroup.setString(1, sql);
                            rs = listWritingGroup.executeQuery();
                            DisplayGroup(rs);
                            break;
                        }
                        case 3 :    //View all Publishers
                        {
                            sql = "SELECT * FROM publishers";
                            rs = stmt.executeQuery(sql);
                            DisplayPub(rs);
                            break;
                        }
                        case 4 :    //View specific Publisher
                        {
                            PreparedStatement listPublisher = conn.prepareStatement("SELECT * FROM publishers WHERE pubname = ?");
                            System.out.println("What Publisher would you like to view?");
                            sql = in.nextLine();
                            listPublisher.setString(1, sql);
                            rs = listPublisher.executeQuery();
                            DisplayPub(rs);
                            break;
                        }
                        case 5 :    //View all Books
                        {
                            sql = "SELECT * FROM book";
                            rs = stmt.executeQuery(sql);
                            DisplayBook(rs);
                            break;
                        }
                        case 6 :    //View specific Book
                        {
                            PreparedStatement listBook = conn.prepareStatement("SELECT * FROM book WHERE bookname = ?");
                            System.out.println("What Book would you like to view?");
                            sql = in.nextLine();
                            listBook.setString(1, sql);
                            rs = listBook.executeQuery();
                            DisplayBook(rs);
                            break;
                        }
                        case 7 :    //Insert Book
                        {
                            PreparedStatement insertBook = conn.prepareStatement("INSERT INTO book VALUE(?, ?, ?, ?, ?)");
                            System.out.println("Please enter the Book Title");
                            sql = in.nextLine();
                            insertBook.setString(1, sql);
                            System.out.println("Please enter the Year Published");
                            sql = in.nextLine();
                            insertBook.setString(2, sql);
                            System.out.println("Please enter the Number of Pages");
                            sql = in.nextLine();
                            insertBook.setString(3, sql);
                            System.out.println("Please enter the Writing Group Name");
                            sql = in.nextLine();
                            insertBook.setString(4, sql);
                            System.out.println("Please enter the Publisher's Name");
                            sql = in.nextLine();
                            insertBook.setString(5, sql);
                            insertBook.execute();
                            DisplayBook(rs);
                            break;
                        }
                        case 8 :    //Insert new Publisher + replace old publishers books
                        {
                            //PreparedStatement insertPub = conn.prepareStatement("INSERT INTO publishers VALUE ")
                            break;
                        }
                        case 9 :    //Remove specific Book
                        {
                            break;
                        }
                    }

                    //STEP 5: Extract data from result set
                    System.out.printf(displayWritingGroupFormat, "Group Name", "Head Writer", "Year Formed", "Subject");
                    while (rs.next()) {
                        //Retrieve by column name
                        String Publisher = rs.getString("GroupName");
                        String Address = rs.getString("HeadWriter");
                        String Phone = rs.getString("YearFormed");
                        String Email = rs.getString("Subject");

                        //Display values
                        System.out.printf(displayWritingGroupFormat,
                                dispNull(Publisher), dispNull(Address), dispNull(Phone), dispNull(Email));
                    }
                
                }
                catch(InputMismatchException e) {
                    System.out.println("Invalid Input, Please enter an Integer");
                    in.nextLine();
                    continue;
                }
            }
            //step 6: Clean up environment
            stmt.close();
            conn.close();
            rs.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
    
    public static void DisplayMenu() {
        System.out.println("Please select one of the options below\n" +
                           "1)  List all writing groups\n" +
                           "2)  List all the data for a group specified by the user\n" +
                           "3)  List all publishers\n" +
                           "4)  List all the data for a pubisher specified by the user\n" +
                           "5)  List all book titles\n" +
                           "6)  List all the data for a book specified by the user\n" +
                           "7)  Insert a new book\n" +
                           "8)  Insert a new publisher and update all book published by one publisher to be published by the new pubisher\n" +
                           "9)  Remove a book specified by the user" +
                           "10) Quit");
    }
}//end FirstExample}

/*
1)List all writing groups
2)List all the data for a group specified by the user
3)List all publishers
4)List all the data for a pubisher specified by the user
5)List all book titles
6)List all the data for a book specified by the user
7)Insert a new book
8)Insert a new publisher and update all book published by one publisher to be published by the new pubisher
9)Remove a book specified by the user
*/
