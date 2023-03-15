import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCOperations {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String userName = "root";
        String password = "root";
        String connectionURL = "jdbc:mysql://localhost:3306/test";
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)) {
            System.out.println("Connection was established");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}