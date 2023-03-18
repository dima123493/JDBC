import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCOperations {
    static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            final String userName = "root";
            final String password = "root";
            final String databaseName = "test";
            final String connectionURL = "jdbc:mysql://localhost:3306/" + databaseName;
            return connection = DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createDB(Statement statement, String databaseName) throws SQLException {
        String sql = "CREATE DATABASE " + databaseName;
        statement.executeUpdate(sql);
        System.out.println("Database \"" + databaseName + "\" created successfully");
    }

    public static void switchToDatabase(Statement statement, String databaseName) throws SQLException {
        String query = "USE " + databaseName;
        statement.execute(query);
        System.out.println("Database was changed to \"" + databaseName + "\"");
    }

    public static void createTable(Statement statement, String tableName) throws SQLException {
        String sql = "CREATE TABLE " + tableName + "(\n" +
                "    id int NOT NULL AUTO_INCREMENT,\n" +
                "    firstName varchar(255) NOT NULL,\n" +
                "    lastName varchar(255) NOT NULL,\n" +
                "    city varchar(255) NOT NULL,\n" +
                "    perDiem double(6, 2),\n" +
                "    dateFirst DATE,\n" +
                "    dateLast DATE,\n" +
                "    PRIMARY KEY (id)\n" +
                ");";
        statement.executeUpdate(sql);
        System.out.println("The table \"" + tableName + "\" was created");
    }

    public static void createTrip(String firstName, String lastName, String city, double perDiem, LocalDate dateFirst, LocalDate dateLast) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO trip (firstName, lastName, city, perDiem, dateFirst, dateLast)" +
                    " VALUES(?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, city);
            ps.setDouble(4, perDiem);
            ps.setDate(5, Date.valueOf(dateFirst));
            ps.setDate(6, Date.valueOf(dateLast));
            ps.executeUpdate();
            System.out.println("One record was inserted into table");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List records() {
        List<TripRecords> list = new ArrayList<>();
        list.add(new TripRecords("Denial", "Dol", "US", 100.00, "2023-03-14", "2023-03-18"));
        list.add(new TripRecords("Rocky", "Mol", "US", 200.00, "2023-03-15", "2023-03-19"));
        list.add(new TripRecords("Steve", "Col", "US", 300.00, "2023-03-16", "2023-03-20"));
        list.add(new TripRecords("Ramesh", "Zol", "India", 400.00, "2023-03-17", "2023-03-21"));
        return list;
    }

    public static void createMultipleTrips() {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO trip (firstName, lastName, city, perDiem, dateFirst, dateLast)" +
                    " VALUES(?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);

            for (Object o : records()) {
                TripRecords records = (TripRecords) o;
                ps.setString(1, records.getFirstName());
                ps.setString(2, records.getLastName());
                ps.setString(3, records.getCity());
                ps.setDouble(4, records.getPerDiem());
                ps.setDate(5, Date.valueOf(records.getDateFirst()));
                ps.setDate(6, Date.valueOf(records.getDateLast()));
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("Multiple records were inserted into table");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateRecord(Statement statement) throws SQLException {
        String sql = "UPDATE trip " +
                "SET city='Kyiv'" +
                "WHERE firstName='Dmytro';";
        statement.executeUpdate(sql);
        System.out.println("Record was updated in the table");
    }

    public static void deleteOneRecord(Statement statement) throws SQLException {
        String sql = "DELETE FROM trip WHERE firstName=\"Dmytro\" AND perDiem=100.00;";
        statement.executeUpdate(sql);
        System.out.println("Record was deleted from the table");
    }

    public static void deleteTable(Statement statement, String tableName) throws SQLException {
        String sql = "DROP TABLE " + tableName + ";";
        statement.executeUpdate(sql);
        System.out.println("The table \"" + tableName + "\" was deleted form database");
    }

    public static void deleteDatabase(Statement statement, String databaseName) throws SQLException {
        String sql = "DROP DATABASE " + databaseName + ";";
        statement.executeUpdate(sql);
        System.out.println("The database \"" + databaseName + "\" was deleted form MySQL");
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            System.out.println("Connection to MySQL database was established");

            createDB(statement, "vocation");
            switchToDatabase(statement, "vocation");
            createTable(statement, "trip");
            createTrip("Dmytro", "Zubenko", "Lviv", 100.00, LocalDate.of(2023, 3, 17), LocalDate.of(2023, 3, 19));
            updateRecord(statement);
            createMultipleTrips();
            deleteOneRecord(statement);
            deleteTable(statement, "trip");
            deleteDatabase(statement, "vocation");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}