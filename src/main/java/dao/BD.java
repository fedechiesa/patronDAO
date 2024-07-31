package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BD {

    private static final String SQL_TABLE_CREATE_DENTIST = "DROP TABLE IF EXISTS DENTIST; " +
            "CREATE TABLE DENTIST"
            + "("
            + " ID INT AUTO_INCREMENT PRIMARY KEY,"
            + " REGISTRATION INT NOT NULL,"
            + " NAME VARCHAR(100) NOT NULL,"
            + " LASTNAME VARCHAR(100) NOT NULL"
            + ")";

    private static final String SQL_TABLE_CREATE_PATIENT = "DROP TABLE IF EXISTS PATIENT; " +
            "CREATE TABLE PATIENT "
            + "("
            + " ID INT AUTO_INCREMENT PRIMARY KEY,"
            + " NAME VARCHAR(100) NOT NULL,"
            + " LASTNAME VARCHAR(100) NOT NULL,"
            + " CARDIDENTITY VARCHAR(100) NOT NULL,"
            + " ADMISSIONOFDATE DATE NOT NULL"
            + ")";


    public static Connection getConnection() throws Exception {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/ClinicaOdontologicaDAO");
    }

    public static void createTables(){
        Connection connection = null;

        try{
            connection = getConnection();

            Statement statement = connection.createStatement();
            statement.execute(SQL_TABLE_CREATE_DENTIST);
            statement.execute(SQL_TABLE_CREATE_PATIENT);


        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                connection.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
