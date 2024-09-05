package com.example.demo1.demo.repository.user;

import com.example.demo1.demo.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductRepository implements IProductRepository {
    private static final String INSERT_PRODUCT = "INSERT INTO Products(product_id,name,description,brand,stock,image) VALUES(?,?,?,?,?,?) ";
    private static final String SELECT_PRODUCT = "SELECT * FROM Products";
    BaseRepository baseRepository = new BaseRepository();

    @Override
    public List<Product> findAll(String sql) {
        List<Product> products = new ArrayList<>();
        try (Connection conn = baseRepository.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                String brand = rs.getString("brand");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                String image = rs.getString("image");
                products.add(new Product(name, description, brand, price, stock, image));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
