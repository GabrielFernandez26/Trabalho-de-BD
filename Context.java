package src.com.example.projetoaeroporto.DAO;


import java.sql.DriverManager;

public class Context {

    private static final String URLDB = "jdbc:mariadb://localhost:3306/Aeroporto?allowMultiQueries=true";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "poo123";

    private java.sql.Connection coon;

    public static java.sql.Connection getConnection() {

        try {

            java.sql.Connection conn = DriverManager.getConnection(URLDB, USUARIO, PASSWORD);

            return conn;

        } catch(Exception e) {
            System.err.println(e.getMessage() + "/n/n" + e.getStackTrace());
            return null;
        }
    }

    public static void closeConnection(java.sql.Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch(Exception e) {
                System.err.println(e.getMessage() + "/n/n" + e.getStackTrace());
            }
        }
    }

}