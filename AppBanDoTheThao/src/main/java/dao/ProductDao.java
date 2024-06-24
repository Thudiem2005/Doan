package dao;

import database.SQLServerConnection;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDao {
    
    private static final Logger logger = Logger.getLogger(ProductDao.class.getName());

    // Thêm một sản phẩm mới vào CSDL
    public boolean addProduct(Product product) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                // Câu truy vấn SQL để chèn dữ liệu sản phẩm mới vào CSDL
                String query = "INSERT INTO Product (ProductName, CategoryID, Description, Size, Color, Price, Quantity, Image) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                statement = connection.prepareStatement(query);
                statement.setString(1, product.getProductName());
                statement.setInt(2, product.getCategoryID());
                statement.setString(3, product.getDescription());
                statement.setString(4, product.getSize());
                statement.setString(5, product.getColor());
                statement.setDouble(6, product.getPrice());
                statement.setInt(7, product.getQuantity());
                statement.setString(8, product.getImage());

                // Thực hiện câu truy vấn
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Thêm sản phẩm thành công!");
                    success = true;
                } else {
                    System.out.println("Thêm sản phẩm thất bại!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tuyên bố
            SQLServerConnection.closeConnection();
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    // Sửa thông tin của một sản phẩm trong CSDL
    public boolean updateProduct(Product product) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                System.out.println("Kết nối cơ sở dữ liệu thành công.");
                // Câu truy vấn SQL để cập nhật thông tin của sản phẩm trong CSDL
                String query = "UPDATE Product SET ProductName = ?, CategoryID = ?, Description = ?, " +
                               "Size = ?, Color = ?, Price = ?, Quantity = ?, Image = ? WHERE ProductID = ?";

                statement = connection.prepareStatement(query);
                statement.setString(1, product.getProductName());
                statement.setInt(2, product.getCategoryID());
                statement.setString(3, product.getDescription());
                statement.setString(4, product.getSize());
                statement.setString(5, product.getColor());
                statement.setDouble(6, product.getPrice());
                statement.setInt(7, product.getQuantity());
                statement.setString(8, product.getImage());
                statement.setInt(9, product.getProductID());

                // Thực hiện câu truy vấn
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    logger.info("Cập nhật sản phẩm thành công!");
                    success = true;
                } else {
                    logger.warning("Cập nhật sản phẩm thất bại! Không có dòng nào được cập nhật.");
                }
            } else {
                logger.warning("Kết nối đến cơ sở dữ liệu thất bại.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Lỗi khi cập nhật sản phẩm: " + e.getMessage(), e);
            System.out.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tuyên bố
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Lỗi khi đóng tài nguyên: " + e.getMessage(), e);
                System.out.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return success;
    }

    // Xóa một sản phẩm khỏi CSDL dựa trên ID
    public boolean deleteProduct(int productId) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                // Câu truy vấn SQL để xóa sản phẩm khỏi CSDL
                String query = "DELETE FROM Product WHERE ProductID = ?";

                statement = connection.prepareStatement(query);
                statement.setInt(1, productId);

                // Thực hiện câu truy vấn
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Xóa sản phẩm thành công!");
                    success = true;
                } else {
                    System.out.println("Xóa sản phẩm thất bại!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tuyên bố
            SQLServerConnection.closeConnection();
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    // Lấy danh sách các sản phẩm từ CSDL
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                // Câu truy vấn SQL để lấy danh sách các sản phẩm từ CSDL
                String query = "SELECT * FROM Product";

                statement = connection.prepareStatement(query);
                resultSet = statement.executeQuery();

                // Duyệt qua các dòng kết quả và tạo danh sách sản phẩm
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setProductID(resultSet.getInt("ProductID"));
                    product.setProductName(resultSet.getString("ProductName"));
                    product.setCategoryID(resultSet.getInt("CategoryID"));
                    product.setDescription(resultSet.getString("Description"));
                    product.setSize(resultSet.getString("Size"));
                    product.setColor(resultSet.getString("Color"));
                    product.setPrice(resultSet.getFloat("Price"));
                    product.setQuantity(resultSet.getInt("Quantity"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tuyên bố
            SQLServerConnection.closeConnection();
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }
    
    public Product getProductById(int productId) {
        Product product = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                String query = "SELECT * FROM Product WHERE ProductID = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, productId);
                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    product = new Product();
                    product.setProductID(resultSet.getInt("ProductID"));
                    product.setProductName(resultSet.getString("ProductName"));
                    product.setCategoryID(resultSet.getInt("CategoryID"));
                    product.setDescription(resultSet.getString("Description"));
                    product.setSize(resultSet.getString("Size"));
                    product.setColor(resultSet.getString("Color"));
                    product.setPrice(resultSet.getFloat("Price"));
                    product.setQuantity(resultSet.getInt("Quantity"));
                    product.setImage(resultSet.getString("Image"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLServerConnection.closeConnection();
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return product;
    }
    
    public List<Product> getProductsByCategoryId(int categoryId) {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                // Câu truy vấn SQL để lấy danh sách các sản phẩm theo danh mục ID từ CSDL
                String query = "SELECT * FROM Product WHERE CategoryID = ?";

                statement = connection.prepareStatement(query);
                statement.setInt(1, categoryId);
                resultSet = statement.executeQuery();

                // Duyệt qua các dòng kết quả và tạo danh sách sản phẩm
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setProductID(resultSet.getInt("ProductID"));
                    product.setProductName(resultSet.getString("ProductName"));
                    product.setCategoryID(resultSet.getInt("CategoryID"));
                    product.setDescription(resultSet.getString("Description"));
                    product.setSize(resultSet.getString("Size"));
                    product.setColor(resultSet.getString("Color"));
                    product.setPrice(resultSet.getFloat("Price"));
                    product.setQuantity(resultSet.getInt("Quantity"));
                    product.setImage(resultSet.getString("Image"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tuyên bố
            SQLServerConnection.closeConnection();
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }
}
