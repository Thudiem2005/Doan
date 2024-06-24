package controller;

import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.User;
import view.QuanlynguoidungView;
import view.TrangchuView;

public class QuanLyNguoiDungController {
    private QuanlynguoidungView view;
    private UserDao userDao;
    
    public QuanLyNguoiDungController(QuanlynguoidungView view) {
        this.view = view;
        userDao = new UserDao();
        loadData();
        initEvent();
    }

    private void loadData() {
        List<User> users = userDao.getAllUsers();
        DefaultTableModel model = (DefaultTableModel) view.getTblNguoiDung().getModel();
        model.setRowCount(0);
        for(User user : users) {
            model.addRow(new Object[] {user.getUserID(), user.getEmail(), user.getFullname(), user.getPhone(), user.getGender(), user.getAge(), user.getAddress(), user.getRoleID()});
        }
    }

    private void initEvent() {
        view.getBtnTroVe().addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e) {
                    TrangchuView view = new TrangchuView();
                    view.setLocationRelativeTo(null);
                    view.setVisible(true);
                    disposeCurrentView();
                }
        });
    }
    
    private void disposeCurrentView() {
        if (this.view != null) {
            this.view.dispose();
        }
    }
}