/**
 * Created by Varq on 2016-05-27 - connects and downloads the data from a MySQL database .
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataReaderDB {

    public static List<List<String>> readFromDB(String table) {

        //DB details:
        String url = "jdbc:mysql://mysql.agh.edu.pl:3306/";
        String dbName = "jtveiro2";
        String dbPassword = "Z9t7rtQD";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "jtveiro2";

        List<List<String>> dataset = new ArrayList();

        try {
            //Connect with DB and get data from a table:
            Class.forName(driver).newInstance();
            Connection dbCon = DriverManager.getConnection(url+dbName,userName,dbPassword);

            Statement st = dbCon.createStatement();
            String dbSQLQuery = "SELECT * from " + table;
            ResultSet res = st.executeQuery(dbSQLQuery);

            //Copy data into dataset:
            String columnName = new String();
            int columnCount = res.getMetaData().getColumnCount();
            List<String> columnRow = new ArrayList<>();

            //get column names:
            for(int i = 1; i<=columnCount; i++) {
                columnRow.add(res.getMetaData().getColumnName(i));
            }
            dataset.add(columnRow);

            //get the data:
            while(res.next()) {
                List<String> dataRow = new ArrayList<>();
                for(int i = 1; i<=columnCount; i++) {
                    columnName = columnRow.get(i-1);
                    if(res.getString(columnName) != null) {
                        dataRow.add(res.getString(columnName));
                    }
                    else {
                        dataRow.add("noData");
                    }

                }
                dataset.add(dataRow);
            }


            dbCon.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    return dataset;
    }
}


