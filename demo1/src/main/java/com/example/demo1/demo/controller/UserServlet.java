package com.example.demo1.demo.controller;


import com.example.demo1.demo.model.User;
import com.example.demo1.demo.service.IUserService;
import com.example.demo1.demo.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {
    private IUserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertUser(request, response);
                    break;
                case "login":
                    loginUser(request, response);
                    break;
                case "update":
                    updateUser(request, response);
                    break;
                    case "changePass":

                default:
                    response.sendRedirect("/home");
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "logOut":
                logout(request, response);
                break;
            case "into-user":

                showUserInfo(request, response);
                break;
            case "change-password":
                showPassForm(request, response);
                break;
            case "into-card":
                showCartForm(request, response);
                break;

            default:
                response.sendRedirect("/home.jsp");
                break;
        }
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String role = "customer";
        System.out.println(confirmPassword);
        System.out.println(password);
        System.out.println(password.equals(confirmPassword));
        if (!password.equals(confirmPassword)) {
            request.setAttribute("passError", "Mật khẩu không khớp!");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        }
        List<User> usersList = userService.getAllUsers();
        for (int i = 0; i < usersList.size(); i++) {

            if (usersList.get(i).getAccount().equals(account)) {
                request.setAttribute("accountError", "Tên tài khoản đã tồn tại!");

                request.getRequestDispatcher("/home.jsp").forward(request, response);
                return;
            }
            if (usersList.get(i).getEmail().equals(email)) {
                request.setAttribute("emailError", "Email đã tồn tại!");
                request.getRequestDispatcher("/home.jsp").forward(request, response);
                return;
            }
        }
        User user1 = new User(account, password, email, name, phone, address, role);
        userService.insertUser(user1);
        response.sendRedirect("/login_successfully.jsp");

    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        User user = userService.authenticateUser(account, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            String role = user.getRole();
            if ("admin".equals(role)) {
                response.sendRedirect("/admin_home.jsp");
            } else if ("customer".equals(role)) {
                response.sendRedirect("/home.jsp");
            } else {
                response.sendRedirect("/user.jsp");
            }
        } else {
            request.setAttribute("loginError", "Tên tài khoản hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

        String account = request.getParameter("account");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        User user = new User(account, email, name, phone, address);
        boolean isSuccessful = userService.updateUser(user);
        if (isSuccessful) {
            request.setAttribute("successMessage", "Cập nhật thông tin thành công");
        } else {
            request.setAttribute("errorMessage", "Cập nhật thông tin không thanh cong.");

        }
        request.getRequestDispatcher("/home.jsp").forward(request, response);


    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("/home.jsp");
    }
private void changePassWord(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {}
    private void showUserInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_info/user_infor.jsp");
        dispatcher.forward(request, response);
    }

    private void showPassForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_info/change-password.jsp");
        dispatcher.forward(request, response);
    }

    private void showCartForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_info/cart.jsp");
        dispatcher.forward(request, response);
    }
}
