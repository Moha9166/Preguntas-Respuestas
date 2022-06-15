package com.mohamed.gui;

import com.mohamed.utils.UserFiles;
import com.mohamed.user;
import com.mohamed.utils.encrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

public class register extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField userField;
    private JTextField nameField;
    private JTextField surnameField;
    private JPasswordField passField;
    private JLabel userLabel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel passLabel;
    private JLabel passIssueLabel;
    private JLabel titleLabel;
    private JButton mostrarButton;
    private JLabel passStength;
    private Boolean visible = false;

    public register() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        mostrarButton.addActionListener(showPass);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        passField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String pass = String.valueOf(passField.getPassword());
                int fontSize = 14;
                if (pass.length()<8){
                    passStength.setText("Débil");
                    passStength.setFont(new Font("Arial", Font.BOLD, fontSize));
                    passStength.setForeground(new Color(255, 0, 0));
                    pack();
                } else if(pass.length()<=12) {
                    passStength.setText("Intermedio");
                    passStength.setFont(new Font("Arial", Font.BOLD, fontSize));
                    passStength.setForeground(new Color(255, 115, 0));
                    pack();
                } else if (pass.length()>12) {
                    passStength.setText("Fuerte");
                    passStength.setFont(new Font("Arial", Font.BOLD, fontSize));
                    passStength.setForeground(new Color(31, 255, 0));
                    pack();
                }
            }
        });
    }
    //Start of Listeners
    ActionListener showPass = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (visible){
                passField.setEchoChar('•');
                visible = false;
            }else{
                passField.setEchoChar((char)0);
                visible = true;
            }
        }
    };
    //Start of methods
    private void onOK() {
        // add your code here
        String user = userField.getText();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String pass = String.valueOf(passField.getPassword());
        try {
            if (pass.length()<8){
                JOptionPane.showMessageDialog(contentPane, "La contraseña es demasiado corta", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (register(user, name, surname, pass)){
                JOptionPane.showMessageDialog(contentPane, "Registro completado con exito!!!","Registro", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        }catch (IOException | ClassNotFoundException | NoSuchAlgorithmException e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getClass());
        }
    }
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    public boolean register(String user, String name, String surname, String pass) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        boolean regSucc = false;
        if (user.isBlank() || name.isBlank() || surname.isBlank() || pass.isBlank()){
            passIssueLabel.setText("El formulario no esta completado correctamente!!!");
            passIssueLabel.setForeground(Color.RED);
            pack();
            return regSucc;
        }else{
            if (!isCreated(user)){
                String pass2 = new encrypt().securePass(pass);
                user tempUS = new user(user, name, surname, pass2);
                UserFiles uf = new UserFiles();
                LinkedList<user> userList = new LinkedList<>();
                userList.add(tempUS);
                uf.saveUsers(userList);
                return regSucc = true;
            }else {
                passIssueLabel.setText("El usuario ya se encuentra registrado!!!");
                passIssueLabel.setForeground(Color.RED);
                pack();
            }
        }
        return regSucc;
    };
    private boolean isCreated(String userName) throws IOException, ClassNotFoundException {
        UserFiles uf = new UserFiles();
        if (Files.exists(Path.of(uf.FILENAME))){
            LinkedList<user> usersList = new LinkedList<>();
            usersList = uf.loadUsers();
            for (user us:usersList) {
                if (us.getUser().equals(userName)){
                    return true;
                }
            }
        }
        uf = null;
        return false;
    };

}
