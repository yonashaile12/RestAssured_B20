package Utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DB_Utility {

    static Connection conn; // make it static field so we can reuse in every methods we write
    static Statement stmnt;
    static ResultSet rs;

    public static void createConnection() {

        String connectionStr = ConfigurationReader.getProperty("database.url");
        String username = ConfigurationReader.getProperty("database.username");
        String password = ConfigurationReader.getProperty("database.password");

        try {
            conn = DriverManager.getConnection(connectionStr, username, password);
            System.out.println("CONNECTION SUCCESSFUL !! ");
        } catch (SQLException e) {
            System.out.println("CONNECTION HAS FAILED !!! " + e.getMessage());
        }

    }

    // MAKE ABOVE METHOD ACCEPT 3 PARAMETERS
    public static void createConnection(String connectionStr,String username,String password ) {

//        String connectionStr = ConfigurationReader.getProperty("database.url");
//        String username = ConfigurationReader.getProperty("database.username");
//        String password = ConfigurationReader.getProperty("database.password");

        try {
            conn = DriverManager.getConnection(connectionStr, username, password);
            System.out.println("CONNECTION SUCCESSFUL !! ");
        } catch (SQLException e) {
            System.out.println("CONNECTION HAS FAILED !!! " + e.getMessage());
        }

    }

    // Create a method called runQuery that accept a SQL Query
    // and return ResultSet Object
    public static ResultSet runQuery(String query) {

//        ResultSet rs  = null;
        // reusing the connection built from previous method
        try {
            stmnt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmnt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("Error while getting resultset " + e.getMessage());
        }

        return rs;

    }

    // create a method to clean up all the connection statemnet and resultset
    public static void destroy() {

        try {

            rs.close();
            stmnt.close();
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    /**
     * Count how many row we have
     *
     * @return the row count of the resultset we got
     */
    public static int getRowCount() {

        int rowCount = 0;

        try {
            rs.last();
            rowCount = rs.getRow();

            // move the cursor back to beforeFirst location to avoid accident
            rs.beforeFirst();

        } catch (SQLException e) {

            System.out.println("ERROR WHILE GETTING ROW COUNT " + e.getMessage());
        }

        return rowCount;

    }

    /**
     * Get the column count
     *
     * @return count of column the result set have
     */
    public static int getColumnCount() {

        int columnCount = 0;

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();

        } catch (SQLException e) {
            System.out.println("ERROR WHILE COUNTING THE COLUMNS " + e.getMessage());
        }

        return columnCount;
    }

    /**
     * a method that return all column names as List<String>
     */
    public static List<String> getColumnNames() {

        List<String> columnList = new ArrayList<>();

        try {
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int colNum = 1; colNum <= getColumnCount(); colNum++) {

                String columnName = rsmd.getColumnLabel(colNum);
                columnList.add(columnName);
            }

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING ALL COLUMN NAMES " + e.getMessage());
        }
        return columnList;

    }

    /**
     * Create a method that return all row data as a List<String>
     *
     * @param rowNum Row number you want to get the data
     * @return the row data as List object
     */
    public static List<String> getRowDataAsList(int rowNum) {

        List<String> rowDataList = new ArrayList<>();

        // first we need to move the pointer to the location the rowNum specified
        try {
            rs.absolute(rowNum);

            for (int colNum = 1; colNum <= getColumnCount(); colNum++) {

                String cellValue = rs.getString(colNum);
                rowDataList.add(cellValue);

            }
            rs.beforeFirst();

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING ROW DATA AS LIST " + e.getMessage());
        }
        return rowDataList;

    }


    /**
     * Create a method to return the cell value at certain row certain column
     *
     * @param rowNum row number
     * @param colNum column number
     * @return Cell value as String
    =     */
    public static String getColumnDataAtRow(int rowNum, int colNum) {

        String result = "";

        try {
            rs.absolute(rowNum);
            result = rs.getString(colNum);
            rs.beforeFirst(); // moving back to before first location

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING CELL VALUE AT ROWNUM COLNUM " + e.getMessage());
        }

        return result;
    }

    /**
     * Create a method to return the cell value at certain row certain column
     *
     * @param rowNum row number
     * @param colName column name
     * @return Cell value as String at specified row numeber and column number
     */
    public static String getColumnDataAtRow(int rowNum, String colName) {

        String result = "";

        try {
            rs.absolute(rowNum);
            result = rs.getString(colName);
            rs.beforeFirst(); // moving back to before first location

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING CELL VALUE AT ROWNUM column name " + e.getMessage());
        }

        return result;
    }

    /**
     * return value of all cells in that column
     *
     * @param colNum the column number you want to get the list out of
     * @return value of all cells in that column as a List<String>
     */
    public static List<String> getColumnDataAsList(int colNum) {

        List<String> cellValuesList = new ArrayList<>();

        try {

            while (rs.next()) {

                String cellValue = rs.getString(colNum);
                cellValuesList.add( cellValue ) ;

            }
            rs.beforeFirst();

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING ONE COLUMN DATA AS LIST " + e.getMessage() );
        }
        return cellValuesList ;

    }
    /**
     * return value of all cells in that column using column name
     *
     * @param colName the column name you want to get the list out of
     * @return value of all cells in that column as a List<String>
     */
    public static List<String> getColumnDataAsList(String colName) {

        List<String> cellValuesList = new ArrayList<>();

        try {

            while (rs.next()) {

                String cellValue = rs.getString(colName);
                cellValuesList.add( cellValue ) ;

            }
            rs.beforeFirst(); //Move it back to before first location

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING ONE COLUMN DATA AS LIST " + e.getMessage() );
        }
        return cellValuesList ;

    }

    /**
     * A method that display all the result set data on console
     */
    public static void displayAllData(){

        try {
            rs.beforeFirst();

            while (rs.next()) {

                for (int colNum = 1; colNum <= getColumnCount(); colNum++) {
//                    System.out.print(rs.getString(colNum) + "\t");
                    //  for making it pretty
                    System.out.printf("%-35s", rs.getString(colNum));
                }
                System.out.println();
            }
            rs.beforeFirst();

        } catch (SQLException e) {
            System.out.println("ERROR WHILE PRINTING WHOLE TABLE " + e.getMessage());
        }
    }

    /**
     * A method that return the row data along with column name as Map object
     * @param rowNum row numebr you want to get the data
     * @return Map object -- column name as key and cell value as value
     */
    public static Map<String,String> getRowMap(int rowNum){

        Map<String,String>  rowMap = new LinkedHashMap<>() ;

        try{

            rs.absolute(rowNum) ;
            ResultSetMetaData rsmd = rs.getMetaData() ;

            for (int colNum = 1; colNum <= rsmd.getColumnCount() ; colNum++) {

                String columnName   =  rsmd.getColumnLabel( colNum ) ;
                String cellValue    =  rs.getString( colNum ) ;
                rowMap.put(columnName, cellValue) ;

            }
            rs.beforeFirst();

        } catch (SQLException e) {
            System.out.println("ERROR WHILE getting RowMap " + e.getMessage());
        }
        return rowMap ;

    }

    public static List<Map<String,String> > getAllDataAsListOfMap(){

        List<Map<String,String> > rowMapList = new ArrayList<>();

        for (int rowNum = 1; rowNum <= getRowCount() ; rowNum++) {

            rowMapList.add(   getRowMap(rowNum)    ) ;

        }
        return  rowMapList ;
    }
}
