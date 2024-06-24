package view;

import controller.TrangChuAdminController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingUtilities;


public class TrangChuAdminView extends javax.swing.JFrame {

    private TrangChuAdminController controller;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Map<String, AdminChatDialog> chatDialogs;
    
    public TrangChuAdminView() {
        initComponents();
        controller = new TrangChuAdminController(this);
    }
    
    public TrangChuAdminView(Socket socket) {
        initComponents();
        controller = new TrangChuAdminController(this);
        this.socket = socket;
        chatDialogs = new HashMap<>();

         try {
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(this::receiveMessages).start();
    }
    
    private void receiveMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                String[] parts = message.split(": ", 2);
                if (parts.length == 2) {
                    String username = parts[0];
                    String msg = parts[1];

                    SwingUtilities.invokeLater(() -> {
                        AdminChatDialog chatDialog = chatDialogs.get(username);
                        if (chatDialog == null || !chatDialog.isVisible()) {
                            chatDialog = new AdminChatDialog(this, username, out);
                            chatDialogs.put(username, chatDialog);
                            chatDialog.appendMessage(username + ": " + msg);
                            chatDialog.setVisible(true);
                        } else {
                            chatDialog.appendMessage(username + ": " + msg);
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Các getter cho các thành phần của giao diện
    public javax.swing.JButton getBtnQLDanhMuc() {
        return btnQLDanhMuc;
    }
    public javax.swing.JButton getBtnQLSanPham() {
        return btnQLSanPham;
    }
    public javax.swing.JButton getBtnQLNguoiDung() {
        return btnQLNguoiDung;
    }
    public javax.swing.JButton getBtnThongKe() {
        return btnThongKe;
    }
    public javax.swing.JButton getBtnDangXuat() {
        return btnDangXuat;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnQLDanhMuc = new javax.swing.JButton();
        btnQLSanPham = new javax.swing.JButton();
        btnQLNguoiDung = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        btnQLDanhMuc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnQLDanhMuc.setText("Quản lý danh mục");

        btnQLSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnQLSanPham.setText("Quản lý sản phẩm");

        btnQLNguoiDung.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnQLNguoiDung.setText("Quản lý người dùng");

        btnThongKe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnThongKe.setText("Thống kê");

        btnDangXuat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDangXuat.setText("Đăng xuất");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(btnQLDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnQLSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(231, 231, 231)
                .addComponent(btnQLNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120)
                .addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(193, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(383, 383, 383))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQLSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQLDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQLNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(109, 109, 109)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrangChuAdminView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnQLDanhMuc;
    private javax.swing.JButton btnQLNguoiDung;
    private javax.swing.JButton btnQLSanPham;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
