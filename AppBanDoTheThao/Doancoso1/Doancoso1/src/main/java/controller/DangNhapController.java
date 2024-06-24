package controller;

import dao.UserDao;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import view.DangnhapView;
import model.User;
import view.DangkyView;
import view.TrangchuView;
import view.KhachHangView;

public class DangNhapController {
    private DangnhapView view;
    private UserDao userDao;
    
    private static User loggedInUser;

    public static User getLoggedInUser() {
        return loggedInUser;
    }
    
    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }
    
    public DangNhapController(DangnhapView view) {
        this.view = view;
        userDao = new UserDao();
        
        view.getTxtUsername().setText("long@gmail.com");
        view.getTxtPassword().setText("123");
        
        initEvent();
    }

    private void initEvent() {
        view.getBtnDangNhap().addActionListener(e -> xuLyDangNhap());
        view.getLblDangKy().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                moManHinhDangKy();
            }
        });
    }

    private void xuLyDangNhap() {
        String email = view.getTxtUsername().getText();
        String password = view.getTxtPassword().getText();

        User user = userDao.loginUser(email, password);

        // Kiểm tra kết quả đăng nhập
        if (user != null) {
            // Lưu thông tin người dùng đã đăng nhập
            DangNhapController.setLoggedInUser(user);

            // Đăng nhập thành công
            int roleId = user.getRoleID();
            if (roleId == 1) {
                TrangchuView adminView = new TrangchuView();
                adminView.setLocationRelativeTo(null);
                adminView.setVisible(true);
            } else if (roleId == 2) {
                KhachHangView homeView = new KhachHangView();
                homeView.setLocationRelativeTo(null);
                homeView.setVisible(true);
            } else {
                // Xử lý trường hợp roleId khác
                JOptionPane.showMessageDialog(view, "RoleId không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            // Đóng màn hình đăng nhập sau khi đăng nhập thành công
            view.dispose();
        } else {
            // Đăng nhập thất bại, hiển thị thông báo lỗi
            JOptionPane.showMessageDialog(view, "Email hoặc mật khẩu không đúng", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void moManHinhDangKy() {
        DangkyView dangKyView = new DangkyView();
        dangKyView.setLocationRelativeTo(null);
        dangKyView.setVisible(true);
        view.dispose();
    }
}
