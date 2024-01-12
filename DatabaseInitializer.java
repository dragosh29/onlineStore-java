import java.sql.*;

public class DatabaseInitializer {

    // SQLite database connection URL
    private static final String URL = "jdbc:sqlite:E:/IdeaProjects/onlineStore-java/sqlite";

    public static String getURL(){
        return URL;
    }
    public static void initializeDatabase() {
        try {

            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();

            String createProductsTable = "CREATE TABLE IF NOT EXISTS products (" +
                    "type TEXT NOT NULL," + // topWear or bottomWear
                    "name TEXT NOT NULL," +
                    "size TEXT NOT NULL," +
                    "color TEXT NOT NULL," +
                    "quantity INTEGER NOT NULL," +
                    "price INTEGER NOT NULL," +
                    "PRIMARY KEY (name, size, color))";
            statement.execute(createProductsTable);

            String createOwnerTable = "CREATE TABLE IF NOT EXISTS owner (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "phoneNumber TEXT NOT NULL)";
            statement.execute(createOwnerTable);


            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        initializeDatabase();
    }
}
