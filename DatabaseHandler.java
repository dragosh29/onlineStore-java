import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    private Connection connection;

    public DatabaseHandler(String url) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database");
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertProduct(Product product) {
        try {
            String insertQuery = "INSERT INTO products (type, name, size, color, quantity, price) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, product.getType());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getSize());
                preparedStatement.setString(4, product.getColor());
                preparedStatement.setInt(5, product.getQuantity());
                preparedStatement.setInt(6, product.getPrice());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertOwner(Owner owner) {
        try {
            String insertQuery = "INSERT INTO owner (name, email, phoneNumber) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, owner.getName());
                preparedStatement.setString(2, owner.getEmail());
                preparedStatement.setString(3, owner.getPhoneNumber());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductQuantity(Product product) {
        try {
            String updateQuery = "UPDATE products SET quantity = ? WHERE name = ? AND size = ? AND color = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, product.getQuantity());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getSize());
                preparedStatement.setString(4, product.getColor());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductPrice(Product product) {
        try {
            String updateQuery = "UPDATE products SET price = ? WHERE name = ? AND size = ? AND color = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, product.getPrice());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getSize());
                preparedStatement.setString(4, product.getColor());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOwner(Owner owner) {
        try {
            String updateQuery = "UPDATE owner SET name = ?, email = ?, phoneNumber = ? WHERE id = 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, owner.getName());
                preparedStatement.setString(2, owner.getEmail());
                preparedStatement.setString(3, owner.getPhoneNumber());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(Product product) {
        try {
            String deleteQuery = "DELETE FROM products WHERE name = ? AND size = ? AND color = ? AND PRICE = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getSize());
                preparedStatement.setString(3, product.getColor());
                preparedStatement.setInt(4, product.getPrice());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOwner(Owner owner) {
        try {
            String deleteQuery = "DELETE FROM owner WHERE name = ? AND email = ? AND phoneNumber = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, owner.getName());
                preparedStatement.setString(2, owner.getEmail());
                preparedStatement.setString(3, owner.getPhoneNumber());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllProducts() {
        try {
            String deleteQuery = "DELETE FROM products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public void printProduct(Product product) {
        try {
            String selectQuery = "SELECT * FROM products WHERE name = ? AND size = ? AND color = ? AND price = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getSize());
                preparedStatement.setString(3, product.getColor());
                preparedStatement.setInt(4, product.getPrice());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("Product ID: " + resultSet.getInt("id"));
                    System.out.println("Product Type: " + resultSet.getString("type"));
                    System.out.println("Product Name: " + resultSet.getString("name"));
                    System.out.println("Product Size: " + resultSet.getString("size"));
                    System.out.println("Product Color: " + resultSet.getString("color"));
                    System.out.println("Product Quantity: " + resultSet.getInt("quantity"));
                    System.out.println("Product Price: " + resultSet.getInt("price"));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAllProducts() {
        try {
            String selectQuery = "SELECT * FROM products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("Product ID: " + resultSet.getInt("id"));
                    System.out.println("Product Type: " + resultSet.getString("type"));
                    System.out.println("Product Name: " + resultSet.getString("name"));
                    System.out.println("Product Size: " + resultSet.getString("size"));
                    System.out.println("Product Color: " + resultSet.getString("color"));
                    System.out.println("Product Quantity: " + resultSet.getInt("quantity"));
                    System.out.println("Product Price: " + resultSet.getInt("price"));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAllOwners() {
        try {
            String selectQuery = "SELECT * FROM owner";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("Owner ID: " + resultSet.getInt("id"));
                    System.out.println("Owner Name: " + resultSet.getString("name"));
                    System.out.println("Owner Email: " + resultSet.getString("email"));
                    System.out.println("Owner Phone Number: " + resultSet.getString("phoneNumber"));
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> retrieveProductListFromDB(){
        ArrayList<Product> productList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String type = resultSet.getString("type");
                    String name = resultSet.getString("name");
                    String size = resultSet.getString("size");
                    String color = resultSet.getString("color");
                    int quantity = resultSet.getInt("quantity");
                    int price = resultSet.getInt("price");
                    if(type.equals("topWear")) productList.add(new TopWear(name, size, color, quantity, price));
                    else productList.add(new BottomWear(name, size, color, quantity, price));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public Owner retrieveOwnerFromDB() {
        try {
            String selectQuery = "SELECT * FROM owner";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    return new Owner(name, email, phoneNumber);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OnlineStore retrieveOnlineStoreFromDB(){
        ArrayList<Product> productList = retrieveProductListFromDB();
        Owner owner = retrieveOwnerFromDB();
        return new OnlineStore(owner, productList);
    }
}
