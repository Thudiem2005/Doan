package controller;

import dao.UserDao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;
import view.DangNhapView;
import model.User;
import view.DangKyView;
import view.TrangChuAdminView;
import view.TrangChuView;

public class DangNhapController {
    private DangNhapView view;
    private UserDao userDao;
    
    private static User loggedInUser;

    public static User getLoggedInUser() {
        return loggedInUser;
    }
    
    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }
    
    public DangNhapController(DangNhapView view) {
        this.view = view;
        userDao = new UserDao();
        
//        view.getTxtUsername().setText("tuan@gmail.com");
//        view.getTxtPassword().setText("123");
        
        initEvent();
    }

    private void initEvent() {
        view.getBtnDangNhap().addActionListener(e -> xuLyDangNhap());
        view.getBtnDangKy().addActionListener(e -> moManHinhDangKy());
    }

//    private void xuLyDangNhap() {
//        String email = view.getTxtUsername().getText();
//        String password = view.getTxtPassword().getText();
//
//        User user = userDao.loginUser(email, password);
//
//        // Kiểm tra kết quả đăng nhập
//        if (user != null) {
//            // Lưu thông tin người dùng đã đăng nhập
//            DangNhapController.setLoggedInUser(user);
//
//            // Đăng nhập thành công
//            int roleId = user.getRoleID();
//            if (roleId == 1) {
//                TrangChuAdminView adminView = new TrangChuAdminView();
//                adminView.setLocationRelativeTo(null);
//                adminView.setVisible(true);
//            } else if (roleId == 2) {
//                TrangChuView homeView = new TrangChuView();
//                homeView.setLocationRelativeTo(null);
//                homeView.setVisible(true);
//            } else {
//                // Xử lý trường hợp roleId khác
//                JOptionPane.showMessageDialog(view, "RoleId không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
//            // Đóng màn hình đăng nhập sau khi đăng nhập thành công
//            view.dispose();
//        } else {
//            // Đăng nhập thất bại, hiển thị thông báo lỗi
//            JOptionPane.showMessageDialog(view, "Email hoặc mật khẩu không đúng", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
    
    private void xuLyDangNhap() {
    String email = view.getTxtUsername().getText();
    String password = view.getTxtPassword().getText();

    User user = userDao.loginUser(email, password);

    if (user != null) {
        DangNhapController.setLoggedInUser(user);

        // Thiết lập kết nối socket tới server
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Gửi thông tin đăng nhập tới server
            out.println(email);
            out.println(password);

            String response = in.readLine();
            if ("Login successful".equals(response)) {
                // Đăng nhập thành công
                int roleId = user.getRoleID();
                if (roleId == 1) {
                    TrangChuAdminView adminView = new TrangChuAdminView(socket);
                    adminView.setLocationRelativeTo(null);
                    adminView.setVisible(true);
                } else if (roleId == 2) {
                    TrangChuView homeView = new TrangChuView(user, socket);
                    homeView.setLocationRelativeTo(null);
                    homeView.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(view, "RoleId không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Email hoặc mật khẩu không đúng", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Không thể kết nối tới server", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(view, "Email hoặc mật khẩu không đúng", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void moManHinhDangKy() {
        DangKyView dangKyView = new DangKyView();
        dangKyView.setLocationRelativeTo(null);
        dangKyView.setVisible(true);
        view.dispose();
    }
}
