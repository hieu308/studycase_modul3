package com.example.demo1.demo.controller;

import com.example.demo1.demo.model.Product;
import com.example.demo1.demo.service.IProductService;
import com.example.demo1.demo.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/products")
public class ProductServlet extends HttpServlet {
    private static final String SELECT_PRODUCT = "SELECT * FROM Products";
    private static final String SELECT_MSI = "SELECT * FROM Products WHERE NAME LIKE 'MSI%'";
    private static final String SELECT_ASUS = "SELECT * FROM Products WHERE NAME LIKE 'ASUS%'";
    private static final String SELECT_HP = "SELECT * FROM Products WHERE NAME LIKE 'HP%'";
    private static final String SELECT_LENOVO = "SELECT * FROM Products WHERE NAME LIKE 'LENOVO%'";
    private static final String SELECT_ACER = "SELECT * FROM Products WHERE NAME LIKE 'ACER%'";
    private static final String SELECT_GYGABYTE = "SELECT * FROM Products WHERE NAME LIKE 'GYGABYTE%'";
    private static final String SELECT_BIG_SALE = "SELECT * FROM Products WHERE DESCRIPTION LIKE '%BS'";
    private static final String SELECT_BEST_SELLER = "SELECT * FROM Products WHERE DESCRIPTION LIKE '%BSL'";
    private static final IProductService PRODUCT_SERVICE = new ProductService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "get-msi":
                productList(SELECT_MSI, req, resp);
                break;
            case "get-asus":
                productList(SELECT_ASUS, req, resp);
                break;
            case "get-hp":
                productList(SELECT_HP, req, resp);
                break;
            case "get-lenovo":
                productList(SELECT_LENOVO, req, resp);
                break;
            case "get-acer":
                productList(SELECT_ACER, req, resp);
                break;
            case "get-gygabyte":
                productList(SELECT_GYGABYTE, req, resp);
                break;
            case "get-big-sale":
                productList(SELECT_BIG_SALE, req, resp);
                break;
            case "get-best-seller":
                productList(SELECT_BEST_SELLER, req, resp);
                break;
            default:
                productList(SELECT_ASUS, req, resp);
        }
    }

    private void productList(String sql, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Product> products = PRODUCT_SERVICE.findAll(sql);
        req.setAttribute("products", products);
        System.out.println("ok");
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
