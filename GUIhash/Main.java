package GUIhash;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class Main {
    private static UserManager userManager = new UserManager();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Đăng ký / Đăng nhập");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel lblUser = new JLabel("Tên người dùng:");
        lblUser.setBounds(30, 30, 120, 25);
        JTextField txtUser = new JTextField();
        txtUser.setBounds(150, 30, 200, 25);

        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setBounds(30, 70, 120, 25);
        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(150, 70, 200, 25);

        JButton btnRegister = new JButton("Đăng ký");
        btnRegister.setBounds(30, 110, 100, 30);
        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setBounds(140, 110, 110, 30);

        JButton btnExport = new JButton("Xuất XML");
        btnExport.setBounds(30, 160, 100, 30);
        JButton btnImport = new JButton("Nhập XML");
        btnImport.setBounds(140, 160, 110, 30);

        frame.add(lblUser); frame.add(txtUser);
        frame.add(lblPass); frame.add(txtPass);
        frame.add(btnRegister); frame.add(btnLogin);
        frame.add(btnExport); frame.add(btnImport);

        btnRegister.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            if (userManager.register(user, pass)) {
                JOptionPane.showMessageDialog(frame, "Đăng ký thành công!");
            } else {
                JOptionPane.showMessageDialog(frame, "Người dùng đã tồn tại.");
            }
        });

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            if (userManager.login(user, pass)) {
                JOptionPane.showMessageDialog(frame, "Đăng nhập thành công!");
            } else {
                JOptionPane.showMessageDialog(frame, "Sai thông tin.");
            }
        });

        btnExport.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    userManager.exportToXML(chooser.getSelectedFile().getAbsolutePath());
                    JOptionPane.showMessageDialog(frame, "Xuất thành công!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Lỗi khi xuất: " + ex.getMessage());
                }
            }
        });

        btnImport.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    List<String> users = userManager.importFromXML(chooser.getSelectedFile().getAbsolutePath());
                    JOptionPane.showMessageDialog(frame, "Người dùng: " + String.join(", ", users));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "File không hợp lệ: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
